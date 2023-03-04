package server.api;

import com.fasterxml.jackson.databind.node.ObjectNode;
import commons.Card;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.database.CardRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/cards")
public class CardController {

    private final CardRepository repo;

    public CardController(CardRepository repo) {
        this.repo = repo;
    }

    @GetMapping("/")
    public List<Card> getAllCards() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Card> getCardById(@PathVariable("id") long id) {
        if (id < 0 || !repo.existsById(id)) return ResponseEntity.badRequest().build();
        Card res = repo.findById(id).get();
        return ResponseEntity.ok(res);
    }
    @PostMapping("/")
    public ResponseEntity<Card> addCard(@RequestBody Card card) {
        if (card == null || isNullOrEmpty(card.title)) return ResponseEntity.badRequest().build();

        Card saved = repo.save(card);
        return ResponseEntity.ok(saved);
    }
    @DeleteMapping("/")
    public ResponseEntity<Card> removeCard(@RequestBody Card card){
        if (card == null || isNullOrEmpty(card.title) || !repo.existsById(card.getId())) return ResponseEntity.badRequest().build();

        repo.delete(card);
        return ResponseEntity.ok(card);

    }

    @PutMapping("/{id}")
    public ResponseEntity<Card> replaceCard(@RequestBody Card card, @PathVariable("id") long id){
        if (card == null || isNullOrEmpty(card.title) || !repo.existsById(id)) return ResponseEntity.badRequest().build();

        Card toDelete = repo.findById(id).get();

        repo.delete(toDelete);

        repo.save(card);
        return ResponseEntity.ok(card);
    }


    private static boolean isNullOrEmpty(String s) {
        return s == null || s.isEmpty();
    }
}
