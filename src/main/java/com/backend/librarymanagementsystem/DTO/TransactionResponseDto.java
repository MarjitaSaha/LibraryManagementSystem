package com.backend.librarymanagementsystem.DTO;

import com.backend.librarymanagementsystem.Enum.TransactionStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionResponseDto {
   private String transactionNo;
   private TransactionStatus transactionStatus;
   private Date transactionDate;
   private boolean isIssueOperation;
   private String message;
}
