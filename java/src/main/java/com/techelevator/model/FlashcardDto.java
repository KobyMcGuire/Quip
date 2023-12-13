package com.techelevator.model;

public class FlashcardDto {

    private int deckId;
    private String question;
    private String answer;
    private String tag;
    private String image;
    private String creator;

    public FlashcardDto(int deckId, String question, String answer, String tag, String image, String creator) {
        this.deckId = deckId;
        this.question = question;
        this.answer = answer;
        this.tag = tag;
        this.image = image;
        this.creator = creator;
    }

    public FlashcardDto() {
    }

    public int getDeckId() {
        return deckId;
    }

    public void setDeckId(int deckId) {
        this.deckId = deckId;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }
}
