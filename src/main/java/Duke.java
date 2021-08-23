import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;


public class Duke {
    public static void main(String[] args) {

        // ASCII DIVIDER to clean up the output
        final String DIVIDER = "---------------------------";

        // Initialise the taskList
        ArrayList<Task> taskList = new ArrayList<>();
        boolean taskListIsUpdated = false;
        boolean taskListIsAddedTo = false;

        // Init the file obj
        try {
            File dataFile = new File("data/duke.txt");
            dataFile.getParentFile().mkdirs();
            if (dataFile.createNewFile()) {
                System.out.println("New task list saved at: " + dataFile.getName());
            } else {
                System.out.println("Loading your previous task list...");
                // Read the txt into the taskList arrayList
                Scanner taskListReader = new Scanner(dataFile);
                while (taskListReader.hasNext()) {
                    String taskDetails = taskListReader.nextLine();
                    if (taskDetails.startsWith("D ")) {
                        // [0] is the Task category, [1] is the isDone boolean, [2] is the task desc, [3] is the task dueDate
                        if (taskDetails.split(" \\| ")[1].equals("0")) {
                            taskList.add(new Deadline(taskDetails.split(" \\| ")[2], taskDetails.split(" \\| ")[3]));
                        } else {
                            taskList.add(new Deadline(taskDetails.split(" \\| ")[2], taskDetails.split(" \\| ")[3], true));
                        }
                    } else if (taskDetails.startsWith("E ")) {
                        if (taskDetails.split(" \\| ")[1].equals("0")) {
                            taskList.add(new Event(taskDetails.split(" \\| ")[2], taskDetails.split(" \\| ")[3]));
                        } else {
                            taskList.add(new Event(taskDetails.split(" \\| ")[2], taskDetails.split(" \\| ")[3], true));
                        }

                    } else {
                        if (taskDetails.split(" \\| ")[1].equals("0")) {
                            taskList.add(new Todo(taskDetails.split(" \\| ")[2]));
                        } else {
                            taskList.add(new Todo(taskDetails.split(" \\| ")[2], true));
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        // Init the scanner
        Scanner in = new Scanner(System.in);

        // Intro message
        System.out.println(
                "Hello! I'm Duke" + "\n" +
                        "What can I do for you?" + "\n" +
                        DIVIDER);

        while (true) {
            try {
                String nextTask = in.nextLine();  // Read user input

                // Case where user marks a task as done
                // Uses a series of checks to only check for "done (number)"
                if (nextTask.startsWith("done")) {

                    // there must be a number following "done"
                    if (nextTask.length() == 4) {
                        throw new DukeException("Please use this format: 'done (task number)'");
                    }

                    try {
                        int taskIndex = Integer.parseInt(nextTask.substring(5)) - 1;

                        if (taskIndex >= taskList.size() || taskIndex < 0) {
                            throw new DukeException("Invalid task number!");
                        }

                        taskList.get(taskIndex).markAsDone();
                        System.out.println(DIVIDER + "\n" + "Nice! I've marked this task as done: " + "\n" +
                                taskList.get(taskIndex) + "\n" +
                                DIVIDER);
                        taskListIsUpdated = true;
                    } catch (NumberFormatException e) {
                        throw new DukeException("Please enter a proper task number!");
                    }
                    //continue;

                    // case where user wants to delete a task item, similar to done
                } else if (nextTask.startsWith("delete")) {

                    if (nextTask.length() == 6) {
                        throw new DukeException("Please use this format: 'delete (task number)'");
                    }

                    try {
                        int taskIndex = Integer.parseInt(nextTask.substring(7)) - 1;

                        if (taskIndex >= taskList.size() || taskIndex < 0) {
                            throw new DukeException("Invalid task number!");
                        }

                        System.out.println(DIVIDER + "\n" + "Nice! Noted. I've removed this task: " + "\n" +
                                taskList.get(taskIndex) + "\n" +
                                "You now have " + (taskList.size() - 1) + " tasks remaining!" + "\n" +
                                DIVIDER);

                        taskList.remove(taskIndex);
                        taskListIsUpdated = true;
                        //continue;
                    } catch (NumberFormatException e) {
                        throw new DukeException("Please enter a proper task number!");
                    }

                } // Case where user wants to see the entire task list
                else if (nextTask.equals("list")) {
                    System.out.println(DIVIDER + "\n" + "Here are the items in your task list: ");
                    for (int i = 0; i < taskList.size(); i++) {
                        System.out.println(i + 1 + ". " + taskList.get(i));
                    }
                    System.out.println(DIVIDER);
                    continue;

                    // Case where user exits the program
                } else if (nextTask.equals("bye")) {
                    System.out.println(DIVIDER + "\n" + "Bye. Hope to see you again soon!" + "\n" + DIVIDER);
                    break;
                }

                // Case where user wants to add a new to do task
                else if (nextTask.startsWith("todo")) {
                    try {
                        String todoDesc = nextTask.substring(5);
                        taskList.add(new Todo(todoDesc));
                        taskListIsAddedTo = true;

                        // catch the exception created by .substring method and throw a new DukeException which is caught at the end
                    } catch (IndexOutOfBoundsException e) {
                        throw new DukeException("☹ OOPS!!! The description of a todo cannot be empty.");
                    }
                }

                // Case where user wants to add a new event task
                else if (nextTask.startsWith("event")) {

                    // need to check that for event they use the /at properly else reject
                    if (!nextTask.contains("/at ")) {
                        throw new DukeException("Please use this format: 'event <task> /at <date and time>' to specify the date and time!");
                    }

                    int eventDateIndex = nextTask.indexOf("/at ") + 4;
                    String eventDesc = nextTask.substring(6, eventDateIndex - 4); //skip "event "
                    taskList.add(new Event(eventDesc, nextTask.substring(eventDateIndex)));
                    taskListIsAddedTo = true;
                }

                // Case where user wants to add a new deadline task
                else if (nextTask.startsWith("deadline")) {

                    // need to check that for deadline they use the /by properly else reject
                    if (!nextTask.contains("/by ")) {
                        throw new DukeException("Please use this format: 'deadline <task> /by <date and time>' to specify the date and time!");
                    }

                    int deadlineDateIndex = nextTask.indexOf("/by ") + 4;
                    String deadlineDesc = nextTask.substring(9, deadlineDateIndex - 4); //skip the "deadline "
                    taskList.add(new Deadline(deadlineDesc, nextTask.substring(deadlineDateIndex)));
                    taskListIsAddedTo = true;

                    // Else case for all non-recognised user inputs
                } else {
                    throw new DukeException("Please enter a valid command");

                }

                if (taskListIsUpdated || taskListIsAddedTo) {

                    // save to data/duke.txt
                    try {
                        FileWriter myWriter = new FileWriter("data/duke.txt", false);
                        for (int i = 0; i < taskList.size(); i++) {
                            Task current = taskList.get(i);
                            myWriter.write(current.getTaskType() + " | " + current.getDoneStatus() + " | " +
                                    current.getDescription() + " | " + current.getDueDate() + "\n");
                        }
                        myWriter.close();

                        System.out.println("Successfully wrote to the file.");
                    } catch (IOException e) {
                        System.out.println("An error occurred.");
                        e.printStackTrace();
                    }

                    if (taskListIsAddedTo) {
                        // When adding a new task, this message be printed
                        System.out.println(taskList.size() > 1
                                ? DIVIDER + "\n" + "added: " + taskList.get(taskList.size() - 1) + "\n" +
                                "now you have: " + taskList.size() + " tasks! type 'list' to see them!" + "\n" + DIVIDER
                                : DIVIDER + "\n" + "added: " + taskList.get(taskList.size() - 1) + "\n" +
                                "now you have: " + taskList.size() + " task! type 'list' to see them!" + "\n" + DIVIDER);
                    }
                }


                // catch all the custom exceptions and displays the message
            } catch (DukeException e) {
                System.out.println(DIVIDER + "\n" + e.getMessage() + "\n" + DIVIDER);

                // catch the remaining exceptions
            } catch (Exception e) {
                System.out.println(DIVIDER + "\n" + "Please enter a valid task" + "\n" + DIVIDER + "\n");
            }
        }
    }
}
