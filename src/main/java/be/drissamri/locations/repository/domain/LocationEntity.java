package be.drissamri.locations.repository.domain;

import java.util.Objects;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "locations")
public class LocationEntity {
  private String id;
  private String subject;
  private GeoJsonPoint location;

  public LocationEntity(final String subject, final GeoJsonPoint location) {
    this.subject = subject;
    this.location = location;
  }

  public String getId() {
    return this.id;
  }

  public void setId(final String id) {
    this.id = id;
  }

  public String getSubject() {
    return this.subject;
  }

  public void setSubject(final String subject) {
    this.subject = subject;
  }

  public GeoJsonPoint getLocation() {
    return this.location;
  }

  public void setLocation(final GeoJsonPoint location) {
    this.location = location;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) return true;
    if (o == null || this.getClass() != o.getClass()) return false;
    final LocationEntity that = (LocationEntity) o;
    return Objects.equals(this.getId(), that.getId()) &&
      Objects.equals(this.getSubject(), that.getSubject());
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.getId(), this.getSubject());
  }

}
