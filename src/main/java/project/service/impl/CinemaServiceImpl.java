package project.service.impl;

import jakarta.persistence.EntityNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import project.entity.Cinema;
import project.repository.CinemaRepository;
import project.service.CinemaService;

import java.util.List;

@Service
public class CinemaServiceImpl implements CinemaService {
    private final CinemaRepository cinemaRepository;

    public CinemaServiceImpl(CinemaRepository cinemaRepository) {
        this.cinemaRepository = cinemaRepository;
    }
    private Logger logger = LogManager.getLogger("serviceLogger");
    @Override
    public Cinema saveCinema(Cinema cinema) {
        logger.info("saveCinema() - Saving cinema");
        Cinema savedCinema = cinemaRepository.save(cinema);
        logger.info("saveCinema() - Cinema was saved");
        return savedCinema;
    }

    @Override
    public List<Cinema> getAllCinemas() {
        logger.info("getAllCinemas() - Finding all cinemas");
        List<Cinema> cinemas = cinemaRepository.findAll();
        logger.info("getAllCinemas() - Cinemas were found");
        return cinemas;
    }

    @Override
    public Cinema getCinemaById(Long id) {
        logger.info("getCinemaById() - Finding cinema by id "+id);
        Cinema cinema = cinemaRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        logger.info("getCinemaById() - Cinema was found");
        return cinema;
    }

    @Override
    public void deleteCinemaById(Long id) {
        logger.info("deleteCinemaById() - Deleting cinema by id "+id);
        cinemaRepository.deleteById(id);
        logger.info("deleteCinemaById() - Cinema was deleted");
    }
}
