package de.hsba.bi.projectwork.task;

import de.hsba.bi.projectwork.project.Project;
import de.hsba.bi.projectwork.user.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.time.LocalDate;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Booking {

    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    Long id;
    LocalDate date;
    int timeSpent;

    @ManyToOne(optional = false)
    User user;

    @ManyToOne(optional = false)
    private Task task;

    @ManyToOne(optional = false)
    private Project project;

    public Booking(User user, LocalDate date, int timeSpent) {
        this.user = user;
        this.date = date;
        this.timeSpent = timeSpent;
    }
}