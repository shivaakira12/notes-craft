package com.notescraft.entity;

import jakarta.persistence.*;

import java.util.Date;


@Entity
@Table(name = "Category")
public class Category extends BaseModel{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String description;

    // No-args constructor
    public Category() {}

    // All-args constructor
    public Category(Integer id, String name, String description, Boolean isActive, Boolean isDeleted, Date createdBy, Integer updatedBy, Date updatedDate) {
        super(isActive, isDeleted, createdBy, updatedBy, updatedDate);
        this.id = id;
        this.name = name;
        this.description = description;
    }

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
