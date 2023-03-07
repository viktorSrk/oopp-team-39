package server.api;

import commons.Board;
import java.util.*;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.database.BoardRepository;

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

    //right now only for adding the main Board
    //adds an empty board to the repo
    @PostMapping(path = { "", "/" })
    public ResponseEntity<Board> add() {
        Board newBoard = new Board();
        Board saved = repo.save(newBoard);
        return ResponseEntity.ok(saved);
    }
}
