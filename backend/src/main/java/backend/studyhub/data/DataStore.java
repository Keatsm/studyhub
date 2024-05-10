package backend.studyhub.data;

import backend.studyhub.entities.User;

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
}
