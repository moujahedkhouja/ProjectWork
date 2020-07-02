package de.hsba.bi.projectWork.user.admin;

import de.hsba.bi.projectWork.project.Project;
import de.hsba.bi.projectWork.project.ProjectRepository;
import de.hsba.bi.projectWork.project.ProjectService;
import de.hsba.bi.projectWork.user.User;
import de.hsba.bi.projectWork.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional
public class UserAdminService {

    private final UserAdminRepository userAdminRepository;
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;
    private final ProjectService projectService;

    public User findById(Long id) {
        Optional<User> user = userAdminRepository.findById(id);
        return user.orElse(null);
    }

    public User findByName(String name) {
        Optional<User> user = userRepository.findByName(name);
        return user.orElse(null);
    }

    public boolean changeRole(Long id, String role) {
        // TODO Als Admin kann ich die Rollen anderer Nutzer ändern
        Optional<User> user = userRepository.findById(id);
        if(user.isPresent()){
            User user1 = user.get();
            user1.setRole(role);
            userRepository.save(user1);
        }
        return false;
    }

    public void createNewProject(Project project) {
        // TODO Als Admin kann ich ein neues Projekt anlegen
        projectRepository.save(project);
    }

    public void addUserToProject(Long projectId, List<User> users) {
        // TODO Als Admin kann ich andere Nutzer (jeder Rolle) zu meinem Projekt hinzufügen
        Project project = projectService.findById(projectId);
        for (User user: users) {
            project.getMembers().add(user);
            user.getProjects().add(project);
            userRepository.save(user);
        }
        projectRepository.save(project);
    }

    public void removeUserFromProject(Long projectId, List<User> users) {
        // TODO Als Admin kann ich andere Nutzer (jeder Rolle) zu meinem Projekt hinzufügen
        Project project = projectService.findById(projectId);
        for (User user: users) {
            project.getMembers().remove(user);
            user.getProjects().remove(project);
            userRepository.save(user);
        }
        projectRepository.save(project);
    }

}
