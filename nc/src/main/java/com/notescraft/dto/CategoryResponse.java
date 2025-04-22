package com.notescraft.dto;

import java.util.Date;

public class CategoryResponse {

    private String name;
    private String description;
    private Boolean isActive;

    public CategoryResponse(){

    }
    public CategoryResponse(String name, String description, Boolean isActive) {
        this.name = name;
        this.description = description;
        this.isActive = isActive;
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

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }
}
