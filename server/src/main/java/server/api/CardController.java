package server.api;


import commons.Card;
import commons.MoveCardMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;
import server.database.CardRepository;
import server.database.ListRepository;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;


@RestController
@RequestMapping("/api/cards")
public class CardController {

    private final CardRepository repo;

    @Autowired
    ListRepository listRepo;

    private final Map<Object, Consumer<Card>> listeners = new HashMap();


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
        int indexTarget = message.getIndex();
        Card card = message.getCard();

        commons.List listSource = listRepo.getById(listIdSource);
        listSource.reOrder();

        if (listIdSource == listIdTarget && indexTarget == card.getPosition()) {
            return ResponseEntity.ok(listRepo.save(listRepo.getById(listIdSource)));
        }
        if (listIdSource == listIdTarget && indexTarget != card.getPosition()) {
            listSource.reOrder();
            listSource.move(card.getId(), indexTarget);
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
        listTarget.insert(indexTarget);
        listTarget.reOrder();

        commons.List savedTarget = listRepo.save(listTarget);
        ResponseEntity.ok(savedTarget);

        return ResponseEntity.ok(savedSource);
    }

    @MessageMapping("/cards/add/{listId}")
    @SendTo("/topic/list/update")
    public Card addMessage(Card card, @DestinationVariable long listId) {
        return addCard(card, listId).getBody();
    }

    @PostMapping("add/{listId}")
    public ResponseEntity<Card> addCard(
            @RequestBody Card card,
            @PathVariable long listId
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

    @PutMapping("/{id}")
    public ResponseEntity<Card> replaceCard(@RequestBody Card card){
        if (card == null || isNullOrEmpty(card.getTitle()) || !repo.existsById(card.getId()))
            return ResponseEntity.badRequest().build();

        long id = card.getId();
        commons.Card cardToChange = repo.findById(id).isPresent() ? repo.findById(id).get() : null;

        cardToChange.setTitle(card.getTitle());
        cardToChange.setDescription(card.getDescription());
        cardToChange.setTasks(card.getTasks());

        repo.save(cardToChange);

        System.out.println("Number of listeners " + listeners.size());

        listeners.forEach((k,l) -> l.accept(cardToChange));

        return ResponseEntity.ok(card);
    }


    private static boolean isNullOrEmpty(String s) {
        return s == null || s.isEmpty();
    }

    @GetMapping("/updates")
    public DeferredResult<ResponseEntity<Card>> cardUpdates(){
        var noContent = new ResponseEntity(HttpStatus.NO_CONTENT);
        var result = new DeferredResult<ResponseEntity<Card>>(5000L, noContent);

        var key = new Object();
        listeners.put(key, c -> {
            result.setResult(ResponseEntity.ok(c));
        });
        result.onCompletion(() -> listeners.remove(key));

        return result;
    }
}
