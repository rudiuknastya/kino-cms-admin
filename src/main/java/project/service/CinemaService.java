package project.service;

import project.entity.Cinema;
import project.entity.Hall;

import java.util.List;

public interface CinemaService {
    Cinema saveCinema(Cinema cinema);
    List<Cinema> getAllCinemas();
    Cinema getCinemaById(Long id);
    void deleteCinemaById(Long id);
}
