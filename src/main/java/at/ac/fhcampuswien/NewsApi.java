package at.ac.fhcampuswien;

import java.io.IOException;

import at.ac.fhcampuswien.enums.CountryEnum;
import com.google.gson.Gson;

import at.ac.fhcampuswien.enums.CategoryEnum;
import at.ac.fhcampuswien.enums.EndpointEnum;
import okhttp3.*;


public class NewsApi {

    private static final String API_KEY = "dc31e44d4d4e4653aad6d93b182e981e";
    private static OkHttpClient client = new OkHttpClient();

    public final static String url = "https://newsapi.org/";

    public static EndpointEnum endpointEnum;
    public static CategoryEnum categoryEnum;
    public static CountryEnum countryEnum;

    static NewsResponse run() throws IOException {
        HttpUrl.Builder urlBuilder = HttpUrl.parse(url).newBuilder();
        urlBuilder.addPathSegment("v2");

        urlBuilder.addPathSegment(endpointEnum.getName());
        urlBuilder.addQueryParameter("q", categoryEnum.toString());
        urlBuilder.addQueryParameter("apiKey", API_KEY);

        if (countryEnum != null) {
            urlBuilder.addQueryParameter("country", countryEnum.toString());
        }


        // https://newsapi.org/v2/everything?q=bitcoin&apiKey=<YOUR_API_KEY>

        System.out.println(urlBuilder.build());

        Request request = new Request.Builder()
                .url(urlBuilder.build().toString())
                .build();

        try (Response response = client.newCall(request).execute()) {
            Gson gson = new Gson();

            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
            else {
                NewsResponse newsResponse = gson.fromJson(response.body().string(), NewsResponse.class);
                return newsResponse;
            }
            /*
            3 infos:
                -> status
                -> number
                -> articles
             */


        }
    }

    public static void main(String[] args) throws IOException {
        NewsApi newsApi = new NewsApi();
        NewsResponse response = newsApi.run();

        for (Article a : response.getArticles()) {
            System.out.println(a);
        }
    }
}

