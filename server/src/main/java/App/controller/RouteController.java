package App.controller;

import App.form.route.request.RouteSearchRequest;
import App.form.route.response.PopulateResponse;
import App.form.route.response.PurgeResponse;
import App.form.route.response.RouteResponse;
import App.form.route.response.RouteSearchResponse;
import App.service.RouteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;

/**
 * All operations related to movies
 */
@RestController("Route")
@RequestMapping("/route")
@Api(value = "route", description = "All operations related to GPX routes")
public class RouteController {
    @Autowired
    RouteService routeService;

    @GetMapping("")
    @ResponseBody
    @ApiOperation(value = "Returns all known routes")
    public ResponseEntity<RouteResponse> findRoute() {
        RouteResponse response = routeService.findRoute();
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping("/date")
    @ResponseBody
    @ApiOperation(value = "Returns all routes in date range")
    public ResponseEntity<RouteSearchResponse> findRouteInDateRange(
            @Valid @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fromDate,
            @Valid @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date toDate) {
        RouteSearchRequest routeSearchRequest = new RouteSearchRequest(fromDate, toDate);
        RouteSearchResponse response = routeService.findRouteInDateRange(routeSearchRequest);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping("/populate")
    @ApiOperation(value = "Populate DB with routes from gpx files")
    public ResponseEntity<PopulateResponse> populateDB() {
        PopulateResponse response = routeService.populateDB();
        return ResponseEntity.status(response.getStatus()).body(null);
    }

    @GetMapping("/purge")
    @ApiOperation(value = "Purge route collection")
    public ResponseEntity<PurgeResponse> purgeDB() {
        PurgeResponse response = routeService.purgeDB();
        return ResponseEntity.status(response.getStatus()).body(null);
    }
}
