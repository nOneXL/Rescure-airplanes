package iaf.ofek.hadracha.base_course.web_server.Launches;

import iaf.ofek.hadracha.base_course.web_server.Data.Entity;
import iaf.ofek.hadracha.base_course.web_server.Data.Coordinates;

public class Launch implements Entity<Launch> {

    private int id;
    public Coordinates launchPoint;
    public Coordinates impactPoint;
    public String name;

    public Launch(Coordinates launchPoint, Coordinates impactPoint, String name) {
        this.launchPoint = launchPoint;
        this.impactPoint = impactPoint;
        this.name = name;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public Launch clone() {
        try {
            return (Launch) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError("Not possible - Entity implements Cloneable");
        }
    }
}
