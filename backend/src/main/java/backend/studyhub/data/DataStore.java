package backend.studyhub.data;

import backend.studyhub.entities.User;
import backend.studyhub.entities.Workspace;

/**
 * DataStore
 */
public interface DataStore {

    public long getId();

    public User createUser(String name, String email, String password);

    public User getUser(long id);

    public User getUser(String email);

    public void updateUser(User user);

    public void deleteUser(long id);

    public Workspace createWorkspace(String name, String description, User owner);

    public Workspace getWorkspace(long id);

    public void updateWorkspace(Workspace workspace);

    public void deleteWorkspace(long id);
}
