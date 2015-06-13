package net.ellerton.japng;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import net.ellerton.japng.argb8888.Argb8888Bitmap;
import net.ellerton.japng.error.PngException;
import net.ellerton.japng.argb8888.HexHelper;
import net.ellerton.japng.util.IoHelper;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

/**
 * One of the most critical test suites during development was this, which ensures that
 * each of the filter un-apply functions works as expected.
 */
public class PngFilterTest {

    @Test
    public void filters_grey_image_with_no_filter() throws IOException, PngException {
        try (InputStream is = getClass().getResourceAsStream("/pngsuite/f00n0g08.png")) {
            Argb8888Bitmap bitmap = Png.readArgb8888Bitmap(is);//, new Argb8888Processor());
            assertEquals(32, bitmap.getWidth());
            assertEquals(32, bitmap.getHeight());
            assertEquals(readResourceText("/pngsuite/f00n0g08.hexrgb"), HexHelper.columnsToRgbHex(bitmap));
        }
    }

    // f00n0g08 - color, no interlacing, filter-type 0
    @Test
    public void filters_colour_image_with_no_filter() throws IOException, PngException {
        try (InputStream is = getClass().getResourceAsStream("/pngsuite/f00n2c08.png")) {
            Argb8888Bitmap bitmap = Png.readArgb8888Bitmap(is);//, new Argb8888Processor());
            assertEquals(32, bitmap.getWidth());
            assertEquals(32, bitmap.getHeight());
            assertEquals(readResourceText("/pngsuite/f00n2c08.hexrgb"), HexHelper.columnsToRgbHex(bitmap));
        }
    }

    // f01n2c08 - color, no interlacing, filter-type 1
    @Test
    public void filters_colour_image_with_sub_filter() throws IOException, PngException {
        try (InputStream is = getClass().getResourceAsStream("/pngsuite/f01n2c08.png")) {
            Argb8888Bitmap bitmap = Png.readArgb8888Bitmap(is);//, new Argb8888Processor());
            assertEquals(32, bitmap.getWidth());
            assertEquals(32, bitmap.getHeight());
            assertEquals(readResourceText("/pngsuite/f01n2c08.hexrgb"), HexHelper.columnsToRgbHex(bitmap));
        }
    }

    //
    @Test //(expected = PngFeatureException.class)
    public void filters_grey_image_with_sub_filter() throws IOException, PngException {
        try (InputStream is = getClass().getResourceAsStream("/pngsuite/f01n2c08.png")) {
            Argb8888Bitmap bitmap = Png.readArgb8888Bitmap(is);//, new Argb8888Processor());
            assertEquals(32, bitmap.getWidth());
            assertEquals(32, bitmap.getHeight());
            assertEquals(readResourceText("/pngsuite/f01n2c08.hexrgb"), HexHelper.columnsToRgbHex(bitmap));
        }
    }

    @Test
    public void filters_colour_image_with_up_filter() throws IOException, PngException {
        try (InputStream is = getClass().getResourceAsStream("/pngsuite/f02n2c08.png")) {
            Argb8888Bitmap bitmap = Png.readArgb8888Bitmap(is);//, new Argb8888Processor());
            assertEquals(32, bitmap.getWidth());
            assertEquals(32, bitmap.getHeight());
            assertEquals(readResourceText("/pngsuite/f02n2c08.hexrgb"), HexHelper.columnsToRgbHex(bitmap));
        }
    }

    @Test
    public void filters_grey4_image_with_up_filter() throws IOException, PngException {
        try (InputStream is = getClass().getResourceAsStream("/pngsuite/f02n0g08.png")) {
            Argb8888Bitmap bitmap = Png.readArgb8888Bitmap(is);//, new Argb8888Processor());
            assertEquals(32, bitmap.getWidth());
            assertEquals(32, bitmap.getHeight());
            assertEquals(readResourceText("/pngsuite/f02n0g08.hexrgb"), HexHelper.columnsToRgbHex(bitmap));
        }
    }

    // filter type 3
    //  f03n2c08 - color, no interlacing, filter-type 3
    @Test
    public void filters_colour_image_with_average_filter() throws IOException, PngException {
        try (InputStream is = getClass().getResourceAsStream("/pngsuite/f03n2c08.png")) {
            Argb8888Bitmap bitmap = Png.readArgb8888Bitmap(is);//, new Argb8888Processor());
            assertEquals(32, bitmap.getWidth());
            assertEquals(32, bitmap.getHeight());
            assertEquals(readResourceText("/pngsuite/f03n2c08.hexrgb"), HexHelper.columnsToRgbHex(bitmap));
        }
    }

    @Test
    public void filters_grey4_image_with_average_filter() throws IOException, PngException {
        try (InputStream is = getClass().getResourceAsStream("/pngsuite/f03n0g08.png")) {
            Argb8888Bitmap bitmap = Png.readArgb8888Bitmap(is);//, new Argb8888Processor());
            assertEquals(32, bitmap.getWidth());
            assertEquals(32, bitmap.getHeight());
            assertEquals(readResourceText("/pngsuite/f03n0g08.hexrgb"), HexHelper.columnsToRgbHex(bitmap));
        }
    }

    // f04n2c08 - color, no interlacing, filter-type 4
    @Test
    public void filters_colour_image_with_paeth_filter() throws IOException, PngException {
        try (InputStream is = getClass().getResourceAsStream("/pngsuite/f04n2c08.png")) {
            Argb8888Bitmap bitmap = Png.readArgb8888Bitmap(is);//, new Argb8888Processor());
            assertEquals(32, bitmap.getWidth());
            assertEquals(32, bitmap.getHeight());
            assertEquals(readResourceText("/pngsuite/f04n2c08.hexrgb"), HexHelper.columnsToRgbHex(bitmap));
        }
    }

    @Test
    public void filters_grey4_image_with_paeth_filter() throws IOException, PngException {
        try (InputStream is = getClass().getResourceAsStream("/pngsuite/f04n0g08.png")) {
            Argb8888Bitmap bitmap = Png.readArgb8888Bitmap(is);//, new Argb8888Processor());
            assertEquals(32, bitmap.getWidth());
            assertEquals(32, bitmap.getHeight());
            assertEquals(readResourceText("/pngsuite/f04n0g08.hexrgb"), HexHelper.columnsToRgbHex(bitmap));
        }
    }

    //  f99n0g04 - bit-depth 4, filter changing per scanline
    // white circle in black box
    @Test
    public void filters_colour_image_with_varying_filters() throws IOException, PngException {
        try (InputStream is = getClass().getResourceAsStream("/pngsuite/f99n0g04.png")) {
            Argb8888Bitmap bitmap = Png.readArgb8888Bitmap(is);//, new Argb8888Processor());
            assertEquals(32, bitmap.getWidth());
            assertEquals(32, bitmap.getHeight());
            assertEquals(readResourceText("/pngsuite/f99n0g04.hexrgb"), HexHelper.columnsToRgbHex(bitmap));
        }
    }

    String readResourceText(String resourceName) throws IOException {
        InputStream is = getClass().getResourceAsStream(resourceName);
        return new String(IoHelper.toByteArray(is));
    }

}
