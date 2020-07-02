package de.hsba.bi.projectWork.user;

import javax.persistence.*;

import de.hsba.bi.projectWork.project.Project;
import de.hsba.bi.projectWork.task.Booking;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class User implements Comparable<User> {

    public static String ADMIN_ROLE = "ADMIN";
    public static String DEVELOPER_ROLE = "DEVELOPER";
    public static String MANAGER_ROLE = "MANAGER";

    public static String getCurrentUsername() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        }
        return null;
    }


    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private Long id;

    @Basic(optional = false)
    private String name;

    @Basic(optional = false)
    private String password;

    private String role;

    @ManyToMany(cascade = CascadeType.ALL)
    @OrderBy
    private List<Project> projects;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "user")
    @OrderBy
    private List<Booking> bookedTimes;

    @Override
    public int compareTo(User other) {
        return this.name.compareTo(other.name);
    }

    @Override
    public String toString() {
        return name;
    }

    public User(String name) {
        this.name = name;
    }
    public User(String name, String password, String role) {
        this.name = name;
        this.password = password;
        this.role = role;
    }
}
