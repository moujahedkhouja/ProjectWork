package de.hsba.bi.expenses.user.userAdmin;

import de.hsba.bi.expenses.user.UserRepository;
import de.hsba.bi.expenses.user.UserService;
import de.hsba.bi.expenses.user.admin.UserAdminService;
import org.junit.jupiter.api.Test;

import de.hsba.bi.expenses.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class UserAdminTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserAdminService userAdminService;

    @Autowired
    private UserRepository userRepository;

    @Test
    void userAdminTest(){
        /*userService.createUser("TestUser", "password", User.DEVELOPER_ROLE);
        List<Project> projects = new ArrayList<Project>();
        projects.add(new Project("TestProject"));
        User user = userAdminService.findByName("TestUser");
        user.setProjects(projects);*/
    }

}
