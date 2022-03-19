package at.ac.fhcampuswien;

import java.io.IOException;

import com.google.gson.Gson;

import at.ac.fhcampuswien.enums.CategoryEnum;
import at.ac.fhcampuswien.enums.EndpointEnum;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class NewsApi {

    private static final String API_KEY = "dc31e44d4d4e4653aad6d93b182e981e";
    private final OkHttpClient client = new OkHttpClient();

    public static String url = "https://newsapi.org/";

    public static EndpointEnum endpointEnum;
    public static CategoryEnum categoryEnum;


    NewsResponse run() throws IOException {
        HttpUrl.Builder urlBuilder = HttpUrl.parse(url).newBuilder();
        urlBuilder.addPathSegment("v2");

        urlBuilder.addPathSegment(endpointEnum.toString());
        urlBuilder.addQueryParameter("q", categoryEnum.toString());

        urlBuilder.addQueryParameter("apiKey", API_KEY);

        // https://newsapi.org/v2/everything?q=bitcoin&apiKey=<YOUR_API_KEY>

        url = urlBuilder.build().toString();

        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            Gson gson = new Gson();

            //System.out.println(response.body().string());

            NewsResponse newsResponse = gson.fromJson(response.body().string(), NewsResponse.class);

            /*
            3 infos:
                -> status
                -> number
                -> articles
             */

            return newsResponse;
        }
    }

    public static void main(String[] args) throws IOException {
        NewsApi newsApi = new NewsApi();
        NewsResponse response = newsApi.run();
    }
}

