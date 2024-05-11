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
        users.add(creator);
        admins.add(creator);
    }

    public Workspace(long id, String name, User creator) {
        super(id, name);
        users.add(creator);
        admins.add(creator);
    }

    public Set<User> getUsers() {
        return users;
    }

    public Set<User> getAdmins() {
        return admins;
    }

    public void addUser(User user) {
        users.add(user);
    }

    public void addAdmin(User user) {
        admins.add(user);
    }

    public void removeUser(User user) {
        users.remove(user);
    }

    public void removeAdmin(User user) {
        admins.remove(user);
    }
}
