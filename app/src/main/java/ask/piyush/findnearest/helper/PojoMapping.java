package ask.piyush.findnearest.helper;

import com.google.gson.Gson;

import ask.piyush.findnearest.model.Response;

/**
 * Created by piyush on 21/6/15.
 */
public class PojoMapping {
    public Response getPlacesResponse(String jsonResponse) {
        Gson gson = new Gson();
        Response response = gson.fromJson(jsonResponse, Response.class);
        return response;
    }
}
