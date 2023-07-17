package project.controller;

import jakarta.validation.Valid;
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

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public String getUsersList(Model model){
        Integer a = 8;
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("page", a);
        return "users";
    }
    @GetMapping("/users/edit/{id}")
    public String editUser(@PathVariable Long id, Model model){
        Integer c = 8;
        model.addAttribute("user",userService.getUserById(id));
        List<String> cities = List.of("Київ","Львів","Харків","Дніпро","Одеса");
        model.addAttribute("cities",cities);
        model.addAttribute("pagenum", c);
        return "edit_user";
    }
    @PostMapping("/user/{id}")
    public String updateUser(@PathVariable Long id, @Valid @ModelAttribute("user") User user,  BindingResult bindingResult, Model model){
        Integer d = 8;
        List<String> cities = List.of("Київ","Львів","Харків","Дніпро","Одеса");
        model.addAttribute("cities",cities);
        model.addAttribute("pagenum", d);
        if (bindingResult.hasErrors()) {
            return "edit_user";
        }
        User userInDB = userService.getUserById(id);
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
        return "redirect:/users";
    }
    @GetMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable Long id){
        userService.deleteUserById(id);
        return "redirect:/users";
    }
}
