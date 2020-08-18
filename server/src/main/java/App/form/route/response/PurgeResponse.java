package App.form.route.response;

import org.springframework.http.HttpStatus;

public class PurgeResponse {
    private HttpStatus status;
    private String message;

    public PurgeResponse() {
    }

    /**
     * Return routes found
     *
     * @param results List of all routes
     * @return Status OK and results
     */
    public PurgeResponse ok() {
        status = HttpStatus.OK;
        message = "DB Purged";
        return this;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
