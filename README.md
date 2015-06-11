# Japng: Flexible Java-only PNG processor with APNG and Android support.

## TL;DR

A Java-only library for reading and processing PNG and APNG files.

Pronounced "jah-ping" or "yah-ping".

Copyright (C) 2015 Andrew Ellerton


## Usage

To load a file into a bitmap where each pixel is a 32-bit integer representing
8-bits each for alpha, red, green and blue (i.e. suitable for Android bitmaps):

    import net.ellerton.japng.Png;
    import net.ellerton.japng.argb8888.Argb8888Bitmap;

    ...
    
    Argb8888Bitmap bitmap = Png.readArgb8888Bitmap(new FileInputStream("foo.png"));


## Motivation

Really this library shouldn't be necessarhy. It boggles my mind that at the time
of writing (2015) there is no off-the-shelf support for APNG in Java with no native
dependency.

Even with a JNI dependency, a patched version of libpng is required. The default
libpng does not support APNG for historical reasons that persist to this day.

Look at modern Android applications and you'll see nice little animations, not
all of which are runtime code generated. How are they done? I don't know if it is
closed source APNG reading or GIFs. GIF animation is prevalent and will never go
away but the lack of true alpha channels is unacceptable in a modern context.

The WebP format ought to be prevalent on Android (and elsewhere) but doesn't seem
to be. The PNG-approved MNG format ought to be everywhere but seems to be nowhere.
Mozilla, at least, continues to support the APNG format.

If you want a true alpha channel animating image format then your only real choice
at the moment is APNG. It's an imperfect format but pretty adequate for many needs.

## Use Cases

The Japng library isn't designed soley for APNG support. I've needed to do more
general PNG reading processing on other projects, and this library would have been
useful.

Some ideas:

- rendering animated PNGs from any input source on Android
- analysing the contents of any given PNG source
- processing PNG files with custom chunks, e.g. Fireworks PNG files.


## Features and non-features

Supports:

- Non-interlaced images
- All PNG colour types
- All PNG pixel formats
- All APNG chunks
- Images with multiple IDAT and fdAT chunks

Does *not* support:

- Does not support interlaced images (I didn't need it so didn't implement it)
- Does not support writing PNGs in any form.
- Does not check the chunk CRC ever (to be honest, I don't see what value is added
  for the use of CPU)


## PNG and APNG Specification Coverage

+----------------------+---------------------------------------------+
+ Key standard chunks
+----------------------+---------------------------------------------+
+ IHDR                 | Fully Supported
+ PLTE                 |
+ IDAT                 |
+ IEND                 |
+----------------------+---------------------------------------------+
+ Transparency, Gamma
+----------------------+---------------------------------------------+
+ gAMA                 | Parsed but no processing done
+ bKGD                 |
+----------------------+---------------------------------------------+
+ tRNS                 | Fully Supported
+----------------------+---------------------------------------------+
+ Animation
+----------------------+---------------------------------------------+
+ acTL                 | Fully Supported
+ fcTL                 |
+ fdAT                 |
+----------------------+---------------------------------------------+
+ Other
+----------------------+---------------------------------------------+
+ text and others      | Processor recognises and you can process
+                      | them yourself but this library does
+                      | nothing with them.
+----------------------+---------------------------------------------+



## Author and License

Written by A. Ellerton (https://github.com/aellerton/) and licensed under Apache 2.
See ``LICENSE.txt``.

## Dependencies

None. 

Originally the library used Google commons IO but I was able to remove it
and take only a small function from that library.

## Overview

The current implementation shows how to process plain PNG and APNG files into
bitmaps suitable for use on Android - but see also the separate library 
``japng_android`` for the remainder of the Android-specific processing.

The implementation is designed to be "reasonable memory efficient" - buffers
are allocated once and up-front when possible, not repeatedly during processing.

The implementation is designed t obe "reasonable flexible" - you can process
plain PNG files, animated PNG files, any other PNG file including Fireworks PNG
files (though there is nothing in this library specific to Fireworks or any
other custom chunk producer).

## History

0.5.0: Initial Release. 20150612

***
