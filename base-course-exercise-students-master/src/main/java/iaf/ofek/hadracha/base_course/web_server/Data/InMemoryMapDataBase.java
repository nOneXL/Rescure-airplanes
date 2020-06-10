package iaf.ofek.hadracha.base_course.web_server.Data;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class InMemoryMapDataBase implements CrudDataBase {

    private Map<Class,Map<Integer, Entity>> data = new HashMap<>();
    private int id=1;

    @Override
    public <T extends Entity> @Nullable T getByID(int id, Class<T> type) throws ClassCastException {
        Map<Integer, T> typeData = (Map<Integer, T>) data.get(type);
        T entity = typeData!=null?(T) typeData.get(id):null;
        return entity!=null? (T) entity.clone() : null;
    }

    @Override
    public @NotNull <T extends Entity> List<T> getAllOfType(Class<T> type) {
        if (!data.containsKey(type)) return new ArrayList<>();
        return data.get(type).values().stream().map(o -> (T)o.clone()).collect(Collectors.toList());
    }

    @Override
    public <T extends Entity> int create(T entity) throws IllegalArgumentException {
        if (entity.getId()<=0) {
            int newId = id++;
            entity.setId(newId);
        }

        Class<? extends Entity> type = entity.getClass();

        if(!data.containsKey(type)){
            data.put(type,new HashMap<>());
        }

        if(data.get(type).containsKey(entity.getId()))
            throw new IllegalArgumentException("Entity of type " + type + " and ID " + entity.getId() + " already exists");

        data.get(type).put(entity.getId(), entity.clone());
        return entity.getId();
    }

    @Override
    public <T extends Entity> void update(T entity) throws IllegalArgumentException {
        Class<? extends Entity> type = entity.getClass();

        if (!data.containsKey(type) || !data.get(type).containsKey(entity.getId()))
            throw new IllegalArgumentException("No entity of this type with this ID exists. Desired entity: " +
                    type + ":" + entity.getId());

        data.get(type).put(entity.getId(), entity.clone());
    }

    @Override
    public <T extends Entity>  void delete(int id, Class<T> type) throws IllegalArgumentException {

        if (!data.containsKey(type) || !data.get(type).containsKey(id))
            throw new IllegalArgumentException("No entity of this type with this ID exists. Desired entity: " +
                    type + ":" + id);

        data.get(type).remove(id);
    }
}
