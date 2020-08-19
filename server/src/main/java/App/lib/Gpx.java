package App.lib;

import App.model.Position;
import App.model.Route;
import io.jenetics.jpx.GPX;
import io.jenetics.jpx.Metadata;
import io.jenetics.jpx.Track;
import io.jenetics.jpx.TrackSegment;

import java.io.IOException;
import java.nio.file.Path;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

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
