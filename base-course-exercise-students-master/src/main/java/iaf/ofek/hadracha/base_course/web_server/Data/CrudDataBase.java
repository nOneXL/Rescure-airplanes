package iaf.ofek.hadracha.base_course.web_server.Data;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * A simple CRUD Data Base
 */
public interface CrudDataBase {
    /**
     * Gets an entity according to a given ID, casted to type T
     * @param id the ID to query
     * @param <T> the type to cast the entity
     * @return an entity of the desired ID or null if not present
     * @throws ClassCastException in case that the entity is not of type T
     */
    @Nullable
    <T extends Entity> T getByID(int id, Class<T> type) throws ClassCastException;

    /**
     * Returns a list of all the entities of type T (also given as parameter)
     * @param type the type
     * @param <T> the type
     * @return all entities of the type. If none, an empty list will be returned
     */
    @NotNull
    <T extends Entity> List<T> getAllOfType(Class<T> type);

    /**
     * The entity will be saved to the data base, and a <b>new ID</b> will be assigned to it.<br/>
     * Warning: This method calls setID() on the entity.
     * @param entity the entity to save
     * @param <T> the type of the entity
     * @return the new ID of this entity.
     */
    <T extends Entity> int create(T entity) throws IllegalArgumentException;

    /**
     * The entity will be saved to the data base, as an update to the entity with the same ID
     * @param entity the entity to save
     * @param <T> the type of the entity
     * @throws IllegalArgumentException if an entity with this ID doesn't exist.
     */
    <T extends Entity> void update(T entity) throws IllegalArgumentException;

    /**
     * Deletes an entity with the given ID from the DB
     * @param id the ID of the entity to delete
     * @throws IllegalArgumentException if an entity with this ID does not exist
     */
    <T extends Entity>  void delete(int id, Class<T> type) throws IllegalArgumentException;
}
