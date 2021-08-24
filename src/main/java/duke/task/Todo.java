package duke.task;

public class Todo extends Task {

    public Todo(String desc) {
        super(desc);
        this.taskType = "T";
        this.dueDate = "";
    }

    public Todo(String desc, Boolean isDone) {
        super(desc);
        this.taskType = "T";
        this.dueDate = "";
        this.isDone = isDone;
    }

    @Override
    public String toString() {
        return "[" + taskType + "]" + super.toString();
    }
}
