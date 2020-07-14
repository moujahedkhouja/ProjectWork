package de.hsba.bi.projectwork.task;

import de.hsba.bi.projectwork.project.Project;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Task {

    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private Long id;
    private String name;
    private String description;
    private int estimation;
    private int totalTime;
    private String status;
    //private Enum<Status> status;

    /*@ManyToOne(optional = false)
    private User assignee;*/

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "task")
    private List<Booking> times;

    @ManyToOne(optional = false)
    private Project project;

    public enum Status {
        IDEA("Idea"),
        PLANNED("Planned"),
        WORK_IN_PROGRESS("Work in progress"),
        TESTING("Testing"),
        DONE("Done");

        private final String displayValue;

        private Status(String displayValue) {
            this.displayValue = displayValue;
        }

        public String getDisplayValue() {
            return displayValue;
        }
    }

    public int calcTotalTime() {
        int sum = 0;
        for (int i = 0; i<this.times.size(); i++) {
            int time = this.times.get(i).timeSpent;
            sum = sum + time;
        }
        this.setTotalTime(sum);
        return sum;
    }


    public Task (String name, String description, int estimation, String status) {
        this.name = name;
        this.description = description;
        this.estimation = estimation;
        this.status = status;
    }

}
