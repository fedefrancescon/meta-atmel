import SAMBA 3.2
import SAMBA.Connection.Serial 3.2
import SAMBA.Device.SAM9X60 3.2

// See inside "onConnectionOpened" below which files are used! They have to be present
// in the directory before starting. The names follow the ones from deployment (but as
// these contain version/git id, you may need to rename sometimes part of them)

SerialConnection {

	// For HID use SAM9X60 instead of SAM9X60EK for EVB
	device: SAM9X60 {
		config {
			nandflash {
				ioset: 1
				busWidth: 8
				header: 0xc1304805	// HID
				// header: 0xc1e04e07      // EVB
			}
		}
	}

	function initNand() {
		/* Placeholder: Nothing to do */
	}

	function checkDeviceID() {
		// read CHIPID_CIDR register
		var cidr = readu32(0xfffff240)
		if (cidr == 0x819b35a0) {
			throw new Error("Chip ID (CIDR = " + Utils.hex(cidr) + ")" +
				" tells that this is an Engineering Sample: not supported")
		} else {
			print("Chip ID: CIDR/EXID = " + Utils.hex(cidr) +
				"/" + Utils.hex(readu32(0xfffff244)))
		}
	}

	function getEraseSize(size) {
		/* get smallest erase block size supported by applet */
		var eraseSize
		for (var i = 0; i <= 32; i++) {
			eraseSize = 1 << i
			if ((applet.eraseSupport & eraseSize) !== 0)
				break;
		}
		eraseSize *= applet.pageSize

		/* round up file size to erase block size */
		return (size + eraseSize - 1) & ~(eraseSize - 1)
	}

	function eraseWrite(offset, filename, bootfile) {
		/* get file size */
		var file = File.open(filename, false)
		var size = file.size()
		file.close()

		applet.erase(offset, getEraseSize(size))
		applet.write(offset, filename, bootfile)
	}

	onConnectionOpened: {

		//
		// Make sure all the files above are present in running directory!
		//
		var atBootFileName = "at91bootstrap-sam9x60ek.bin"
		var ubootFileName = "u-boot-sam9x60ek-hid.bin"
		var ubootEnvFileName = "u-boot.env"
		var dtbFileName = "at91-sam9x60-hid-sam9x60-hid.dtb"
		var zImageFileName = "zImage-sam9x60-hid.bin"
		var rootfsFileName = "hid-image-sam9x60-hid.ubi"



		// check that chip revision is supported by Linux4SAM delivery
		checkDeviceID()

		// initialize Low-Level applet
		print("-I- === Initilize low level (system clocks) ===")
		initializeApplet("lowlevel")

		// intialize extram applet (needed for sam9) 
		// Not Needed for HID, but possibly useful for EVK
		//print("-I- === Initialize extram ===")
		//initializeApplet("extram")

		print("-I- === Initialize nandflash access ===")
		initializeApplet("nandflash")

		print("-I- === Load AT91Bootstrap ===")
		eraseWrite(0x00000000, atBootFileName, true)

		print("-I- === Load u-boot environment ===")
		// Erase redundant env to be in a clean and known state
		applet.erase(0x00100000, getEraseSize(0x20000))
		eraseWrite(0x00140000, ubootEnvFileName)

		print("-I- === Load u-boot ===")
		eraseWrite(0x00040000, ubootFileName)

		print("-I- === Load DTB Image ===")
		eraseWrite(0x00180000, dtbFileName)

		print("-I- === Load zImage ===")
		eraseWrite(0x001c0000, zImageFileName)

		print("-I- === Load root file-system image ===")
		// Erase all remaining NAND
		applet.erase(0x006c0000, applet.memorySize - 0x006c0000)
		// Write root filesystem partition
		applet.write(0x006c0000, rootfsFileName)

		print("-I- === Done. ===")
	}
}
