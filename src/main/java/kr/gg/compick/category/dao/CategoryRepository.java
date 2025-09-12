package kr.gg.compick.category.dao;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.gg.compick.domain.board.Category;

public interface CategoryRepository extends JpaRepository<Category, String>{
   boolean existsByCategoryIdx(String categoryIdx);
   Optional<Category> findByCategoryIdx(String categoryIdx);
}
