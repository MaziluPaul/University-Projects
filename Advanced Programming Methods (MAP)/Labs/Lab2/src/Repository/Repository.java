package Repository;

import Domain.Entity;

import java.util.ArrayList;

public class Repository<T extends Entity> {
    private final ArrayList<T> entities = new ArrayList<T>();

    public void add(T entity) {
        entities.add(entity);
    }

    public void delete(T entity) {
        entities.remove(entity);
    }

    public void update(T entity) {
        for (int i = 0; i < entities.size(); i++) {
            if (entities.get(i).getId() == entity.getId())
                entities.set(i, entity);
        }
    }

    public T getById(int id) {
        for (T entity : entities) {
            if (entity.getId() == id) {
                return entity;
            }
        }
        return null;
    }

    public ArrayList<T> getAll() {
        return this.entities;
    }
}
