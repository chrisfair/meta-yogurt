# Copyright (C) 2017 Stefan Riedmüller <s.riedmueller@phytec.de>
# Released under the MIT license (see COPYING.MIT for the terms)

DESCRIPTION = "Phytec Gstreamer examples"
HOMEPAGE = "http://www.phytec.de"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://COPYING.MIT;md5=f7ef7ac2ba19b104c3d5c03ecc22eb12"

SECTION = "multimedia"

PR = "r0"

# Archive created via
#   $ unzip gstreamer_examples.zip
#   $ mv gstreamer_examples phytec-gstreamer-examples-imx6ul-1.0
#   $ rm phytec-gstreamer-examples-imx6ul-1.0/tools/i2c
#   $ cp -r phytec-gstreamer-examples-2.4.3/tools/ phytec-gstreamer-examples-imx6ul-1.0/
#   $ cp phytec-gstreamer-examples-2.4.3/COPYING.MIT  phytec-gstreamer-examples-imx6ul-1.0/
#   $ find phytec-gstreamer-examples-imx6ul-1.0/ -exec "touch" "{}" ";"
#   $ find phytec-gstreamer-examples-imx6ul-1.0/ -name "*.sh" -exec "chmod" "+x" "{}" ";"
#   $ tar --owner=root --group=root -czf phytec-gstreamer-examples-imx6ul-1.0.tar.gz \
#        phytec-gstreamer-examples-imx6ul-1.0/
SRC_URI = "file://${PN}-${PV}.tar.gz"

GSTREAMER_EXAMPLES_DIR = "${datadir}/phytec-gstreamer-examples"

do_compile() {
	${CC} ${CFLAGS} ${LDFLAGS} -o tools/i2c tools/i2c.c
}

do_install() {
	DESTDIR="${D}${GSTREAMER_EXAMPLES_DIR}"

	for directory in `find -type d`; do
		if [ ${directory} != "./patches" ]; then
			install -d ${DESTDIR}/${directory}
		fi
	done

	for text in `find -name '*.txt'`; do
		install -m 0644 ${text} ${DESTDIR}/${text}
	done

	for scripts in `find -name '*.sh'`; do
		install -m 0755 ${scripts} ${DESTDIR}/${scripts}
	done

	install -m 0755 tools/i2c ${DESTDIR}/tools

	# Create link in home folder for old documentation
	install -d ${D}/home/root
	ln -s ${GSTREAMER_EXAMPLES_DIR} ${D}/home/root/gstreamer_examples
}

FILES_${PN} += " \
    /home/root/ \
    ${GSTREAMER_EXAMPLES_DIR} \
"
FILES_${PN}-dbg = " \
    ${prefix}/src \
    ${GSTREAMER_EXAMPLES_DIR}/tools/.debug \
"
RDEPENDS_${PN} += " \
	gst-plugin-i2c \
	media-ctl \
	v4l-utils \
	gstreamer1.0 \
	gstreamer1.0-plugins-good-multifile \
	gstreamer1.0-plugins-good-video4linux2 \
	gstreamer1.0-plugins-bad-fbdevsink \
	gstreamer1.0-plugins-bad-bayer \
	gstreamer1.0-plugins-good-jpeg \
	gstreamer1.0-plugin-bayer2rgb-neon \
"