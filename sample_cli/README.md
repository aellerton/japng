# japng command line sample

A simple utility to read given PNG files and display info to the console.

Example:

    $ gradle makeDist
    $ ./build/install/sample_cli/bin/sample_cli -h   # TODO: rename this to pnginfo
    Usage: <main class> [options] <filename> ....

    $ ./build/install/sample_cli/bin/sample_cli loading_16.png
    loading_16.png: PNG type 6 (Truecolour with alpha)
    loading_16.png: width: 16
    loading_16.png: height: 16
    loading_16.png: interlaced: no
    loading_16.png: animated? yes
    loading_16.png: animation frames: 12
    loading_16.png: animation repeats: infinite
    ...



