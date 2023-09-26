package com.se.kltn.spamanagement.repository;

import com.se.kltn.spamanagement.model.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {
    Optional<List<Rating>> findRatingsByProduct_Id(Long id);

    Optional<List<Rating>> findRatingsByTreatment_Id(Long id);

}
