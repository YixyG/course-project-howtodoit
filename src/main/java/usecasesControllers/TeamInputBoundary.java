package usecasesControllers;

public interface TeamInputBoundary {
    /**
     * Create a new team.
     * @param username current username
     * @param teamName name of the team you want to create
     * @return boolean indicating whether success or failure
     */
    boolean newTeam(String username, String teamName);

    /**
     * Delete a team.
     * @param username current username
     * @param teamName name of the team you want to delete
     * @return boolean indicating whether success or failure
     */
    boolean delTeam(String username, String teamName);

    /**
     * Change the name of a team.
     * @param username current username
     * @param name1 name of the team you want to change
     * @param name2 the new name
     * @return boolean indicating whether success or failure
     */
    boolean modTeam(String username, String name1, String name2);

    /**
     * Add a member to a team.
     * @param username current username
     * @param teamName name of the team you want to add a member to
     * @param memName name of the member you want to add to the team
     * @return boolean indicating whether success or failure
     */
    boolean addMem(String username, String teamName, String memName);

    /**
     * Delete a member from a team.
     * @param username current username
     * @param teamName name of the team you want to delete a member from
     * @param memName name of the member you want to delete from the team
     * @return boolean indicating whether success or failure
     */
    boolean delMem(String username, String teamName, String memName);

    /**
     * Promote a member to admin.
     * @param username current username
     * @param teamName name of the team
     * @param memName name of the member you want to promote
     * @return boolean indicating whether success or failure
     */
    boolean addAdmin(String username, String teamName, String memName);
}
