package chiroito.sample.quarkus.spring.todo;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.data.domain.Sort.Direction.ASC;

@RestController
@RequestMapping(value = "/api", consumes = MediaType.APPLICATION_JSON_VALUE)
public class TodoResource {

    private final TodoRepository todoRepository;

    public TodoResource(TodoRepository todoRepository){
        this.todoRepository = todoRepository;
    }

    @RequestMapping(method = {RequestMethod.OPTIONS})
    public void opt() {
    }

    @GetMapping
    public List<Todo> getAll() {
        return this.todoRepository.findAll(new Sort(ASC,"order"));
    }

    @GetMapping("/{id}")
    public Todo getOne(@PathVariable("id") Long id) {
        Optional<Todo> entity = this.todoRepository.findById(id);
        if (!entity.isPresent()) {
            throw new RuntimeException("Todo with id of " + id + " does not exist.");
        }
        return entity.get();
    }

    @PostMapping
    @Transactional
    public ResponseEntity create(@Valid Todo item) {
        this.todoRepository.save(item);
        return ResponseEntity.status(HttpStatus.CREATED).body(item);
    }

    @PatchMapping("/{id}")
    @Transactional
    public Todo update(@Valid Todo todo, @PathVariable(name = "id") Long id) {
        Optional<Todo> opt = this.todoRepository.findById(id);
        Todo entity = opt.get();
        entity.setId(id);
        entity.setCompleted(todo.isCompleted());
        entity.setOrder(todo.getOrder());
        entity.setTitle(todo.getTitle());
        entity.setUrl(todo.getUrl());

        this.todoRepository.save(entity);
        return entity;
    }

    @DeleteMapping
    @Transactional
    public void deleteCompleted() {
        List<Todo> deleteTargets = this.todoRepository.findAll().stream().filter(e->e.isCompleted()).collect(Collectors.toList());
        this.todoRepository.deleteAll(deleteTargets);
    }

    @Transactional
    @DeleteMapping("/{id}")
    public void deleteOne(@PathVariable("id") Long id) {
        Optional<Todo> entity = this.todoRepository.findById(id);
        if (!entity.isPresent()) {
            throw new RuntimeException("Todo with id of " + id + " does not exist.");
        }
        this.todoRepository.delete(entity.get());
    }
}