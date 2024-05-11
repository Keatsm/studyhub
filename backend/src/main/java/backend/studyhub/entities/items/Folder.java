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

    @Override
    public String getType() {
        return "folder";
    }
}
