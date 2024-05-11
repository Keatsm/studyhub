package backend.studyhub.entities.items;

import backend.studyhub.entities.Entity;

public abstract class Item extends Entity {
    public Item(String name) {
        super(name);
    }

    public Item(long id, String name) {
        super(id, name);
    }

    public abstract String getType();
}
