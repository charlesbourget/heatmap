package App.form.route.response;

import App.model.Position;
import org.springframework.http.HttpStatus;

import java.util.List;

public class RouteSearchResponse {
    private HttpStatus status;
    private String message;
    private List<Position> results;

    public RouteSearchResponse() {
    }

    /**
     * Return routes found
     *
     * @param results List of all routes
     * @return Status OK and results
     */
    public RouteSearchResponse ok(List<Position> results) {
        status = HttpStatus.OK;
        message = "Routes found";
        this.results = results;
        return this;
    }

    /**
     * No routes where found
     *
     * @return Status NO_CONTENT
     */
    public RouteSearchResponse noContent() {
        status = HttpStatus.NO_CONTENT;
        message = "No result found";
        return this;
    }

    public RouteSearchResponse invalidRequest() {
        status = HttpStatus.BAD_REQUEST;
        message = "At least one date should be passed as param";
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

    public List<Position> getResults() {
        return results;
    }

    public void setResults(List<Position> results) {
        this.results = results;
    }
}
