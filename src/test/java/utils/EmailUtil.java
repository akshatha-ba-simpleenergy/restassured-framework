package utils;

import javax.mail.*;
import javax.mail.internet.*;
import java.io.File;
import java.util.Properties;

public class EmailUtil {

    public static void sendEmail() throws Exception {

        final String fromEmail = "akshatha.ba@simpleenergy.in";
        final String password = "dwfd cooo ayli ibsb"; // ⚠️ not normal password

        String toEmail = "software.qa@simpleenergy.in\r\n";

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(props,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(fromEmail, password);
                    }
                });

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(fromEmail));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
        message.setSubject("Automation Test Report");

        // Email body
        BodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setText("Hi,\n\nPlease find attached automation test report.\n\nThanks");

        // Attachment
        MimeBodyPart attachmentPart = new MimeBodyPart();
        attachmentPart.attachFile(new File("test-output/AutomationReport.pdf"));

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);
        multipart.addBodyPart(attachmentPart);

        message.setContent(multipart);

        Transport.send(message);

        System.out.println("✅ Email sent successfully!");
    }
}