package ask.piyush.findnearest.model.places;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class OpeningHours {

    @SerializedName("open_now")
    private Boolean openNow;
    private List<Object> weekdayText = new ArrayList<Object>();

    /**
     * @return The openNow
     */
    public Boolean getOpenNow() {
        return openNow;
    }

    /**
     * @param openNow The open_now
     */
    public void setOpenNow(Boolean openNow) {
        this.openNow = openNow;
    }

    /**
     * @return The weekdayText
     */
    public List<Object> getWeekdayText() {
        return weekdayText;
    }

    /**
     * @param weekdayText The weekday_text
     */
    public void setWeekdayText(List<Object> weekdayText) {
        this.weekdayText = weekdayText;
    }

    @Override
    public String toString() {
        return "OpeningHours{" +
                "openNow=" + openNow +
                ", weekdayText=" + weekdayText +
                '}';
    }
}
