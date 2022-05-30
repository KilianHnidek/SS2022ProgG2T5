package at.ac.fhcampuswien;

import java.io.IOException;
import com.google.gson.Gson;
import okhttp3.*;

public class NewsApi {

    private static final String API_KEY = "dc31e44d4d4e4653aad6d93b182e981e";
    private static final String API_KEY_2 = "eeda1a2724234291a06c60a4990b3a31";
    private static final OkHttpClient client = new OkHttpClient();

    public final static String url = "https://newsapi.org/";

    public static String endpointEnum, categoryEnum, countryEnum, languageEnum, sortByEnum;
    public static String query;
    private static Response response;

    static NewsResponse run() throws NewsApiException {
        HttpUrl.Builder urlBuilder = HttpUrl.parse(url).newBuilder();

        urlBuilder.addPathSegment("v2");
        urlBuilder.addPathSegment(endpointEnum);

        urlBuilder.addQueryParameter("q", query);
        urlBuilder.addQueryParameter("apiKey", API_KEY_2);

        if (sortByEnum != null) {
            urlBuilder.addQueryParameter("sortBy", sortByEnum);
        }

        if (countryEnum != null) {
            urlBuilder.addQueryParameter("country", countryEnum);
        }

        if (categoryEnum != null) {
            urlBuilder.addQueryParameter("category", categoryEnum);
        }

        System.out.println(urlBuilder.build());

        Request request = new Request.Builder()
                .url(urlBuilder.build().toString())
                .build();

        try {
            Response response = client.newCall(request).execute();
            Gson gson = new Gson();

            if (!response.isSuccessful()) {
                switch (response.code()) {
                    case 400 -> throw new NewsApiException("400 - Bad Request. The request was unacceptable, often due to a missing or misconfigured parameter.");
                    case 401 -> throw new NewsApiException("401 - Unauthorized. Your API key was missing from the request, or wasn't correct.");
                    case 429 -> throw new NewsApiException("429 - Too Many Requests. You made too many requests within a window of time and have been rate limited. Back off for a while.");
                    case 500 -> throw new NewsApiException("500 - Server Error. Something went wrong on our side.");
                }
                // default ????
                throw new NewsApiException("");
            } else {
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
        } catch (IOException e) {
            // wifi connection
            throw new NewsApiException("something went wrong");
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

