package com.khanhbn.hibernatejwt.user;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name="KBN_LOCAL_USER")
public class User {

    @Id
    @GeneratedValue
    private Long user_id;

    @Column(nullable = false, unique = true)
    private String username;
    private String password;
}
