package com.webtechproject.backend.service;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import java.util.Properties;

@Service
public class OTPService {

    private final Map<String, String> otpCache = new HashMap<>();

    public String generateOTP(String email) {
        String otp = String.format("%06d", new Random().nextInt(999999));
        otpCache.put(email, otp);
        return otp;
    }

    public boolean validateOTP(String email, String otp) {
        String storedOtp = otpCache.get(email);
        return storedOtp != null && storedOtp.equals(otp);
    }

    public void sendOTPEmail(String to, String otp) {
        String from = "raissawebtech@gmail.com"; //  email
        String password = "rxdw flvu lrsd pnra"; // email password

        // Email server configuration
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject("Your OTP Code");
            message.setText("Your OTP code is: " + otp);

            Transport.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
