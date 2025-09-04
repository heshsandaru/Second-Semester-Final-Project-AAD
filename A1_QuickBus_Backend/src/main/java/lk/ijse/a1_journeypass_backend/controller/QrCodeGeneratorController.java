package lk.ijse.a1_journeypass_backend.controller;

import lk.ijse.a1_journeypass_backend.service.impl.QrCodeGeneratorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class QrCodeGeneratorController {
    @Autowired
    QrCodeGeneratorService qrCodeGeneratorService;

    @PostMapping("/qrcode")
    public String addCustomer(@RequestBody String message){
        log.info("Input message:{}",message);
        qrCodeGeneratorService.generateQRCode(message);
        return "Qr code create success.";
    }
}
