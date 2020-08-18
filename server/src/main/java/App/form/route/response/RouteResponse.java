package App.form.route.response;

import App.model.Position;
import App.model.Route;
import org.springframework.http.HttpStatus;

import java.util.List;

public class RouteResponse {
    private HttpStatus status;
    private String message;
    private List<Position> results;

    public RouteResponse() {
    }

    /**
     * Return routes found
     *
     * @param results List of all routes
     * @return Status OK and results
     */
    public RouteResponse ok(List<Position> results) {
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
    public RouteResponse noContent() {
        status = HttpStatus.NO_CONTENT;
        message = "No result found";
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

