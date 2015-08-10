package ask.piyush.findnearest.helper;

import com.orm.SugarRecord;

/**
 * Created by piyush on 9/8/15.
 */
public class FirstTimeUser extends SugarRecord<FirstTimeUser> {
    boolean first_time;

    public FirstTimeUser() {
    }

    public FirstTimeUser(boolean first_time) {
        this.first_time = first_time;
    }

    public boolean isFirst_time() {
        return first_time;
    }

    public void setFirst_time(boolean first_time) {
        this.first_time = first_time;
    }

    @Override
    public String toString() {
        return "FirstTimeUser{" +
                "first_time=" + first_time +
                '}';
    }
}
