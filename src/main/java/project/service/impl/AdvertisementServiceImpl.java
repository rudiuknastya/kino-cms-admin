package project.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import project.entity.Advertisement;
import project.repository.AdvertisementRepository;
import project.service.AdvertisementService;
@Service
public class AdvertisementServiceImpl implements AdvertisementService {
    private final AdvertisementRepository advertisementRepository;

    public AdvertisementServiceImpl(AdvertisementRepository advertisementRepository) {
        this.advertisementRepository = advertisementRepository;
    }
    private Logger logger = LogManager.getLogger("serviceLogger");
    @Override
    public Advertisement saveAd(Advertisement advertisement) {
        logger.info("saveAd() - Saving advertisement");
        Advertisement advertisement1 = advertisementRepository.save(advertisement);
        logger.info("saveAd() - Advertisement was saved");
        return advertisement1;
    }

    @Override
    public Advertisement getAd() {
        logger.info("getAd() - Finding advertisement");
        Advertisement advertisement = advertisementRepository.findById(1L).get();
        logger.info("getAd() - Advertisement was found");
        return advertisement;
    }
}
