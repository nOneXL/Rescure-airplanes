package iaf.ofek.hadracha.base_course.web_server.EjectedPilotRescue;

import iaf.ofek.hadracha.base_course.web_server.Data.CrudDataBase;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/ejectedPilotRescue")
public class EjectedPilotController {
    private final CrudDataBase dataBase;

    public EjectedPilotController(CrudDataBase dataBase) {
        this.dataBase = dataBase;
    }

    @GetMapping("/infos")
    public List<EjectedPilotInfo> getEjectedPilot() {
        return this.dataBase.getAllOfType(EjectedPilotInfo.class);
    }
}
