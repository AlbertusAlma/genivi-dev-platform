FILESEXTRAPATHS_append := ":${THISDIR}/${PN}"

SRC_URI_append = "\
     file://weston.service \
     file://GDP_AM_Button.png \
     file://GDP_Background.png \
     file://GDP_Browser_Button.png \
     file://start_am-poc.sh \
     file://start_browser-poc.sh \
     file://browser_poc_hack.patch \
     "

# GDP specific weston.ini
SRC_URI_append = " \
    file://weston.ini \
    "

do_install_append() {
    install -m644 ${WORKDIR}/GDP*.png ${D}/usr/share/weston
    mkdir -p ${D}/${bindir}/
    cp ${WORKDIR}/start_am-poc.sh ${D}/${bindir}
    cp ${WORKDIR}/start_browser-poc.sh ${D}/${bindir}
    mkdir -p ${D}/lib/systemd/system/
    cp ${WORKDIR}/weston.service ${D}/lib/systemd/system/
    mkdir -p ${D}/${sysconfdir}/systemd/system/multi-user.target.wants/
    ln -sf /lib/systemd/system/weston.service ${D}/${sysconfdir}/systemd/system/multi-user.target.wants/weston.service

    WESTON_INI_CONFIG=${sysconfdir}/xdg/weston
    install -d ${D}${WESTON_INI_CONFIG}
    install -m 0644 ${WORKDIR}/weston.ini ${D}${WESTON_INI_CONFIG}/weston.ini
}

FILES_${PN} += " \
                /lib/systemd/system/weston.service \
                /etc/systemd/system/multi-user.target.wants/weston.service \
               "
