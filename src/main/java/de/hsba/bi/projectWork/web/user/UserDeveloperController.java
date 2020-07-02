package de.hsba.bi.projectWork.web.user;

import de.hsba.bi.projectWork.project.ProjectService;
import de.hsba.bi.projectWork.task.Task;
import de.hsba.bi.projectWork.task.TaskService;
import de.hsba.bi.projectWork.user.developer.UserDeveloperService;
import de.hsba.bi.projectWork.web.task.TaskForm;
import de.hsba.bi.projectWork.web.task.TaskFormConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@Controller
@RequestMapping("/userDeveloper")
@RequiredArgsConstructor
public class UserDeveloperController {

    private final UserDeveloperService userDeveloperService;
    private final ProjectService projectService;
    private final TaskService taskService;
    private final TaskFormConverter taskFormConverter;

    // dashboard
    @GetMapping
    public String index() {
        return "userDeveloper/index";
    }


    // Als Entwickler in einem Projekt kann ich eine Aufgabe zu diesem Projekt hinzufügen, diese beinhaltet wenigstens einen Titel und eine Beschreibung
    @GetMapping("/projects")
    public String viewProjects(Model model) {
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
        userDeveloperService.addNewTask(task, projectId);
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
        Task task = taskService.findById(taskId);
        taskService.save(taskFormConverter.update(task, form));
        return "redirect:/userDeveloper/viewTask/"+taskId;
    }


    //  Als Entwickler in einem Projekt kann ich aufgewendete Zeiten für eine Aufgabe buchen (diese Buchung gilt pro Aufgabe und Entwickler, außerdem kann ein Entwickler mehrmals Zeiten buchen, wenn die Bearbeitung beispielsweise mehrere Tage dauert)
    @PostMapping("/bookTime")
    public String bookTime(@RequestParam("taskId") Long taskId, @RequestParam("projectId") Long projectId, @RequestParam("date") String date, @RequestParam("time") double time) {
        userDeveloperService.bookTime(taskId, projectId, time, date);
        return "redirect:/userDeveloper/viewTask/"+taskId;
    }

}