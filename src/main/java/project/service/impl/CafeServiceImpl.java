package project.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import project.entity.Cafe;
import project.repository.CafeRepository;
import project.service.CafeService;
@Service
public class CafeServiceImpl implements CafeService {
    private final CafeRepository cafeRepository;

    public CafeServiceImpl(CafeRepository cafeRepository) {
        this.cafeRepository = cafeRepository;
    }
    private Logger logger = LogManager.getLogger("serviceLogger");

    @Override
    public Cafe saveCafe(Cafe cafe) {
        logger.info("saveCafe() - Saving cafe");
        Cafe cafe1 = cafeRepository.save(cafe);
        logger.info("saveCafe() - Cafe was saved");
        return cafe1;
    }

    @Override
    public Cafe getCafe() {
        logger.info("getCafe() - Finding cafe");
        Cafe cafe = cafeRepository.findById(1L).get();
        logger.info("getCafe() - Cafe was found");
        return cafe;
    }
}
