package de.hsba.bi.expenses.project;

import de.hsba.bi.expenses.task.Booking;
import de.hsba.bi.expenses.task.Task;
import de.hsba.bi.expenses.user.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Project {

    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private Long id;

    private String name;

    @ManyToMany(cascade = CascadeType.ALL)
    @OrderBy
    private List<User> members;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "project")
    @OrderBy
    private List<Task> tasks;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "project")
    @OrderBy
    private List<Booking> bookedTimes;

    @Transient
    private transient double usersTimeSpentInProject;

    public Project(String name){
        this.name = name;
    }

}
