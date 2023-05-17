package com.exam.examserver.repo;

import com.exam.examserver.model.exam.Category;
import com.exam.examserver.model.exam.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

public interface QuizRepository extends JpaRepository<Quiz,Long> {

//    @Query(value = "SELECT distinct(category) from Quiz where active=:true",nativeQuery = true)
    public Set<Quiz> findBycategory(Category category);

    public List<Quiz> findByActive(Boolean b);

    public List<Quiz> findByCategoryAndActive(Category c,boolean b);
}
