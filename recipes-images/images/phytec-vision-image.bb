require recipes-images/images/phytec-headless-image.bb

SUMMARY =  "This image is designed to show development of camera and \
            imaging applications with openCV and QT."

IMAGE_FEATURES += "splash ssh-server-openssh hwcodecs qtcreator-debug"

LICENSE = "MIT"

inherit populate_sdk_qt5

IMAGE_INSTALL += "\
    packagegroup-base \
    \
    packagegroup-gstreamer \
    \
    qt5-opengles2-test \
    phytec-qtdemo \
    opencv \
    gstreamer1.0-plugins-bad-opencv \
    yavta \
    \
    ${@bb.utils.contains('DISTRO_FEATURES', 'wayland', 'qtwayland qtwayland-plugins weston weston-init', '', d)} \
    ${@bb.utils.contains('DISTRO_FEATURES', 'x11 wayland', 'weston-xwayland', '', d)} \
"

IMAGE_INSTALL_remove_mx6ul = "\
    qt5-opengles2-test \
"
