package cs4370.hciinsights.models;

/**
 * Represents a session result in the HCI Insights platform.
 */
public class Result {

    /**
     * Unique identifier of the result.
     */
    private final int resultId;

    /**
     * Session id of which the result belongs to.
     */
    private final int sessionId;

    /**
     * Task id that the session tracks.
     */
    private final int taskId;

    /**
     * Boolean variable representing completion
     * of the result.
     */
    private final boolean isComplete;

    /**
     * Time (in minutes) that it took to
     * achieve completion.
     */
    private final Integer completionTime;

    /**
     * Number of errors made within the session.
     */
    private final Integer errors;

    /**
     * Result notes.
     */
    private final String notes;

    /**
     * Creates a result with the specified details.
     * 
     * @param resultId       unique identifier of the result
     * @param sessionId      session id of which the result belongs to
     * @param taskId         task id that the session tracks
     * @param isComplete     represents completion of the result
     * @param completionTime time (in minutes) that it took to achieve completion
     * @param errors         number of errors made within the session
     * @param notes          notes from the result
     */
    public Result(int resultId, int sessionId, int taskId, boolean isComplete,
            Integer completionTime, Integer errors, String notes) {
        this.resultId = resultId;
        this.sessionId = sessionId;
        this.taskId = taskId;
        this.isComplete = isComplete;
        this.completionTime = completionTime;
        this.errors = errors;
        this.notes = notes;
    }

    /**
     * Returns the result id.
     * 
     * @return result id
     */
    public int getResultId() {
        return resultId;
    }

    /**
     * Returns the session id.
     * 
     * @return session id
     */
    public int getSessionId() {
        return sessionId;
    }

    /**
     * Returns the task id.
     * 
     * @return task id
     */
    public int getTaskId() {
        return taskId;
    }

    /**
     * Returns the completion status of the result.
     * 
     * @return completion status
     */
    public boolean getIsComplete() {
        return isComplete;
    }

    /**
     * Returns the completion time (in minutes) of the result.
     * 
     * @return completion time (in minutes)
     */
    public Integer getCompletionTime() {
        return completionTime;
    }

    /**
     * Returns the number of errors from the result.
     * 
     * @return error number
     */
    public Integer getErrors() {
        return errors;
    }

    /**
     * Returns the notes from the result.
     * 
     * @return result notes
     */
    public String getNotes() {
        return notes;
    }
}
