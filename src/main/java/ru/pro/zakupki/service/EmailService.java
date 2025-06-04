package ru.pro.zakupki.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender mailSender;

    public void sendConfirmationEmail(String to, String token) {
        String frontendLink = "http://localhost:5173/log?token=" + token;

        String htmlContent = """
                <html>
                    <body style="font-family: Arial, sans-serif; background-color: #f8f8f8; padding: 20px;">
                        <div style="background-color: #ffffff; padding: 20px; border-radius: 10px; text-align: center;">
                            <h2 style="color: #333333;">Добро пожаловать в ЗакупкиПро!</h2>
                            <p style="color: #555555;">Пожалуйста, подтвердите ваш email, нажав на кнопку ниже:</p>
                            <a href="%s" style="display: inline-block; padding: 10px 20px; background-color: #4CAF50; color: white; border-radius: 5px; text-decoration: none;">Подтвердить Email</a>
                            <p style="color: #999999; margin-top: 20px;">Если вы не регистрировались, просто проигнорируйте это письмо.</p>
                        </div>
                    </body>
                </html>
                """.formatted(frontendLink);

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(to);
            helper.setSubject("Подтверждение регистрации");
            helper.setText(htmlContent, true);
            helper.setFrom("your_email@gmail.com");

            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Не удалось отправить email", e);
        }
    }

}