package com.hospital.util;

import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.PNGTranscoder;

import java.io.*;
import java.nio.file.*;

public class IconConverter {
    public static void main(String[] args) {
        String resourcePath = "src/main/resources/images/";
        File resourceDir = new File(resourcePath);
        
        if (!resourceDir.exists()) {
            System.out.println("Creating resources directory...");
            resourceDir.mkdirs();
        }

        // Convert all SVG files to PNG
        File[] svgFiles = resourceDir.listFiles((dir, name) -> name.toLowerCase().endsWith(".svg"));
        if (svgFiles != null) {
            for (File svgFile : svgFiles) {
                try {
                    String pngFileName = svgFile.getName().replace(".svg", ".png");
                    convertSvgToPng(svgFile.getPath(), resourcePath + pngFileName);
                    System.out.println("Converted " + svgFile.getName() + " to PNG");
                } catch (Exception e) {
                    System.err.println("Error converting " + svgFile.getName() + ": " + e.getMessage());
                }
            }
        }
    }

    private static void convertSvgToPng(String svgPath, String pngPath) throws Exception {
        // Create a PNG transcoder
        PNGTranscoder transcoder = new PNGTranscoder();
        
        // Set the transcoding hints
        transcoder.addTranscodingHint(PNGTranscoder.KEY_WIDTH, 48f);
        transcoder.addTranscodingHint(PNGTranscoder.KEY_HEIGHT, 48f);

        // Create the transcoder input
        String svgURI = Paths.get(svgPath).toUri().toURL().toString();
        TranscoderInput input = new TranscoderInput(svgURI);

        // Create the transcoder output
        try (OutputStream outStream = new FileOutputStream(pngPath)) {
            TranscoderOutput output = new TranscoderOutput(outStream);

            // Perform the transcoding
            transcoder.transcode(input, output);
        }
    }
} 