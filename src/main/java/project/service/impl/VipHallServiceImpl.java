package project.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import project.entity.VipHall;
import project.repository.VipHallRepository;
import project.service.VipHallService;
@Service
public class VipHallServiceImpl implements VipHallService {
    private final VipHallRepository vipHallRepository;

    public VipHallServiceImpl(VipHallRepository vipHallRepository) {
        this.vipHallRepository = vipHallRepository;
    }
    private Logger logger = LogManager.getLogger("serviceLogger");

    @Override
    public VipHall saveVipHall(VipHall vipHall) {
        logger.info("saveCafe() - Saving vip hall");
        VipHall vipHall1 = vipHallRepository.save(vipHall);
        logger.info("saveCafe() - Vip hall was saved");
        return vipHall1;
    }

    @Override
    public VipHall getVipHall() {
        logger.info("getVipHall() - Finding vip hall");
        VipHall vipHall = vipHallRepository.findById(1L).get();
        logger.info("getVipHall() - Vip hall was found");
        return vipHall;
    }
}
