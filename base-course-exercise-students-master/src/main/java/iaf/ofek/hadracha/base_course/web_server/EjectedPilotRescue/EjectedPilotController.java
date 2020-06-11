package iaf.ofek.hadracha.base_course.web_server.EjectedPilotRescue;

import iaf.ofek.hadracha.base_course.web_server.Data.CrudDataBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ejectedPilotRescue")
public class EjectedPilotController {
    private final CrudDataBase dataBase;

    @Autowired
    private AirplanesAllocationManager airplanesAllocationManager;

    public EjectedPilotController(CrudDataBase dataBase) {
        this.dataBase = dataBase;
    }

    @GetMapping("/infos")
    public List<EjectedPilotInfo> getEjectedPilots() {
        return this.dataBase.getAllOfType(EjectedPilotInfo.class);
    }

    @GetMapping("/takeResponsibility")
    public void sendRescuePilotsToEjectedPilot(@RequestParam("ejectionId")Integer ejectionId,
                                               @CookieValue(value = "client-id", defaultValue = "") String clientId) {
        EjectedPilotInfo ejectedPilot = this.dataBase.getByID(ejectionId, EjectedPilotInfo.class);

        if (ejectedPilot.getRescuedBy() == null) {
            ejectedPilot.setRescuedBy(clientId);
            dataBase.update(ejectedPilot);
            airplanesAllocationManager.allocateAirplanesForEjection(ejectedPilot, clientId);
        }
    }
}
