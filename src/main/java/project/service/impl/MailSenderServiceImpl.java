package project.service.impl;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import project.service.MailSenderService;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

@Service
public class MailSenderServiceImpl implements MailSenderService {
    @Autowired
    private JavaMailSender mailSender;
    private Logger logger = LogManager.getLogger("serviceLogger");

    private String uploadPath = "/Users/Anastassia/IdeaProjects/Kino-CMS_admin/uploads";
    @Override
    public void sendEmail(String to, String file) {
        logger.info("sendEmail() - sending email to user "+to+" with file "+file);
        //MimeMessage message = mailSender.createMimeMessage();

        try {
            MimeMessage message = mailSender.createMimeMessage();
            message.setFrom(new InternetAddress("ruduknasta13@gmail.com"));
            message.setRecipients(MimeMessage.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject("Test email from Spring");

            String htmlTemplate = readFile(file);
            message.setContent(htmlTemplate, "text/html; charset=utf-8");
            mailSender.send(message);
            logger.info("sendEmail() - email was sent");
        } catch (MessagingException e) {
            logger.warn(e.getMessage());
            //throw new RuntimeException(e);
        }

    }

    private String readFile(String file){
        StringBuilder html = new StringBuilder();
        String result="";

        FileReader fr = null;
        try {
            fr = new FileReader(uploadPath+"/"+file);
        } catch (FileNotFoundException e) {
            logger.warn(e.getMessage());
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
        catch (IOException ex) {
            logger.warn(ex.getMessage());
            System.out.println(ex.getMessage());
        }
        return result;
    }
}
