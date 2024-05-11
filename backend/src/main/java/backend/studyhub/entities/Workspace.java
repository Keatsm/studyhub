package backend.studyhub.entities;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import backend.studyhub.entities.items.Item;

public class Workspace extends Entity {

    Set<User> users = new HashSet<User>();
    Set<User> admins = new HashSet<User>();
    List<Item> items = new ArrayList<Item>();

    public Workspace(String name, User creator) {
        super(name);
        addAdmin(creator);
        addUser(creator);
    }

    public Workspace(long id, String name, User creator) {
        super(id, name);
        addAdmin(creator);
        addUser(creator);
    }

    public Set<User> getUsers() {
        return users;
    }

    public Set<User> getAdmins() {
        return admins;
    }

    public void addUser(User user) {
        users.add(user);
        user.addWorkspace(this);
    }

    public void addAdmin(User user) {
        admins.add(user);
    }

    public void removeUser(User user) {
        if (admins.contains(user)) {
            removeAdmin(user);
        }
        users.remove(user);
        user.removeWorkspace(this);
    }

    public void removeAdmin(User user) {
        admins.remove(user);
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

    public Item findItem(long id) {
        for (Item item : items) {
            Item found = item.findItem(id);
            if (found != null) {
                return found;
            }
        }
        return null;
    }
}
