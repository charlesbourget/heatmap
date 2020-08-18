package App.service;

import App.config.ReadPropertyFile;
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
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class RouteService {

    @Autowired
    private RouteRepository repository;

    private ReadPropertyFile readPropertyFile;

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
            readPropertyFile = ReadPropertyFile.getInstance();

            String path = readPropertyFile.getGpxDir();

            Files.walk(Paths.get(path)).forEach(gpxFile -> {
                if (Files.isRegularFile(gpxFile) && gpxFile.toString().matches(".*\\.gpx")) {
                    readGpxFile(gpxFile).ifPresent(value -> repository.save(value));
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new PopulateResponse().ok();
    }

    public PurgeResponse purgeDB() {

        repository.deleteAll();

        return new PurgeResponse().ok();
    }

    private Optional<Route> readGpxFile(Path path) {
        ArrayList<Position> listPositions = new ArrayList<Position>();
        AtomicInteger i = new AtomicInteger();

        GPX gpxFile;

        try {
            gpxFile = GPX.read(path);
        } catch (IOException e) {
            return Optional.empty();
        }

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

        if (listPositions.isEmpty()) {
            // Route can't exist without GPS positions
            return Optional.empty();
        }

        Route route = new Route(name, type, date, listPositions);

        return Optional.of(route);
    }
}
