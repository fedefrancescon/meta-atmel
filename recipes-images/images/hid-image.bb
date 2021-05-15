DESCRIPTION = "HID image based on SAM9x60"

LICENSE = "MIT"

inherit core-image

EXTRA_IMAGE_FEATURES = " debug-tweaks ssh-server-openssh package-management "


IMAGE_INSTALL_append = " \
	binutils \
	gdbserver \
	strace \
	iproute2 \
	mtd-utils \
	mtd-utils-ubifs \
	devmem2 \
	i2c-tools \
	minicom \
	ethtool \
	dosfstools \
	e2fsprogs \
	usbutils \
	iw \
	wpa-supplicant \
	opkg \
	opkg-collateral \
	json-c \
	ppp \
	ppp-tools \
	lua \
	luaposix \
	luacjson \
	luasys \
	luasocket \
	lualfs \
	luaexpat \
	curl \
	libmodbus \
	openvpn \
	lighttpd \
	lighttpd-module-openssl \
	lighttpd-module-rewrite \
	lighttpd-module-redirect \
	lighttpd-module-alias \
	lighttpd-module-auth \
	lighttpd-module-authn-file \
	lighttpd-module-evasive \
	lighttpd-module-usertrack \
	lighttpd-module-setenv \
	lighttpd-module-cgi \
	lighttpd-module-compress \
	libmicrohttpd \
	ntpdate \
	procps \
	xz \
	wget \
	tar \
	sysstat \
	screen \
	msmtp \
	lsof \
	iptables \
	gzip \
	grep \
	bzip2 \
	bash \
	dos2unix \
	nano \
	traceroute \
	hostapd \
	iproute2-ss \
	iproute2-devlink \
	iproute2-genl \
	iproute2-ifstat \
	iproute2-lnstat \
	iproute2-nstat \
	iproute2-rtacct \
	iproute2-tc \
	iproute2-tipc \
	libxml2-utils \
	util-linux \
	avahi-utils \
	usb-modeswitch \
	htop \
	dhcp-client \
	bind-utils \
	netcat \
	iputils \
	coreutils \
	gd \
	fswebcam \
	cryptodev-linux \
	cryptodev-module \
"

# Keep separated HID hardware specific packages, so we can create x86-64 version as well
IMAGE_INSTALL_append_sam9x60 = " \
	mchp-wireless-firmware \
	kernel-module-pwrseq-wilc \
	kernel-module-wilc-sdio \
	kernel-module-cfg80211 \
	kernel-module-uvcvideo \
	kernel-module-ftdi-sio \
	kernel-module-pl2303 \
	kernel-module-asix \
	kernel-module-ax88179-178a \
	kernel-module-usbnet \
	kernel-module-qmi-wwan \
	kernel-module-sierra-net \
	kernel-module-huawei-cdc-ncm \
	kernel-module-cdc-ether \
"

# Add wpa supplicant static part to toolchain
TOOLCHAIN_TARGET_TASK += " wpa-supplicant-staticdev"

