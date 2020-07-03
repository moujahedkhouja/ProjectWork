package de.hsba.bi.projectWork.task;

import de.hsba.bi.projectWork.project.Project;
import de.hsba.bi.projectWork.project.ProjectRepository;
import de.hsba.bi.projectWork.project.ProjectService;
import de.hsba.bi.projectWork.user.User;
import de.hsba.bi.projectWork.user.UserService;
import de.hsba.bi.projectWork.web.task.TaskForm;
import de.hsba.bi.projectWork.web.task.TaskFormConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserService userService;
    private final ProjectRepository projectRepository;
    private final ProjectService projectService;
    private final TaskFormConverter taskFormConverter;

    public Task findById(Long id) {
        Optional<Task> optionalTask = taskRepository.findById(id);

        if(optionalTask.isPresent()){
            Task task = optionalTask.get();
            task.calcTotalTime();
            taskRepository.save(task);
            return task;
        }
        else {
            // TODO throw an exception
            return null;
        }
    }

    public List<Task> findAll() {
        return taskRepository.findAll();
    }
    public List<Task> findTasksByStatus(String status) {
        if (status != null) {
            if (status.equals("Idea") || status.equals("Planned") || status.equals("wip") || status.equals("Testing") || status.equals("Done")) {
                List<Task> allTasks = taskRepository.findAll();
                List<Task> tasks = new ArrayList<>();

                if(status.equals("wip")){
                    status = "Work in progress";
                }

                for (Task task: allTasks) {
                    if(task.getStatus().equals(status)) {
                        tasks.add(task);
                    }
                }
                return tasks;
            }
        }
        return this.findAll();
    }
    public List<Task> findTasksByStatusAndUser(String status) {
        User user = userService.findCurrentUser();
        List<Task> tasks = this.findTasksByStatus(status);
        if(tasks.size() > 0) {
            tasks.removeIf(task -> !task.getProject().getMembers().contains(user));
        }
        return tasks;
    }

    public List<String> findRemainingStatuses(Long taskId) {
        Task task = this.findById(taskId);
        List<String> remainingStatuses = new ArrayList<String>();
        remainingStatuses.add("Idea");
        remainingStatuses.add("Planned");
        remainingStatuses.add("Work in progress");
        remainingStatuses.add("Testing");
        remainingStatuses.add("Done");
        remainingStatuses.remove(task.getStatus());
        return remainingStatuses;
    }

    public void save (Task task) {
        taskRepository.save(task);
    }

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
        Task task = this.findById(taskId);
        this.save(taskFormConverter.update(task, form));
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
