package com.example.application.data.workspaces;

import java.util.Set;

import com.example.application.data.AbstractEntity;
import com.example.application.data.User;

import jakarta.persistence.Entity;

@Entity
public class Workspace extends AbstractEntity {
    private String name;
    private String description;
    private Set<User> admins;
    private Set<User> members;

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Set<User> getAdmins() {
        return admins;
    }

    public void addAdmin(User user) {
        admins.add(user);
    }

    public void removeAdmin(User user) {
        admins.remove(user);
    }

    public Set<User> getMembers() {
        return members;
    }

    public void addMember(User user) {
        members.add(user);
    }

    public void removeMember(User user) {
        members.remove(user);
    }
    
}
