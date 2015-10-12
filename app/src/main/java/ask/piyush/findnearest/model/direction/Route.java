package ask.piyush.findnearest.model.direction;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Route {

  private Bounds bounds;
  private String copyrights;
  private List<Leg> legs = new ArrayList<Leg>();
  @SerializedName("overview_polyline")
  private OverviewPolyline overviewPolyline;
  private String summary;
  private List<Object> warnings = new ArrayList<Object>();
  private List<Object> waypointOrder = new ArrayList<Object>();
  private Map<String, Object> additionalProperties = new HashMap<String, Object>();

  /**
   * @return The bounds
   */
  public Bounds getBounds() {
    return bounds;
  }

  /**
   * @param bounds The bounds
   */
  public void setBounds(Bounds bounds) {
    this.bounds = bounds;
  }

  /**
   * @return The copyrights
   */
  public String getCopyrights() {
    return copyrights;
  }

  /**
   * @param copyrights The copyrights
   */
  public void setCopyrights(String copyrights) {
    this.copyrights = copyrights;
  }

  /**
   * @return The legs
   */
  public List<Leg> getLegs() {
    return legs;
  }

  /**
   * @param legs The legs
   */
  public void setLegs(List<Leg> legs) {
    this.legs = legs;
  }

  /**
   * @return The overviewPolyline
   */
  public OverviewPolyline getOverviewPolyline() {
    return overviewPolyline;
  }

  /**
   * @param overviewPolyline The overview_polyline
   */
  public void setOverviewPolyline(OverviewPolyline overviewPolyline) {
    this.overviewPolyline = overviewPolyline;
  }

  /**
   * @return The summary
   */
  public String getSummary() {
    return summary;
  }

  /**
   * @param summary The summary
   */
  public void setSummary(String summary) {
    this.summary = summary;
  }

  /**
   * @return The warnings
   */
  public List<Object> getWarnings() {
    return warnings;
  }

  /**
   * @param warnings The warnings
   */
  public void setWarnings(List<Object> warnings) {
    this.warnings = warnings;
  }

  /**
   * @return The waypointOrder
   */
  public List<Object> getWaypointOrder() {
    return waypointOrder;
  }

  /**
   * @param waypointOrder The waypoint_order
   */
  public void setWaypointOrder(List<Object> waypointOrder) {
    this.waypointOrder = waypointOrder;
  }

  public Map<String, Object> getAdditionalProperties() {
    return this.additionalProperties;
  }

  public void setAdditionalProperty(String name, Object value) {
    this.additionalProperties.put(name, value);
  }

}
