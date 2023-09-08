package project.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class SecurityController {
    @GetMapping("/admin/login")
    public String showLogin(Model model){
        return "security/loginForm";
    }
    @GetMapping("/login_user")
    public String showLoginUser(Model model){
        return "security/loginUser";
    }

    @GetMapping("/login-error")
    public String login(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession(false);
        String errorMessage = null;
        if (session != null) {
            AuthenticationException ex = (AuthenticationException) session
                    .getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
            if (ex != null) {
                errorMessage = ex.getMessage();
            }
        }
        model.addAttribute("errorMessage", errorMessage);
        return "security/error";
    }
    @GetMapping("/admin/logout")
    public String logout() {
        return "redirect:/admin/login";
    }
    @GetMapping("/logout_user")
    public String logoutUser() {
        return "redirect:/main_page";
    }
}
