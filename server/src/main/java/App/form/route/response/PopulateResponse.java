package App.form.route.response;

import org.springframework.http.HttpStatus;

public class PopulateResponse {
    private HttpStatus status;
    private String message;

    public PopulateResponse() {
    }

    /**
     * Return routes found
     *
     * @return Status OK and results
     */
    public PopulateResponse ok() {
        status = HttpStatus.OK;
        message = "DB Populated";
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
