package driver.commands;

import entities.Team;
import entities.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import usecases.UserList;

import static org.junit.jupiter.api.Assertions.*;

class AddAdminTest {
    private final AddAdmin addadminCommand = new AddAdmin();
    private UserList userList = new UserList();
    private final String username = "Jiayang";
    private final String username1 = "Krystal";

    @BeforeEach
    void setUp() {
        userList = new UserList();
        User user = new User(username, "+1=0");
        User user1 = new User(username1, "1111");
        userList.addUser(user);
        userList.addUser(user1);
        user.addTeam(new Team("howtodoit"));
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    public void testSuccessfullyAddedAdmin() {
        try {
            String teamName = "howtodoit";
            String[] args = {teamName, username1};
            addadminCommand.execute(username, args);
            // Check that the user is a new administrator
            User user = userList.getUser(username);
            User user1 = userList.getUser(username1);
            Assertions.assertTrue(user.getTeam(teamName).isAdmin(username) &&
                    user1.getTeam(teamName).isAdmin(username1),
                    "Failure: Administrator has not been added successfully");

        } catch (Exception e) {
            Assertions.fail("Failure: an unexpected Exception was thrown.");
        }
    }
}