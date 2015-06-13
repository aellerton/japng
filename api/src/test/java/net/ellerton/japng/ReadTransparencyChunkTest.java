package net.ellerton.japng;

import net.ellerton.japng.util.IoHelper;
import net.ellerton.japng.argb8888.Argb8888Bitmap;
import net.ellerton.japng.error.PngException;
import net.ellerton.japng.argb8888.HexHelper;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

import static org.junit.Assert.assertEquals;

/**
 * Created by aellerton on 26/05/2015.
 */
public class ReadTransparencyChunkTest {

    // tp1n3p08 - transparent, but no background chunk
    @Test
    public void reads_transparent_no_background_chunk_to_argb8888() throws IOException, PngException {
        try (InputStream is = getClass().getResourceAsStream("/pngsuite/tp1n3p08.png")) {
            Argb8888Bitmap bitmap = Png.readArgb8888Bitmap(is);//, new Argb8888Processor());
            assertEquals(32, bitmap.getWidth());
            assertEquals(32, bitmap.getHeight());
            assertEquals(readResourceText("/pngsuite/tp1n3p08.argb"), HexHelper.columnsToArgbHex(bitmap));
        }
    }

    // tm3n3p02 - multiple levels of transparency, 3 entries
    @Test
    public void reads_palette_transparency_to_argb8888() throws IOException, PngException {
        try (InputStream is = getClass().getResourceAsStream("/pngsuite/tm3n3p02.png")) {
            Argb8888Bitmap bitmap = Png.readArgb8888Bitmap(is);//, new Argb8888Processor());
            assertEquals(32, bitmap.getWidth());
            assertEquals(32, bitmap.getHeight());
            assertEquals(readResourceText("/pngsuite/tm3n3p02.argb"), HexHelper.columnsToArgbHex(bitmap));
        }
    }

    // tbbn2c16 - transparent, blue background chunk
    @Test
    public void reads_transparent_blue_background_to_argb8888() throws IOException, PngException {
        try (InputStream is = getClass().getResourceAsStream("/pngsuite/tbbn2c16.png")) {
            Argb8888Bitmap bitmap = Png.readArgb8888Bitmap(is);//, new Argb8888Processor());
            assertEquals(32, bitmap.getWidth());
            assertEquals(32, bitmap.getHeight());
            assertEquals(readResourceText("/pngsuite/tbbn2c16.argb"), HexHelper.columnsToArgbHex(bitmap));
        }
    }

    // tbbn0g04 - transparent, black background chunk
    @Test
    public void reads_transparent_with_grey4_chunk_to_argb8888() throws IOException, PngException {
        try (InputStream is = getClass().getResourceAsStream("/pngsuite/tbbn0g04.png")) {
            Argb8888Bitmap bitmap = Png.readArgb8888Bitmap(is);//, new Argb8888Processor());
            assertEquals(32, bitmap.getWidth());
            assertEquals(32, bitmap.getHeight());
            assertEquals(readResourceText("/pngsuite/tbbn0g04.argb"), HexHelper.columnsToArgbHex(bitmap));
        }
    }

    String readResourceText(String resourceName) throws IOException {
        InputStream is = getClass().getResourceAsStream(resourceName);
        return new String(IoHelper.toByteArray(is));
    }
}
