package App.repository;

import App.model.Route;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Date;
import java.util.List;

public interface RouteRepository extends MongoRepository<Route, String> {
    List<Route> findAllByDateBetween(Date fromDate, Date toDate);
    List<Route> findAllByDateAfter(Date fromDate);
    List<Route> findAllByDateBefore(Date toDate);
}
