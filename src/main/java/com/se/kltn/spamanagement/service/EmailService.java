package com.se.kltn.spamanagement.service;

import com.se.kltn.spamanagement.dto.request.SendMailRequest;

public interface EmailService {

    String sendSimpleEmail(Long idCustomer, SendMailRequest emailDetails);

    String sendEmailWithAttachment(Long idCustomer, SendMailRequest emailDetails);
}
