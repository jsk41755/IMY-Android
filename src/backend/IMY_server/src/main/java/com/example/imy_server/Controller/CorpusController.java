package com.example.imy_server.Controller;


import com.example.imy_server.Domain.Corpus;
import com.example.imy_server.Domain.User;
import com.example.imy_server.Repository.CorpusRepository;
import com.example.imy_server.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class CorpusController {

    private final CorpusRepository corpusRepository;


    @Autowired
    public CorpusController(CorpusRepository corpusRepository){
        this.corpusRepository = corpusRepository;
    }


    //전체 데이터 조회
    @GetMapping("/corpus")
    public List findAll(){
        List<Corpus> all = corpusRepository.findAll();
        return all;
    }

    //corpus_id로 하나의 데이터 조회
    @GetMapping("/corpus/{corpus_id}")
    public Optional<Corpus> findOneByCorpusid(@PathVariable Long corpus_id) {
        Optional<Corpus> byId = corpusRepository.findById(corpus_id);
        return byId;
    }

    //emotion_maincategory(슬픔, 기쁨, 당황...)의 키워드에 해당하는 데이터 전체 조회
    @GetMapping("/corpus/emotion_maincategory/{emotion_maincategory}")
    public List<Corpus> findAllByMaincategory(@PathVariable String emotion_maincategory){
        List<Corpus> allByEmotionmaincategory = corpusRepository.findAllByEmotionmaincategory(emotion_maincategory);
        return allByEmotionmaincategory;
    }

}
