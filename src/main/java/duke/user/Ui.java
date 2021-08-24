package duke.user;

import duke.data.TaskList;

/**
 * Represents the class which handles all the output by the program and prompts the user for input
 */
public class Ui {

    // ASCII DIVIDER to clean up the output
    final String DIVIDER = "---------------------------";

    public Ui() {
        // Intro message
        System.out.println(
                "Hello! I'm Duke" + "\n" +
                        "What can I do for you?" + "\n" +
                        DIVIDER);
    }

    public void showLoadingError() {
        System.out.println("An error occurred.");
    }

    /**
     * Message that is displayed when a done command is successfully called
     *
     * @param task String representing the task that was done
     * @return A formatted String to display to the user
     */
    public String displayDoneTaskMessage(String task) {
        return (DIVIDER + "\n" + "Nice! I've marked this task as done: " + "\n" +
                task + "\n" +
                DIVIDER);
    }

    /**
     * Message that is displayed when a delete command is successfully called
     *
     * @param task String representing the task that was deleted
     * @return A formatted String to display to the user
     */
    public String displayDeletedTaskMessage(String task, int taskListSize) {
        return (DIVIDER + "\n" + "Nice! Noted. I've removed this task: " + "\n" +
                task + "\n" +
                "You now have " + taskListSize + " tasks remaining!" + "\n" +
                DIVIDER);
    }

    /**
     * Message that is displayed when a list command is successfully called
     *
     * @param tasks TaskList representing the tasks
     * @return A formatted String of all the tasks in the taskList to display to the user
     */
    public String displayListMessage(TaskList tasks) {
        String output = "";
        output += DIVIDER + "\n" + "Here are the items in your task list: " + "\n";
        for (int i = 0; i < tasks.getLength(); i++) {
            output += (i + 1 + ". " + tasks.getTask(i) + "\n");
        }
        output += (DIVIDER) + "\n";
        return output;
    }


    /**
     * Message that is displayed when a bye command is successfully called
     *
     * @return A formatted String to display to the user
     */
    public String displayByeMessage() {
        return DIVIDER + "\n" + "Bye. Hope to see you again soon!" + "\n" + DIVIDER;
    }

    /**
     * Message that is displayed when a todo, event or deadline command is successfully called
     *
     * @param tasks String representing the task that was added
     * @return A formatted String to display to the user
     */
    public String displayAddTaskMessage(TaskList tasks) {
        return DIVIDER + "\n" + "added: " + tasks.getTask(tasks.getLength() - 1) + "\n" +
                "now you have: " + tasks.getLength() + " tasks! type 'list' to see them!" + "\n" + DIVIDER;
    }

    public String displayDukeExceptionMessage(DukeException e) {
        return DIVIDER + "\n" + e.getMessage() + "\n" + DIVIDER;
    }

    public String displayExceptionMessage(Exception e) {
        return DIVIDER + "\n" + e.getMessage() + "\n" + DIVIDER;
    }
}
