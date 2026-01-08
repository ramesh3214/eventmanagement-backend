package com.backend.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.backend.backend.dto.Bookingemaildto;
import com.backend.backend.dto.Enquireyemail;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Autowired
    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    // ================= BOOKING EMAIL =================
    public void sendBookingConfirmation(Bookingemaildto body) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(body.getEmail());
        message.setSubject("Booking Confirmation - Eventify");
        message.setText(buildEmailBody(body));

        mailSender.send(message);
    }

    private String buildEmailBody(Bookingemaildto body) {
        return """
                Hello %s,

                🎉 Your booking has been successfully confirmed!

                📌 Event Name: %s
                📅 Event Date: %s

                Thank you for choosing Eventify.
                We look forward to making your event special!

                Best regards,
                Eventify Team
                """
                .formatted(
                        body.getName(),
                        body.getEventname(),
                        body.getDate()
                );
    }

    // ================= ENQUIRY EMAIL =================
    public void sendquery(Enquireyemail query) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);

        // Admin email (receives enquiry)
        message.setTo("vraj73833@gmail.com");

        message.setSubject("New Enquiry - Eventify");
        message.setText(buildEmailBodyforquery(query));

        mailSender.send(message);
        // ❌ NO EXCEPTION HERE
    }

    private String buildEmailBodyforquery(Enquireyemail body) {

        return """
                Hello %s,

                ✅ We have received a new enquiry.

                📧 Email: %s
                📞 Contact Number: %s
                📌 Subject: %s

                📝 Message:
                %s

                Please respond as soon as possible.

                Best regards,
                Eventify Support Team
                """
                .formatted(
                        body.getName(),
                        body.getEmail(),
                        body.getNumber(),
                        body.getSubject(),
                        body.getMassage()
                );
    }
}
