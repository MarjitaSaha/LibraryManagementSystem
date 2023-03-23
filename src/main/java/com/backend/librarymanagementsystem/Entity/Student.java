package com.backend.librarymanagementsystem.Entity;

import com.backend.librarymanagementsystem.Enum.Department;
import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int studId;
    private String name;
    private int age;
    @Enumerated(EnumType.STRING)
    private Department department;
    @Column(unique = true)
    private String email;
    @OneToOne(mappedBy = "student",cascade = CascadeType.ALL)
    LibraryCard card;

}
