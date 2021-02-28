SUMMARY = "Silicon Laboratories WFx Wi-Fi secure embedded firmware images"
SECTION = "wfx-linux"
LICENSE = "SILABS_FW"

LIC_FILES_CHKSUM = "file://LICENSE.md;md5=222d961af3bbe7a219e279b6dc4d39cd"

SRCREV = "fa2f0f61f8047d81183a65bf9cc9da9215be235c"
SRC_URI = "git://github.com/SiliconLabs/wfx-firmware.git;protocol=https"
S = "${WORKDIR}/git"

inherit allarch

do_install() {
	install -d ${D}${nonarch_base_libdir}/firmware/
	cp -r ${S}/*.sec ${D}${nonarch_base_libdir}/firmware/

}

FILES_${PN} += " \
	${nonarch_base_libdir}/firmware/*.sec \
	"
