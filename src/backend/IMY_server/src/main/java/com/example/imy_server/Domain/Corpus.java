package com.example.imy_server.Domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
public class Corpus {
    @Id
    private Long corpus_id;
    private String age;
    private String sex;
    private String status_keyword;
    private String physical_illness;
    private String emotion_maincategory;
    private String emotion_subcategory;
    private String human_sentence1;
    private String system_response1;
    private String human_sentence2;
    private String system_response2;
    private String human_sentence3;
    private String system_response3;
    private String human_sentence4;
    private String system_response4;

}
