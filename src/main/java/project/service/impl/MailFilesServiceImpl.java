package project.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import project.entity.MailFiles;
import project.repository.MailFilesRepository;
import project.service.MailFilesService;

import java.util.List;

@Service
public class MailFilesServiceImpl implements MailFilesService {
    private final MailFilesRepository mailFilesRepository;

    public MailFilesServiceImpl(MailFilesRepository mailFilesRepository) {
        this.mailFilesRepository = mailFilesRepository;
    }
    private Logger logger = LogManager.getLogger("serviceLogger");
    @Override
    public List<MailFiles> getAllMailFiles() {
        logger.info("getMailFiles() - Finding all mail files");
        List<MailFiles> mailFiles = mailFilesRepository.findAll();
        logger.info("getMailFiles() - All mail files were found");
        return mailFiles;
    }

    @Override
    public MailFiles getMailFileById(Long id) {
        logger.info("getMailFileById() - Finding mail file by id "+id);
        MailFiles mailFiles = mailFilesRepository.findById(id).get();
        logger.info("getMailFileById() - Mail files was found");
        return mailFiles;
    }

    @Override
    public MailFiles saveMailFiles(MailFiles mailFiles) {
        logger.info("saveMailFiles() - Saving mail file");
        MailFiles mailFiles1 = mailFilesRepository.save(mailFiles);
        logger.info("saveMailFiles() - Mail file was saved");
        return mailFiles1;
    }

    @Override
    public void deleteMailFileById(Long id) {
        logger.info("deleteMailFileById() - Deleting mail file by id "+id);
        mailFilesRepository.deleteById(id);
        logger.info("deleteMailFileById() - Mail file was deleted");
    }
}
