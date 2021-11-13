package driver.commands;

import controllers.LoginRegisterController;
/**
 * This class allows the current user to login or register.
 */
public class LoginRegisterExecutor {
    public String executeCommand(String userInput) throws Exception {
        String[] inputArray = userInput.split(";"); // Use ";" to split user input String

        if (inputArray.length != 3) {
            throw new Exception("Wrong argument length!");
        }

        String userCommandName = inputArray[0];
        if (userCommandName.equals("login")) {
            LoginRegisterController.getInstance().login(inputArray[1], inputArray[2]);
        } else if (userCommandName.equals("register")) {
            LoginRegisterController.getInstance().register(inputArray[1], inputArray[2]);
        } else {
            throw new Exception("Command not found!");
        }

        return inputArray[1];
    }
}
