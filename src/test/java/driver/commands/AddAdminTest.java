package driver.commands;

import controllers.DataMemoryController;
import controllers.TeamController;
import entities.Team;
import entities.User;
import helpers.TestingSystemSetUp;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import usecases.DataMemoryUseCases;
import usecases.TeamUseCases;
import usecases.managers.UserList;

class AddAdminTest {
    private final AddAdmin addAdminCommand = new AddAdmin();
    private UserList userList = new UserList();

    @BeforeEach
    void setUp() {
        userList = TestingSystemSetUp.SetUp();
        TeamController.getInstance().setInputBoundary(new TeamUseCases(userList));
        DataMemoryController.getInstance().setInputBoundary(new DataMemoryUseCases(userList));
    }

    @Test
    public void testSuccessfullyAddedAdmin() {
        try {
            String[] args = {"Goats Club", "Rafa"};
            addAdminCommand.execute("Rogea", args);
            // Check that the user is a new administrator
            User user2 = userList.getUser("Rafa");
            Team team = user2.getTeamList().getTeam("Goats Club");
            Assertions.assertTrue(team.isAdmin("Rafa"),
                    "Failure: Administrator has not been added successfully");
        } catch (Exception e) {
            Assertions.fail(e.getMessage());
        }
    }

    @Test
    public void testTeamNotExist() {
        try {
            String[] args = {"Serve & Volley Gang", "Nole"};
            addAdminCommand.execute("Roge", args);
            Assertions.fail("Failure: Expected Exception has not been thrown.");
        } catch (Exception ignored) {
        }
    }

    @Test
    public void testMemNotAdmin() {
        try {
            String[] args = {"Goats Club", "Roge"};
            addAdminCommand.execute("Rafa", args);
            Assertions.fail("Failure: Expected Exception has not been thrown.");
        } catch (Exception ignored) {
        }
    }

    @Test
    public void testMemNotInTeam() {
        try {
            String[] args = {"Goats Club", "Nole"};
            addAdminCommand.execute("Roge", args);
            Assertions.fail("Failure: Expected Exception has not been thrown.");
        } catch (Exception ignored) {
        }
    }
}
