package com.daviaNhat.osahaneat.repository;

import com.daviaNhat.osahaneat.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryReposiroty extends JpaRepository<Category, Integer> {

}
