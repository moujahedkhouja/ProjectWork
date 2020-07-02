package de.hsba.bi.projectWork.web.user;

import de.hsba.bi.projectWork.project.Project;
import de.hsba.bi.projectWork.project.ProjectService;
import de.hsba.bi.projectWork.user.User;
import de.hsba.bi.projectWork.user.UserService;
import de.hsba.bi.projectWork.user.admin.UserAdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping("/userAdmin")
@RequiredArgsConstructor
public class UserAdminController {

    private final UserAdminService userAdminService;
    private final UserService userService;
    private final ProjectService projectService;

    // dashboard
    @GetMapping
    public String index() {
        return "userAdmin/index";
    }


    // Als Admin kann ich die Rollen anderer Nutzer ändern
    @GetMapping("/users")
    public String users(Model model) {
        model.addAttribute("users", userService.findAll());
        return "userAdmin/users";
    }

    @PostMapping("/changeRole")
    public String changeRole(@RequestParam("id") Long id, @RequestParam("role") String role) {
        userAdminService.changeRole(id, role);
        return "redirect:/userAdmin/users";
    }


    // Als Admin kann ich andere Nutzer (jeder Rolle) zu meinem Projekt hinzufügen
    @GetMapping("/projects")
    public String projects(Model model) {
        model.addAttribute("projects", projectService.findAll());
        model.addAttribute("newProject", new Project());
        model.addAttribute("users", userService.findAll());
        return "userAdmin/projects";
    }

    @GetMapping("/project/{id}")
    public String project(@PathVariable Long id, Model model) {
        model.addAttribute("project", projectService.findById(id));
        model.addAttribute("usersNotInProject", projectService.findUsersNotInProject(id));
        return "userAdmin/project";
    }

    @PostMapping("/addUserToProject")
    public String addUserToProject(@RequestParam("projectId") Long projectId, @RequestParam("users") List<User> users) {
        userAdminService.addUserToProject(projectId, users);
        return "redirect:/userAdmin/projects";
    }

    @PostMapping("/removeUserFromProject")
    public String removeUserFromProject(@RequestParam("projectId") Long projectId, @RequestParam("users") List<User> users) {
        userAdminService.removeUserFromProject(projectId, users);
        return "redirect:/userAdmin/projects";
    }


    // Als Admin kann ich ein neues Projekt anlegen
    @PostMapping("/createNewProject")
    public String createNewProject(@ModelAttribute("project") Project project) {
        userAdminService.createNewProject(project);
        return "redirect:/userAdmin/projects";
    }
}