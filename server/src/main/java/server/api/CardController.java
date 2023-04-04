package server.api;


import commons.Card;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;
import server.database.CardRepository;
import server.database.ListRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;


@RestController
@RequestMapping("/api/cards")
public class CardController {

    private final CardRepository repo;

    private final Map<Object, Consumer<Card>> listeners = new HashMap();
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
    @MessageMapping("/cards/add/{listId}")
    @SendTo("/topic/list/update")
    public Long addMessage(@DestinationVariable long listId, Card card) {
        addCard(card, listId);
        return listId;
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
    public Long removeMessage(Card card) {
        removeCard(card);
        return -1L; //value shouldn't matter right now, because it just updates all lists anyway
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

        listeners.forEach((k,l) -> l.accept(cardToChange));

        return ResponseEntity.ok(card);
    }


    private static boolean isNullOrEmpty(String s) {
        return s == null || s.isEmpty();
    }

    @GetMapping("/updates")
    public DeferredResult<ResponseEntity<Card>> cardUpdates(){
        var noContent = new ResponseEntity(HttpStatus.NO_CONTENT);
        var res = new DeferredResult<ResponseEntity<Card>>(5000L, noContent);

        var key = new Object();
        listeners.put(key, c -> {
            res.setResult(ResponseEntity.ok(c));
        });
        res.onCompletion(() -> listeners.remove(key));

        return res;



    }
}
