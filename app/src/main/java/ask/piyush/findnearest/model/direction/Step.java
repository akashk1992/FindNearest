package ask.piyush.findnearest.model.direction;

import java.util.HashMap;
import java.util.Map;

public class Step {

  private Distance distance;
  private Duration duration;
  private EndLocation endLocation;
  private String htmlInstructions;
  private Polyline polyline;
  private StartLocation startLocation;
  private String travelMode;
  private String maneuver;
  private Map<String, Object> additionalProperties = new HashMap<String, Object>();

  public Distance getDistance() {
    return distance;
  }

  public void setDistance(Distance distance) {
    this.distance = distance;
  }

  public Duration getDuration() {
    return duration;
  }

  public void setDuration(Duration duration) {
    this.duration = duration;
  }

  public EndLocation getEndLocation() {
    return endLocation;
  }

  public void setEndLocation(EndLocation endLocation) {
    this.endLocation = endLocation;
  }

  public StartLocation getStartLocation() {
    return startLocation;
  }

  public void setStartLocation(StartLocation startLocation) {
    this.startLocation = startLocation;
  }

  public void setAdditionalProperties(Map<String, Object> additionalProperties) {
    this.additionalProperties = additionalProperties;
  }

  /**
   * @return The htmlInstructions
   */
  public String getHtmlInstructions() {
    return htmlInstructions;
  }

  /**
   * @param htmlInstructions The html_instructions
   */
  public void setHtmlInstructions(String htmlInstructions) {
    this.htmlInstructions = htmlInstructions;
  }

  /**
   * @return The polyline
   */
  public Polyline getPolyline() {
    return polyline;
  }

  /**
   * @param polyline The polyline
   */
  public void setPolyline(Polyline polyline) {
    this.polyline = polyline;
  }

  /**
   * @return The travelMode
   */
  public String getTravelMode() {
    return travelMode;
  }

  /**
   * @param travelMode The travel_mode
   */
  public void setTravelMode(String travelMode) {
    this.travelMode = travelMode;
  }

  /**
   * @return The maneuver
   */
  public String getManeuver() {
    return maneuver;
  }

  /**
   * @param maneuver The maneuver
   */
  public void setManeuver(String maneuver) {
    this.maneuver = maneuver;
  }

  public Map<String, Object> getAdditionalProperties() {
    return this.additionalProperties;
  }

  public void setAdditionalProperty(String name, Object value) {
    this.additionalProperties.put(name, value);
  }

}
