SUMMARY = "Silicon Laboratories WFx Wi-Fi linux driver"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://CHANGES;md5=8aa6721444f0a383a565da5525ac5d6e"

inherit module


SRCREV = "e2f15feafe1d66f3e89510af158ab8ccbb9d838f"
SRC_URI = "git://github.com/SiliconLabs/wfx-linux-driver.git;protocol=https"

S = "${WORKDIR}/git"

do_compile() {
	export CFLAGS="$CFLAGS -DDEBUG"
	make KDIR=${STAGING_KERNEL_DIR}
}

do_install() {
	make install KDIR=${STAGING_KERNEL_DIR}  INSTALL_MOD_PATH="${D}"
}


# The inherit of module.bbclass will automatically name module packages with
# "kernel-module-" prefix as required by the oe-core build environment.
RPROVIDES_${PN} += "kernel-module-wfx"
