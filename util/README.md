# japng Utilities

During development it is useful to have utilities to read and process PNG
files. These utilities intentionally use libpng via Python+Pillow.

## Setup

You'll need python installed. I recommend setting up a ``virtualenv``. Then:

    pip install requirements.txt

## Run

Dump basic chunk information for a file:

    $ cd ../api/src/test/resources/mozilla/
    
    $ ../../../../../util/pnginfo demo-1.png
    demo-1.png: IHDR chunk: width=192 height=110 bit_depth=8 type=Trucolour with alpha (6) 'Each pixel is an R, G, B triple followed by an alpha sample' interlace=0
    demo-1.png: acTL chunk: 1 frames, 0 plays
    demo-1.png: IDAT chunk (5620 bytes)
    demo-1.png: fcTL chunk: seq=0  width=192  height=110  xoff=0  yoff=0  delay_num=500  delay_den=1000  dispose=1 blend=0
    demo-1.png: fdAT chunk (5499 bytes)
    demo-1.png: IEND chunk (0 bytes)


Dump a given PNG file to hex RGB text:

    $ ../../../../../util/pngdump --hexrgb basn0g01.png
    FFFFFF FFFFFF FFFFFF FFFFFF FFFFFF FFFFFF FFFFFF FFFFFF FFFFFF ...

To dump has hex ARGB:

    $ ../../../../../util/pngdump --ahexrgb basn0g01.png
    FFFFFFFF FFFFFFFF FFFFFFFF FFFFFFFF FFFFFFFF FFFFFFFF FFFFFFFF FFFFFFFF FFFFFFFF ...


***