package iaf.ofek.hadracha.base_course.web_server.EjectedPilotRescue;

import iaf.ofek.hadracha.base_course.web_server.Data.CrudDataBase;
import iaf.ofek.hadracha.base_course.web_server.Data.Entity;
import iaf.ofek.hadracha.base_course.web_server.Utilities.ListOperations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
public class EjectionsImporter {

    @Value("${ejections.server.url}")
    public String EJECTION_SERVER_URL;
    @Value("${ejections.namespace}")
    public String NAMESPACE;


    private ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
    private final RestTemplate restTemplate;
    private final CrudDataBase dataBase;
    private final ListOperations listOperations;
    private static final Double SHIFT_NORTH = 1.7;

    public EjectionsImporter(RestTemplateBuilder restTemplateBuilder, CrudDataBase dataBase, ListOperations listOperations) {
        restTemplate = restTemplateBuilder.build();
        this.dataBase = dataBase;
        this.listOperations = listOperations;
        executor.scheduleAtFixedRate(this::updateEjections, 1, 1, TimeUnit.SECONDS);

    }

    private void updateEjections() {
        try {
            List<EjectedPilotInfo> updatedEjections = getUpdatedEjectionsFromServer();

            List<EjectedPilotInfo> previousEjections = getPreviousEjections();

            addNewEjections(updatedEjections, previousEjections);

            removeOldEjections(updatedEjections, previousEjections);

        } catch (RestClientException e) {
            System.err.println("Could not get ejections: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private List<EjectedPilotInfo> getUpdatedEjectionsFromServer() {
        List<EjectedPilotInfo> updatedEjections;
        //     List<EjectedPilotInfo> ejectionsFromServer;
        ResponseEntity<List<EjectedPilotInfo>> responseEntity = restTemplate.exchange(
                EJECTION_SERVER_URL + "/ejections?name=" + NAMESPACE, HttpMethod.GET,
                null, new ParameterizedTypeReference<List<EjectedPilotInfo>>() {
                });
        updatedEjections = responseEntity.getBody();
        if (updatedEjections != null) {
            for (EjectedPilotInfo ejectedPilotInfo : updatedEjections) {
                ejectedPilotInfo.getCoordinates().lat += SHIFT_NORTH;
            }
        }
        return updatedEjections;
    }

    private List<EjectedPilotInfo> getPreviousEjections() {
        List<EjectedPilotInfo> previousEjections = dataBase.getAllOfType(EjectedPilotInfo.class);
        return previousEjections;
    }

    private void addNewEjections(List<EjectedPilotInfo> updatedEjections, List<EjectedPilotInfo> previousEjections) {
        List<EjectedPilotInfo> addedEjections = ejectionsListDifference(updatedEjections, previousEjections);
        addedEjections.forEach(dataBase::create);
    }

    private void removeOldEjections(List<EjectedPilotInfo> updatedEjections, List<EjectedPilotInfo> previousEjections) {
        List<EjectedPilotInfo> removedEjections = ejectionsListDifference(previousEjections, updatedEjections);
        removedEjections.stream().map(EjectedPilotInfo::getId).forEach(id -> dataBase.delete(id, EjectedPilotInfo.class));
    }

    private List<EjectedPilotInfo> ejectionsListDifference(List<EjectedPilotInfo> mainEjections, List<EjectedPilotInfo> toRemoveEjections) {
        return listOperations.subtract(mainEjections, toRemoveEjections, new Entity.ByIdEqualizer<>());
    }
}
