// SKELETON CLASS

package server.api;

import java.util.List;

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
}
