package de.hsba.bi.projectWork.project;

import de.hsba.bi.projectWork.task.Booking;
import de.hsba.bi.projectWork.task.Task;
import de.hsba.bi.projectWork.user.User;
import de.hsba.bi.projectWork.user.UserService;
import de.hsba.bi.projectWork.web.project.UpdateProjectForm;
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

    public void save(Project project) {
        // TODO Als Admin kann ich ein neues Projekt anlegen
        projectRepository.save(project);
    }

    public void addUserToProject(UpdateProjectForm updateProjectForm, Long projectId) {
        // TODO Als Admin kann ich andere Nutzer (jeder Rolle) zu meinem Projekt hinzufügen
        List<User> newUsers  = updateProjectForm.getNewUsers();
        List<User> projectMembers = this.findById(projectId).getMembers();
        Project project = this.findById(projectId);

        for (User newUser: newUsers) {
            if(!projectMembers.contains(newUser)) {
                project.getMembers().add(newUser);
                newUser.getProjects().add(project);
                userService.save(newUser);
            }
            this.save(project);
        }
    }

    public void removeUserFromProject(UpdateProjectForm updateProjectForm, Long projectId) {
        // TODO Als Admin kann ich andere Nutzer (jeder Rolle) zu meinem Projekt hinzufügen
        List<User> removeUsers = updateProjectForm.getNewUsers();
        List<User> projectMembers = this.findById(projectId).getMembers();
        Project project = this.findById(projectId);

        for (User newUser : removeUsers) {
            if (projectMembers.contains(newUser)) {
                project.getMembers().remove(newUser);
                newUser.getProjects().remove(project);
                userService.save(newUser);
            }
            this.save(project);
        }
    }

}
