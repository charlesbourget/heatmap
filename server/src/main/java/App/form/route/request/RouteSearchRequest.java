package App.form.route.request;

import java.util.Date;

public class RouteSearchRequest {
    private Date fromDate;
    private Date toDate;


    public RouteSearchRequest(Date fromDate, Date toDate) {
        this.fromDate = fromDate;
        this.toDate = toDate;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public Date getToDate() {
        return toDate;
    }
}
