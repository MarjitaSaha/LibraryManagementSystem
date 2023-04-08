package com.backend.librarymanagementsystem.Controller;

import com.backend.librarymanagementsystem.DTO.AuthorRequestDto;
import com.backend.librarymanagementsystem.Entity.Author;
import com.backend.librarymanagementsystem.Service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/author")
public class AuthorController {
    @Autowired
    AuthorService authorService;
    @PostMapping("/add")
    public String addAuthor(@RequestBody AuthorRequestDto authorRequestDto)
    {
        authorService.addAuthor(authorRequestDto);
        return "Author has been added";
    }
    @GetMapping("/get_authors")
    public List<Author> getAuthors()
    {
      return authorService.getAuthors();
    }

}
