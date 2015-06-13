package net.ellerton.japng;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import net.ellerton.japng.util.IoHelper;
import net.ellerton.japng.argb8888.Argb8888Bitmap;
import net.ellerton.japng.argb8888.Argb8888BitmapSequence;
import net.ellerton.japng.chunks.PngFrameControl;
import net.ellerton.japng.error.PngException;
import net.ellerton.japng.reader.DefaultPngChunkReader;
import net.ellerton.japng.util.PngContainerBuilder;
import net.ellerton.japng.reader.PngReadHelper;
import net.ellerton.japng.argb8888.HexHelper;
import net.ellerton.japng.util.PngContainer;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class ReadApngTest {

    @Test
    public void readsContainer_throbber() throws IOException, PngException {

        try (InputStream is = getClass().getResourceAsStream("/mozilla/loading_16.png")) {
            //PngContainer f = Png.readContainer(is, "loading_16.png");
            DefaultPngChunkReader<PngContainer> reader = new DefaultPngChunkReader<>(new PngContainerBuilder());
            PngContainer f = PngReadHelper.read(is, reader);

            assertNotNull(f.header);
            assertEquals(16, f.header.width);
            assertEquals(16, f.header.height);
            assertEquals(8, f.header.bitDepth);
            assertEquals(PngColourType.PNG_TRUECOLOUR_WITH_ALPHA, f.header.colourType);
            assertEquals(6, f.header.colourType.code);
            assertEquals(0, f.header.compressionMethod);
            assertEquals(0, f.header.filterMethod);
            assertEquals(0, f.header.interlaceMethod);
            assertEquals(4 * 16 + 1, f.header.bytesPerRow);
            assertTrue(f.header.isZipCompression());
            assertFalse(f.header.isInterlaced());

            // Test reading of the acTL chunk
            assertTrue(f.isAnimated());
            //assertEquals(PngMainImageOp.MAIN_IMAGE_STARTS_ANIMATION, f.mainImageOp);
            assertEquals(PngAnimationType.ANIMATED_KEEP_DEFAULT_IMAGE, reader.getAnimationType());
            assertEquals(12, f.animationControl.numFrames);
            assertEquals(0, f.animationControl.numPlays);
            assertTrue(f.animationControl.loopForever());

            // Test reading of frames
            List<PngFrameControl> frames = f.animationFrames;
            PngFrameControl frame;
            assertEquals(12, frames.size());

            frame = frames.get(0);
            assertEquals(0, frame.sequenceNumber);
            assertEquals(16, frame.width);
            assertEquals(16, frame.height);
            assertEquals(0, frame.xOffset);
            assertEquals(0, frame.yOffset);
            assertEquals(70, frame.delayNumerator);
            assertEquals(1000, frame.delayDenominator);
            assertEquals(1, frame.disposeOp);
            assertEquals(0, frame.blendOp);
            assertEquals(1, frame.getImageChunks().size());
            assertEquals(PngChunkCode.IDAT, frame.getImageChunks().get(0).code);

            frame = frames.get(1);
            assertEquals(1, frame.sequenceNumber);
            assertEquals(16, frame.width);
            assertEquals(16, frame.height);
            assertEquals(0, frame.xOffset);
            assertEquals(0, frame.yOffset);
            assertEquals(70, frame.delayNumerator);
            assertEquals(1000, frame.delayDenominator);
            assertEquals(1, frame.disposeOp);
            assertEquals(0, frame.blendOp);
            assertEquals(1, frame.getImageChunks().size());
            assertEquals(PngChunkCode.fdAT, frame.getImageChunks().get(0).code);

            frame = frames.get(2);
            assertEquals(3, frame.sequenceNumber); // Note: sequence #2 is the image in fdAT
            assertEquals(16, frame.width);
            assertEquals(16, frame.height);
            assertEquals(0, frame.xOffset);
            assertEquals(0, frame.yOffset);
            assertEquals(70, frame.delayNumerator);
            assertEquals(1000, frame.delayDenominator);
            assertEquals(1, frame.disposeOp);
            assertEquals(0, frame.blendOp);

            frame = frames.get(3);
            assertEquals(5, frame.sequenceNumber);
            assertEquals(16, frame.width);
            assertEquals(16, frame.height);
            assertEquals(0, frame.xOffset);
            assertEquals(0, frame.yOffset);
            assertEquals(70, frame.delayNumerator);
            assertEquals(1000, frame.delayDenominator);
            assertEquals(1, frame.disposeOp);
            assertEquals(0, frame.blendOp);

            // prove that nth frame can be decoded and is correct
        }
    }


    /**
     * Demo1.png: An APNG file showing that the IDAT (default image) is not part
     * of the animation. The default image has text that reads "Your browser does NOT
     * support Animated PNGs" and will be shown in non-APNG-aware viewers. The second
     * frame reads "Your browser DOES support Animated PNGs" and will only show in
     * APNG-aware viewers. Nifty.
     */
    @Test
    public void reads_demo_1() throws IOException, PngException {
        try (InputStream is = getClass().getResourceAsStream("/mozilla/demo-1.png")) {
            Argb8888Bitmap bitmap;
            Argb8888BitmapSequence sequence= Png.readArgb8888BitmapSequence(is);//, new Argb8888Processor());
            assertTrue(sequence.hasDefaultImage());
            //bitmap = sequence.defaultImage;
            assertNotNull(sequence.defaultImage);
            assertEquals(192, sequence.defaultImage.getWidth());
            assertEquals(110, sequence.defaultImage.getHeight());
            assertEquals(8, sequence.header.bitDepth);
            assertEquals(PngColourType.PNG_TRUECOLOUR_WITH_ALPHA, sequence.header.colourType);

            assertTrue(sequence.isAnimated());
            assertNotNull(sequence.getAnimationControl());
            assertTrue(sequence.getAnimationControl().loopForever());
            assertEquals(1, sequence.getAnimationControl().numFrames);
            assertNotNull(sequence.getAnimationFrames());
            assertEquals(1, sequence.getAnimationFrames().size());

            Argb8888BitmapSequence.Frame frame = sequence.getAnimationFrames().get(0);
            assertNotNull(frame);
            assertEquals(new PngFrameControl(0, 192, 110, 0, 0, (short)500, (short)1000, (byte)1, (byte)0), frame.control);

            assertEquals(192, frame.bitmap.getWidth());
            assertEquals(110, frame.bitmap.getHeight());

            // Testing objects are not the same instance (intentionally not using isEquals).
            assertTrue(sequence.defaultImage != frame.bitmap);

//            try(PrintWriter out = new PrintWriter("demo-1.default.png.argb")) {
//                out.println(HexHelper.columnsToArgbHex(sequence.defaultImage));
//            }
//            try(PrintWriter out = new PrintWriter("demo-1.frame-1.png.argb")) {
//                out.println(HexHelper.columnsToArgbHex(frame.bitmap));
//            }
            assertEquals(readResourceText("/mozilla/demo-1.default.png.argb"), HexHelper.columnsToArgbHex(sequence.defaultImage));
            assertEquals(readResourceText("/mozilla/demo-1.frame-1.png.argb"), HexHelper.columnsToArgbHex(frame.bitmap));
        }
    }
    //xx_TODO : read a animated PNG file and prove that it ignores the later frames, only takes main frame


    @Test
    public void reads_loading_frames() throws IOException, PngException {
        try (InputStream is = getClass().getResourceAsStream("/mozilla/loading_16.png")) {
            Argb8888BitmapSequence sequence = Png.readArgb8888BitmapSequence(is);//, new Argb8888Processor());
            assertFalse(sequence.hasDefaultImage());
//            assertNotNull(sequence.defaultImage);
//            assertEquals(16, sequence.defaultImage.getWidth());
//            assertEquals(16, sequence.defaultImage.getHeight());
//            assertEquals(8, sequence.header.bitDepth);
            assertEquals(PngColourType.PNG_TRUECOLOUR_WITH_ALPHA, sequence.header.colourType);

            assertTrue(sequence.isAnimated());
            assertNotNull(sequence.getAnimationControl());
            assertTrue(sequence.getAnimationControl().loopForever());
            assertEquals(12, sequence.getAnimationControl().numFrames);
            assertNotNull(sequence.getAnimationFrames());
            assertEquals(12, sequence.getAnimationFrames().size());

            Argb8888BitmapSequence.Frame frame = sequence.getAnimationFrames().get(0);
            assertNotNull(frame);
            assertEquals(new PngFrameControl(0, 16, 16, 0, 0, (short) 70, (short) 1000, (byte) 1, (byte) 0), frame.control);

            assertEquals(16, frame.bitmap.getWidth());
            assertEquals(16, frame.bitmap.getHeight());

            // Testing objects are not the same instance (intentionally not using isEquals).
            assertTrue(sequence.defaultImage != frame.bitmap);

            frame = sequence.getAnimationFrames().get(1);
            assertNotNull(frame);
            assertEquals(new PngFrameControl(1, 16, 16, 0, 0, (short) 70, (short) 1000, (byte) 1, (byte) 0), frame.control);

            assertEquals(16, frame.bitmap.getWidth());
            assertEquals(16, frame.bitmap.getHeight());

//            try(PrintWriter out = new PrintWriter("loading_16.02.argb")) {
//                out.println(HexHelper.columnsToArgbHex(frame.bitmap));
//            }

        }
    }

    String readResourceText(String resourceName) throws IOException {
        InputStream is = getClass().getResourceAsStream(resourceName);
        return new String(IoHelper.toByteArray(is));
    }
}
