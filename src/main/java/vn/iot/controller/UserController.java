package vn.iot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import vn.iot.enity.UserEntity;
import vn.iot.service.IUserService;

@Controller
public class UserController {
    @Autowired
    private IUserService userService;

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, Model model) {
        UserEntity user = userService.login(username, password);
        if (user != null) {
            model.addAttribute("user", user);
            return "redirect:/admin/categories";
        } else {
            model.addAttribute("error", "Invalid credentials");
            return "login";
        }
    }

    @GetMapping("/register")
    public String registerForm() {
        return "register";
    }

    @PostMapping("/register")
    public String register(@RequestParam String username, @RequestParam String password, @RequestParam String role, @RequestParam String fullname, Model model) {
        UserEntity user = new UserEntity();
        user.setUsername(username);
        user.setPassword(password);
        user.setRole(role);
        user.setFullname(fullname);
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
