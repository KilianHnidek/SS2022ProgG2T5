package at.ac.fhcampuswien.enums;

public enum EndpointEnum
{
    everything("everything"),
    topHeadlines("top-headlines");

    private String name;

    EndpointEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
