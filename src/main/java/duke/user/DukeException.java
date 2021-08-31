package duke.user;

/**
 * Custom exception class which catches all Duke-specific exceptions.
 */
public class DukeException extends Exception {
    public DukeException() {
        super(" ☹ OOPS!!! I'm sorry, but I don't know what that means :-(");
    }

    public DukeException(String message) {
        super(message);
    }
}
