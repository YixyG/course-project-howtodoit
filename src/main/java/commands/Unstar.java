package commands;

import driver.DataSaver;
import entities.Task;

/**
 * This class unstars a task.
 */
public class Unstar implements Executable {
    /**
     * This function executes the unstar command: Add a task called <name> to Starred label
     * The task must already exist in the system.
     * @param args a String, representing user arguments
     * @return a String indicating a task has been added to Starred label
     */
    @Override
    public String execute(DataSaver dataSaver, String[] args) {
        TodoSystem todoSystem = dataSaver.getSystem(); // Get access to entities
        // checkArgs(todoSystem, args); // Check whether arguments are valid

        // Map user arguments to task name
        String name = args[0];

        Task task = todoSystem.getTasks().get(name);
        Folder starred = todoSystem.getLabels().get("Starred");
        starred.getTasks().remove(name); // Remove task from Starred
        task.getLabels().remove(starred);

        return "Task <" + name + "> has been removed from label <Starred> successfully.";
    }
}