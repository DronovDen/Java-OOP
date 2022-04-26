package status;

public class Status {
    public StatusCode statusCode;
    public String statusMessage;

    /**
     * Creates new {@code Status} with a specific code and message
     * @param code - new status code
     * @param msg - new message code
     */
    public Status(StatusCode code, String msg) {
        this.statusCode = code;
        this.statusMessage = msg;
    }
}
