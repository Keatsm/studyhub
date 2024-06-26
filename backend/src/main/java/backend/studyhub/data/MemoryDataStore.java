package backend.studyhub.data;

import java.util.HashMap;

import backend.studyhub.entities.User;
import backend.studyhub.entities.Workspace;

public class MemoryDataStore implements DataStore {
    private static HashMap<Long, User> idToUsers = new HashMap<>();
    private static HashMap<String, User> emailToUsers = new HashMap<>();

    private long id = 0;

    @Override
    public long getId() {
        long oldId = this.id;
        this.id++;
        return oldId;
    }

    @Override
    public User createUser(String name, String email, String password) {
        User user = new User(name, email, password);
        idToUsers.put(user.getId(), user);
        emailToUsers.put(user.getEmail(), user);
        return user;
    }

    @Override
    public void deleteUser(long id) {
        String email = idToUsers.get(id).getEmail();
        idToUsers.remove(id);
        emailToUsers.remove(email);
    }

    @Override
    public User getUser(long id) {
        return idToUsers.get(id);
    }

    @Override
    public User getUser(String email) {
        return emailToUsers.get(email);
    }

    @Override
    public void updateUser(User user) {
        // In memory so we don't need to do anything      
    }

    private static HashMap<Long, Workspace> idToWorkspaces = new HashMap<>();

    @Override
    public Workspace createWorkspace(String name, String description, User owner) {
        Workspace workspace = new Workspace(name, description, owner);
        idToWorkspaces.put(workspace.getId(), workspace);
        return workspace;
    }

    @Override
    public Workspace getWorkspace(long id) {
        return idToWorkspaces.get(id);
    }

    @Override
    public void updateWorkspace(Workspace workspace) {
        // In memory so we don't need to do anything
    }

    @Override
    public void deleteWorkspace(long id) {
        idToWorkspaces.remove(id);
    }

    @Override
    public boolean emailExists(String email) {
        return emailToUsers.containsKey(email);
    }

}
