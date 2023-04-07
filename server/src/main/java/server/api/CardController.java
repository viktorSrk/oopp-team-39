package server.api;


import commons.Card;
import commons.MoveCardMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;
import server.database.CardRepository;
import server.database.ListRepository;

import javax.transaction.Transactional;
import java.util.List;


@RestController
@RequestMapping("/api/cards")
public class CardController {

    private final CardRepository repo;
    @Autowired
    ListRepository listRepo;

    public CardController(CardRepository repo, ListRepository listRepo) {
        this.repo = repo;
        this.listRepo = listRepo;
    }

    @GetMapping({"", "/"})
    public List<Card> getAllCards() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Card> getCardById(@PathVariable("id") long id) {
        if (id < 0 || !repo.existsById(id)) return ResponseEntity.badRequest().build();
        Card res = repo.findById(id).get();
        return ResponseEntity.ok(res);
    }

    @Transactional
    @MessageMapping("/cards/move")
    @SendTo("/topic/list/update")
    public ResponseEntity<commons.List> moveCards(@Payload MoveCardMessage message) {
        long listIdSource = message.getListIdSource();
        long listIdTarget = message.getListIdTarget();
        int index = message.getIndex();
        Card card = message.getCard();

        commons.List listSource = listRepo.getById(listIdSource);
        listSource.reOrder();

        if (listIdSource == listIdTarget && index == card.getPosition()) {
            return ResponseEntity.ok(listRepo.save(listRepo.getById(listIdSource)));
        }

        if (listIdSource == listIdTarget && index != card.getPosition()) {
            listSource.reOrder();
            listSource.move(card.getId(), index);
            listSource.reOrder();
            return ResponseEntity.ok(listRepo.save(listSource));
        }

        commons.List listTarget = listRepo.getById(listIdTarget);

        listSource.reOrder();

        listSource.removeCard(card);
        listSource.reOrder();
        commons.List savedSource = listRepo.save(listSource);

        listTarget.reOrder();

        Card savedCard = repo.save(card);
        savedCard.setList(listTarget);
        savedCard = repo.save(savedCard);
        listTarget.addCard(savedCard);
        listTarget.insert(index);
        listTarget.reOrder();

        commons.List savedTarget = listRepo.save(listTarget);
        ResponseEntity.ok(savedTarget);

        return ResponseEntity.ok(savedSource);
    }


//    @MessageMapping("/cards/reorder")
//    @SendTo("/topic/list/update")
//    public commons.List reOrder(@Payload  commons.List list) {
//        if (list.getCards().size() == 0) return listRepo.save(list);
//        for (Card c : list.getCards()) {
//            c.setPosition(list.getCards().indexOf(c));
//            repo.save(c);
//        }
//        return listRepo.save(list);
//    }

    @MessageMapping("/cards/add/{listId}")
    @SendTo("/topic/list/update")
    public Card addMessage(Card card, @DestinationVariable long listId) {
        return addCard(card, listId).getBody();
    }

    @PostMapping("add/{listId}")
    public ResponseEntity<Card> addCard(
            @RequestBody Card card,
            @PathVariable Long listId
    ) {
        if (card == null || isNullOrEmpty(card.getTitle()))
            return ResponseEntity.badRequest().build();

        commons.List assoc = listRepo.getById(listId);

        Card saved = repo.save(card);
        saved.setList(assoc);
        saved = repo.save(saved);
        return ResponseEntity.ok(saved);
    }

    @MessageMapping("/card/delete")
    @SendTo("/topic/list/update")
    public Card removeMessage(Card card) {
        return removeCard(card).getBody();
    }
    @DeleteMapping("/")
    public ResponseEntity<Card> removeCard(@RequestBody Card card){
        if (card == null || isNullOrEmpty(card.getTitle()) || !repo.existsById(card.getId()))
            return ResponseEntity.badRequest().build();

        repo.delete(card);
        return ResponseEntity.ok(card);

    }

    @PutMapping("/")
    public ResponseEntity<Card> replaceCard(@RequestBody Card card){
        long id = card.getId();
        if (card == null || isNullOrEmpty(card.getTitle()) || !repo.existsById(id))
            return ResponseEntity.badRequest().build();

        commons.Card cardToChange = repo.findById(id).isPresent() ? repo.findById(id).get() : null;

        cardToChange.setTitle(card.getTitle());

        repo.save(cardToChange);

        return ResponseEntity.ok(card);
    }


    private static boolean isNullOrEmpty(String s) {
        return s == null || s.isEmpty();
    }
}
