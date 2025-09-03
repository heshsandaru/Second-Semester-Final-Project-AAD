package lk.ijse.a1_journeypass_backend.service.impl;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import io.micrometer.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
@Slf4j
public class QrCodeGeneratorService {

    @Value("${qrcode.output.directory}")
    private String outputLocation;

    @Autowired
    private EmailService emailService;

    private static final String charset = "UTF-8";
    private static final String strDateFormat = "yyyy-MM-dd_HH-mm-ss"; // safer filename format

    public File generateQRCode(String message) {
        log.info("Generate QR Code");

        try {
            String finalMessage = (StringUtils.isNotBlank(message)) ? message : "";
            log.info("Final Input Message: {}", finalMessage);

            String outputPath = prepareOutputFileName();
            processQrCode(finalMessage, outputPath, charset, 200, 200);

            File qrFile = new File(outputPath);
            return qrFile;

        } catch (Exception e) {
            throw new RuntimeException("QR code generation failed", e);
        }
    }

    private String prepareOutputFileName() {
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat(strDateFormat);
        String formattedDate = dateFormat.format(date);

        StringBuilder sb = new StringBuilder();
        sb.append(outputLocation).append(File.separator).append("QrCode_").append(formattedDate).append(".png");
        log.info("Final output file: {}", sb.toString());
        return sb.toString();
    }

    private void processQrCode(String data, String path, String charset, int width, int height) throws IOException, WriterException {
        BitMatrix bitMatrix = new MultiFormatWriter()
                .encode(new String(data.getBytes(charset), charset), BarcodeFormat.QR_CODE, width, height);
        MatrixToImageWriter.writeToFile(bitMatrix, path.substring(path.lastIndexOf('.') + 1), new File(path));
    }

}
