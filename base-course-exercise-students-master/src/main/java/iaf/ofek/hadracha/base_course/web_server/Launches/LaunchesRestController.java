package iaf.ofek.hadracha.base_course.web_server.Launches;

import iaf.ofek.hadracha.base_course.web_server.Data.CrudDataBase;
import iaf.ofek.hadracha.base_course.web_server.Data.Coordinates;
import iaf.ofek.hadracha.base_course.web_server.Exceptions.UserArgumentException;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController()
@RequestMapping("launches")
public class LaunchesRestController {
    private CrudDataBase dataBase;

    public LaunchesRestController(@Autowired CrudDataBase dataBase) {
        this.dataBase = dataBase;
    }

    @GetMapping
    public @NotNull List<Launch> getLaunches(){
        return dataBase.getAllOfType(Launch.class);
    }

    @GetMapping("/createLaunch")
    public int createLaunch(
            @RequestParam("launch_lat") float launchLat,
            @RequestParam("launch_lon") float launchLon,
            @RequestParam("impact_lat") float impactLat,
            @RequestParam("impact_lon") float impactLon,
            @RequestParam("name") String name) throws UserArgumentException {

        return dataBase.create(
                new Launch(
                        new Coordinates(launchLat, launchLon), new Coordinates(impactLat, impactLon), name
                )
        );
    }

    @GetMapping("/updateLaunch")
    public void updateLaunch(
            @RequestParam("id") int id,
            @RequestParam("launch_lat") Optional<Float> launchLat,
            @RequestParam("launch_lon") Optional<Float> launchLon,
            @RequestParam("impact_lat") Optional<Float> impactLat,
            @RequestParam("impact_lon") Optional<Float> impactLon) throws UserArgumentException {

        if (launchLat.isPresent() ^ launchLon.isPresent()) {
            throw new UserArgumentException("Launch point sent with only LAT or LON");
        }
        if (impactLat.isPresent() ^ impactLon.isPresent()) {
            throw new UserArgumentException("Impact point sent with only LAT or LON");
        }

        Launch launch = dataBase.getByID(id, Launch.class);
        if (launch==null)
            throw new UserArgumentException("No launch with this ID exists");

        launchLat.ifPresent(lat -> launch.launchPoint.lat = lat);
        launchLon.ifPresent(lon -> launch.launchPoint.lon = lon);
        impactLat.ifPresent(lat -> launch.impactPoint.lat = lat);
        impactLon.ifPresent(lon -> launch.impactPoint.lon = lon);

        dataBase.update(launch);
    }
}
