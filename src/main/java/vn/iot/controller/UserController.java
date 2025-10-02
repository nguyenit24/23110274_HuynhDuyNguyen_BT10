package vn.iot.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import vn.iot.enity.UserEntity;
import vn.iot.service.IUserService;

@Controller
public class UserController {
    @Autowired
    private IUserService userService;

    @GetMapping("/login")
    public String loginForm(Model model) {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        Model model, HttpSession session) {
        UserEntity loggedIn = userService.login(username, password);
        if (loggedIn != null) {
            System.out.println("Logged in user: " + loggedIn.getUsername() + ", Role: " + loggedIn.getRole());
            session.setAttribute("user", loggedIn);
            model.addAttribute("user", loggedIn);
            if(loggedIn.getRole().equals("Admin")){
                return "redirect:/admin/categories";
            } else if(loggedIn.getRole().equals("User")){
                return  "redirect:/user/home";
            } else {
                model.addAttribute("error", "Unknown role");
                return "login";
            }
        } else {
            model.addAttribute("error", "Invalid credentials");
            return "login";
        }
    }

    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("user", new UserEntity());
        return "register";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("user") UserEntity user,
                           BindingResult result,
                           Model model) {
        if (result.hasErrors()) {
            return "register";
        }

        UserEntity registered = userService.register(user);
        if (registered != null) {
            model.addAttribute("message", "Registration successful");
            return "login";
        } else {
            model.addAttribute("error", "Username already exists");
            return "register";
        }
    }

    @GetMapping
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }


    @GetMapping("user/home")
    public String home() {
        return "user/home";
    }
}