package project.service.impl;

import jakarta.persistence.EntityNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import project.entity.AboutCinema;
import project.repository.AboutCinemaRepository;
import project.service.AboutCinemaService;
@Service
public class AboutCinemaServiceImpl implements AboutCinemaService {
    private final AboutCinemaRepository aboutCinemaRepository;

    public AboutCinemaServiceImpl(AboutCinemaRepository aboutCinemaRepository) {
        this.aboutCinemaRepository = aboutCinemaRepository;
    }
    private Logger logger = LogManager.getLogger("serviceLogger");

    @Override
    public AboutCinema saveAboutCinema(AboutCinema aboutCinema) {
        logger.info("saveAboutCinema() - Saving about cinema");
        AboutCinema aboutCinema1 = aboutCinemaRepository.save(aboutCinema);
        logger.info("saveAboutCinema() - About cinema was saved");
        return aboutCinema1;
    }

    @Override
    public AboutCinema getAboutCinema() {
        logger.info("getAboutCinema() - Finding about cinema");
        AboutCinema aboutCinema = aboutCinemaRepository.findById(1L).orElseThrow(EntityNotFoundException::new);
        logger.info("getAboutCinema() - About cinema was found");
        return aboutCinema;
    }
}
