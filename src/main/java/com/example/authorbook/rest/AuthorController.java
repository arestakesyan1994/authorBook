package com.example.authorbook.rest;

import com.example.authorbook.model.Author;
import com.example.authorbook.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/authors")
public class AuthorController {

    @Autowired
    private AuthorRepository authorRepository;

    @GetMapping()
    public ResponseEntity users() {
        return ResponseEntity.ok(authorRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity getUserById(@PathVariable(name = "id") int id) {
        Author one = authorRepository.findOne(id);
        if (one == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with " + id + " id no found");
        }
        return ResponseEntity.ok(one);
    }

    @PostMapping()
    public ResponseEntity saveUser(@RequestBody Author author) {
        authorRepository.save(author);
        return ResponseEntity.ok("created");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteUserById(@PathVariable(name = "id") int id) {
        try {
            authorRepository.delete(id);
            return ResponseEntity.ok("Delete");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("user with" + id + "does not exist");
        }
    }

    @PutMapping()
    public ResponseEntity updateAuthor(@RequestBody Author author) {
        if (authorRepository.exists(author.getId())) {
            authorRepository.save(author);
            return ResponseEntity.ok("update");
        }
        return ResponseEntity.badRequest().body("User with" + author.getEmail() + "does not exist");
    }
}
