package ask.piyush.findnearest.model.direction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DirectionResponse {

  private List<GeocodedWaypoint> geocodedWaypoints = new ArrayList<GeocodedWaypoint>();
  private List<Route> routes = new ArrayList<Route>();
  private String status;
  private Map<String, Object> additionalProperties = new HashMap<String, Object>();

  /**
   * @return The geocodedWaypoints
   */
  public List<GeocodedWaypoint> getGeocodedWaypoints() {
    return geocodedWaypoints;
  }

  /**
   * @param geocodedWaypoints The geocoded_waypoints
   */
  public void setGeocodedWaypoints(List<GeocodedWaypoint> geocodedWaypoints) {
    this.geocodedWaypoints = geocodedWaypoints;
  }

  /**
   * @return The routes
   */
  public List<Route> getRoutes() {
    return routes;
  }

  /**
   * @param routes The routes
   */
  public void setRoutes(List<Route> routes) {
    this.routes = routes;
  }

  /**
   * @return The status
   */
  public String getStatus() {
    return status;
  }

  /**
   * @param status The status
   */
  public void setStatus(String status) {
    this.status = status;
  }

  public Map<String, Object> getAdditionalProperties() {
    return this.additionalProperties;
  }

  public void setAdditionalProperty(String name, Object value) {
    this.additionalProperties.put(name, value);
  }

}
