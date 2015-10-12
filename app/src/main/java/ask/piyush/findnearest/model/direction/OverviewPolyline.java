package ask.piyush.findnearest.model.direction;

import java.util.HashMap;
import java.util.Map;

public class OverviewPolyline {

  private String points;
  private Map<String, Object> additionalProperties = new HashMap<String, Object>();

  /**
   * @return The points
   */
  public String getPoints() {
    return points;
  }

  /**
   * @param points The points
   */
  public void setPoints(String points) {
    this.points = points;
  }

  public Map<String, Object> getAdditionalProperties() {
    return this.additionalProperties;
  }

  public void setAdditionalProperty(String name, Object value) {
    this.additionalProperties.put(name, value);
  }

}
