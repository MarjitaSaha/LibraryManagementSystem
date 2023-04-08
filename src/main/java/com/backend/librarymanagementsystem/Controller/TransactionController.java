package com.backend.librarymanagementsystem.Controller;

import com.backend.librarymanagementsystem.DTO.IssueBookRequestDto;
import com.backend.librarymanagementsystem.DTO.IssueBookResponseDto;
import com.backend.librarymanagementsystem.DTO.TransactionResponseDto;
import com.backend.librarymanagementsystem.Service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transaction")
public class TransactionController {
   @Autowired
   TransactionService transactionService;
   @PostMapping("/issue")
   public ResponseEntity issueBook (@RequestBody IssueBookRequestDto issueBookRequestDto) throws Exception {
      IssueBookResponseDto issueBookResponseDto;
      try {
         issueBookResponseDto=transactionService.issueBook(issueBookRequestDto);
      }
     catch (Exception e)
     {
       return new ResponseEntity(e.getMessage(),HttpStatus.NOT_ACCEPTABLE);
     }
      return new ResponseEntity(issueBookResponseDto,HttpStatus.ACCEPTED);
   }

    @GetMapping("/get")
    public List<TransactionResponseDto> getAllTxns(@RequestParam("cardId") int cardId){
        return transactionService.getAllTxns(cardId);
    }
}
