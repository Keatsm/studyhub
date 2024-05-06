package com.example.application.data.workspaces;

import com.example.application.data.AbstractEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Lob;

public abstract class Item extends AbstractEntity {
    private String name;
    @Lob
    @Column(length = 1000000)
    private byte[] icon;

    public String getName() {
        return name;
    }
}
