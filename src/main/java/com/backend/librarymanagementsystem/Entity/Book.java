package com.backend.librarymanagementsystem.Entity;

import com.backend.librarymanagementsystem.Enum.Genre;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int book_id;
    private String title;
    private int price;
    @Enumerated(EnumType.STRING)
    private Genre genre;
    private boolean isIssued;
    @ManyToOne
    @JoinColumn
    Author author;
    @OneToMany(mappedBy = "book",cascade = CascadeType.ALL)
    List<Transaction> transaction=new ArrayList<>();

    @ManyToOne
    @JoinColumn
    LibraryCard libraryCard;
}
