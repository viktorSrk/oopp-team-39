package server.api;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.database.ListRepository;

@RestController
@RequestMapping("/api/lists")
public class ListController {

    private final ListRepository repo;

    public ListController(ListRepository repo) {
        this.repo = repo;
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

    @PostMapping({"", "/"})
    public ResponseEntity<commons.List> addList(@RequestBody commons.List list) {
        if (list == null || isNullOrEmpty(list.getTitle()))
            return ResponseEntity.badRequest().build();

        commons.List saved = repo.save(list);
        return ResponseEntity.ok(saved);
    }

    @DeleteMapping({"", "/"})
    public ResponseEntity<commons.List> removeList(@RequestBody commons.List list){
        if (list == null || isNullOrEmpty(list.getTitle()) || !repo.existsById(list.getId()))
            return ResponseEntity.badRequest().build();

        repo.delete(list);
        return ResponseEntity.ok(list);
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
