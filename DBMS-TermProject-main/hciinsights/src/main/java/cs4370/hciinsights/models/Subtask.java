package cs4370.hciinsights.models;

/**
 * Represents a subtask in the HCI Insights platform.
 */
public class Subtask {

    /**
     * Unique identifier of the subtask.
     */
    private final int subtaskId;

    /**
     * Identifier of the task that the subtask belongs to.
     */
    private final int taskId;

    /**
     * Description of the subtask.
     */
    private final String description;

    /**
     * Expected completion time (in minutes) of the subtask.
     */
    private final int expectedCompTime;

    /**
     * Creates a subtask with the speicified details.
     * 
     * @param subtaskId        unique identifier of the subtask
     * @param taskId           identifier of the task that the subtask belongs to
     * @param description      description of the subtask
     * @param expectedCompTime expected completion time (in minutes) of the subtask
     */
    public Subtask(int subtaskId, int taskId, String description, Integer expectedCompTime) {
        this.subtaskId = subtaskId;
        this.taskId = taskId;
        this.description = description;
        this.expectedCompTime = expectedCompTime;
    }

    /**
     * Returns the id of the subtask.
     * 
     * @return the subtask id
     */
    public int getSubtaskId() {
        return subtaskId;
    }

    /**
     * Returns the task id of the task that the subtask belongs to.
     * 
     * @return the task id
     */
    public int getTaskId() {
        return taskId;
    }

    /**
     * Returns the description of the subtask.
     * 
     * @return the description of the subtask
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns the expected completion time (in minutes) of the subtask.
     * 
     * @return the expected completion time (in minutes) of the subtask
     */
    public Integer getExpectedCompTime() {
        return expectedCompTime;
    }

}