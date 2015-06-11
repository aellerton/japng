package net.ellerton.japng;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import net.ellerton.japng.PngScanlineBuffer;
import net.ellerton.japng.chunks.PngHeader;
import org.junit.Test;

public class PngScanlineBufferTest {

    final int ONE_K = 1 << 10;
    final int TWO_K = 2 << 10;
    final int FOUR_K = 4 << 10;
    final int FIVE_HUNDRED_K = 500 << 10;

    @Test
    public void calculatesSize() {

        // Image 8 x 4 (24 bpp) = 4 * (8*3+1) = 100 bytes for whole bitmap
        assertEquals(100, PngScanlineBuffer.sizeFrom(PngHeader.makeTruecolour(8, 4), ONE_K));
        assertEquals(100, PngScanlineBuffer.sizeFrom(PngHeader.makeTruecolour(8, 4), TWO_K));
        assertEquals(100, PngScanlineBuffer.sizeFrom(PngHeader.makeTruecolour(8, 4), FOUR_K));

        // Image 200 x 200 (24bpp) = 200 x (200x3 + 1) = 200 x 601 = 120200 for whole bitmap.
        // If limit is 1K then it fits one line
        assertEquals(601, PngScanlineBuffer.sizeFrom(PngHeader.makeTruecolour(200, 200), ONE_K));
        // If limit is 2K then it fits three lines (2048 / 601 = 3.4)
        assertEquals(3*601, PngScanlineBuffer.sizeFrom(PngHeader.makeTruecolour(200, 200), TWO_K));
        // If limit is 4K then it fits six lines (4096 / 601 = 6.8)
        assertEquals(6*601, PngScanlineBuffer.sizeFrom(PngHeader.makeTruecolour(200, 200), FOUR_K));
        // If limit is 500k then the whole bitmap fits
        assertEquals(200*601, PngScanlineBuffer.sizeFrom(PngHeader.makeTruecolour(200, 200), FIVE_HUNDRED_K));

        // Test case where the minimum size is too small for even one row...

        // Image 400 x 200 (32bpp) = 200 x (400x4 + 1) = 200 x 1601 = 320200 for whole bitmap.
        // If limit is 1K then one row will exceed the minimum
        assertEquals(1*1601, PngScanlineBuffer.sizeFrom(PngHeader.makeTruecolourAlpha(400, 200), ONE_K));
        // If limit is 2K then it fits one line (2048 / 1601 > 1.0)
        assertEquals(1*1601, PngScanlineBuffer.sizeFrom(PngHeader.makeTruecolourAlpha(400, 200), TWO_K));
        // If limit is 4k then it fits (4096 / 1601 = 2.5)
        assertEquals(2*1601, PngScanlineBuffer.sizeFrom(PngHeader.makeTruecolourAlpha(400, 200), FOUR_K));
        // If limit is 500k then the whole bitmap fits
        assertEquals(200*1601, PngScanlineBuffer.sizeFrom(PngHeader.makeTruecolourAlpha(400, 200), FIVE_HUNDRED_K));
    }
}