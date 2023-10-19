package com.se.kltn.spamanagement.controller;

import com.se.kltn.spamanagement.dto.request.SendMailRequest;
import com.se.kltn.spamanagement.service.EmailService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/send-mail")
public class SendMailController {

    private final EmailService emailService;

    @Autowired
    public SendMailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/{idCustomer}")
    @Operation(summary = "Send simple email")
    public ResponseEntity<String> sendSimpleEmail(@PathVariable Long idCustomer, @RequestBody SendMailRequest emailDetails) {
        return ResponseEntity.ok(emailService.sendSimpleEmail(idCustomer, emailDetails));
    }

    @PostMapping("/attachment/{idCustomer}")
    @Operation(summary = "Send email with attachment")
    public ResponseEntity<String> sendEmailWithAttachment(@PathVariable Long idCustomer, @RequestBody SendMailRequest emailDetails) {
        return ResponseEntity.ok(emailService.sendEmailWithAttachment(idCustomer, emailDetails));
    }
}
