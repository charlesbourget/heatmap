package App.lib;

import App.model.Position;
import App.model.Route;
import io.jenetics.jpx.GPX;
import io.jenetics.jpx.Track;
import io.jenetics.jpx.TrackSegment;

import java.io.IOException;
import java.nio.file.Path;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class Gpx {
    public static Optional<Route> readGpxFile(Path path) {
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
        AtomicReference<ZonedDateTime> zonedDateTime = new AtomicReference<>();

        gpxFile.tracks()
                .flatMap(Track::segments)
                .flatMap(TrackSegment::points)
                .forEach(position -> {
                    if (i.get() == 0) {
                        zonedDateTime.set(position.getTime().orElse(ZonedDateTime.now()));
                    }
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

        Date date = Date.from(zonedDateTime.get().toInstant());

        Route route = new Route(name, type, date, listPositions);

        return Optional.of(route);
    }
}
