package de.hsba.bi.projectWork.web.user;

import de.hsba.bi.projectWork.project.Project;
import de.hsba.bi.projectWork.project.ProjectService;
import de.hsba.bi.projectWork.user.User;
import de.hsba.bi.projectWork.user.UserService;
import de.hsba.bi.projectWork.web.project.UpdateProjectForm;
import lombok.RequiredArgsConstructor;
import org.hibernate.sql.Update;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("/userAdmin")
@RequiredArgsConstructor
public class UserAdminController {

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
        userService.changeRole(id, role);
        return "redirect:/userAdmin/users";
    }


    // Als Admin kann ich andere Nutzer (jeder Rolle) zu meinem Projekt hinzufügen
    @GetMapping("/projects")
    public String projects(Model model) {
        model.addAttribute("projects", projectService.findAll());
        model.addAttribute("newProject", new Project());
        model.addAttribute("users", userService.findAll());
        model.addAttribute("updateProjectForm", new UpdateProjectForm());
        return "userAdmin/projects";
    }


    @PostMapping("/addUserToProject")
    public String addUserToProject(@ModelAttribute("updateProjectForm") UpdateProjectForm updateProjectForm, @RequestParam("projectId") Long projectId) {
        projectService.addUserToProject(updateProjectForm, projectId);
        return "redirect:/userAdmin/projects";
    }

    @PostMapping("/removeUserFromProject")
    public String removeUserFromProject(@ModelAttribute("updateProjectForm") UpdateProjectForm updateProjectForm, @RequestParam("projectId") Long projectId) {
        projectService.removeUserFromProject(updateProjectForm, projectId);
        return "redirect:/userAdmin/projects";
    }


    // Als Admin kann ich ein neues Projekt anlegen
    @PostMapping("/createNewProject")
    public String createNewProject(@ModelAttribute("project") Project project) {
        projectService.save(project);
        return "redirect:/userAdmin/projects";
    }
}