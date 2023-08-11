package project.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import project.entity.Hall;
import project.repository.HallRepository;
import project.service.HallService;

import java.util.List;
@Service
public class HallServiceImpl implements HallService {
    private final HallRepository hallRepository;

    public HallServiceImpl(HallRepository hallRepository) {
        this.hallRepository = hallRepository;
    }
    private Logger logger = LogManager.getLogger("serviceLogger");

    @Override
    public List<Hall> getAllHalls() {
        logger.info("getAllHalls() - Finding all halls");
        List<Hall> halls = hallRepository.findAll();
        logger.info("getAllHalls() - All halls were found");
        return halls;
    }

    @Override
    public Hall getHallById(Long id) {
        logger.info("getHallById() - Finding hall by id "+id);
        Hall hall = hallRepository.findById(id).get();
        logger.info("getHallById() - Hall was found");
        return hall;
    }
}
