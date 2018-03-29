package com.musala.testRest.restapi.helloworld;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

// The Java class will be hosted at the URI path "/helloworld"
@Path("/helloworld")
public class HelloWorld {
    // The Java method will process HTTP GET requests
    @GET
    // The Java method will process content identified by the MIME Media type "text/plain"
    @Produces("text/plain")
    public String getClichedMessage() {
        // Return some cliched textual content
        return "Hello World";
    }
    @GET
    @Produces("application/json")
    @Path("/json")
    public String getJsonMessage() {

        try {
            // Create some json content
            JSONObject card = new JSONObject();
            card.put("number", "0123456789012345")
                .put("expiryDate", "10/22")
                .put("controlNumber", "566")
                .put("type", "MasterCard");

            return card.toString();
        } catch(JSONException e) {

            return "Unknown error: " + e;
        }
    }
}