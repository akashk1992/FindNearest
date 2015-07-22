package ask.piyush.findnearest.helper;

import com.google.gson.Gson;

import ask.piyush.findnearest.model.direction.DirectionResponse;
import ask.piyush.findnearest.model.places.Response;

/**
 * Created by piyush on 21/6/15.
 */
public class PojoMapping {
    public Response getPlacesResponse(String jsonResponse) {
        Gson gson = new Gson();
        Response response = gson.fromJson(jsonResponse, Response.class);
        return response;
    }

    public DirectionResponse getResponse(String jsonResponse) {
        Gson gson = new Gson();
        DirectionResponse response = gson.fromJson(jsonResponse, DirectionResponse.class);
        return response;
    }
}
