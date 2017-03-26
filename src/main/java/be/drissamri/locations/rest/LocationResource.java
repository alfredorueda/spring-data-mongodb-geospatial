package be.drissamri.locations.rest;

import be.drissamri.locations.model.LocationEntry;
import be.drissamri.locations.repository.LocationRepository;
import be.drissamri.locations.repository.domain.LocationEntity;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/*
https://drissamri.be/blog/2015/08/18/build-a-location-api-with-spring-data-mongodb-and-geojson/
En el tutorial hay dos errores:
el primer error es el siguiente: use locations -> use test por defecto, Spring Boot usa test.
db.locations.createIndex( { location : "2dsphere" } )


El Segundo:  @RequestParam(value = "s", required = false) String subjects)  -> @RequestParam(value = "s") String subject)
 En la query de Spring Data el subject es obligatorio. El nombre no es subjects sino subject.
 */


@RestController
@RequestMapping(value = "/api/locations")
public class LocationResource {
  @Autowired
  private LocationRepository repository;

  @RequestMapping(method = RequestMethod.GET)
  public final List<LocationEntity> getLocations(
    @RequestParam("lat") String latitude,
    @RequestParam("long") String longitude,
    @RequestParam("d") double distance,
    @RequestParam(value = "s") String subject) {

    return this.repository.findBySubjectAndLocationNear(subject,
      new Point(Double.valueOf(longitude), Double.valueOf(latitude)),
      new Distance(distance, Metrics.KILOMETERS));
  }

  @RequestMapping(method = RequestMethod.POST)
  @ResponseStatus(HttpStatus.CREATED)
  public final void addLocations(
    @RequestParam("s") String sid,
    @RequestBody List<LocationEntry> entries) {

    List<LocationEntity> entities = new ArrayList<>();
    for (LocationEntry location : entries) {
      final GeoJsonPoint locationPoint = new GeoJsonPoint(
        Double.valueOf(location.getLongitude()),
        Double.valueOf(location.getLatitude()));

      entities.add(new LocationEntity(sid, locationPoint));
    }

    this.repository.save(entities);
  }
}
