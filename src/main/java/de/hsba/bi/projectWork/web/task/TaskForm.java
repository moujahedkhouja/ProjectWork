package de.hsba.bi.projectWork.web.task;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import de.hsba.bi.projectWork.project.Project;
import de.hsba.bi.projectWork.task.Booking;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TaskForm {

    @NotNull(message = "Please enter a name.")
    private String name;

    @NotEmpty(message = "Please enter a description.")
    private String description;

    @NotNull(message = "Please choose a status.")
    private String status;

    @NotNull(message = "Please enter an estimation.")
    private double estimation;

    List<Booking> times;
    private double totalTime;
    private Project project;

}