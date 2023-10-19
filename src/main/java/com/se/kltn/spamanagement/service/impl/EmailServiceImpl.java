package com.se.kltn.spamanagement.service.impl;

import com.se.kltn.spamanagement.dto.request.SendMailRequest;
import com.se.kltn.spamanagement.exception.ResourceNotFoundException;
import com.se.kltn.spamanagement.exception.SendEmailException;
import com.se.kltn.spamanagement.model.Customer;
import com.se.kltn.spamanagement.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import com.se.kltn.spamanagement.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import java.util.Objects;

import static com.se.kltn.spamanagement.constants.ErrorMessage.CUSTOMER_NOT_FOUND;
import static com.se.kltn.spamanagement.constants.ErrorMessage.SEND_MAIL_ERROR;

@Service
public class EmailServiceImpl implements EmailService {

    private final CustomerRepository customerRepository;

    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String sender;

    @Autowired
    public EmailServiceImpl(CustomerRepository customerRepository, JavaMailSender javaMailSender) {
        this.customerRepository = customerRepository;
        this.javaMailSender = javaMailSender;
    }

    @Override
    public String sendSimpleEmail(Long idCustomer, SendMailRequest emailDetails) {
        Customer customer = getCustomer(idCustomer);
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(customer.getEmail());
            mailMessage.setSubject(emailDetails.getSubject());
            mailMessage.setText(emailDetails.getMsgBody());
            mailMessage.setFrom(sender);
            javaMailSender.send(mailMessage);
            return "Email sent successfully";
        } catch (Exception e) {
            e.printStackTrace();
            throw new SendEmailException(SEND_MAIL_ERROR);
        }
    }


    @Override
    public String sendEmailWithAttachment(Long idCustomer, SendMailRequest emailDetails) {
        Customer customer = getCustomer(idCustomer);
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper;
        try {
            mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom(sender);
            mimeMessageHelper.setTo(customer.getEmail());
            mimeMessageHelper.setSubject(emailDetails.getSubject());
            mimeMessageHelper.setText(emailDetails.getMsgBody());
            //add attachment
            FileSystemResource fileSystemResource = new FileSystemResource(emailDetails.getAttachment());
            mimeMessageHelper.addAttachment(Objects.requireNonNull(fileSystemResource.getFilename()), fileSystemResource);
            javaMailSender.send(mimeMessage);
            return "Email sent successfully";
        } catch (MessagingException e) {
            e.printStackTrace();
            throw new SendEmailException(SEND_MAIL_ERROR);
        }
    }

    private Customer getCustomer(Long idCustomer) {
        return this.customerRepository.findById(idCustomer).orElseThrow(
                () -> new ResourceNotFoundException(CUSTOMER_NOT_FOUND));
    }
}
