package de.hsba.bi.expenses.web.task;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import de.hsba.bi.expenses.project.Project;
import de.hsba.bi.expenses.task.Booking;
import de.hsba.bi.expenses.user.User;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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