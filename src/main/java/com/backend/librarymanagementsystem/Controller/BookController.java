package com.backend.librarymanagementsystem.Controller;

import com.backend.librarymanagementsystem.DTO.BookRequestDto;
import com.backend.librarymanagementsystem.DTO.BookResponseDto;
import com.backend.librarymanagementsystem.Entity.Book;
import com.backend.librarymanagementsystem.Service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/book")
public class BookController {
@Autowired
    BookService bookService;
@PostMapping("/add")
  public BookResponseDto addBook(@RequestBody BookRequestDto bookRequestDto) throws Exception {

      return bookService.addBook(bookRequestDto);
  }
}
