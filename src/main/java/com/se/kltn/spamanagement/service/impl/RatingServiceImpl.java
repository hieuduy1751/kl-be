package com.se.kltn.spamanagement.service.impl;

import com.se.kltn.spamanagement.dto.request.RatingRequest;
import com.se.kltn.spamanagement.dto.response.RatingResponse;
import com.se.kltn.spamanagement.exception.ResourceNotFoundException;
import com.se.kltn.spamanagement.model.Rating;
import com.se.kltn.spamanagement.repository.ProductRepository;
import com.se.kltn.spamanagement.repository.RatingRepository;
import com.se.kltn.spamanagement.repository.TreatmentRepository;
import com.se.kltn.spamanagement.service.RatingService;
import com.se.kltn.spamanagement.utils.MappingData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

import static com.se.kltn.spamanagement.constants.ErrorMessage.*;

@Service
public class RatingServiceImpl implements RatingService {

    private final RatingRepository ratingRepository;

    private final ProductRepository productRepository;

    private final TreatmentRepository treatmentRepository;

    @Autowired
    public RatingServiceImpl(RatingRepository ratingRepository, ProductRepository productRepository, TreatmentRepository treatmentRepository) {
        this.ratingRepository = ratingRepository;
        this.productRepository = productRepository;
        this.treatmentRepository = treatmentRepository;
    }

    @Override
    public RatingResponse addRatingToProduct(RatingRequest ratingRequest, Long idProduct) {
        Rating rating = MappingData.mapObject(ratingRequest, Rating.class);
        rating.setCreatedDate(new Date());
        rating.setProduct(this.productRepository.findById(idProduct).orElseThrow(
                () -> new ResourceNotFoundException(PRODUCT_NOT_FOUND)
        ));
        return MappingData.mapObject(rating, RatingResponse.class);
    }

    @Override
    public RatingResponse addRatingToTreatment(RatingRequest ratingRequest, Long idTreatment) {
        Rating rating = MappingData.mapObject(ratingRequest, Rating.class);
        rating.setCreatedDate(new Date());
        rating.setTreatment(this.treatmentRepository.findById(idTreatment).orElseThrow(
                () -> new ResourceNotFoundException(TREATMENT_NOT_FOUND)
        ));
        return MappingData.mapObject(rating, RatingResponse.class);
    }

    @Override
    public RatingResponse getRatingByProductId(Long idProduct) {
        Rating rating = this.ratingRepository.findRatingByProduct_Id(idProduct).orElseThrow(
                () -> new ResourceNotFoundException(RATING_NOT_FOUND));
        return MappingData.mapObject(rating, RatingResponse.class);
    }

    @Override
    public RatingResponse getRatingByTreatmentId(Long idTreatment) {
        Rating rating = this.ratingRepository.findRatingByTreatment_Id(idTreatment).orElseThrow(
                () -> new ResourceNotFoundException(RATING_NOT_FOUND));
        return MappingData.mapObject(rating, RatingResponse.class);
    }
}
