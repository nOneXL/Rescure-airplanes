package iaf.ofek.hadracha.base_course.web_server.Data;

public class Coordinates {
    public double lat;
    public double lon;

    @Override
    public String toString() {
        return String.format("N%.6fE%.6f",lat, lon);
    }

    public Coordinates(double lat, double lon) {
        this.lat = lat;
        this.lon = lon;
    }

    public Coordinates() {
    }
}

