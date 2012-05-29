package com.bb.reference;

import com.bb.domain.Card;
import com.bb.domain.ProductCommit;
import com.bb.domain.ProductStake;

import java.util.List;

public class MobileRegisterList {
    List<ProductCommit> commits;
    List<ProductStake> stakes;
    List<Card> cards;

    public List<ProductCommit> getCommits() {
        return commits;
    }

    public void setCommits(List<ProductCommit> commits) {
        this.commits = commits;
    }

    public List<ProductStake> getStakes() {
        return stakes;
    }

    public void setStakes(List<ProductStake> stakes) {
        this.stakes = stakes;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }
}
