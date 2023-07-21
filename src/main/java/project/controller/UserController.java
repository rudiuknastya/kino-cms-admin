package project.controller;

import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import project.entity.User;
import project.service.UserService;

import java.util.List;

@Controller
public class UserController {
    private final UserService userService;
    private Logger logger = LogManager.getLogger("serviceLogger");
    private Integer num = 8;
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/admin/users")
    public String getUsersList(Model model){
        model.addAttribute("users", userService.getAllUsers());
        logger.info("Got all users");
        model.addAttribute("page", num);
        return "user/users";
    }
    @GetMapping("/admin/users/edit/{id}")
    public String editUser(@PathVariable Long id, Model model){
        model.addAttribute("user",userService.getUserById(id));
        logger.info("Got user by id "+id+" for editing");
        List<String> cities = List.of("Київ","Львів","Харків","Дніпро","Одеса");
        model.addAttribute("cities",cities);
        model.addAttribute("pagenum", num);
        return "user/edit_user";
    }
    @PostMapping("/admin/user/{id}")
    public String updateUser(@PathVariable Long id, @Valid @ModelAttribute("user") User user,  BindingResult bindingResult, Model model){
        List<String> cities = List.of("Київ","Львів","Харків","Дніпро","Одеса");
        model.addAttribute("cities",cities);
        model.addAttribute("pagenum", num);
        if (bindingResult.hasErrors()) {
            return "user/edit_user";
        }
        User userInDB = userService.getUserById(id);
        logger.info("Got user by id "+id+" for updating. First name: "+ userInDB.getFirstName());
        userInDB.setFirstName(user.getFirstName());
        userInDB.setLastName(user.getLastName());
        userInDB.setPseudonym(user.getPseudonym());
        userInDB.setEmail(user.getEmail());
        userInDB.setPassword(user.getPassword());
        userInDB.setCardNumber(user.getCardNumber());
        userInDB.setSex(user.getSex());
        userInDB.setPhoneNumber(user.getPhoneNumber());
        userInDB.setBirthDate(user.getBirthDate());
        userInDB.setCity(user.getCity());
        userInDB.setAddress(user.getAddress());
        userInDB.setLanguage(user.getLanguage());
        userService.updateUser(userInDB);
        logger.info("Updated user");
        return "redirect:/admin/users";
    }
    @GetMapping("/admin/users/delete/{id}")
    public String deleteUser(@PathVariable Long id){
        userService.deleteUserById(id);
        logger.info("Deleted user with id "+id);
        return "redirect:/admin/users";
    }
}
