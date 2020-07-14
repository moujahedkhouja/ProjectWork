package de.hsba.bi.projectwork.web.task;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import de.hsba.bi.projectwork.project.Project;
import de.hsba.bi.projectwork.task.Booking;
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
    private int estimation;

    List<Booking> times;
    private int totalTime;
    private Project project;

}