package de.hsba.bi.projectWork.user.developer;

import de.hsba.bi.projectWork.project.Project;
import de.hsba.bi.projectWork.project.ProjectRepository;
import de.hsba.bi.projectWork.project.ProjectService;
import de.hsba.bi.projectWork.task.*;
import de.hsba.bi.projectWork.user.UserService;
import de.hsba.bi.projectWork.web.task.TaskForm;
import de.hsba.bi.projectWork.web.task.TaskFormConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
public class UserDeveloperService {

    private final UserService userService;
    private final ProjectRepository projectRepository;
    private final ProjectService projectService;
    private final TaskRepository taskRepository;
    private final BookingRepository bookingRepository;
    private final TaskService taskService;
    private final TaskFormConverter taskFormConverter;

    public void addNewTask(Task task, Long projectId) {
        // TODO Als Entwickler in einem Projekt kann ich eine Aufgabe zu diesem Projekt hinzufügen, diese beinhaltet wenigstens einen Titel und eine Beschreibung
        Project project = projectService.findById(projectId);
        if (checkStatusValidity(task) && project != null) {
            task.setProject(project);
            List<Task> tasks = project.getTasks();
            tasks.add(task);
            taskRepository.save(task);
            projectRepository.save(project);
        }
        else {
        // TODO throw an expection
        }
    }

    public void editTask(Long taskId, TaskForm form) {
        // TODO Als Entwickler in einem Projekt kann ich eine Zeitschätzung (grob in Stunden) in einer Aufgabe speichern (diese Schätzung soll eine Eigenschaft der Aufgabe sein - verschiedene Entwickler würden diese Schätzung sehen und ändern dürfen)
        // TODO Als Entwickler in einem Projekt kann ich den Status einer Aufgabe ändern (Idee, Geplant, in Bearbeitung, im Test, Fertig)
        Task task = taskService.findById(taskId);
        taskService.save(taskFormConverter.update(task, form));
    }

    public void bookTime(Long taskId, Long projectId, double timeSpent, String date) {
        // TODO Als Entwickler in einem Projekt kann ich aufgewendete Zeiten für eine Aufgabe buchen (diese Buchung gilt pro Aufgabe und Entwickler, außerdem kann ein Entwickler mehrmals Zeiten buchen, wenn die Bearbeitung beispielsweise mehrere Tage dauert)

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(date, dateTimeFormatter);

        Project project = projectService.findById(projectId);
        Task task = taskService.findById(taskId);
        Booking booking = new Booking(userService.findCurrentUser(), localDate, timeSpent);

        booking.setTask(task);
        booking.setProject(project);
        bookingRepository.save(booking);
        task.getTimes().add(booking);
        task.setTotalTime(task.calcTotalTime());
        taskRepository.save(task);
        project.getBookedTimes().add(booking);
        projectRepository.save(project);
    }

    public boolean checkStatusValidity(Task task) {
        switch (task.getStatus()) {
            case "Idea":
                return true;
            case "Planned":
                return true;
            case "Work in progress":
                return true;
            case "Testing":
                return true;
            case "Done":
                return true;
            default:
                return false;
        }
    }

}
