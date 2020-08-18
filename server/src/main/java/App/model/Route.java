package App.model;

import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.Date;

public class Route {

    @Id
    private String id;

    private String name;
    private String type;
    private Date date;
    private ArrayList<Position> listPosition;

    public Route(String name, String type, Date date, ArrayList<Position> listPosition) {
        this.name = name;
        this.type = type;
        this.date = date;
        this.listPosition = listPosition;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public Date getDate() {
        return date;
    }

    public ArrayList<Position> getListPosition() {
        return listPosition;
    }
}
