package net.ellerton.japng;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import net.ellerton.japng.argb8888.Argb8888Bitmap;
import net.ellerton.japng.error.PngException;
import net.ellerton.japng.util.HexHelper;
import net.ellerton.japng.util.IoHelper;
import net.ellerton.japng.util.PngContainer;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

/**
 * Test reading RGB888 (24-bits per pixel)
 */
public class ReadTruecolourTest {
    // basn2c08 - 3x8 bits rgb color
    // Vertically: four 8-pixel bands in increase shades: Yellow, Magenta, Cyan, Black
    // Horizontally each initial colour increases by one each pixel.
//    public static final String BASN2C08_column_0_31_hex =
//        "FFFFFF  FFFFE0\n" +
//        "FFFFDF  FFFFC0\n" +
//        "FFFFBF  FFFFA0\n" +
//        "FFFF9F  FFFF80\n" +
//        "FFFF7F  FFFF60\n" +
//        "FFFF5F  FFFF40\n" +
//        "FFFF3F  FFFF20\n" +
//        "FFFF1F  FFFF00\n" +
//        "FFFFFF  FFE0FF\n" +
//        "FFDFFF  FFC0FF\n" +
//        "FFBFFF  FFA0FF\n" +
//        "FF9FFF  FF80FF\n" +
//        "FF7FFF  FF60FF\n" +
//        "FF5FFF  FF40FF\n" +
//        "FF3FFF  FF20FF\n" +
//        "FF1FFF  FF00FF\n" +
//        "FFFFFF  E0FFFF\n" +
//        "DFFFFF  C0FFFF\n" +
//        "BFFFFF  A0FFFF\n" +
//        "9FFFFF  80FFFF\n" +
//        "7FFFFF  60FFFF\n" +
//        "5FFFFF  40FFFF\n" +
//        "3FFFFF  20FFFF\n" +
//        "1FFFFF  00FFFF\n" +
//        "FFFFFF  E0E0E0\n" +
//        "DFDFDF  C0C0C0\n" +
//        "BFBFBF  A0A0A0\n" +
//        "9F9F9F  808080\n" +
//        "7F7F7F  606060\n" +
//        "5F5F5F  404040\n" +
//        "3F3F3F  202020\n" +
//        "1F1F1F  000000\n";

//    public static final String BASN2C08_column_0_hex = HexHelper.hexStringExtractColumn(BASN2C08_column_0_31_hex, 0);
//    public static final String BASN2C08_column_31_hex = HexHelper.hexStringExtractColumn(BASN2C08_column_0_31_hex, 1);

    @Test
    public void reads_truecolour24_to_container() throws IOException, PngException {

        try (InputStream is = getClass().getResourceAsStream("/pngsuite/basn2c08.png")) {
            PngContainer f = Png.readContainer(is);
            assertNotNull(f.header);
            assertEquals(32, f.header.width);
            assertEquals(32, f.header.height);
            assertEquals(8, f.header.bitDepth);
            assertEquals(PngColourType.PNG_TRUECOLOUR, f.header.colourType);
            assertEquals(2, f.header.colourType.code);
            assertEquals(0, f.header.compressionMethod);
            assertEquals(0, f.header.filterMethod);
            assertEquals(0, f.header.interlaceMethod);
            assertEquals(97, f.header.bytesPerRow);
            assertEquals(24, f.header.bitsPerPixel);
            assertEquals(3, f.header.filterOffset);
            assertTrue(f.header.isZipCompression());
            assertFalse(f.header.isInterlaced());

            assertNotNull(f.gamma);
            assertEquals(100000, f.gamma.imageGamma);
            assertNull(f.palette);
            //assertNull(f.transparency);
            //assertNull(f.background);
        }
    }

//    @Test
//    public void reads_truecolour24_to_argb8888_column_0() throws IOException, PngException {
//        try (InputStream is = getClass().getResourceAsStream("/pngsuite/basn2c08.png")) {
//            Argb8888Bitmap bitmap = Png.readArgb8888Bitmap(is);//, new Argb8888Processor());
//            assertEquals(32, bitmap.getWidth());
//            assertEquals(32, bitmap.getHeight());
//            assertEquals(BASN2C08_column_0_hex, HexHelper.columnToRgbHex(bitmap, 0));
//        }
//    }

    @Test
    public void reads_truecolour24_to_argb8888() throws IOException, PngException {
        try (InputStream is = getClass().getResourceAsStream("/pngsuite/basn2c08.png")) {
            Argb8888Bitmap bitmap = Png.readArgb8888Bitmap(is);//, new Argb8888Processor());
            assertEquals(32, bitmap.getWidth());
            assertEquals(32, bitmap.getHeight());
            //assertEquals(BASN2C08_column_31_hex, HexHelper.columnToRgbHex(bitmap, 31));
            assertEquals(readResourceText("/pngsuite/basn2c08.hexrgb"), HexHelper.columnsToRgbHex(bitmap));
        }
    }

    @Test
    public void reads_rgb16_multiple_IDAT_to_argb8888() throws IOException, PngException {
        try (InputStream is = getClass().getResourceAsStream("/pngsuite/oi2n2c16.png")) {
            Argb8888Bitmap bitmap = Png.readArgb8888Bitmap(is);//, new Argb8888Processor());
            assertEquals(32, bitmap.getWidth());
            assertEquals(32, bitmap.getHeight());
            //assertEquals(BASN2C08_column_31_hex, HexHelper.columnToRgbHex(bitmap, 31));
            assertEquals(readResourceText("/pngsuite/oi2n2c16.hexrgb"), HexHelper.columnsToRgbHex(bitmap));
        }
    }

//    oi2n0g16 - grayscale image with 2 idat-chunks
//    oi2n2c16 - color image with 2 idat-chunks


//    throw new IOException("TODO");
////            assertNotNull(f.decompressedFilteredImageData);
////            assertEquals(97 * 32, f.decompressedFilteredImageData.length); // 160
////
////            //PngPixelFormatter<String> pixels = PngPixelAsciiFormat.from(f.header);
////            //assertEquals(BASN0G01_string, f.processBitmap(pixels));

    String readResourceText(String resourceName) throws IOException {
        InputStream is = getClass().getResourceAsStream(resourceName);
        return new String(IoHelper.toByteArray(is));
    }

}
