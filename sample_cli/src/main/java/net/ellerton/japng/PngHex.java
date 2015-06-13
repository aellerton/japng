package net.ellerton.japng;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import net.ellerton.japng.argb8888.Argb8888BitmapSequence;
import net.ellerton.japng.argb8888.HexHelper;
import net.ellerton.japng.chunks.PngFrameControl;
import net.ellerton.japng.error.PngException;
import net.ellerton.japng.map.PngChunkMap;
import net.ellerton.japng.util.PngContainer;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Convert named PNG files to text files of hex colours.
 *
 * ** NOT YET COMPLETE **
 */
public class PngHex {
    static class Config {
        @Parameter(names = { "-v", "-verbose" }, description = "Level of verbosity")
        private Integer verbosity = 0;

        @Parameter(names = { "-h", "--help"}, description = "Show help", help = true)
        private boolean help;

        @Parameter(names = { "-f", "--frames"}, description = "Decode animation frames")
        private boolean frames = true;

        @Parameter(names = { "-d", "--defaultimage"}, description = "Decode default image")
        private boolean defaultImage = true;

        @Parameter(names = { "--rgb"}, description = "Format as RRGGBB")
        private boolean formatRgb = true;

        @Parameter(names = { "--argb"}, description = "Format as AARRGGBB")
        private boolean formatArgb = false;

        @Parameter(description = "<filename> ....")
        private List<String> filenames = new ArrayList<String>();

        public boolean verbose() {
            return verbosity > 0;
        }
    }

    public static void main(String[] args) {
        Config config = new Config();
        JCommander commander = new JCommander(config, args);

        if (config.filenames.isEmpty() || config.help) {
            commander.usage();
            return;
        }

        for (String filename : config.filenames) {
            process(config, filename);
        }
    }

    private static void process(Config config, String filename) {
        File f = new File(filename);
        if (!f.exists()) {
            System.err.println(filename+": no such file");
            return;
        }
        if (!f.isFile() || !f.canRead()) {
            System.err.println(filename+": not a file or can not read");
            return;
        }
        try (FileInputStream fis = new FileInputStream(f)) {
            Argb8888BitmapSequence info = Png.readArgb8888BitmapSequence(fis);
            System.out.println(String.format("%s: PNG type %d (%s)", filename, info.header.colourType.code, info.header.colourType.name));
            if (config.verbose()) {
                System.out.println(String.format("%s: %s", filename, info.header.colourType.descriptino));
            }
            System.out.println(String.format("%s: width: %d", filename, info.header.width));
            System.out.println(String.format("%s: height: %d", filename, info.header.height));
            System.out.println(String.format("%s: interlaced: %s", filename, info.header.isInterlaced() ? "yes" : "no"));
            if (config.verbose()) {
                System.out.println(String.format("%s: bit-depth: %d (bits-per-pixel-channel)", filename, info.header.bitDepth));
                System.out.println(String.format("%s: bits-per-pixel: %d", filename, info.header.bitsPerPixel));
                System.out.println(String.format("%s: bytes-per-row: %d", filename, info.header.bytesPerRow));
            }
            System.out.println(String.format("%s: animated? %s", filename, info.isAnimated() ? "yes" : "no"));
            if (info.isAnimated()) {
                if (info.hasDefaultImage()) {
                    System.out.println("default image:");
                    System.out.println(HexHelper.columnsToRgbHex(info.defaultImage));
                }
                List<Argb8888BitmapSequence.Frame> frames = info.getAnimationFrames();
                int n = frames.size();
//                System.out.println(String.format("%s: animation frames: %d", filename, n));
//                System.out.println(String.format("%s: animation repeats: %s", filename, info.animationControl.loopForever() ? "infinite" : ""+info.animationControl.numPlays));
//                System.out.println(String.format("%s: default image before animation: %s", filename, info.hasDefaultImage ? "yes" : "no"));
                int i=0;
                for (Argb8888BitmapSequence.Frame frame : frames) {
                    i++;
                    System.out.println(String.format("%s: frame %2d of %2d: size %d x %d pixels", filename, i, n, frame.bitmap.width, frame.bitmap.height));
                    System.out.println(HexHelper.columnsToRgbHex(frame.bitmap));
//                    System.out.println(String.format("%s: frame %2d of %2d: position %d, %d", filename, i, n, frame.xOffset, frame.yOffset));
//                    System.out.println(String.format("%s: frame %2d of %2d: duration %d/%d = %d milliseconds", filename, i, n, frame.delayNumerator, frame.delayDenominator, frame.getDelayMilliseconds()));
//                    System.out.println(String.format("%s: frame %2d of %2d: dipose = %s", filename, i, n, frame.disposeOp));
//                    System.out.println(String.format("%s: frame %2d of %2d: blend = %s", filename, i, n, frame.blendOp));
                }
//                i = 0;
//                n = info.chunks.size();
//                for (PngChunkMap chunk : info.chunks) {
//                    i++;
//                    System.out.println(String.format("%s: chunk %2d of %2d: %s at %7d for %7d bytes (%s, %s, %s, %s)",
//                            filename, i, n, chunk.code.letters, chunk.dataPosition, chunk.dataLength,
//                            chunk.code.isAncillary() ? "ancillary" : "critical",
//                            chunk.code.isPrivate() ? "private" : "public",
//                            chunk.code.isReserved() ? "reserved" :"not reserved",
//                            chunk.code.isSafeToCopy() ? "safe-to-copy" : "not safe to copy"
//                            ));
//                }
            } else {
                // nop
            }
        } catch (IOException e) {
            System.err.println(filename+": IO error: "+e.getMessage());
        } catch (PngException e) {
            System.err.println(filename+": PNG error: "+e.getMessage());
        }
    }
}
