# Copyright (C) 2015-2016 GENIVI Alliance
# Released under the MIT license (see COPYING.MIT for the terms)

include genivi-dev-platform-hmi.inc

SUMMARY = "Simple HMI for the GENIVI Development Platform (GDP)"
DEPENDS = "dbus-c++ systemd wayland-ivi-extension"

SRC_URI_append ="\
    file://0001-gdp-hmi-launcher-replace-audiomanager-demo-name.patch \
    file://0002-TESTING-Sleep-for-500ms-before-processing-newly-disc.patch \
    file://gdp-hmi-controller.service \
    "

S = "${WORKDIR}/git"

PATCHTOOL = "git"

inherit autotools pkgconfig

do_install_append() {
	install -d ${D}${libdir}/systemd/user
	install -m 0444 ${WORKDIR}/gdp-hmi-controller.service \
	                ${D}${libdir}/systemd/user
}

FILES_${PN} += "\
    ${libdir}/systemd/user/* \
    "
