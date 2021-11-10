package commands;

import usecasesControllers.TeamController;

/**
 * This class deletes a member from a team.
 */
public class DelMem implements Command {
    /**
     * This function executes the delMem command: Remove the user called <username> from the team called <teamname>.
     * Only admins of the team can perform this action.
     *
     * @param username current username
     * @param args a list of Strings with length 2, representing user arguments
     * @return a String indicating a member has been removed successfully
     */
    @Override
    public String execute(String username, String[] args) throws Exception {
        if (args.length != 2) throw new Exception("Incorrect argument length!");
        return TeamController.getInstance().delMem(username, args[0], args[1]);
    }
}
