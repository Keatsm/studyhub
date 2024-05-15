package backend.studyhub.entities.items;

import java.util.ArrayList;
import java.util.List;

public class Folder extends Item {
    List<Item> items = new ArrayList<Item>();

    public Folder(String name) {
        super(name);
    }

    public Folder(long id, String name) {
        super(id, name);
    }

    public List<Item> getItems() {
        return items;
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public void removeItem(Item item) {
        items.remove(item);
    }

    @Override
    public String getType() {
        return "folder";
    }

    @Override
    public Item findItem(long id) {
        if (super.findItem(id) != null) {
            return this;
        }
        for (Item item : items) {
            Item found = item.findItem(id);
            if (found != null) {
                return found;
            }
        }
        return null;
    }

    @Override
    public Folder findFolder(long id) {
        for (Item item : items) {
            if (item.getId() == id) {
                return this;
            }
            Folder found = item.findFolder(id);
            if (found != null) {
                return found;
            }
        }
        return null;
    }
    
}
