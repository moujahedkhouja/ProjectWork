package de.hsba.bi.projectWork.web.task;

import de.hsba.bi.projectWork.task.Task;
import org.springframework.stereotype.Component;


@Component
public class TaskFormConverter {

    public Task update(Task task, TaskForm form) {
        task.setName(form.getName());
        task.setDescription(form.getDescription());
        task.setStatus(form.getStatus());
        task.setEstimation(form.getEstimation());
        task.setTotalTime(form.getTotalTime());
        return task;
    }
}
