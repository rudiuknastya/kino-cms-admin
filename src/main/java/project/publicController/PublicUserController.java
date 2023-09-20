package project.publicController;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import project.entity.User;
import project.service.*;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

@Controller
public class PublicUserController {
    private final UserService userService;
    private final MainPageService mainPageService;
    private final AboutCinemaService aboutCinemaService;
    private final CafeService cafeService;
    private final VipHallService vipHallService;
    private final AdvertisementService advertisementService;
    private final ChildrenRoomService childrenRoomService;
    private final NewPageService newPageService;
    private final ContactsService contactsService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public PublicUserController(UserService userService, MainPageService mainPageService, AboutCinemaService aboutCinemaService, CafeService cafeService, VipHallService vipHallService, AdvertisementService advertisementService, ChildrenRoomService childrenRoomService, NewPageService newPageService, ContactsService contactsService) {
        this.userService = userService;
        this.mainPageService = mainPageService;
        this.aboutCinemaService = aboutCinemaService;
        this.cafeService = cafeService;
        this.vipHallService = vipHallService;
        this.advertisementService = advertisementService;
        this.childrenRoomService = childrenRoomService;
        this.newPageService = newPageService;
        this.contactsService = contactsService;
    }

    @GetMapping("/user/profile")
    public String showUser(Principal principal,Model model){
        model.addAttribute("user",userService.getUserByEmail(principal.getName()));
        model.addAttribute("mainPage",mainPageService.getMainPage());
        List<String> cities = List.of("Київ","Львів","Харків","Дніпро","Одеса");
        model.addAttribute("cities",cities);
        model.addAttribute("newPages",newPageService.getEnabledNewPages());
        model.addAttribute("aboutCinemaPage",aboutCinemaService.getAboutCinema());
        model.addAttribute("cafePage",cafeService.getCafe());
        model.addAttribute("vipHallPage",vipHallService.getVipHall());
        model.addAttribute("adPage",advertisementService.getAd());
        model.addAttribute("childrenRoomPage",childrenRoomService.getChildrenRoom());
        model.addAttribute("contactPage", contactsService.getContact());
        return "user/public_user";
    }
    @PostMapping("/user/profile")
    public String updatePublicUser(@Valid @ModelAttribute("user") User user, BindingResult bindingResult,Model model){
        if (bindingResult.hasErrors()) {
            model.addAttribute("mainPage", mainPageService.getMainPage());
            List<String> cities = List.of("Київ", "Львів", "Харків", "Дніпро", "Одеса");
            model.addAttribute("cities", cities);
            model.addAttribute("newPages", newPageService.getEnabledNewPages());
            model.addAttribute("aboutCinemaPage", aboutCinemaService.getAboutCinema());
            model.addAttribute("cafePage", cafeService.getCafe());
            model.addAttribute("vipHallPage", vipHallService.getVipHall());
            model.addAttribute("adPage", advertisementService.getAd());
            model.addAttribute("childrenRoomPage", childrenRoomService.getChildrenRoom());
            model.addAttribute("contactPage", contactsService.getContact());
            return "user/public_user";
        }
        User userInDB = userService.getUserById(user.getId());
        userInDB.setFirstName(user.getFirstName());
        userInDB.setLastName(user.getLastName());
        userInDB.setPseudonym(user.getPseudonym());
        userInDB.setEmail(user.getEmail());
        if(!user.getPassword().equals("")) {
            String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
            userInDB.setPassword(encodedPassword);
        }
        System.out.println(user.getPassword());
        userInDB.setCardNumber(user.getCardNumber());
        userInDB.setSex(user.getSex());
        userInDB.setPhoneNumber(user.getPhoneNumber());
        userInDB.setBirthDate(user.getBirthDate());
        userInDB.setCity(user.getCity());
        userInDB.setAddress(user.getAddress());
        userInDB.setLanguage(user.getLanguage());
        userService.updateUser(userInDB);
        return "redirect:/user/profile";
    }
    @GetMapping("/register")
    public String registerUser(Model model){
        model.addAttribute("user",new User());
        model.addAttribute("mainPage",mainPageService.getMainPage());
        List<String> cities = List.of("Київ","Львів","Харків","Дніпро","Одеса");
        model.addAttribute("cities",cities);
        model.addAttribute("newPages",newPageService.getEnabledNewPages());
        model.addAttribute("aboutCinemaPage",aboutCinemaService.getAboutCinema());
        model.addAttribute("cafePage",cafeService.getCafe());
        model.addAttribute("vipHallPage",vipHallService.getVipHall());
        model.addAttribute("adPage",advertisementService.getAd());
        model.addAttribute("childrenRoomPage",childrenRoomService.getChildrenRoom());
        model.addAttribute("contactPage", contactsService.getContact());
        return "security/registrationForm";
    }
    @PostMapping("/register")
    public String saveUser(@Valid @ModelAttribute("user") User user, BindingResult bindingResult, Model model){

        if (bindingResult.hasErrors()) {
            model.addAttribute("mainPage",mainPageService.getMainPage());
            List<String> cities = List.of("Київ","Львів","Харків","Дніпро","Одеса");
            model.addAttribute("cities",cities);
            model.addAttribute("newPages",newPageService.getEnabledNewPages());
            model.addAttribute("aboutCinemaPage",aboutCinemaService.getAboutCinema());
            model.addAttribute("cafePage",cafeService.getCafe());
            model.addAttribute("vipHallPage",vipHallService.getVipHall());
            model.addAttribute("adPage",advertisementService.getAd());
            model.addAttribute("childrenRoomPage",childrenRoomService.getChildrenRoom());
            model.addAttribute("contactPage", contactsService.getContact());
            return "security/registrationForm";
        }
        user.setRegistrationDate(LocalDate.now());
        String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        user.setRole("USER");
        userService.saveUser(user);
        return "redirect:/";
    }
}
