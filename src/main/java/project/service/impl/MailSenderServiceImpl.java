package project.service.impl;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import project.service.MailSenderService;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

@Service
public class MailSenderServiceImpl implements MailSenderService {
    @Autowired
    private JavaMailSender mailSender;

    private String uploadPath = "/Users/Anastassia/IdeaProjects/Kino-CMS_admin/uploads";
    @Override
    public void sendEmail(String to, String file) {
        System.out.println(to);
        System.out.println(file);
        MimeMessage message = mailSender.createMimeMessage();

        try {
            message.setFrom(new InternetAddress("ruduknasta13@gmail.com"));
            message.setRecipients(MimeMessage.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject("Test email from Spring");

            String htmlTemplate = readFile(file);
            message.setContent(htmlTemplate, "text/html; charset=utf-8");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        mailSender.send(message);
//        MimeMessage message = mailSender.createMimeMessage();
//
//        try {
//            message.setFrom(new InternetAddress("sender@example.com"));
//            message.setRecipients(MimeMessage.RecipientType.TO, to);
//            message.setSubject("Test email from my Springapplication");
//            String htmlTemplate = readFile("template.html");
//            message.setContent(htmlTemplate, "text/html; charset=utf-8");
//        } catch (MessagingException e) {
//            throw new RuntimeException(e);
//        }
//        mailSender.send(message);
    }

    private String readFile(String file){
        StringBuilder html = new StringBuilder();
        String result="";

        FileReader fr = null;
        try {
            fr = new FileReader(uploadPath+"/"+file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            BufferedReader br = new BufferedReader(fr);
            String val;
            while ((val = br.readLine()) != null) {
                html.append(val);
            }
            result = html.toString();
            System.out.println(result);
            br.close();
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return result;
    }
}
