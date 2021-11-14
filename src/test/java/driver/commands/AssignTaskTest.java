package driver.commands;

import entities.Project;
import entities.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import usecases.UserList;

import static org.junit.jupiter.api.Assertions.*;

class AssignTaskTest {
    private final AssignTask assigntaskCommand = new AssignTask();
    private UserList userList = new UserList();
    private final String username = "Jiayang";
    private final String username1 = "Krystal";

    @BeforeEach
    void setUp() {
        userList = new UserList();
        User user = new User(username, "+1=0");
        userList.addUser(user);
        user.addProject(new Project("CSC207"));
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    public void testSuccessfullyAssignedTask() {
        try {
            String taskName = "Phase 1";
            String dueDate = "2021-11-15";
            String[] args = {taskName, dueDate};
            assigntaskCommand.execute(username, args);
            // Check that the system has the task and the task has been assigned to the user
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}