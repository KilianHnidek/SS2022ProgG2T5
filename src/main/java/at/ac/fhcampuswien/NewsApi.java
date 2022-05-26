package at.ac.fhcampuswien;

import java.io.IOException;

import at.ac.fhcampuswien.enums.*;
import com.google.gson.Gson;
import okhttp3.*;

public class NewsApi {

    private static final String API_KEY = "dc31e44d4d4e4653aad6d93b182e981e";
    private static final OkHttpClient client = new OkHttpClient();

    public final static String url = "https://newsapi.org/";

    public static EndpointEnum endpointEnum;
    //public static CategoryEnum categoryEnum;
    public static CountryEnum countryEnum;
    public static SortByEnum sortByEnum;
    public static String query;

    static NewsResponse run() throws IOException {
        HttpUrl.Builder urlBuilder = HttpUrl.parse(url).newBuilder();

        urlBuilder.addPathSegment("v2");
        urlBuilder.addPathSegment(endpointEnum.getName());

        urlBuilder.addQueryParameter("q", query);
        urlBuilder.addQueryParameter("apiKey", API_KEY);

        if (sortByEnum != null) {
            urlBuilder.addQueryParameter("sortBy", sortByEnum.toString());
        }

        if (countryEnum != null) {
            urlBuilder.addQueryParameter("country", countryEnum.toString());
        }

        System.out.println(urlBuilder.build());

        Request request = new Request.Builder()
                .url(urlBuilder.build().toString())
                .build();

        try (Response response = client.newCall(request).execute()) {
            Gson gson = new Gson();

            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            } else {
                //System.out.println(response.body().string());
                return gson.fromJson(response.body().string(), NewsResponse.class);

                /*
                    200 - OK. The request was executed successfully.
                    400 - Bad Request. The request was unacceptable, often due to a missing or misconfigured parameter.
                    401 - Unauthorized. Your API key was missing from the request, or wasn't correct.
                    429 - Too Many Requests. You made too many requests within a window of time and have been rate limited. Back off for a while.
                    500 - Server Error. Something went wrong on our side.

                    apiKeyDisabled - Your API key has been disabled.
                    apiKeyExhausted - Your API key has no more requests available.
                    apiKeyInvalid - Your API key hasn't been entered correctly. Double check it and try again.
                    apiKeyMissing - Your API key is missing from the request. Append it to the request with one of these methods.
                    parameterInvalid - You've included a parameter in your request which is currently not supported. Check the message property for more details.
                    parametersMissing - Required parameters are missing from the request and it cannot be completed. Check the message property for more details.
                    rateLimited - You have been rate limited. Back off for a while before trying the request again.
                    sourcesTooMany - You have requested too many sources in a single request. Try splitting the request into 2 smaller requests.
                    sourceDoesNotExist - You have requested a source which does not exist.
                    unexpectedError - This shouldn't happen, and if it does then it's our fault, not yours. Try the request again shortly.
                 */
            }
        }
    }
/*
    public static void main(String[] args) throws IOException {
        NewsApi newsApi = new NewsApi();
        NewsResponse response = newsApi.run();

        for (Article a : response.getArticles()) {
            System.out.println(a);
        }
    }
 */
}

