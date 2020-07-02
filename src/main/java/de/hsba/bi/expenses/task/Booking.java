package de.hsba.bi.expenses.task;

import de.hsba.bi.expenses.project.Project;
import de.hsba.bi.expenses.user.User;
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
    @ManyToOne(optional = false)
    User user;
    LocalDate date;
    Double timeSpent;
    @ManyToOne(optional = false)
    private Task task;

    @ManyToOne(optional = false)
    private Project project;

    public Booking(User user, LocalDate date, Double timeSpent) {
        this.user = user;
        this.date = date;
        this.timeSpent = timeSpent;
    }
}