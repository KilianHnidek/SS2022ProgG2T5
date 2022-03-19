package at.ac.fhcampuswien.apiClass;

import java.io.IOException;
import java.util.List;

import com.google.gson.Gson;

import at.ac.fhcampuswien.Article;
import at.ac.fhcampuswien.enums.CategoryEnum;
import at.ac.fhcampuswien.enums.EndpointEnum;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class NewsApi {

    private EndpointEnum endpointEnum = EndpointEnum.everything;

    private static final String API_KEY = "dc31e44d4d4e4653aad6d93b182e981e";

    final OkHttpClient client = new OkHttpClient();

    NewsResponse run(String url) throws IOException {

        HttpUrl.Builder urlBuilder = HttpUrl.parse(url).newBuilder();
        urlBuilder.addPathSegment("v2");
        urlBuilder.addPathSegment(endpointEnum.toString());
        urlBuilder.addQueryParameter("q", CategoryEnum.Bitcoin.toString());
        urlBuilder.addQueryParameter("apiKey", API_KEY);

        // https://newsapi.org/v2/everything?q=bitcoin&apiKey=<YOUR_API_KEY>

        url = urlBuilder.build().toString();


        Request request = new Request.Builder()
                .url(url)
                .build();

        System.out.println(url);

        try (Response response = client.newCall(request).execute()) {

            Gson gson = new Gson();

            NewsResponse newsResponse = gson.fromJson(response.body().toString(), NewsResponse.class);
            return newsResponse;

        }
    }

    public static void main(String[] args) throws IOException {
        NewsApi newsApi = new NewsApi();
        NewsResponse response = newsApi.run("https://newsapi.org/");

        for (Article a : response.getArticles()) {
            System.out.println(a);
        }

    }
}

