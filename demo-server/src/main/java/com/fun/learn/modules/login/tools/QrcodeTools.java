package com.fun.learn.modules.login.tools;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @author littlePart
 * @version 1.0
 * @date 2020/03/2020/3/6 10:53
 */
@Slf4j
public class QrcodeTools {

//    public static String decode(byte[] qrcodeImg) {
//        QRCodeReader reader = new QRCodeReader();
//
//        LuminanceSource luminanceSource = new RGBLuminanceSource();
//
//        BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(luminanceSource));
//        try {
//            Result result = reader.decode(binaryBitmap);
//            return result.getText();
//        } catch (Exception e) {
//            log.error("二维码解码时异常", e);
//            throw new ServiceException(GlobalErrorEnum.QRCODE_ERROR);
//        }
//    }

    public static void encode(String content, OutputStream stream) {
        encode(content, 150, 150, stream);
    }

    public static byte[] encode(String content, int width, int height) {
        ByteArrayOutputStream stream = null;
        try {
            stream = new ByteArrayOutputStream(1024 * 1024);
            encode(content, width, height, stream);
            return stream.toByteArray();
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    public static void encode(String content, int width, int height, OutputStream stream) {
        QRCodeWriter writer = new QRCodeWriter();
        Map<EncodeHintType, Object> hint = new HashMap<>(2);
        hint.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        hint.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        try {
            BitMatrix matrix = writer.encode(content, BarcodeFormat.QR_CODE, width, height, hint);
            MatrixToImageWriter.writeToStream(matrix, "png", stream);
        } catch (WriterException | IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        File file = new File(".\\test.png");
        FileOutputStream stream = null;
        try {
            file.createNewFile();
            stream = new FileOutputStream(file);
            encode("我想抱抱你，啾咪", 200, 200, stream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                stream.close();
            } catch (IOException e) {
                //do nothing
            }
        }
    }


}
