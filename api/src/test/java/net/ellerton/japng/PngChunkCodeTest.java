package net.ellerton.japng;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;


public class PngChunkCodeTest {

    @Test
    public void decodes_IHDR() {
        PngChunkCode c = PngChunkCode.from(PngConstants.IHDR_VALUE);
        assertEquals("IHDR", c.letters);
        assertTrue(c == PngChunkCode.IHDR); // object is same object
        assertTrue(c.isCritical());
        assertFalse(c.isAncillary());
        assertTrue(c.isPublic());
        assertFalse(c.isPrivate());
        assertFalse(c.isSafeToCopy());
    }

    @Test
    public void decodes_gAMA() {
        PngChunkCode c = PngChunkCode.from(PngConstants.gAMA_VALUE);
        assertEquals("gAMA", c.letters);
        assertTrue(c == PngChunkCode.gAMA); // object is same object
        assertFalse(c.isCritical());
        assertTrue(c.isAncillary());
        assertTrue(c.isPublic());
        assertFalse(c.isPrivate());
        assertFalse(c.isSafeToCopy());
    }

    @Test
    public void decodes_acTL() {
        PngChunkCode c = PngChunkCode.from(PngConstants.acTL_VALUE);
        assertEquals("acTL", c.letters);
        assertTrue(c == PngChunkCode.acTL); // object is same object
        assertFalse(c.isCritical());
        assertTrue(c.isAncillary());
        assertFalse(c.isPublic());
        assertTrue(c.isPrivate());
        assertFalse(c.isSafeToCopy());
    }

    @Test
    public void decodes_custom() {
        PngChunkCode c = PngChunkCode.from(
            'H'<<24 | 'E'<<16 | 'L'<<8 | 'O'
        );
        assertEquals("HELO", c.letters);
        assertEquals(1212501071, c.numeric);
    }
}
