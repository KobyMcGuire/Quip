package com.techelevator.model;

public class DeckDto {


    private String title;
    private String description;
    private String color;

    public DeckDto(String title,String description, String color){
        this.title = title;
        this.description = description;
        this.color = color;
    }

    public DeckDto() {
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
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
