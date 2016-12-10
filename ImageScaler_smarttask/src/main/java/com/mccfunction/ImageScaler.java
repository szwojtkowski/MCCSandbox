package com.mccfunction;

import org.imgscalr.Scalr;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageScaler implements IImageScaler {

    private BufferedImage createImageFromBytes(byte[] imageData) {
        ByteArrayInputStream bais = new ByteArrayInputStream(imageData);
        try {
            return ImageIO.read(bais);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private byte[] bufferedImageToBytes(BufferedImage bufferedImage) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "png", baos );
        baos.flush();
        byte[] imageInByte = baos.toByteArray();
        baos.close();
        return imageInByte;
    }

    @Override
    public ImageScalerOutput process(ImageScalerInput request) {
        BufferedImage scaled = Scalr.resize(createImageFromBytes(request.getPayload()), request.getWidth(), request.getHeight());
        ImageScalerOutput response;
        try {
            response = new ImageScalerOutput(bufferedImageToBytes(scaled), request.getWidth(), request.getHeight());
        } catch (IOException e) {
            response = new ImageScalerOutput(new byte[0], 0, 0);
        }
        return response;
    }
}

