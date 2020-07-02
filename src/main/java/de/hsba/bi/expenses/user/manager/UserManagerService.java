package de.hsba.bi.expenses.user.manager;

import de.hsba.bi.expenses.task.Booking;
import de.hsba.bi.expenses.task.BookingRepository;
import de.hsba.bi.expenses.task.Task;
import de.hsba.bi.expenses.task.TaskRepository;
import de.hsba.bi.expenses.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
public class UserManagerService {

    private final UserManagerRepository userManagerRepository;
    private final TaskRepository taskRepository;
    private final BookingRepository bookingRepository;

    public List<ZonedDateTime> getBookedTimes(Task newTask) {
        // TODO Als Manager kann ich die gebuchten Zeiten aller Entwickler einsehen
        List<ZonedDateTime> bookedTimes = null;
        return bookedTimes;
    }

    public HashMap<User, Task> compareEstimatedAndBookedTimes() {
        // TODO Als Manager kann ich eine Statistik einsehen, bei der die geschätzten und gebuchten Zeiten gegenübergestellt werden
        HashMap<User, Task> comparison = null;
        return comparison;
    }

    public void deleteBookedTime(Task task, Booking booking) {
        task.getTimes().remove(booking);
        taskRepository.save(task);
        bookingRepository.delete(booking);
    }

}