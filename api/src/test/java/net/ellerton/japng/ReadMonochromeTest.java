package net.ellerton.japng;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import java.io.IOException;
import java.io.InputStream;

import net.ellerton.japng.util.IoHelper;
import net.ellerton.japng.error.PngException;
import net.ellerton.japng.map.PngChunkMap;
import net.ellerton.japng.map.PngMap;
import net.ellerton.japng.reader.PngAtOnceSource;
import net.ellerton.japng.reader.PngReadHelper;
import net.ellerton.japng.util.AsciiBitmapProcessor;
import net.ellerton.japng.util.PngContainer;
import org.junit.Test;


public class ReadMonochromeTest {
    public final static String BASN0G01_string =
        "PngHeader{width=32, height=32, bitDepth=1, colourType=PNG_GREYSCALE, compressionMethod=0, filterMethod=0, interlaceMethod=0}\n" +
        "Main Image Data (IDAT)\n" +
        "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX \n" +
        "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXX  \n" +
        "XXXXXXXXXXXXXXXXXXXXXXXXXXXXX   \n" +
        "XXXXXXXXXXXXXXXXXXXXXXXXXXXX    \n" +
        "XXXX  XXXXXX  XXXXXXXXXXXXX     \n" +
        "XXXX  XXXXXX  XXXXXXXXXXXX      \n" +
        "XXXX  XXXXXX  XXXXXXXXXXX       \n" +
        "XXXX  XX  XX  XXXXXXXXXX        \n" +
        "XXXX  XX  XX  XXXXXXXXX         \n" +
        "XXXX  XX  XX  XXXXXXXX          \n" +
        "XXXXX        XXXXXXXX           \n" +
        "XXXXX        XXXXXXX            \n" +
        "XXXXXX  XX  XXXXXXX             \n" +
        "XXXXXX  XX  XXXXXX              \n" +
        "XXXXXXXXXXXXXXXXX               \n" +
        "XXXXXXXXXXXXXXXX                \n" +
        "XXXXXXXXXXXXXXX                 \n" +
        "XXXXXXXXXXXXXX                  \n" +
        "XXXXXXXXXXXXX       XXXXXXX     \n" +
        "XXXXXXXXXXXX        XXXXXXX     \n" +
        "XXXXXXXXXXX         XX    XX    \n" +
        "XXXXXXXXXX          XX    XX    \n" +
        "XXXXXXXXX           XXXXXXX     \n" +
        "XXXXXXXX            XXXXXXX     \n" +
        "XXXXXXX             XX    XX    \n" +
        "XXXXXX              XX    XX    \n" +
        "XXXXX               XXXXXXX     \n" +
        "XXXX                XXXXXXX     \n" +
        "XXX                             \n" +
        "XX                              \n" +
        "X                               \n" +
        "                                \n";

    @Test
    public void loadsResourceAsBytes() throws IOException {
        try (InputStream is = getClass().getResourceAsStream("/pngsuite/basi0g01.png")) {
            byte[] bytes = IoHelper.toByteArray(is);
            assertEquals(217, bytes.length);
        }
    }

    @Test
    public void readsSignature() throws IOException {
        try (InputStream is = getClass().getResourceAsStream("/pngsuite/basi0g01.png")) {
            boolean b = PngReadHelper.readSignature(is);
            assertTrue(b);

            byte[] bytes = IoHelper.toByteArray(is);
            assertEquals(217 - PngConstants.LENGTH_SIGNATURE, bytes.length);
        }
    }

    @Test
    public void readsChunksVerbosely() throws IOException {
        try (InputStream is = getClass().getResourceAsStream("/pngsuite/basi0g01.png")) {
            is.skip(8);

            PngAtOnceSource source = PngAtOnceSource.from(is);
            assertEquals(0, source.tell());
            assertEquals(217 - PngConstants.LENGTH_SIGNATURE, source.getBytes().length);
            assertEquals(217 - PngConstants.LENGTH_SIGNATURE, source.getLength());

            // -- IHDR Chunk --
            int chunkLength = source.readInt();
            assertEquals(4, source.tell());
            assertEquals(13, chunkLength);

            int chunkCode = source.readInt();
            assertEquals(8, source.tell());
            assertEquals(PngConstants.IHDR_VALUE, chunkCode);

            source.skip(chunkLength);
            assertEquals(8+13, source.tell());

            int chunkChecksum = source.readInt();
            assertEquals(738621391, chunkChecksum);

            // Future: calculate the checksum

            // -- gAMA Chunk --
            chunkLength = source.readInt();
            assertEquals(8+13+4+4, source.tell());
            assertEquals(4, chunkLength);

            chunkCode = source.readInt();
            assertEquals(33, source.tell());
            assertEquals(PngConstants.gAMA_VALUE, chunkCode);

            source.skip(chunkLength);
            assertEquals(33 + 4, source.tell());

            chunkChecksum = source.readInt();
            assertEquals(837326431, chunkChecksum);

            // -- IDAT Chunk --

            chunkLength = source.readInt();
            chunkCode = source.readInt();

            assertEquals(144, chunkLength);
            assertEquals(PngConstants.IDAT_VALUE, chunkCode);
            assertEquals(49, source.tell()); // start of IDAT bytes

            source.skip(chunkLength);

            chunkChecksum = source.readInt();
            assertEquals(1712857842, chunkChecksum);

            // -- IEND Chunk --

            chunkLength = source.readInt();
            chunkCode = source.readInt();

            assertEquals(0, chunkLength);
            assertEquals(PngConstants.IEND_VALUE, chunkCode);

            chunkChecksum = source.readInt();
            assertEquals(-1371381630, chunkChecksum);
            assertEquals(0, source.available());
        }
    }

    @Test
    public void readsMap_gray_1bit() throws IOException, PngException {

        try (InputStream is = getClass().getResourceAsStream("/pngsuite/basi0g01.png")) {
            PngMap map;
            PngChunkMap chunk;

            map = Png.readMap(is, "test");
            assertEquals(4, map.chunks.size());
            assertEquals("test", map.source);

            chunk = map.chunks.get(0);
            assertEquals("IHDR", chunk.code.letters);
            assertEquals(8, chunk.dataPosition); // Remember that "dataPosition" skips the 4 byte len and 4 byte code.
            assertEquals(13, chunk.dataLength);

            chunk = map.chunks.get(1);
            assertEquals("gAMA", chunk.code.letters);
            assertEquals(33, chunk.dataPosition);
            assertEquals(4, chunk.dataLength);

            chunk = map.chunks.get(2);
            assertEquals("IDAT", chunk.code.letters);
            assertEquals(49, chunk.dataPosition);
            assertEquals(144, chunk.dataLength);

            chunk = map.chunks.get(3);
            assertEquals("IEND", chunk.code.letters);
            assertEquals(205, chunk.dataPosition);
            assertEquals(0, chunk.dataLength);
        }
    }

    @Test
    public void readsContainer_gray_1bit() throws IOException, PngException {

        try (InputStream is = getClass().getResourceAsStream("/pngsuite/basn0g01.png")) {
            PngContainer f = Png.readContainer(is);
            assertNotNull(f.header);
            assertEquals(32, f.header.width);
            assertEquals(32, f.header.height);
            assertEquals(1, f.header.bitDepth);
            assertEquals(PngColourType.PNG_GREYSCALE, f.header.colourType);
            assertEquals(0, f.header.colourType.code);
            assertEquals(0, f.header.compressionMethod);
            assertEquals(0, f.header.filterMethod);
            assertEquals(0, f.header.interlaceMethod);
            assertEquals(5, f.header.bytesPerRow);
            assertEquals(1, f.header.bitsPerPixel);
            assertEquals(1, f.header.filterOffset);
            assertTrue(f.header.isZipCompression());
            assertFalse(f.header.isInterlaced());

            assertNotNull(f.gamma);
            assertEquals(100000, f.gamma.imageGamma);

            //assertNotNull(f.decompressedFilteredImageData);
            //assertEquals(5 * 32, f.decompressedFilteredImageData.length); // 160

            //PngPixelFormatter<String> pixels = PngPixelAsciiFormat.from(f.header);
            //assertEquals(BASN0G01_string, f.processBitmap(pixels));
        }
    }


    // TODO: are these worth keeping?
    @Test
    public void buildsAscii_gray_1bit() throws IOException, PngException {

        try (InputStream is = getClass().getResourceAsStream("/pngsuite/basn0g01.png")) {
            String f = Png.read(is, new AsciiBitmapProcessor());
//            AsciiBitmap f = Png.read(is, new AsciiBitmapBuilder());
//            assertNotNull(f.header);
//            assertEquals(32, f.header.width);
//            assertEquals(32, f.header.height);
//            assertEquals(1, f.header.bitDepth);
//            assertEquals(PngColourType.PNG_GREYSCALE, f.header.colourType);
//            assertEquals(0, f.header.colourType.code);
//            assertEquals(0, f.header.compressionMethod);
//            assertEquals(0, f.header.filterMethod);
//            assertEquals(0, f.header.interlaceMethod);
//            assertEquals(5, f.header.bytesPerRow);
//            assertTrue(f.header.isZipCompression());
//            assertFalse(f.header.isInterlaced());
//
//            assertNotNull(f.gamma);
//            assertEquals(100000, f.gamma.imageGamma);
//
//            //assertNotNull(f.decompressedFilteredImageData);
//            //assertEquals(5 * 32, f.decompressedFilteredImageData.length); // 160
//
//            //PngPixelFormatter<String> pixels = PngPixelAsciiFormat.from(f.header);
//            //assertEquals(BASN0G01_string, f.processBitmap(pixels));

            assertEquals(BASN0G01_string, f);
        }
    }

}
