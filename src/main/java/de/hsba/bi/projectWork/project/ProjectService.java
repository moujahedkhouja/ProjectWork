package de.hsba.bi.projectWork.project;

import de.hsba.bi.projectWork.task.Booking;
import de.hsba.bi.projectWork.task.Task;
import de.hsba.bi.projectWork.user.User;
import de.hsba.bi.projectWork.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final UserService userService;

    public List<Project> findAll() {
        return projectRepository.findAll();
    }

    public Project findById(Long id) {
        Optional<Project> project = projectRepository.findById(id);
        return project.orElse(null);
    }

    public List<User> findUsersNotInProject(Long projectId) {
        Project project = this.findById(projectId);
        List<User> usersNotInProject = new ArrayList<User>(userService.findAll());
        usersNotInProject.removeAll(project.getMembers());
        return usersNotInProject;
    }

    public List<Project> findUsersProjects() {
        User user = userService.findCurrentUser();
        List<Project> allProjects = projectRepository.findAll();
        List<Project> usersProjects = new ArrayList<>();
        double usersTimeSpentInProject;

        // find projects belonging to the logged in user
        for (Project project: allProjects) {
            if (project.getMembers().contains(user)) {
                usersProjects.add(project);
            }
        }

        // calc the time the user spent working on each project
        for (Project project: usersProjects) {
            usersTimeSpentInProject = 0;
            for (Task task: project.getTasks()) {
                for (Booking booking: task.getTimes()) {
                    if(booking.getUser() == user){
                        usersTimeSpentInProject += booking.getTimeSpent();
                    }
                }
            }
            project.setUsersTimeSpentInProject(usersTimeSpentInProject);
        }
        return usersProjects;
    }

}
