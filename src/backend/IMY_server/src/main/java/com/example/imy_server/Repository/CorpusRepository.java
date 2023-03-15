package com.example.imy_server.Repository;

import com.example.imy_server.Domain.Corpus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CorpusRepository extends JpaRepository<Corpus, Long> {

    /**
     * user_id를 입력하면 user_pk를 반환해 삭제함
     * user_id를 이용한 직접적인 delete를 하지 않는 이유는 테이블에서는 key 값을 이용한 update와 delete만 하도록 설정되어 있기 떄문
     * 하고 싶다면 sql safemode를 해제해야한다.
     */

  //  @Query(value = "select u.user_pk from User u where u.user_id = ?1")


    @Query(value = "SELECT * FROM Corpus WHERE emotion_maincategory = ?1", nativeQuery = true)
    List<Corpus> findAllByEmotionmaincategory(String emotion_maincategory);

    /**
     * 따라서 이와같은 쿼리는 작동하지 않는다
     * @Query(value = "delete from User u where u.user_id = ?1")
     * void deleteByUserId(String user_id);
     */
}
