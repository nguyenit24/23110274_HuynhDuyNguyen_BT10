package vn.iot.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
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
        model.addAttribute("user", new UserEntity());
        return "login";
    }

    @PostMapping("/login")
    public String login(@Valid @ModelAttribute("user") UserEntity user,
                        BindingResult result,
                        Model model) {

        if (result.hasErrors()) {
            return "login";
        }

        UserEntity loggedIn = userService.login(user.getUsername(), user.getPassword());
        System.out.println("Logged in user: " + loggedIn.getUsername() + ", Role: " + loggedIn.getRole());
        if (loggedIn != null) {
            model.addAttribute("user", loggedIn);

            return "redirect:/admin/categories";
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
}