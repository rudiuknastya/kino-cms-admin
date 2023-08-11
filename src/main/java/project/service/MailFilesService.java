package project.service;

import project.entity.MailFiles;

import java.util.List;

public interface MailFilesService {
    List<MailFiles> getAllMailFiles();
    MailFiles getMailFileById(Long id);
    MailFiles saveMailFiles(MailFiles mailFiles);
    void deleteMailFileById(Long id);
}
