package backend.studyhub.entities;

public class User extends Entity {
    private String email;
    private String password;

    public User(String name, String email, String password) {
        super(name);
        this.email = email;
        this.password = password;
    }

    // Constructor for persistent data
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

    
}
