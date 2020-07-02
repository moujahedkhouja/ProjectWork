package de.hsba.bi.expenses.web;

import de.hsba.bi.expenses.user.User;
import de.hsba.bi.expenses.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class IndexController {

    private final UserService userService;

    @GetMapping
    public String dashboard(Model model) {
        model.addAttribute("authenticatedUser", userService.findCurrentUser());
        model.addAttribute("user", new User());
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        switch (auth.getAuthorities().toString()) {
            case "[ROLE_ADMIN]":
                return "redirect:/userAdmin";
            case "[ROLE_DEVELOPER]":
                return "redirect:/userDeveloper";
            case "[ROLE_MANAGER]":
                return "redirect:/userManager";
        }
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth instanceof AnonymousAuthenticationToken ? "login" : "redirect:/";
    }

    // Als Nutzer kann ich mich registrieren und bekomme standardmäßig die Rolle "Entwickler"
    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute("user") User user) {
        userService.createUser(user.getName(), user.getPassword(), User.DEVELOPER_ROLE);
        return "register";
    }


    // Als Nutzer kann ich mein Passwort ändern
    @GetMapping("/account")
    public String account(Model model) {
        model.addAttribute("user", userService.findCurrentUser());
        return "account";
    }

    @PostMapping("/changePassword")
    public String changePassword(@RequestParam("oldPassword") String oldPassword, @RequestParam("newPassword") String newPassword, @RequestParam("newPasswordConfirmation") String newPasswordConfirmation) {
        userService.changePassword(oldPassword, newPassword, newPasswordConfirmation);
        return "redirect:/account";
    }

}
