package ask.piyush.findnearest.model.direction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GeocodedWaypoint {

  private String geocoderStatus;
  private String placeId;
  private List<String> types = new ArrayList<String>();
  private Map<String, Object> additionalProperties = new HashMap<String, Object>();

  /**
   * @return The geocoderStatus
   */
  public String getGeocoderStatus() {
    return geocoderStatus;
  }

  /**
   * @param geocoderStatus The geocoder_status
   */
  public void setGeocoderStatus(String geocoderStatus) {
    this.geocoderStatus = geocoderStatus;
  }

  /**
   * @return The placeId
   */
  public String getPlaceId() {
    return placeId;
  }

  /**
   * @param placeId The place_id
   */
  public void setPlaceId(String placeId) {
    this.placeId = placeId;
  }

  /**
   * @return The types
   */
  public List<String> getTypes() {
    return types;
  }

  /**
   * @param types The types
   */
  public void setTypes(List<String> types) {
    this.types = types;
  }

  public Map<String, Object> getAdditionalProperties() {
    return this.additionalProperties;
  }

  public void setAdditionalProperty(String name, Object value) {
    this.additionalProperties.put(name, value);
  }

}
