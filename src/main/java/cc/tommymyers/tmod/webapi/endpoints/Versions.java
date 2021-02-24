package cc.tommymyers.tmod.webapi.endpoints;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class Versions {

    public static Response.Version getLatest() throws IOException, JsonSyntaxException {
        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
            HttpGet getRequest = new HttpGet("https://api.tmod.cc/versions");
            HttpResponse httpResponse = httpClient.execute(getRequest);
            String jsonString = EntityUtils.toString(httpResponse.getEntity());
            Gson gson = new Gson();
            Response response = gson.fromJson(jsonString, Response.class);
            return response.getLatest();
        }
    }

    public class Response {

        private Version latest;

        public Version getLatest() {
            return latest;
        }

        public class Version {

            private String id;
            private String url;

            public String getUrl() {
                return url;
            }

            public String getId() {
                return id;
            }

        }

    }

}
