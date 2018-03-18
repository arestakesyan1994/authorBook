package com.example.authorbook.rest;


import com.example.authorbook.model.Author;
import com.example.authorbook.model.Book;
import com.example.authorbook.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/books")
public class BookController {

    @Autowired
    private BookRepository bookRepository;


    @GetMapping()
    public ResponseEntity books() {
        return ResponseEntity.ok(bookRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity getUserById(@PathVariable(name = "id") int id) {
        Book one = bookRepository.findOne(id);
        if (one == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with " + id + " id no found");
        }
        return ResponseEntity.ok(one);
    }

    @PostMapping()
    public ResponseEntity saveUser(@RequestBody Book book) {
        bookRepository.save(book);
        return ResponseEntity.ok("created");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteUserById(@PathVariable(name = "id") int id) {
        try {
            bookRepository.delete(id);
            return ResponseEntity.ok("Delete");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("user with" + id + "does not exist");
        }
    }

    @PutMapping()
    public ResponseEntity updateAuthor(@RequestBody Book book) {
        if (bookRepository.exists(book.getId())) {
            bookRepository.save(book);
            return ResponseEntity.ok("update");
        }
        return ResponseEntity.badRequest().body("User with" + ""+ "does not exist");
    }
}
