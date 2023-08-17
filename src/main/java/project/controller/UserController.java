package project.controller;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserService userService;
    private Integer num = 8;
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/admin/users")
    public String getUsersList(Model model){
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("page", num);
        return "user/users";
    }
    @GetMapping("/admin/users/edit/{id}")
    public String editUser(@PathVariable Long id, Model model){
        model.addAttribute("user",userService.getUserById(id));
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
        userInDB.setFirstName(user.getFirstName());
        userInDB.setLastName(user.getLastName());
        userInDB.setPseudonym(user.getPseudonym());
        userInDB.setEmail(user.getEmail());
        String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
        userInDB.setPassword(encodedPassword);
        userInDB.setCardNumber(user.getCardNumber());
        userInDB.setSex(user.getSex());
        userInDB.setPhoneNumber(user.getPhoneNumber());
        userInDB.setBirthDate(user.getBirthDate());
        userInDB.setCity(user.getCity());
        userInDB.setAddress(user.getAddress());
        userInDB.setLanguage(user.getLanguage());
        userService.updateUser(userInDB);
        return "redirect:/admin/users";
    }
    @GetMapping("/admin/users/delete/{id}")
    public String deleteUser(@PathVariable Long id){
        userService.deleteUserById(id);
        return "redirect:/admin/users";
    }
}
