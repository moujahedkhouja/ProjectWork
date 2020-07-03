package de.hsba.bi.projectWork.task;

import de.hsba.bi.projectWork.project.Project;
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
    private double estimation;
    private double totalTime;
    private String status;
    //private Enum<Status> status;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "task")
    List<Booking> times;

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

    public double calcTotalTime() {
        double sum = 0;
        for (int i = 0; i<this.times.size(); i++) {
            double time = this.times.get(i).timeSpent;
            sum = sum + time;
        }
        this.setTotalTime(sum);
        return sum;
    }

}
