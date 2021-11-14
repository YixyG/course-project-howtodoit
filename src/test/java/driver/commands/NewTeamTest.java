package driver.commands;

import entities.Team;
import entities.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import usecases.UserList;

import static org.junit.jupiter.api.Assertions.*;

class NewTeamTest {
    private final NewTeam newteamCommand = new NewTeam();
    private UserList userList = new UserList();
    private final String username = "Jiayang";

    @BeforeEach
    void setUp() {
        userList = new UserList();
        User user = new User(username, "+1=0");
        userList.addUser(user);
        user.addTeam(new Team("howtodoit"));
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    public void testSuccessfullyAddedTeam() {
        try {
            String teamName = "CSC207";
            String[] args = {teamName};
            newteamCommand.execute(username, args);
            // Check that the system has the team
            User user = userList.getUser(username);
            Assertions.assertTrue(user.hasTeam(teamName),
                    "Failure: Team has not been added successfully");
        } catch (Exception e) {
            Assertions.fail("Failure: an unexpected Exception was thrown.");
        }

    }
}