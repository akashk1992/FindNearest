package ask.piyush.findnearest.model.direction;

import java.util.HashMap;
import java.util.Map;

public class Duration {

  private String text;
  private Integer value;
  private Map<String, Object> additionalProperties = new HashMap<String, Object>();

  /**
   * @return The text
   */
  public String getText() {
    return text;
  }

  /**
   * @param text The text
   */
  public void setText(String text) {
    this.text = text;
  }

  /**
   * @return The value
   */
  public Integer getValue() {
    return value;
  }

  /**
   * @param value The value
   */
  public void setValue(Integer value) {
    this.value = value;
  }

  public Map<String, Object> getAdditionalProperties() {
    return this.additionalProperties;
  }

  public void setAdditionalProperty(String name, Object value) {
    this.additionalProperties.put(name, value);
  }

}
