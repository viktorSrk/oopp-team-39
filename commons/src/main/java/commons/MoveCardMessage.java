package commons;

public class MoveCardMessage {
    private long listIdSource;
    private long listIdTarget;
    private int index;
    private commons.Card card;

    public MoveCardMessage() {

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

    public void setListIdSource(long listIdSource) {
        this.listIdSource = listIdSource;
    }

    public long getListIdTarget() {
        return listIdTarget;
    }

    public void setListIdTarget(long listIdTarget) {
        this.listIdTarget = listIdTarget;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }
}

//    @MessageMapping("/cards/move")
//    @SendTo("/topic/list/update")
//    public ResponseEntity<commons.List> moveCards(@Payload MoveCardMessage message) {
//        long listIdSource = message.getListIdSource();
//        long listIdTarget = message.getListIdTarget();
//        int index = message.getIndex();
//        Card card = message.getCard();
//
//        // implementation
//        // ...
//    }
