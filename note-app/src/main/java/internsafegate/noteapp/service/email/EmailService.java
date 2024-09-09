package internsafegate.noteapp.service.email;

import jakarta.mail.MessagingException;
import org.springframework.mail.SimpleMailMessage;

public interface EmailService {
    void sendEmail(String toEmail, String subject, String body) throws MessagingException;
}
