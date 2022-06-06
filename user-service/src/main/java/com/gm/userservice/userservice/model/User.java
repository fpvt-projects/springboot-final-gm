package com.gm.userservice.userservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;


@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    @NotBlank
    private String firstName;

    private String middleName;

    @NotBlank
    private String lastName;

    private double balance;

    @NotBlank
    @Column(unique=true)
    private String email;

    @NotBlank
    private String password;

    private LocalDateTime createdAt;

    private LocalDateTime lastUpdated;



}
