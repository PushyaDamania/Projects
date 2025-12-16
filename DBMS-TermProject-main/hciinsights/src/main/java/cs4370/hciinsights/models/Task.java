package cs4370.hciinsights.models;

/**
 * Represents a task in the HCI Insights platform.
 */
public class Task {

    /**
     * Unique identifier for the task.
     */
    private final int taskId;

    /**
     * Identifier of the study that the task
     * is a part of.
     */
    private final int studyId;

    /**
     * Order number of the task.
     */
    private final int taskOrder;

    /**
     * Description of the task.
     */
    private final String description;

    /**
     * Success criteria that the task needs
     * for completion.
     */
    private final String successCriteria;

    /**
     * Expected completion time (in minutes)
     * of the task.
     */
    private final int expectedCompTime;

    /**
     * Constructs a task with the specified details.
     * 
     * @param taskId           unique identifier of the task
     * @param studyId          identifier of the study that the task is a part of
     * @param taskOrder        order number of the task
     * @param description      description of the task
     * @param successCriteria  success criteria that the task needs for completion
     * @param expectedCompTime expected completion time (in minutes) of the task
     */
    public Task(int taskId, int studyId, int taskOrder, String description,
            String successCriteria, int expectedCompTime) {

        this.taskId = taskId;
        this.studyId = studyId;
        this.taskOrder = taskOrder;
        this.description = description;
        this.successCriteria = successCriteria;
        this.expectedCompTime = expectedCompTime;

    }

    /**
     * Returns the task id.
     * 
     * @return the task id
     */
    public int getTaskId() {
        return taskId;
    }

    /**
     * Returns the study id that the task belongs to.
     * 
     * @return the study id
     */
    public int getStudyId() {
        return studyId;
    }

    /**
     * Returns the order number of the task.
     * 
     * @return the task order
     */
    public int getTaskOrder() {
        return taskOrder;
    }

    /**
     * Returns the description of the task.
     * 
     * @return the task description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns the success criteria of the task.
     * 
     * @return the success criteria
     */
    public String getSuccessCriteria() {
        return successCriteria;
    }

    /**
     * Returns the expected completion time of the task.
     * 
     * @return the expected completion time
     */
    public int getExpectedCompTime() {
        return expectedCompTime;
    }

}