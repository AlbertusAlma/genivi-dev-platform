DESCRIPTION = "SOTA Reference Implementation project - Client"
HOMEPAGE = "https://github.com/advancedtelematic/rvi_sota_client"
LICENSE = "MPL-2.0"

inherit cargo systemd

SRC_URI = "git://github.com/advancedtelematic/rvi_sota_client.git;protocol=https \
           file://rvi-sota-client.service \
           file://0001-run.sh-Listen-anywhere-if-SOTA_CLIENT_ADDR-is-not-se.patch \
          "
SRCREV="57e803803691acab8e443f1631767edaec9da10f"
LIC_FILES_CHKSUM="file://LICENSE;md5=65d26fcc2f35ea6a181ac777e42db1ea"

S = "${WORKDIR}/git"

BBCLASSEXTEND = "native"

DEPENDS += "dbus"
RDEPENDS_${PN} += "dbus-lib libcrypto libssl"

SYSTEMD_SERVICE_${PN} = "rvi-sota-client.service"

do_install_append() {
 install -m 0755 -p -D ${S}/docker/client.toml ${D}/var/sota/client.toml
 install -m 0755 -p -D ${S}/docker/run.sh ${D}${prefix}/bin/run.sh
 if ${@bb.utils.contains('DISTRO_FEATURES', 'systemd', 'true', 'false', d)}; then
  install -p -D ${WORKDIR}/rvi-sota-client.service ${D}${systemd_unitdir}/system/rvi-sota-client.service
 fi
}

FILES_${PN} += "/var/sota/"
FILES_${PN} += "/var/sota/client.toml"
FILES_${PN} += "${prefix}/bin/"
FILES_${PN} += "${prefix}/bin/run.sh"

## dbus-rs
SRC_URI += "\
        git://github.com/diwic/dbus-rs.git;protocol=https;name=dbus-rs;destsuffix=dbus-rs \
        file://dbus-rs/0001-Cast-correctly-c_char-raw-pointers-for-ARM.patch;patchdir=../dbus-rs \
"

# 0.1.2
SRCREV_dbus-rs = "c2c4c98adcf9949992ac5b0050bf17afe10868c9"

SRCREV_FORMAT .= "_dbus-rs"
EXTRA_OECARGO_PATHS += "${WORKDIR}/dbus-rs"
