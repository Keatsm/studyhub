package backend.studyhub.entities;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import backend.studyhub.entities.items.Folder;
import backend.studyhub.entities.items.Item;

public class Workspace extends Entity {

    private Set<User> users = new HashSet<User>();
    private Set<User> admins = new HashSet<User>();
    private Folder items = new Folder(getName());

    private String description;

    public Workspace(String name, String description, User creator) {
        super(name);
        addAdmin(creator);
        addUser(creator);
        this.description = description;
    }

    public Workspace(long id, String name, String description, User creator) {
        super(id, name);
        addAdmin(creator);
        addUser(creator);
        this.description = description;
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

    public long getRootFolderId() {
        return items.getId();
    }

    public List<Item> getItems() {
        return items.getItems();
    }

    public void addItem(Item item) {
        items.addItem(item);
    }

    public void removeItem(Item item) {
        items.removeItem(item);
    }

    public Item findItem(long id) {
        return items.findItem(id);
    }

    public Folder findFolder(long id) {
        return items.findFolder(id);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
