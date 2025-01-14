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

class AddMemTest {
    private final AddMem addMemCommand = new AddMem();
    private UserList userList = new UserList();

    @BeforeEach
    void setUp() {
        userList = TestingSystemSetUp.SetUp();
        TeamController.getInstance().setInputBoundary(new TeamUseCases(userList));
        DataMemoryController.getInstance().setInputBoundary(new DataMemoryUseCases(userList));
    }

    @Test
    public void testSuccessfullyAddedMem() {
        try {
            String[] args = {"Goats Club", "Nole"};
            addMemCommand.execute("Roge", args);
            // Check that user2 is added to the team
            User user2 = userList.getUser("Nole");
            Team team = user2.getTeamList().getTeam("Goats Club");
            Assertions.assertTrue(team.isMem("Nole") && user2.getTeamList().hasTeam("Goats Club"),
                    "Failure: Member has not been added successfully");
        } catch (Exception e) {
            Assertions.fail(e.getMessage());
        }
    }

    @Test
    public void testTeamNotExist() {
        try {
            String[] args = {"Serve & Volley Gang", "Nole"};
            addMemCommand.execute("Roge", args);
            Assertions.fail("Failure: Expected Exception has not been thrown.");
        } catch (Exception ignored) {
        }
    }

    @Test
    public void testMemNotAdmin() {
        try {
            String[] args = {"Goats Club", "Nole"};
            addMemCommand.execute("Rafa", args);
            Assertions.fail("Failure: Expected Exception has not been thrown.");
        } catch (Exception ignored) {
        }
    }

    @Test
    public void testMemHasSameTeam() {
        try {
            String[] args = {"Goats Club", "Rafa"};
            addMemCommand.execute("Roge", args);
            Assertions.fail("Failure: Expected Exception has not been thrown.");
        } catch (Exception ignored) {
        }
    }
}