package de.hsba.bi.projectwork.web;

import de.hsba.bi.projectwork.project.ProjectService;
import de.hsba.bi.projectwork.task.BookingService;
import de.hsba.bi.projectwork.task.Task;
import de.hsba.bi.projectwork.task.TaskService;
import de.hsba.bi.projectwork.user.UserService;
import de.hsba.bi.projectwork.web.task.TaskForm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@Controller
@RequestMapping("/userDeveloper")
@PreAuthorize("hasRole('ROLE_DEVELOPER')")
@RequiredArgsConstructor
public class UserDeveloperController {

    private final UserService userService;
    private final ProjectService projectService;
    private final TaskService taskService;
    private final BookingService bookingService;

    // dashboard
    @GetMapping
    public String index() {
        return "userDeveloper/index";
    }


    // Als Entwickler in einem Projekt kann ich eine Aufgabe zu diesem Projekt hinzufügen, diese beinhaltet wenigstens einen Titel und eine Beschreibung
    @GetMapping("/projects")
    public String viewProjects(Model model) {
        // TODO Als Entwickler in einem Projekt kann ich sehen, wieviel Zeit ich insgesamt (aufgabenübergreifend) gebucht habe (allerdings nicht die Zeiten anderer Entwickler)
        model.addAttribute("projects", projectService.findUsersProjects());
        return "userDeveloper/projects";
    }

    @GetMapping("/addTask")
    public String addTaskMask(Model model, @RequestParam("projectId") Long projectId) {
        model.addAttribute("task", new Task());
        model.addAttribute("project", projectService.findById(projectId));
        return "userDeveloper/addTask";
    }

    @PostMapping("/addTask")
    public String addTask(@ModelAttribute("task") Task task, @RequestParam("projectId") Long projectId) {
        taskService.addNewTask(task, projectId);
        return "redirect:/userDeveloper/projects";
    }


    // Als Entwickler oder Manager kann ich pro Aufgabe sehen, wieviel Zeit insgesamt schon darauf gebucht wurde (allerdings nicht von wem)
    @GetMapping("/viewTask/{taskId}")
    public String viewTask(@PathVariable("taskId") Long taskId, Model model) {
        Task task = taskService.findById(taskId);
        task.calcTotalTime();
        model.addAttribute("task", task);
        model.addAttribute("taskForm", new TaskForm());
        model.addAttribute("project", projectService.findById(task.getProject().getId()));
        model.addAttribute("remainingStatuses", taskService.findRemainingStatuses(taskId));
        return "userDeveloper/viewTask";
    }

    @PostMapping("/editTask/{taskId}")
    public String update(@PathVariable("taskId") Long taskId, @ModelAttribute("taskForm") @Valid TaskForm form, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "redirect:/userDeveloper/viewTask/"+taskId;
        }
        taskService.editTask(taskId, form);
        return "redirect:/userDeveloper/viewTask/"+taskId;
    }


    //  Als Entwickler in einem Projekt kann ich aufgewendete Zeiten für eine Aufgabe buchen (diese Buchung gilt pro Aufgabe und Entwickler, außerdem kann ein Entwickler mehrmals Zeiten buchen, wenn die Bearbeitung beispielsweise mehrere Tage dauert)
    @PostMapping("/bookTime")
    public String bookTime(@RequestParam("taskId") Long taskId, @RequestParam("projectId") Long projectId, @RequestParam("date") String date, @RequestParam("time") int time) {
        bookingService.bookTime(taskId, projectId, time, date, userService.findCurrentUser());
        return "redirect:/userDeveloper/viewTask/"+taskId;
    }


    // Als Entwickler oder Manager kann ich mir eine Liste der Aufgaben gefiltert nach Status anzeigen lassen.
    @GetMapping("/tasks")
    public String viewTasks(@RequestParam(value = "status", required = false) String status, Model model) {
        model.addAttribute("tasks", taskService.findTasksByStatusAndUser(status));
        return "userDeveloper/tasks";
    }

}