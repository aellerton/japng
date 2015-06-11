package net.ellerton.japng;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import net.ellerton.japng.util.IoHelper;
import net.ellerton.japng.argb8888.Argb8888Bitmap;
import net.ellerton.japng.error.PngException;
import net.ellerton.japng.util.HexHelper;
import net.ellerton.japng.util.PngContainer;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;


/**
 * Test reading RGBA8888 images (32-bits per pixel)
 */
public class ReadTruecolourAlphaTest {

    // basn6a08 - 3x8 bits rgb color + 8 bit alpha-channel,
    //   From top to bottom each row is a shade of the rainbow, from red down to blue.
    //   Horizontally, alpha goes from 0.0 (transparent) to 1.0 (opaque).
    //   If the alpha component is stripped, the colour on each row is the same.
//    public static final String BASN6A08_column_0_31_hex =
//            "FF0008  FF0008\n" +
//            "FF1F08  FF1F08\n" +
//            "FF3F08  FF3F08\n" +
//            "FF5F08  FF5F08\n" +
//            "FF7F07  FF7F07\n" +
//            "FF9F07  FF9F07\n" +
//            "FFBF07  FFBF07\n" +
//            "FFDF07  FFDF07\n" +
//            "FFFF06  FFFF06\n" +
//            "E0FF06  E0FF06\n" +
//            "C0FF06  C0FF06\n" +
//            "A0FF05  A0FF05\n" +
//            "80FF05  80FF05\n" +
//            "60FF05  60FF05\n" +
//            "40FF05  40FF05\n" +
//            "20FF04  20FF04\n" +
//            "04FF00  04FF00\n" +
//            "04FF1F  04FF1F\n" +
//            "03FF3F  03FF3F\n" +
//            "03FF5F  03FF5F\n" +
//            "03FF7F  03FF7F\n" +
//            "03FF9F  03FF9F\n" +
//            "02FFBF  02FFBF\n" +
//            "02FFDF  02FFDF\n" +
//            "02FFFF  02FFFF\n" +
//            "01E0FF  01E0FF\n" +
//            "01C0FF  01C0FF\n" +
//            "01A0FF  01A0FF\n" +
//            "0180FF  0180FF\n" +
//            "0060FF  0060FF\n" +
//            "0040FF  0040FF\n" +
//            "0020FF  0020FF\n";
//
//    public static final String BASN6A08_column_0_3_31_hex_rgba =
//            "00FF0008  18FF0008  FFFF0008\n" +
//            "00FF1F08  18FF1F08  FFFF1F08\n" +
//            "00FF3F08  18FF3F08  FFFF3F08\n" +
//            "00FF5F08  18FF5F08  FFFF5F08\n" +
//            "00FF7F07  18FF7F07  FFFF7F07\n" +
//            "00FF9F07  18FF9F07  FFFF9F07\n" +
//            "00FFBF07  18FFBF07  FFFFBF07\n" +
//            "00FFDF07  18FFDF07  FFFFDF07\n" +
//            "00FFFF06  18FFFF06  FFFFFF06\n" +
//            "00E0FF06  18E0FF06  FFE0FF06\n" +
//            "00C0FF06  18C0FF06  FFC0FF06\n" +
//            "00A0FF05  18A0FF05  FFA0FF05\n" +
//            "0080FF05  1880FF05  FF80FF05\n" +
//            "0060FF05  1860FF05  FF60FF05\n" +
//            "0040FF05  1840FF05  FF40FF05\n" +
//            "0020FF04  1820FF04  FF20FF04\n" +
//            "0004FF00  1804FF00  FF04FF00\n" +
//            "0004FF1F  1804FF1F  FF04FF1F\n" +
//            "0003FF3F  1803FF3F  FF03FF3F\n" +
//            "0003FF5F  1803FF5F  FF03FF5F\n" +
//            "0003FF7F  1803FF7F  FF03FF7F\n" +
//            "0003FF9F  1803FF9F  FF03FF9F\n" +
//            "0002FFBF  1802FFBF  FF02FFBF\n" +
//            "0002FFDF  1802FFDF  FF02FFDF\n" +
//            "0002FFFF  1802FFFF  FF02FFFF\n" +
//            "0001E0FF  1801E0FF  FF01E0FF\n" +
//            "0001C0FF  1801C0FF  FF01C0FF\n" +
//            "0001A0FF  1801A0FF  FF01A0FF\n" +
//            "000180FF  180180FF  FF0180FF\n" +
//            "000060FF  180060FF  FF0060FF\n" +
//            "000040FF  180040FF  FF0040FF\n" +
//            "000020FF  180020FF  FF0020FF\n";
//
//    public static final String BASN6A08_column_0_hex = HexHelper.hexStringExtractColumn(BASN6A08_column_0_31_hex, 0);
//    public static final String BASN6A08_column_31_hex = HexHelper.hexStringExtractColumn(BASN6A08_column_0_31_hex, 1);
//
//    public static final String BASN6A08_column_0_hex_rgba = HexHelper.hexStringExtractColumn(BASN6A08_column_0_3_31_hex_rgba, 0);
//    public static final String BASN6A08_column_3_hex_rgba = HexHelper.hexStringExtractColumn(BASN6A08_column_0_3_31_hex_rgba, 1);
//    public static final String BASN6A08_column_31_hex_rgba = HexHelper.hexStringExtractColumn(BASN6A08_column_0_3_31_hex_rgba, 2);

    @Test
    public void readsContainer_truecolour32() throws IOException, PngException {

        try (InputStream is = getClass().getResourceAsStream("/pngsuite/basn6a08.png")) {
            PngContainer f = Png.readContainer(is);
            assertNotNull(f.header);
            assertEquals(32, f.header.width);
            assertEquals(32, f.header.height);
            assertEquals(8, f.header.bitDepth);
            assertEquals(PngColourType.PNG_TRUECOLOUR_WITH_ALPHA, f.header.colourType);
            assertEquals(6, f.header.colourType.code);
            assertEquals(0, f.header.compressionMethod);
            assertEquals(0, f.header.filterMethod);
            assertEquals(0, f.header.interlaceMethod);
            assertEquals(129, f.header.bytesPerRow);
            assertEquals(32, f.header.bitsPerPixel);
            assertEquals(4, f.header.filterOffset);
            assertTrue(f.header.isZipCompression());
            assertFalse(f.header.isInterlaced());

            assertNotNull(f.gamma);
            assertEquals(100000, f.gamma.imageGamma);

//            assertNotNull(f.decompressedFilteredImageData);
//            assertEquals(129 * 32, f.decompressedFilteredImageData.length); // 160

////            PngPixelFormatter<String> pixels = PngPixelAsciiFormat.from(f.header);
////            assertEquals(BASN0G01_string, f.processBitmap(pixels));
        }
    }

//    @Test
//    public void reads_truecolour24a_to_argb8888_column_0() throws IOException, PngException {
//        try (InputStream is = getClass().getResourceAsStream("/pngsuite/basn6a08.png")) {
//            Argb8888Bitmap bitmap = Png.readArgb8888Bitmap(is);//, new Argb8888Processor());
//            assertEquals(32, bitmap.getWidth());
//            assertEquals(32, bitmap.getHeight());
//            assertEquals(BASN6A08_column_0_hex, HexHelper.columnToRgbHex(bitmap, 0));
//            assertEquals(BASN6A08_column_0_hex_rgba, HexHelper.columnToArgbHex(bitmap, 0));
//        }
//    }

//    @Test
//    public void reads_truecolour24a_to_argb8888_column_3() throws IOException, PngException {
//        try (InputStream is = getClass().getResourceAsStream("/pngsuite/basn6a08.png")) {
//            Argb8888Bitmap bitmap = Png.readArgb8888Bitmap(is);//, new Argb8888Processor());
//            assertEquals(32, bitmap.getWidth());
//            assertEquals(32, bitmap.getHeight());
//            assertEquals(BASN6A08_column_0_hex, HexHelper.columnToRgbHex(bitmap, 3));
//            assertEquals(BASN6A08_column_3_hex_rgba, HexHelper.columnToArgbHex(bitmap, 3));
//            //assertEquals(BASN6A08_column_0_hex_rgba, HexHelper.columnsToArgbHex(bitmap, 0, 31));
//        }
//    }

    @Test
    public void reads_grey8a_to_argb8888() throws IOException, PngException {
        try (InputStream is = getClass().getResourceAsStream("/pngsuite/basn4a08.png")) {
            Argb8888Bitmap bitmap = Png.readArgb8888Bitmap(is);//, new Argb8888Processor());
            assertEquals(32, bitmap.getWidth());
            assertEquals(32, bitmap.getHeight());
//            assertEquals(BASN6A08_column_31_hex, HexHelper.columnToRgbHex(bitmap, 31));
//            assertEquals(BASN6A08_column_31_hex_rgba, HexHelper.columnToArgbHex(bitmap, 31));
            assertEquals(readResourceText("/pngsuite/basn4a08.argb"), HexHelper.columnsToArgbHex(bitmap));

        }
    }

    @Test
    public void reads_truecolour24a_to_argb8888() throws IOException, PngException {
        try (InputStream is = getClass().getResourceAsStream("/pngsuite/basn6a08.png")) {
            Argb8888Bitmap bitmap = Png.readArgb8888Bitmap(is);//, new Argb8888Processor());
            assertEquals(32, bitmap.getWidth());
            assertEquals(32, bitmap.getHeight());
//            assertEquals(BASN6A08_column_31_hex, HexHelper.columnToRgbHex(bitmap, 31));
//            assertEquals(BASN6A08_column_31_hex_rgba, HexHelper.columnToArgbHex(bitmap, 31));
            assertEquals(readResourceText("/pngsuite/basn6a08.argb"), HexHelper.columnsToArgbHex(bitmap));

        }
    }

    //      oi4n2c16 - color image with 4 unequal sized idat-chunks

//    @Test
//    public void reads_4_unequal_idat_chunks_to_argb8888() throws IOException, PngException {
//        try (InputStream is = getClass().getResourceAsStream("/pngsuite/oi4n2c16.png")) {
//            Argb8888Bitmap bitmap = Png.readArgb8888Bitmap(is);//, new Argb8888Processor());
//            assertEquals(32, bitmap.getWidth());
//            assertEquals(32, bitmap.getHeight());
////            assertEquals(BASN6A08_column_31_hex, HexHelper.columnToRgbHex(bitmap, 31));
////            assertEquals(BASN6A08_column_31_hex_rgba, HexHelper.columnToArgbHex(bitmap, 31));
//            assertEquals(readResourceText("/pngsuite/oi4n2c16.argb"), HexHelper.columnsToArgbHex(bitmap));
//
//        }
//    }

    //tbbn3p08
    @Test
    public void reads_transparent_palette8_blue_chunk_to_argb8888() throws IOException, PngException {
        try (InputStream is = getClass().getResourceAsStream("/pngsuite/tbbn3p08.png")) {
            Argb8888Bitmap bitmap = Png.readArgb8888Bitmap(is);//, new Argb8888Processor());
            assertEquals(32, bitmap.getWidth());
            assertEquals(32, bitmap.getHeight());
//            assertEquals(BASN6A08_column_31_hex, HexHelper.columnToRgbHex(bitmap, 31));
//            assertEquals(BASN6A08_column_31_hex_rgba, HexHelper.columnToArgbHex(bitmap, 31));
            assertEquals(readResourceText("/pngsuite/tbbn3p08.argb"), HexHelper.columnsToArgbHex(bitmap));

        }
    }


    // basn6a16 - 3x16 bits rgb color + 16 bit alpha-channel


    // basn6a08 - 3x8 bits rgb color + 8 bit alpha-channel
    @Test
    public void reads_rgba16_to_argb8888() throws IOException, PngException {
        try (InputStream is = getClass().getResourceAsStream("/pngsuite/basn6a08.png")) {
            Argb8888Bitmap bitmap = Png.readArgb8888Bitmap(is);//, new Argb8888Processor());
            assertEquals(32, bitmap.getWidth());
            assertEquals(32, bitmap.getHeight());
            assertEquals(readResourceText("/pngsuite/basn6a08.argb"), HexHelper.columnsToArgbHex(bitmap));
        }
    }

    // basn4a16 - 16 bit grayscale + 16 bit alpha-channel
    @Test
    public void reads_grey16_to_argb8888() throws IOException, PngException {
        try (InputStream is = getClass().getResourceAsStream("/pngsuite/basn4a16.png")) {
            Argb8888Bitmap bitmap = Png.readArgb8888Bitmap(is);//, new Argb8888Processor());
            assertEquals(32, bitmap.getWidth());
            assertEquals(32, bitmap.getHeight());
            assertEquals(readResourceText("/pngsuite/basn4a16.argb"), HexHelper.columnsToArgbHex(bitmap));
        }
    }

    @Test
    public void reads_rgba16_with_multiple_idat() throws IOException, PngException {
        try (InputStream is = getClass().getResourceAsStream("/pngsuite/oi2n2c16.png")) {
            Argb8888Bitmap bitmap = Png.readArgb8888Bitmap(is);//, new Argb8888Processor());
            assertEquals(32, bitmap.getWidth());
            assertEquals(32, bitmap.getHeight());
            assertEquals(readResourceText("/pngsuite/oi2n2c16.argb"), HexHelper.columnsToArgbHex(bitmap));
        }
    }
    //xx_TODO : read a animated PNG file and prove that it ignores the later frames, only takes main frame

    String readResourceText(String resourceName) throws IOException {
        InputStream is = getClass().getResourceAsStream(resourceName);
        return new String(IoHelper.toByteArray(is));
    }

}
