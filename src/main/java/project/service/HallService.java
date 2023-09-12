package project.service;

import project.entity.Hall;

import java.util.List;

public interface HallService {
    List<Hall> getAllHalls();
    Hall getHallById(Long id);
}
