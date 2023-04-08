package com.backend.librarymanagementsystem.Service;

import com.backend.librarymanagementsystem.DTO.IssueBookRequestDto;
import com.backend.librarymanagementsystem.DTO.IssueBookResponseDto;
import com.backend.librarymanagementsystem.DTO.TransactionResponseDto;
import com.backend.librarymanagementsystem.Entity.Book;
import com.backend.librarymanagementsystem.Entity.LibraryCard;
import com.backend.librarymanagementsystem.Entity.Transaction;
import com.backend.librarymanagementsystem.Enum.CardStatus;
import com.backend.librarymanagementsystem.Enum.TransactionStatus;
import com.backend.librarymanagementsystem.Repository.BookRepository;
import com.backend.librarymanagementsystem.Repository.CardRepository;
import com.backend.librarymanagementsystem.Repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class TransactionService {
    @Autowired
    CardRepository cardRepository;
    @Autowired
    BookRepository bookRepository;
    @Autowired
    TransactionRepository transactionRepository;
    @Autowired
    JavaMailSender emailSender;
    public IssueBookResponseDto issueBook(IssueBookRequestDto issueBookRequestDto) throws Exception {
        Transaction transaction=new Transaction();
        transaction.setTransactionNo(String.valueOf(UUID.randomUUID()));
        transaction.setIssuedOperation(true);
        LibraryCard libraryCard;
        try {
            libraryCard = cardRepository.findById(issueBookRequestDto.getCardId()).get();
        }
        catch (Exception e)
        {
            transaction.setTransactionStatus(TransactionStatus.FAILED);
            transaction.setMessage("Invalid card id");
            transactionRepository.save(transaction);
            throw new Exception("Invalid card id");
        }
        Book book;
        try {
            book = bookRepository.findById(issueBookRequestDto.getBookId()).get();
        }
        catch (Exception e)
        {
            transaction.setTransactionStatus(TransactionStatus.FAILED);
            transaction.setMessage("Invalid book id");
            transactionRepository.save(transaction);
            throw new Exception("Invalid book id");
        }
        transaction.setBook(book);
        transaction.setCard(libraryCard);
        if(libraryCard.getCardStatus()!= CardStatus.ACTIVATED)
        {
            transaction.setTransactionStatus(TransactionStatus.FAILED);
            transaction.setMessage("Card is not activated");
            transactionRepository.save(transaction);
            throw new Exception("Card is not activated");
        }
        if(book.isIssued()==true)
        {
            transaction.setTransactionStatus(TransactionStatus.FAILED);
            transaction.setMessage("Sorry!Book has been already issued");
            transactionRepository.save(transaction);
            throw new Exception("Sorry!Book has been already issued");
        }
        transaction.setTransactionStatus(TransactionStatus.SUCCESS);
        transaction.setMessage("Transaction was successful");

        book.setIssued(true);
        book.setLibraryCard(libraryCard);
        book.getTransaction().add(transaction);
        libraryCard.getTransaction().add(transaction);
        libraryCard.getBooksIssued().add(book);
//        transaction.setTransactionStatus(TransactionStatus.SUCCESS);
//        transaction.setMessage("Transaction got successful");
        cardRepository.save(libraryCard);
        IssueBookResponseDto issueBookResponseDto=new IssueBookResponseDto();
        issueBookResponseDto.setTransactionId(transaction.getTransactionNo());
       issueBookResponseDto.setTransactionStatus(TransactionStatus.SUCCESS);
        issueBookResponseDto.setBookName(book.getTitle());

        //send an email
        String text="Congrats! "+book.getTitle()+" book has been issued to "+libraryCard.getStudent().getName();
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("marjitasaha@gmail.com");
        message.setTo(libraryCard.getStudent().getEmail());
        message.setSubject("Issue Book Notification");
        message.setText(text);
        emailSender.send(message);
        return issueBookResponseDto;
    }
    public List<TransactionResponseDto> getAllTxns(int cardId)
    {
        List<Transaction> transactions=transactionRepository.getAllSuccessfullTxnsWithCardNo(cardId);
        List<TransactionResponseDto> transactionResponseDtos=new ArrayList<>();
        for (Transaction t:transactions)
        {
            TransactionResponseDto transactionResponseDto=new TransactionResponseDto();
            transactionResponseDto.setTransactionNo(t.getTransactionNo());
            transactionResponseDto.setTransactionDate(t.getTransactionDate());
            transactionResponseDto.setTransactionStatus(t.getTransactionStatus());
            transactionResponseDto.setIssueOperation(true);
            transactionResponseDto.setMessage(t.getMessage());
            transactionResponseDtos.add(transactionResponseDto);
        }
        return transactionResponseDtos;
    }
}
