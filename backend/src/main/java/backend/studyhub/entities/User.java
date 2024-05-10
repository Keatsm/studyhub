package backend.studyhub.entities;

import backend.studyhub.data.DataAccess;

public class User {
    private long id;
    private String name;
    private String email;
    private String password;

    public User(String name, String email, String password) {
        this.id = DataAccess.getInstance().getId();
        this.name = name;
        this.email = email;
        this.password = password;
    }

    // Constructor for persistent data
    public User(long id, String name, String email, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    
}
