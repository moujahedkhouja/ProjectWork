package de.hsba.bi.projectwork.init;

import de.hsba.bi.projectwork.project.Project;
import de.hsba.bi.projectwork.project.ProjectService;
import de.hsba.bi.projectwork.task.BookingService;
import de.hsba.bi.projectwork.task.Task;
import de.hsba.bi.projectwork.task.TaskService;
import de.hsba.bi.projectwork.user.User;
import de.hsba.bi.projectwork.user.UserService;
import de.hsba.bi.projectwork.web.project.UpdateProjectForm;
import de.hsba.bi.projectwork.web.user.RegisterUserForm;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;


@Component
@RequiredArgsConstructor
public class UserInitializr {

    private final UserService userService;
    private final ProjectService projectService;
    private final TaskService taskService;
    private final BookingService bookingService;


    @EventListener(ApplicationStartedEvent.class)
    public void init() {
        /*if (!userService.findAll().isEmpty()) {
            // prevent initialisation if DB is not empty
            return;
        }*/
        this.initAdmins();
        this.initManager();
        this.initFirstProject();
        //this.initKajasProjects();
    }


    public void initAdmins() {
        // create some admins
        userService.createUser(new RegisterUserForm("admin1", "1234567890", "1234567890"), "ADMIN");
        userService.createUser(new RegisterUserForm("admin2", "1234567890", "1234567890"), "ADMIN");
    }

    public void initManager() {
        // create some managers
        userService.createUser(new RegisterUserForm("petra", "1234567890", "1234567890"), "MANAGER");
    }

    public void initFirstProject() {
        // create petra and make her a manager
        User petra = userService.findByName("petra");

        // create project members
        User sandra = userService.createUser(new RegisterUserForm("sandra", "1234567890", "1234567890"), "DEVELOPER");
        User brigitte = userService.createUser(new RegisterUserForm("brigitte", "1234567890", "1234567890"), "DEVELOPER");
        User conny = userService.createUser(new RegisterUserForm("conny", "1234567890", "1234567890"), "DEVELOPER");
        User claudia = userService.createUser(new RegisterUserForm("claudia", "1234567890", "1234567890"), "DEVELOPER");
        User patrick = userService.createUser(new RegisterUserForm("patrick", "1234567890", "1234567890"), "DEVELOPER");
        User walter = userService.createUser(new RegisterUserForm("walter", "1234567890", "1234567890"), "DEVELOPER");
        List<User> usersFirstProject = Arrays.asList(petra, sandra, brigitte, conny, claudia, patrick, walter);

        // create the project
        Project firstProject = projectService.createNewProject(new Project("Amazing HR Strategy"));
        projectService.addUserToProject(new UpdateProjectForm(usersFirstProject), firstProject.getId());

        // create tasks
        /*Task task1 = taskService.addNewTask(new Task("Create a Company Page on Facebook", "In order to attract possible applicants our company has to be introduced on social media. A facebook page has to be created an filled with content.", 3, "Testing"), firstProject.getId());
        //bookingService.bookTime(task1.getId(), firstProject.getId(), 1, "2020-06-28", sandra);
        //bookingService.bookTime(task1.getId(), firstProject.getId(), 1, "2020-06-30", sandra);
        Task task2 = taskService.addNewTask(new Task("Create a Company Page on Instagram", "In order to attract possible applicants our company has to be introduced on social media. An instagram page has to be created an filled with content.", 3, "Done"), firstProject.getId());
        //bookingService.bookTime(task2.getId(), firstProject.getId(), 1, "2020-07-09", brigitte);
        //bookingService.bookTime(task2.getId(), firstProject.getId(), 1, "2020-07-11", brigitte);
        Task task3 = taskService.addNewTask(new Task("List All Job Fairs Taking Place in the Region", "We want to meet people looking for a job! This is why we want to visit some job fairs that are visited by possible applicants. Please create a list of all job fairs within a radius of 30km.", 2, "Work in progress"), firstProject.getId());
        //bookingService.bookTime(task3.getId(), firstProject.getId(), 3, "2020-07-09", conny);
        Task task4 = taskService.addNewTask(new Task("Design an Employee-Survey", "We want to know how satisfied our current employees are and what we can do to make them even happier. To gain the necessary insights please plan a survey that will later be send to all employees.", 6, "Planned"), firstProject.getId());
        Task task5 = taskService.addNewTask(new Task("Organize a Summer Party", "For staff retention reasons we will be holding a summer party again this year!", 6, "Idea"), firstProject.getId());
        Task task6 = taskService.addNewTask(new Task("Promote the Employee Referral Program", "95% of all applicants that apply for one of our jobs because of a referral are successful but only <1% of all employees know about the program. Please create a page in the intranet and promote it!", 3, "Idea"), firstProject.getId());
        */
    }

    private void initSecondProject() {
        // create project members
        User tim = userService.createUser(new RegisterUserForm("tim", "1234567890", "1234567890"), "DEVELOPER");
        User daniel = userService.createUser(new RegisterUserForm("daniel", "1234567890", "1234567890"), "DEVELOPER");
        User sascha = userService.createUser(new RegisterUserForm("sascha", "1234567890", "1234567890"), "DEVELOPER");
        User mario = userService.createUser(new RegisterUserForm("mario", "1234567890", "1234567890"), "DEVELOPER");
        User thomas = userService.createUser(new RegisterUserForm("thomas", "1234567890", "1234567890"), "DEVELOPER");
        List<User> usersSecondProject = Arrays.asList(tim, daniel, sascha, mario, thomas);

        // create the project
        Project project = projectService.createNewProject(new Project("Renovate the Office"));
        projectService.addUserToProject(new UpdateProjectForm(usersSecondProject), project.getId());

        // create tasks
        taskService.addNewTask(new Task("Stow the Furniture in the Basement", "The craftsmen who will carry out the renovation need space to do so. Please bring all furniture in the basement.", 7, "Planned"), project.getId());
        taskService.addNewTask(new Task("Change the Windows", "We ordered new windows. Please remove the old windows and install the new ones.", 3, "Planned"), project.getId());
        taskService.addNewTask(new Task("Paint the Walls", "Please go to the facility managers office and ask for the colouring plan so that you know which rooms should be painted with which colours. Then go ahead and  actually give the wall new life!", 6, "Planned"), project.getId());
        taskService.addNewTask(new Task("Install the Hardwood Flooring", "Next Monday at 10AM the HORNBACH will deliver the new hardwood flooring along with all necessary equipment to gate 3. Please install it.", 6, "Planned"), project.getId());
        taskService.addNewTask(new Task("Bring the Furniture Back to the Office", "Now that the renovation work has been finished, the furniture has to be brought up from the basement to the office again.", 7, "Planned"), project.getId());
        taskService.addNewTask(new Task("Buy Some New Decoration", "The last employee survey found that our employees love flowers. Please take this into consideration when you go to IKEA and get some nice accessories to decorate the newly renovated office.", 2, "Planned"), project.getId());
        taskService.addNewTask(new Task("Clean the Office Before Everybody Returns", "Yay, Almost done! Please make sure the office is sparkling clean before everybody gets back to work on Monday.", 1, "Planned"), project.getId());
    }

    private void initThirdProject() {
        // create the project
        Project thirdProject = projectService.createNewProject(new Project("New Subsidiary"));
    }

    public void initKajasProjects() {
        // create kaja and make her a manager
        userService.createUser(new RegisterUserForm("kaja", "1234567890", "1234567890"), "MANAGER");

        // FIRST PROJECT
        // create project members
        User harm = userService.createUser(new RegisterUserForm("harm", "1234567890", "1234567890"), "DEVELOPER");
        User nico = userService.createUser(new RegisterUserForm("nico", "1234567890", "1234567890"), "DEVELOPER");
        User tassilo = userService.createUser(new RegisterUserForm("tassilo", "1234567890", "1234567890"), "DEVELOPER");
        List<User> usersFirstProject = Arrays.asList(harm, nico, tassilo);

        // create the project
        Project firstProject = projectService.createNewProject(new Project("Brand Pages"));
        projectService.addUserToProject(new UpdateProjectForm(usersFirstProject), firstProject.getId());
    }
}