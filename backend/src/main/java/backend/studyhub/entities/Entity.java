package backend.studyhub.entities;

import backend.studyhub.data.DataAccess;

public abstract class Entity {
    private long id;
    private String name;

    public Entity(String name) {
        this.id = DataAccess.getInstance().getId();
        this.name = name;
    }

    public Entity(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
}
