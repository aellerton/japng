package net.ellerton.japng;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import net.ellerton.japng.chunks.PngHeader;
import org.junit.Test;


public class PngHeaderTest {

    @Test
    public void constructsTruecolour() {
        PngHeader h = PngHeader.makeTruecolour(10, 20);
        assertEquals(10, h.width);
        assertEquals(20, h.height);
        assertEquals(8, h.bitDepth);
        assertEquals(PngColourType.PNG_TRUECOLOUR, h.colourType);
        assertEquals(0, h.compressionMethod);
        assertEquals(0, h.filterMethod);
        assertEquals(0, h.interlaceMethod);
        assertEquals(10*3+1, h.bytesPerRow);

    }

    @Test
    public void constructsTruecolourAlpha() {
        PngHeader h = PngHeader.makeTruecolourAlpha(12, 8);
        assertEquals(12, h.width);
        assertEquals(8, h.height);
        assertEquals(8, h.bitDepth);
        assertEquals(PngColourType.PNG_TRUECOLOUR_WITH_ALPHA, h.colourType);
        assertEquals(0, h.compressionMethod);
        assertEquals(0, h.filterMethod);
        assertEquals(0, h.interlaceMethod);
        assertEquals(12*4+1, h.bytesPerRow);
    }
}
