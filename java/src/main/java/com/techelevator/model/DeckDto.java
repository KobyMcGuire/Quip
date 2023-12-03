package com.techelevator.model;

public class DeckDto {


    private String title;
    private String description;

    public DeckDto(String title,String description){
        this.title = title;
        this.description = description;
    }

    public DeckDto() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
