package net.ellerton.japng;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import net.ellerton.japng.reader.PngReadHelper;
import org.junit.Test;


public class PngReadHelperTest {

    @Test
    public void calculatesBytesPerRow_1() {
        assertEquals(5, PngReadHelper.calculateBytesPerRow(32, (byte) 1, PngColourType.PNG_GREYSCALE, (byte) 0));
    }

    @Test
    public void calculatesBytesPerRow_2() {
        assertEquals(9, PngReadHelper.calculateBytesPerRow(32, (byte) 2, PngColourType.PNG_GREYSCALE, (byte) 0));
    }

    @Test
    public void calculatesBytesPerRow_4() {
        assertEquals(17, PngReadHelper.calculateBytesPerRow(32, (byte) 4, PngColourType.PNG_GREYSCALE, (byte) 0));
        assertEquals(33, PngReadHelper.calculateBytesPerRow(32, (byte) 4, PngColourType.PNG_GREYSCALE_WITH_ALPHA, (byte) 0));
    }

    @Test
    public void calculatesBytesPerRow_8() {
        assertEquals(65, PngReadHelper.calculateBytesPerRow(32, (byte) 8, PngColourType.PNG_GREYSCALE_WITH_ALPHA, (byte) 0));
        assertEquals(97, PngReadHelper.calculateBytesPerRow(32, (byte) 8, PngColourType.PNG_TRUECOLOUR, (byte) 0));
        assertEquals(129, PngReadHelper.calculateBytesPerRow(32, (byte) 8, PngColourType.PNG_TRUECOLOUR_WITH_ALPHA, (byte) 0));
    }

    @Test
    public void calculatesBytesPerRow_16() {
        assertEquals(65, PngReadHelper.calculateBytesPerRow(32, (byte) 16, PngColourType.PNG_GREYSCALE, (byte) 0));
        assertEquals(129, PngReadHelper.calculateBytesPerRow(32, (byte) 16, PngColourType.PNG_GREYSCALE_WITH_ALPHA, (byte) 0));
        assertEquals(193, PngReadHelper.calculateBytesPerRow(32, (byte) 16, PngColourType.PNG_TRUECOLOUR, (byte) 0));
        assertEquals(257, PngReadHelper.calculateBytesPerRow(32, (byte) 16, PngColourType.PNG_TRUECOLOUR_WITH_ALPHA, (byte) 0));
    }
}
