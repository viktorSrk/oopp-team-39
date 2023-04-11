package server.api;

import commons.Board;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;
import server.database.BoardRepository;

import java.util.List;

@RestController
@RequestMapping("/api/boards")
public class BoardController {
    private final BoardRepository repo;

    public BoardController(BoardRepository repo) {
        this.repo = repo;
    }

    @GetMapping(path = { "", "/" })
    public List<Board> getAll() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Board> getById(@PathVariable("id") long id) {
        if (id < 0 || !repo.existsById(id)) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(repo.findById(id).get());
    }

    @MessageMapping({"/boards/add"})

    public Board addMessage(Board board) {
        return add(board).getBody();
    }
    @PostMapping(path = { "", "/" })
    @SendTo("/topic/boards/update")
    public ResponseEntity<Board> add(@RequestBody Board board) {
        Board saved = repo.save(board);
        return ResponseEntity.ok(saved);
    }

    @MessageMapping("/boards/delete")
    @SendTo("/topic/boards/delete")
    public Board deleteMessage(Board board) {
        delete(board);
        return board;
    }

    @DeleteMapping(path = { "", "/" })
    public ResponseEntity<Board> delete(@RequestBody Board board) {
        repo.delete(board);
        return ResponseEntity.ok(board);
    }
}
