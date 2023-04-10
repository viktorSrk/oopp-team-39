package server.api;

import commons.Board;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;
import server.database.BoardRepository;
import server.database.ListRepository;

import java.util.List;

@RestController
@RequestMapping("/api/lists")
public class ListController {
    private final ListRepository repo;

    @Autowired
    BoardRepository boardRepo;

    public ListController(ListRepository repo, BoardRepository boardRepo) {
        this.repo = repo;
        this.boardRepo = boardRepo;
    }

    @GetMapping({"", "/"})
    public List<commons.List> getAll() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<commons.List> getListById(@PathVariable("id") long id) {
        if (id < 0 || !repo.existsById(id))
            return ResponseEntity.badRequest().build();
        commons.List res = repo.findById(id).isPresent() ? repo.findById(id).get() : null;
        return ResponseEntity.ok(res);
    }

    @MessageMapping("/list/add/{boardId}")
    @SendTo("/topic/list/update")
    public commons.List addMessage(commons.List list, @DestinationVariable long boardId) {
        return addList(list, boardId).getBody();
    }
    @PostMapping({"add/{boardId}"})
    public ResponseEntity<commons.List> addList(
            @RequestBody commons.List list,
            @PathVariable long boardId
    ) {
        if (list == null || isNullOrEmpty(list.getTitle()))
            return ResponseEntity.badRequest().build();

        Board assoc = boardRepo.getById(boardId);
        commons.List saved = repo.save(list);
        saved.setBoard(assoc);
        saved = repo.save(saved);
        return ResponseEntity.ok(saved);
    }

    @MessageMapping("/list/delete")
    @SendTo("/topic/list/update")
    public commons.List removeMessage(commons.List list) {
        return removeList(list).getBody();
    }
    @DeleteMapping({"", "/"})
    public ResponseEntity<commons.List> removeList(@RequestBody commons.List list){
        if (list == null || isNullOrEmpty(list.getTitle()) || !repo.existsById(list.getId()))
            return ResponseEntity.badRequest().build();

        repo.delete(list);
        return ResponseEntity.ok(list);
    }

    @MessageMapping("/list/replace")
    @SendTo("/topic/list/replace")
    public commons.List replaceMessage(commons.List list) {
        return replaceList(list, list.getId()).getBody();
    }



    @PutMapping("/{id}")
    public ResponseEntity<commons.List> replaceList(@RequestBody commons.List list,
                                                    @PathVariable("id") long id){
        if (list == null || isNullOrEmpty(list.getTitle()) || !repo.existsById(id))
            return ResponseEntity.badRequest().build();

        commons.List listToChange = repo.findById(id).isPresent() ? repo.findById(id).get() : null;

        listToChange.setTitle(list.getTitle());
        listToChange.setCards(list.getCards());
        repo.save(listToChange);

        return ResponseEntity.ok(listToChange);
    }

    private static boolean isNullOrEmpty(String s) {
        return s == null || s.isEmpty();
    }
}
