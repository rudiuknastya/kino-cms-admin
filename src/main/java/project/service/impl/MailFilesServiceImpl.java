package project.service.impl;

import jakarta.persistence.EntityNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import project.entity.MailFile;
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
    public List<MailFile> getAllMailFiles() {
        logger.info("getMailFiles() - Finding all mail files");
        List<MailFile> mailFiles = mailFilesRepository.findAll();
        logger.info("getMailFiles() - All mail files were found");
        return mailFiles;
    }

    @Override
    public MailFile getMailFileById(Long id) {
        logger.info("getMailFileById() - Finding mail file by id "+id);
        MailFile mailFiles = mailFilesRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        logger.info("getMailFileById() - Mail files was found");
        return mailFiles;
    }

    @Override
    public MailFile saveMailFiles(MailFile mailFiles) {
        logger.info("saveMailFiles() - Saving mail file");
        MailFile mailFiles1 = mailFilesRepository.save(mailFiles);
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
