package backend.studyhub.entities;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class User extends Entity {
    private String email;
    private String password;
    private Set<Workspace> workspaces = new HashSet<Workspace>();
    private Set<Workspace> admin_workspaces = new HashSet<Workspace>();


    public User(String name, String email, String password) {
        super(name);
        this.email = email;
        this.password = password;
    }

    public User(long id, String name, String email, String password) {
        super(id, name);
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void addWorkspace(Workspace workspace) {
        workspaces.add(workspace);
    }

    public Set<Long> getWorkspaces() {
        return workspaces.stream().map(Workspace::getId).collect(Collectors.toSet());
    }

    public void addAdminWorkspace(Workspace workspace) {
        admin_workspaces.add(workspace);
    }

    public Set<Long> getAdminWorkspaces() {
        return admin_workspaces.stream().map(Workspace::getId).collect(Collectors.toSet());
    }

    public void removeWorkspace(Workspace workspace) {
        workspaces.remove(workspace);
    }

    public void removeAdminWorkspace(Workspace workspace) {
        admin_workspaces.remove(workspace);
    }
}
