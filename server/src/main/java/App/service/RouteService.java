package App.service;

import App.form.route.response.PurgeResponse;
import App.model.Position;
import App.model.Route;
import App.form.route.response.PopulateResponse;
import App.form.route.response.RouteResponse;
import App.repository.RouteRepository;
import io.jenetics.jpx.GPX;
import io.jenetics.jpx.Metadata;
import io.jenetics.jpx.Track;
import io.jenetics.jpx.TrackSegment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class RouteService {

    @Autowired
    private RouteRepository repository;

    public RouteResponse findRoute() {
        List<Route> routes = repository.findAll();

        if (routes.isEmpty()) {
            return new RouteResponse().noContent();
        }

        ArrayList<Position> positions = new ArrayList<>();

        routes.forEach(route -> {
            positions.addAll(route.getListPosition());
        });

        if (positions.isEmpty()) {
            return new RouteResponse().noContent();
        }

        return new RouteResponse().ok(positions);
    }

    public PopulateResponse populateDB() {
        try {
            ArrayList<Position> listPositions = new ArrayList<Position>();
            AtomicInteger i = new AtomicInteger();
            GPX gpxFile = GPX.read("src/main/resources/oka_ride.gpx");

            String name = gpxFile.getTracks().get(0).getName().orElse("");
            String type = gpxFile.getTracks().get(0).getType().orElse("");
            Metadata metadata = gpxFile.getMetadata().orElse(null);
            ZonedDateTime zonedDateTime = ZonedDateTime.now();

            if (metadata != null) {
                zonedDateTime = metadata.getTime().orElse(ZonedDateTime.now());
            }

            Date date = Date.from(zonedDateTime.toInstant());

            gpxFile.tracks()
                    .flatMap(Track::segments)
                    .flatMap(TrackSegment::points)
                    .forEach(position -> {
                        // Strip 9 waypoint on 10 to save space
                        if (i.get() % 10 == 0) {
                            listPositions.add(new Position(
                                    position.getLongitude().doubleValue(),
                                    position.getLatitude().doubleValue()
                            ));
                        }
                        i.getAndIncrement();
                    });

            repository.save(new Route(name, type, date, listPositions));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new PopulateResponse().ok();
    }

    public PurgeResponse purgeDB() {

        repository.deleteAll();

        return new PurgeResponse().ok();
    }
}
