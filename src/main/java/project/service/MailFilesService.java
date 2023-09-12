package project.service;

import project.entity.MailFile;

import java.util.List;

public interface MailFilesService {
    List<MailFile> getAllMailFiles();
    MailFile getMailFileById(Long id);
    MailFile saveMailFiles(MailFile mailFiles);
    void deleteMailFileById(Long id);
}
