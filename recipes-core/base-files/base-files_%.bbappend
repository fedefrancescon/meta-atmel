

do_install_append () {
    cat >> ${D}${sysconfdir}/fstab <<EOF

# HID data partition on /mnt
/dev/ubi1_0             /mnt            ubifs   x-systemd.device-timeout=5 0 0

EOF
}
