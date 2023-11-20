package com.se.kltn.spamanagement.repository;

import com.se.kltn.spamanagement.model.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {
}
