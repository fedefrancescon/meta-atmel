

do_install_append () {
    cat >> ${D}${sysconfdir}/fstab <<EOF

# HID data partition on /mnt
/dev/ubi1_0             /mnt            ubifs   defaults 0 0

EOF
}
