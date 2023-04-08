package com.backend.librarymanagementsystem.Entity;

import com.backend.librarymanagementsystem.Enum.TransactionStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String transactionNo;
    @Enumerated(EnumType.STRING)
    private TransactionStatus transactionStatus;
    @CreationTimestamp
    private Date transactionDate;
    private boolean isIssuedOperation;
    private String message;
    @ManyToOne
    @JoinColumn
    Book book;
    @ManyToOne
    @JoinColumn
    LibraryCard card;

}
