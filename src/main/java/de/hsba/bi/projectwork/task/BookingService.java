package de.hsba.bi.projectwork.task;

import de.hsba.bi.projectwork.project.Project;
import de.hsba.bi.projectwork.project.ProjectRepository;
import de.hsba.bi.projectwork.project.ProjectService;
import de.hsba.bi.projectwork.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional
public class BookingService {

    private final BookingRepository bookingRepository;
    private final ProjectService projectService;
    private final ProjectRepository projectRepository;
    private final TaskRepository taskRepository;
    private final TaskService taskService;


    public Booking findById(Long id) {
        Optional<Booking> booking = bookingRepository.findById(id);
        return booking.orElse(null);
    }

    public void bookTime(Long taskId, Long projectId, int timeSpent, String date, User user) {
        // TODO Als Entwickler in einem Projekt kann ich aufgewendete Zeiten für eine Aufgabe buchen (diese Buchung gilt pro Aufgabe und Entwickler, außerdem kann ein Entwickler mehrmals Zeiten buchen, wenn die Bearbeitung beispielsweise mehrere Tage dauert)
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(date, dateTimeFormatter);

        Project project = projectService.findById(projectId);
        Task task = taskService.findById(taskId);
        Booking booking = new Booking(user, localDate, timeSpent);

        booking.setTask(task);
        booking.setProject(project);
        bookingRepository.save(booking);
        task.getTimes().add(booking);
        task.setTotalTime(task.calcTotalTime());
        taskRepository.save(task);
        project.getBookedTimes().add(booking);
        projectRepository.save(project);
    }

    public void deleteBookedTime(Task task, Booking booking) {
        task.getTimes().remove(booking);
        taskRepository.save(task);
        bookingRepository.delete(booking);
    }

    public HashMap<User, Task> compareEstimatedAndBookedTimes() {
        // TODO Als Manager kann ich eine Statistik einsehen, bei der die geschätzten und gebuchten Zeiten gegenübergestellt werden
        HashMap<User, Task> comparison = null;
        return comparison;
    }

}