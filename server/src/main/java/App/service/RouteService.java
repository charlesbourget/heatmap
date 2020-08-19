package App.service;

import App.config.ReadPropertyFile;
import App.form.route.response.PopulateResponse;
import App.form.route.response.PurgeResponse;
import App.form.route.response.RouteResponse;
import App.lib.Gpx;
import App.model.Position;
import App.model.Route;
import App.repository.RouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

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
                    Thread populate_thread = new Thread(() -> {
                        Gpx.readGpxFile(gpxFile).ifPresent(value -> repository.save(value));
                    });
                    populate_thread.start();

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
}
