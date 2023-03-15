package com.example.imy_server.Domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class User {

    @Id @GeneratedValue
    private Long user_pk;
    private String user_id;
    private String user_pw;
    private String user_name;
    private String user_phone;
    private String user_auth;

}
