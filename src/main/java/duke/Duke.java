package duke;

import java.time.LocalDate;
import java.time.LocalTime;

import duke.data.Storage;
import duke.data.TaskList;
import duke.task.Deadline;
import duke.task.Event;
import duke.task.Task;
import duke.task.Todo;
import duke.user.DukeException;
import duke.user.Parser;
import duke.user.Ui;

/**
 * Main class for the Duke program.
 */
public class Duke {

    private Storage storage;
    private TaskList tasks;
    private Ui ui;


    /**
     * Constructor for the Duke instance.
     *
     * @param filePath the String representing the path of the file where the data is saved
     */
    public Duke(String filePath) {
        storage = new Storage(filePath);
        tasks = new TaskList(storage.loadFromFile());
        ui = new Ui();
    }

    /**
     * Default constructor for Duke.
     * Required for JavaFX GUI
     */
    public Duke() {
        storage = new Storage("data/Duke.txt");
        tasks = new TaskList(storage.loadFromFile());
        ui = new Ui();
    }

    /**
     * Handles the user input and sends it to the parser to perform the commands, if valid.
     */
    public String getResponse(String input) {

        Parser parser = new Parser(tasks);
        boolean taskListIsUpdated = false;
        boolean taskListIsAddedTo = false;
        String output = "";

        try {
            String[] parsedInputString = parser.checkInput(input);
            String command = parsedInputString[0];

            // Case where user marks a task as done
            if (command.equals("done")) {
                Task current = tasks.getTask(Integer.parseInt(parsedInputString[1]) - 1);
                current.setIsDone();
                output += ui.displayDoneTaskMessage(current.toString());
                taskListIsUpdated = true;

                // case where user wants to delete a task item, similar to done
            } else if (command.equals("delete")) {
                Task current = tasks.getTask(Integer.parseInt(parsedInputString[1]) - 1);
                tasks.removeTask(current);
                output += ui.displayDeletedTaskMessage(current.toString(), tasks.getLength());
                taskListIsUpdated = true;

                // Case where user wants to see the entire task list
            } else if (command.equals("list")) {
                output += ui.displayListMessage(tasks);


                // Case where user exits the program
            } else if (command.equals("bye")) {
                output += ui.displayByeMessage();


                // Case where user wants to add a new to do task
            } else if (command.equals("todo")) {
                tasks.addTask(new Todo(parsedInputString[1]));
                taskListIsAddedTo = true;

                // Case where user wants to add a new event task
            } else if (command.equals("event")) {
                tasks.addTask(new Event(parsedInputString[1], parsedInputString[2]));
                taskListIsAddedTo = true;

                // Case where user wants to add a new deadline task
            } else if (command.equals("deadline")) {
                LocalDate date = LocalDate.parse(parsedInputString[2]);
                LocalTime time = LocalTime.parse(parsedInputString[3]);
                String deadlineDesc = parsedInputString[1]; //skip the "deadline "
                tasks.addTask(new Deadline(deadlineDesc, date, time));
                taskListIsAddedTo = true;

                // Case where user wants to find a keyword
            } else if (command.equals("find")) {
                String keyword = parsedInputString[1];
                output += ui.findMessage(tasks.findTask(keyword));


                // Else case for all non-recognised user inputs
            } else {
                throw new DukeException("Please enter a valid command");
            }

            // If the program reaches this point, meaning no continue or break was hit, it means
            // there was an update to the file, and we can save the file
            storage.saveToFile();
            if (taskListIsAddedTo) {
                // When adding a new task, this message be printed
                return output + ui.displayAddTaskMessage(tasks);
            }

            // catch all the custom exceptions and displays the message
        } catch (DukeException e) {
            return output + ui.displayDukeExceptionMessage(e);

            // catch the remaining exceptions
        } catch (Exception e) {
            return output + ui.displayExceptionMessage(e);
        }
        return output;
    }


    /**
     * Depreciated method for the old UI.
     */
    public void run() {

    }

    /**
     * Main method to start the whole program.
     *
     * @param args NIL
     */
    public static void main(String[] args) {
        new Duke("data/Duke.txt").run();
    }
}
