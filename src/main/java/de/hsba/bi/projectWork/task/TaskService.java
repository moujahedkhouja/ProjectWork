package de.hsba.bi.projectWork.task;

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
    public List<Task> findByStatus(String status) {

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

}
