package project.service;

public interface MailSenderService {
    void sendEmail(String to, String file, boolean delete);
}
