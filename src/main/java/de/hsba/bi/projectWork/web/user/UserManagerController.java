package de.hsba.bi.projectWork.web.user;

import de.hsba.bi.projectWork.project.ProjectService;
import de.hsba.bi.projectWork.task.Booking;
import de.hsba.bi.projectWork.task.BookingService;
import de.hsba.bi.projectWork.task.Task;
import de.hsba.bi.projectWork.task.TaskService;
import de.hsba.bi.projectWork.user.User;
import de.hsba.bi.projectWork.user.UserService;
import de.hsba.bi.projectWork.user.manager.UserManagerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;


@Controller
@RequestMapping("/userManager")
@RequiredArgsConstructor
public class UserManagerController {

    private final UserManagerService userManagerService;
    private final UserService userService;
    private final TaskService taskService;
    private final BookingService bookingService;
    private final ProjectService projectService;

    // dashboard
    @GetMapping
    public String index(Model model) {
        return "userManager/index";
    }


    // Als Entwickler oder Manager kann ich pro Aufgabe sehen, wieviel Zeit insgesamt schon darauf gebucht wurde (allerdings nicht von wem)
    @GetMapping("/projects")
    public String viewProjects(Model model) {
        model.addAttribute("projects", projectService.findAll());
        return "userManager/projects";
    }

    @GetMapping("/viewTask/{taskId}")
    public String viewTask(@PathVariable("taskId") Long taskId, Model model) {
        Task task = taskService.findById(taskId);
        model.addAttribute("task", task);
        return "userManager/viewTask";
    }

    @PostMapping("/deleteBookedTime")
    public String deleteBookedTime(@RequestParam("taskId") Long taskId, @RequestParam("bookingId") Long bookingId) {
        Task task = taskService.findById(taskId);
        Booking booking = bookingService.findById(bookingId);
        userManagerService.deleteBookedTime(task, booking);
        return "redirect:/userManager/viewTask/"+taskId;
    }


    // Als Entwickler oder Manager kann ich mir eine Liste der Aufgaben gefiltert nach Status anzeigen lassen.
    @GetMapping("/tasks")
    public String viewTasks(@RequestParam(value = "status", required = false) String status, Model model) {
        if (status != null) {
            if (status.equals("Idea") || status.equals("Planned") || status.equals("wip") || status.equals("Testing") || status.equals("Done")) {
                model.addAttribute("tasks", taskService.findByStatus(status));
            }
        }
        else {
            model.addAttribute("tasks", taskService.findAll());
        }
        return "userManager/tasks";
    }


    // Als Manager kann ich die gebuchten Zeiten aller Entwickler einsehen
    @GetMapping("/users")
    public String viewUsers(Model model) {
        model.addAttribute("users", userService.findUsers());
        return "userManager/users";
    }


    // Als Manager kann ich eine Statistik einsehen, bei der die geschätzten und gebuchten Zeiten gegenübergestellt werden
    @PostMapping("/compareEstimatedAndBookedTimes")
    public String compareEstimatedAndBookedTimes() {
        HashMap<User, Task> comparison = userManagerService.compareEstimatedAndBookedTimes();
        return "redirect:/userManager";
    }

}