package usecases;

import entities.Project;
import entities.Task;
import entities.Team;
import entities.User;
import usecases.managers.ProjectList;
import usecases.managers.TaskList;
import usecases.managers.UserList;

import java.text.SimpleDateFormat;
import java.time.LocalDate;

/**
 * This class deals with task use cases.
 */
public class TaskUseCases implements TaskInputBoundary {
    /**
     * The list of users.
     */
    private final UserList userList;

    /**
     * Constructor.
     *
     * @param userList the list of users
     */
    public TaskUseCases(UserList userList) {
        this.userList = userList;
    }

    /**
     * Create a new task.
     *
     * @param username current username
     * @param taskName name of the new task
     * @param dueDate  due date of the new task
     * @param projName project the new task belongs to
     * @return boolean indicating whether success or failure
     */
    @Override
    public boolean newTask(String username, String taskName, String dueDate, String projName) {
        User user = this.userList.getUser(username);
        ProjectList projectList = user.getProjectList();
        TaskList taskList = user.getTaskList();

        if (taskList.hasTask(taskName)) {
            return false; // task already exists
        } else if (wrongDueDateFormat(dueDate)) {
            return false; // wrong due date format
        } else if (LocalDate.parse(dueDate).isBefore(LocalDate.now())) {
            return false; // overdue task
        } else {
            Project project = projectList.getProject(projName);
            if (project == null) project = projectList.getProject("General"); // for non-existent project name
            Task task = new Task(taskName, dueDate, project);
            taskList.addTask(task);
            task.getProject().addTask(task);
            return true;
        }
    }

    /**
     * Private method that checks if due date format is wrong.
     *
     * @param dueDate the due date String
     * @return false if correct format, true if wrong format
     */
    private boolean wrongDueDateFormat(String dueDate) {
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
        try {
            // If we want the format of string to be formal, use false. Otherwise, use true.
            sd.setLenient(true);
            sd.parse(dueDate);
        } catch (Exception e) {
            return true;
        }
        return false;
    }


    /**
     * Complete a task.
     *
     * @param username current username
     * @param taskName name of the task to complete
     * @return boolean indicating whether success or failure
     */
    @Override
    public boolean completeTask(String username, String taskName) {
        User user = this.userList.getUser(username);
        TaskList taskList = user.getTaskList();

        if (!taskList.hasTask(taskName)) {
            return false; // non-existent task
        } else {
            Task task = taskList.getTask(taskName);
            taskList.delTask(task);
            task.getProject().delTask(task);
            return true;
        }
    }

    /**
     * Star a task.
     *
     * @param username current username
     * @param taskName name of the task to star
     * @return boolean indicating whether success or failure
     */
    @Override
    public boolean star(String username, String taskName) {
        User user = this.userList.getUser(username);
        TaskList taskList = user.getTaskList();

        if (!taskList.hasTask(taskName)) {
            return false; // non-existent task
        } else {
            Task task = taskList.getTask(taskName);
            task.setStarred(true);
            return true;
        }
    }

    /**
     * Unstar a task.
     *
     * @param username current username
     * @param taskName name of the task to unstar
     * @return boolean indicating whether success or failure
     */
    @Override
    public boolean unstar(String username, String taskName) {
        User user = this.userList.getUser(username);
        TaskList taskList = user.getTaskList();

        if (!taskList.hasTask(taskName)) {
            return false; // non-existent task
        } else {
            Task task = taskList.getTask(taskName);
            task.setStarred(false);
            return true;
        }
    }

    /**
     * Rename a task.
     *
     * @param username current username
     * @param name1    current name of the task
     * @param name2    new name of the task
     * @return boolean indicating whether success or failure
     */
    @Override
    public boolean rename(String username, String name1, String name2) {
        User user = this.userList.getUser(username);
        TaskList taskList = user.getTaskList();

        if (!taskList.hasTask(name1) || taskList.hasTask(name2)) {
            return false; // non-existent task or new name already exists
        } else {
            Task task = taskList.getTask(name1);
            task.setName(name2);
            return true;
        }
    }

    /**
     * Reset the due date of a task.
     *
     * @param username current username
     * @param taskName name of the task
     * @param dueDate  new due date of the task
     * @return boolean indicating whether success or failure
     */
    @Override
    public boolean retime(String username, String taskName, String dueDate) {
        User user = this.userList.getUser(username);
        TaskList taskList = user.getTaskList();

        if (!taskList.hasTask(taskName)) {
            return false; // non-existent task
        }  else if (wrongDueDateFormat(dueDate)) {
            return false; // wrong due date format
        } else if (LocalDate.parse(dueDate).isBefore(LocalDate.now())) {
            return false; // overdue task
        } else {
            Task task = taskList.getTask(taskName);
            task.setDueDate(dueDate);
            return true;
        }
    }

    /**
     * Reset the description of a task.
     *
     * @param username current username
     * @param taskName name of the task
     * @param desc     new description of the task
     * @return boolean indicating whether success or failure
     */
    @Override
    public boolean redesc(String username, String taskName, String desc) {
        User user = this.userList.getUser(username);
        TaskList taskList = user.getTaskList();

        if (!taskList.hasTask(taskName)) {
            return false; // non-existent task
        } else {
            Task task = taskList.getTask(taskName);
            task.setDescription(desc);
            return true;
        }
    }

    /**
     * Assign a task to a teammate
     *
     * @param username1 current user
     * @param teamName  the name of the team
     * @param username2 the name of the teammate
     * @param taskName  the name of the task
     * @param dueDate   due date of the task
     * @return boolean indicating whether success or failure
     */
    @Override
    public boolean assignTask(String username1, String teamName, String username2, String taskName, String dueDate) {
        User user1 = this.userList.getUser(username1);
        Team team = user1.getTeamList().getTeam(teamName);

        if (!user1.getTeamList().hasTeam(teamName)) {
            return false; // no team
        } else if (!team.isAdmin(username1)) {
            return false; // user 1 not admin
        } else if (!team.isMem(username2)) {
            return false; // user 2 not teammate
        } else if (wrongDueDateFormat(dueDate)) {
            return false; // wrong due date format
        } else if (LocalDate.parse(dueDate).isBefore(LocalDate.now())) {
            return false; // overdue task
        } else {
            User user2 = team.getMem(username2);
            Project assignedToMe = user2.getProjectList().getProject("Assigned to me");
            Task task = new Task(taskName, dueDate, assignedToMe);
            user2.getTaskList().addTask(task);
            assignedToMe.addTask(task);
            return true;
        }
    }
}
