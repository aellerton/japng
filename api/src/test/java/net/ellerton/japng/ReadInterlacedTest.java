package net.ellerton.japng;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import net.ellerton.japng.argb8888.Argb8888Bitmap;
import net.ellerton.japng.error.PngException;
import net.ellerton.japng.error.PngFeatureException;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

public class ReadInterlacedTest {
    // basi0g01

    @Test(expected=PngFeatureException.class)
    public void fails_on_interlaced_monochrome() throws IOException, PngException {

        try (InputStream is = getClass().getResourceAsStream("/pngsuite/basi0g01.png")) {
            Argb8888Bitmap bitmap = Png.readArgb8888Bitmap(is);//, new Argb8888Processor());
//            assertEquals(32, bitmap.getWidth());
//            assertEquals(32, bitmap.getHeight());
        }
    }

    @Test(expected=PngFeatureException.class)
    public void fails_on_interlaced_2bit() throws IOException, PngException {

        try (InputStream is = getClass().getResourceAsStream("/pngsuite/basi0g02.png")) {
            Argb8888Bitmap bitmap = Png.readArgb8888Bitmap(is);//, new Argb8888Processor());
        }
    }

    @Test(expected=PngFeatureException.class)
    public void fails_on_interlaced_truecolour() throws IOException, PngException {

        try (InputStream is = getClass().getResourceAsStream("/pngsuite/basi6a08.png")) {
            Argb8888Bitmap bitmap = Png.readArgb8888Bitmap(is);//, new Argb8888Processor());
        }
    }

}
