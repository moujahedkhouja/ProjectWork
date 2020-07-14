package de.hsba.bi.projectwork.web;

import de.hsba.bi.projectwork.web.user.ChangePasswordForm;
import de.hsba.bi.projectwork.user.User;
import de.hsba.bi.projectwork.user.UserService;
import de.hsba.bi.projectwork.web.exception.IncorrectPasswordException;
import de.hsba.bi.projectwork.web.exception.UserAlreadyExistException;
import de.hsba.bi.projectwork.web.user.RegisterUserForm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
        model.addAttribute("user", new RegisterUserForm());
        return "register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute("user") @Valid RegisterUserForm userForm, BindingResult bindingResult, Model model) {
        if (!bindingResult.hasErrors()) {
            try {
                User registered = userService.createUser(userForm, "DEVELOPER");
                model.addAttribute("user", userForm);
                model.addAttribute("message", "You've successfully registered.");
                return "login";
            } catch (UserAlreadyExistException uaeEx) {
                model.addAttribute("user", userForm);
                return "register";
            }
        }
        model.addAttribute("user", userForm);
        return "register";
    }


    // Als Nutzer kann ich mein Passwort ändern
    @GetMapping("/account")
    public String account(Model model) {
        model.addAttribute("user", userService.findCurrentUser());
        model.addAttribute("changePasswordForm", new ChangePasswordForm());
        return "account";
    }

    @PreAuthorize("hasRole(authenticated)")
    @PostMapping("/changePassword")
    public String changePassword(@ModelAttribute("changePasswordForm") @Valid ChangePasswordForm changePasswordForm, BindingResult bindingResult, Model model) {
        if (!bindingResult.hasErrors()) {
            try {
                ChangePasswordForm changedPassword = userService.changePassword(changePasswordForm);
                model.addAttribute("user", userService.findCurrentUser());
                model.addAttribute("changePasswordForm", changePasswordForm);
                model.addAttribute("message", "You've successfully changed your password.");
                return "account";
            }
            catch (IncorrectPasswordException ipEx) {
                model.addAttribute("user", userService.findCurrentUser());
                model.addAttribute("changePasswordForm", changePasswordForm);
                return "account";
            }
        }
        model.addAttribute("user", userService.findCurrentUser());
        model.addAttribute("changePasswordForm", changePasswordForm);
        return "account";
    }

}
