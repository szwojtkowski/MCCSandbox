package com.mccfunction;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.EncodeHintType;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.RGBLuminanceSource;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.util.HashMap;
import java.util.Map;

public class BarcodeReader implements IBarcodeReader {

    @Override
    public BarcodeReaderOutput process(BarcodeReaderInput request) {
        try {
            System.out.println("-- LAMBDA --");
            System.out.println("PROCESSING BARCODE...");
            System.out.println(request.getHeight());
            System.out.println(request.getWidth());
            System.out.println(request.getPixels().length);
            System.out.println("--------------------");

            LuminanceSource source = new RGBLuminanceSource(request.getWidth(), request.getHeight(), request.getPixels());
            Map hintMap = new HashMap();
            hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);

            BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(source));

            Result qrCodeResult = new MultiFormatReader().decode(binaryBitmap,
                    hintMap);

            return new BarcodeReaderOutput(qrCodeResult.getText());

        } catch (Exception e) {
            return new BarcodeReaderOutput("ERROR");
        }

    }
}
