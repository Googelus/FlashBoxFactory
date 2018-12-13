package de.richard.alex.flashbox;

import java.util.LinkedList;
import java.util.List;

public class CardStack {

    List<Card> cards;
    String author;
    String name;


    public CardStack(String author, String name) {
        this.author = author;
        this.name = name;
        this.cards = new LinkedList<Card>();

    }

    public void addCard(Card card) {
        cards.add(card);
    }

    public Card getCard(int number) {
        if (number > cards.size()-1)
            return null;
        return cards.get(number);
    }

    public int getSize() {
        return cards.size();
    }

    public String getAuthor() {
        return author;
    }

    public String getName() {
        return name;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setName(String name) {
        this.name = name;
    }
}
