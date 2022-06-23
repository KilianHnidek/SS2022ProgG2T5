package at.ac.fhcampuswien;

import okhttp3.HttpUrl;
import okhttp3.Request;

public class URL {
    private static final String API_KEY = "dc31e44d4d4e4653aad6d93b182e981e";
    private static final String API_KEY_2 = "eeda1a2724234291a06c60a4990b3a31";

    private static final String url = "https://newsapi.org/";

    private String sortByParam, countryParam, categoryParam, languageParam,
            queryParam, endpointParam;

    public Request getRequest() {
        HttpUrl.Builder urlBuilder = HttpUrl.parse(url).newBuilder();

        urlBuilder.addPathSegment("v2").addPathSegment(endpointParam)
                .addQueryParameter("q", queryParam)
                .addQueryParameter("apiKey", API_KEY_2)
                .addQueryParameter("pageSize", "100");

        if (sortByParam != null) {
            urlBuilder.addQueryParameter("sortBy", sortByParam);
        }

        if (countryParam != null) {
            urlBuilder.addQueryParameter("country", countryParam);
        }

        if (categoryParam != null) {
            urlBuilder.addQueryParameter("category", categoryParam);
        }
        if (languageParam != null) {
            urlBuilder.addQueryParameter("language", languageParam);
        }

        return new Request.Builder().url(urlBuilder.toString()).build();
    }

    private URL(Builder builder) {
        this.sortByParam = builder.sortByParam;
        this.countryParam = builder.countryParam;
        this.categoryParam = builder.categoryParam;
        this.languageParam = builder.languageParam;
        this.queryParam = builder.queryParam;
        this.endpointParam = builder.endpointParam;
    }

    public static class Builder {
        private String sortByParam, countryParam, categoryParam, languageParam,
                queryParam, endpointParam;

        public Builder sortBy(String sortByParam) {
            this.sortByParam = sortByParam;
            return this;
        }

        public Builder country(String countryParam) {
            this.countryParam = countryParam;
            return this;
        }

        public Builder category(String categoryParam) {
            this.categoryParam = categoryParam;
            return this;
        }

        public Builder language(String languageParam) {
            this.languageParam = languageParam;
            return this;
        }

        public Builder query(String queryParam) {
            this.queryParam = queryParam;
            return this;
        }

        public Builder endpoint(String endpointParam) {
            this.endpointParam = endpointParam;
            return this;
        }

        public URL build() {
            return new URL(this);
        }
    }
}
