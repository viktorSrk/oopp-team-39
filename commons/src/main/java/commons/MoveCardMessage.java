package commons;

public class MoveCardMessage {
    private long listIdSource;
    private long listIdTarget;
    private int index;
    private commons.Card card;

    public MoveCardMessage() {
        //Object mapper
    }

    public MoveCardMessage(long listIdSource, long listIdTarget, int index, Card card) {
        this.card = card;
        this.listIdSource = listIdSource;
        this.listIdTarget = listIdTarget;
        this.index = index;
    }

    public long getListIdSource() {
        return listIdSource;
    }


    public long getListIdTarget() {
        return listIdTarget;
    }


    public int getIndex() {
        return index;
    }


    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }
}

