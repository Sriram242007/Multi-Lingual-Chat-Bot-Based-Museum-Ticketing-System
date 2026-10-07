package museum.generic;

import java.io.IOException;
import java.util.List;

/**
 * Repository.java
 * Generic repository interface for type-safe data access
 *
 * Demonstrates:
 * - Generic interface with type parameter <T>
 * - Generic methods
 * - CRUD operations contract
 *
 * @author Sriram S
 * @version 1.0
 * @param <T> the entity type
 */
public interface Repository<T> {

    /**
     * Save an entity
     * @param entity the entity to save
     * @return the saved entity
     */
    T save(T entity);

    /**
     * Find entity by ID
     * @param id the entity ID
     * @return the entity if found, null otherwise
     */
    T findById(String id);

    /**
     * Get all entities
     * @return list of all entities
     */
    List<T> findAll();

    /**
     * Update an entity
     * @param entity the entity to update
     * @return true if update successful
     */
    boolean update(T entity);

    /**
     * Delete an entity by ID
     * @param id the entity ID
     * @return true if deletion successful
     */
    boolean delete(String id) throws IOException;

    /**
     * Check if entity exists
     * @param id the entity ID
     * @return true if entity exists
     */
    boolean exists(String id);

    /**
     * Get total count of entities
     * @return number of entities
     */
    int count();
}