# Japng: Flexible Java-only PNG reader with APNG and Android support.

## NOTE: NAME WILL BE CHANGING

The name of this project will change because, as Issue #1 notes, an
[older project](https://www.reto-hoehener.ch/japng/) of the same name exists.
Although that project is closed-source and has a different objective, having
two somewhat similar projects with the same name will be confusing.


## TL;DR

A Java-only library for reading and processing PNG and APNG files.

Named for "Java Animated PNG". Pronounced "jah-ping" or "yah-ping".

Copyright (C) 2015 Andrew Ellerton


## Usage

To load a file into a bitmap where each pixel is a 32-bit integer representing
8-bits each for alpha, red, green and blue (i.e. suitable for Android bitmaps):

    import net.ellerton.japng.Png;
    import net.ellerton.japng.argb8888.Argb8888Bitmap;

    ...
    
    Argb8888Bitmap bitmap = Png.readArgb8888Bitmap(new FileInputStream("foo.png"));

For Android support, see the [japng_android](https://github.com/aellerton/japng_android) library that builds on ``japng``.


## Note

The library is not yet published to any Maven/Ivy repo. To use it, for now:

    $ cd japng
    $ gradle jar
    $ cp api/build/libs/japng-0.5.jar /wherever/your/app/lib


## Motivation

Really this library shouldn't be necessary. It boggles my mind that at the time
of writing (2015) there is no off-the-shelf support for APNG in Java with no native
dependency.

Even with a JNI dependency, a patched version of ``libpng`` is required. The default
libpng does not support APNG for historical reasons that persist to this day and won't change.

Modern Android applications like Gmail and the default clock/stopwatch app (at least
on my Nexus) feature nice little animations, not all of which are runtime code 
generated. How are they done? I don't know if those apps use a closed-source APNG
reader, GIFs or perhaps android ``AnimationDrawable`` using XML + image frames in resources.
Looking inside the manifest of some chat apps it looks like they used a patched libpng,
but I don't know for sure.

GIF animation is prevalent and will never go away but the lack of true alpha 
blending is not acceptable if you want to display small, non-video capture animations
in modern applications.

The WebP format ought to be prevalent on Android (and elsewhere) but doesn't seem
to be. The PNG-approved MNG format never caught on. 
(Mozilla and recent Safari versions)[http://caniuse.com/#feat=apng],
at least, can display the APNG format, and there is an extension for Chrome.

If you want a true alpha channel animating image format then your only real choice
at the moment is APNG. It's an imperfect format but pretty adequate for many needs.

There are a couple Java-only implementations for reading PNG files, like 
[pngj](https://github.com/leonbloy/pngj) and [javapng](https://github.com/srbala/javapng?files=1).
The first is pretty good and recent check-ins show APNG support but I found the
examples limited and wasn't fond of the pixel-processing code for general purposes.
The latter is *ancient*, last updated 10 years ago.

There is also a 2009 closed-source project called ["japng"](https://www.reto-hoehener.ch/japng/) 
that seems only to be about writing (not reading) APNG files.

## Use Cases

The Japng library isn't designed solely for APNG support. I've needed to do more
general PNG reading processing on other projects, and this library would have been
useful.

Some ideas:

- rendering animated PNGs from any input source on Android [(Done! See ``japng_android``)](https://github.com/aellerton/japng_android)
- analysing the contents of any given PNG source [(Example command line client included)](https://github.com/aellerton/japng/blob/master/sample_cli/src/main/java/net/ellerton/japng/PngInfo.java)
- processing PNG files with custom chunks, e.g. Fireworks PNG files.


## Features and non-features

Supports:

- Non-interlaced images only.
- All PNG colour types
- All PNG pixel formats
- All APNG chunks
- Images with multiple IDAT and fdAT chunks

Does *not* support:

- Does not support *interlaced* images (I didn't need it so didn't implement it)
- Does not support *writing* PNGs in any form.
- Does not check the chunk CRC ever (to be honest, I don't see what value is added
  for the use of CPU)

The library is designed to be extensible to process chunks and pixels how you like.


## PNG and APNG Specification Coverage

| Key standard chunks | Status          |
| ------------------- | --------------- |
| IHDR                | Fully Supported |
| PLTE                |                 |
| IDAT                |                 |
| IEND                |                 |
|---------------------|--------------------------------------------|
| Transparency, Gamma |                                            |
|---------------------|---------------------------------------------|
| gAMA                | Parsed but no processing done |
| bKGD                | |
|---------------------|---------------------------------------------|
| tRNS                | Fully Supported |
|---------------------|---------------------------------------------|
| Animation | |
|---------------------|---------------------------------------------|
| acTL                | Fully Supported |
| fcTL                | |
| fdAT                | |
|---------------------|---------------------------------------------|
| Other | |
|---------------------|---------------------------------------------|
| text and others     | Processor recognises and you can process |
|                     | them yourself but this library does |
|                     | nothing with them. |
|---------------------|---------------------------------------------|



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

v0.5.0: 20150611
- Initial Release. 

v0.5.1: 20150613
- Add PngStreamSource as alternative to PngAtOnceSource.
- Not sure about keeping the at-once source.
- Added first cut (incomplete!) of PngHex command-line program.
- Added the missing 16-bit RGBA decoder.
       

***
