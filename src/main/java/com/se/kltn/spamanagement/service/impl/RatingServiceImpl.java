package com.se.kltn.spamanagement.service.impl;

import com.se.kltn.spamanagement.dto.request.RatingRequest;
import com.se.kltn.spamanagement.dto.response.RatingResponse;
import com.se.kltn.spamanagement.exception.ResourceNotFoundException;
import com.se.kltn.spamanagement.model.Rating;
import com.se.kltn.spamanagement.repository.CustomerRepository;
import com.se.kltn.spamanagement.repository.ProductRepository;
import com.se.kltn.spamanagement.repository.RatingRepository;
import com.se.kltn.spamanagement.repository.TreatmentRepository;
import com.se.kltn.spamanagement.service.RatingService;
import com.se.kltn.spamanagement.utils.MappingData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

import static com.se.kltn.spamanagement.constants.ErrorMessage.*;

@Service
public class RatingServiceImpl implements RatingService {

    private final RatingRepository ratingRepository;

    private final ProductRepository productRepository;

    private final TreatmentRepository treatmentRepository;

    private final CustomerRepository customerRepository;

    @Autowired
    public RatingServiceImpl(RatingRepository ratingRepository, ProductRepository productRepository, TreatmentRepository treatmentRepository, CustomerRepository customerRepository) {
        this.ratingRepository = ratingRepository;
        this.productRepository = productRepository;
        this.treatmentRepository = treatmentRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public RatingResponse addRatingToProduct(RatingRequest ratingRequest) {
        Rating ratingUpdate = creatRating(ratingRequest);
        ratingUpdate.setProduct(this.productRepository.findById(ratingRequest.getIdProduct()).orElseThrow(
                () -> new ResourceNotFoundException(PRODUCT_NOT_FOUND)
        ));
        ratingUpdate.setCustomer(this.customerRepository.findById(ratingRequest.getIdCustomer()).orElseThrow(
                () -> new ResourceNotFoundException(CUSTOMER_NOT_FOUND)
        ));
        return MappingData.mapObject(this.ratingRepository.save(ratingUpdate), RatingResponse.class);
    }

    @Override
    public RatingResponse addRatingToTreatment(RatingRequest ratingRequest, Long idTreatment) {
        Rating ratingUpdate = MappingData.mapObject(ratingRequest, Rating.class);
        ratingUpdate.setCreatedDate(new Date());
        ratingUpdate.setTreatment(this.treatmentRepository.findById(idTreatment).orElseThrow(
                () -> new ResourceNotFoundException(TREATMENT_NOT_FOUND)
        ));
        ratingUpdate.setCustomer(this.customerRepository.findById(ratingRequest.getIdCustomer()).orElseThrow(
                () -> new ResourceNotFoundException(CUSTOMER_NOT_FOUND)
        ));
        return MappingData.mapObject(this.ratingRepository.save(ratingUpdate), RatingResponse.class);
    }

    private Rating creatRating(RatingRequest ratingRequest) {
        Rating rating = MappingData.mapObject(ratingRequest, Rating.class);
        rating.setCreatedDate(new Date());
        return this.ratingRepository.save(rating);
    }

    @Override
    public List<RatingResponse> getRatingByProductId(Long idProduct) {
        List<Rating> ratings = this.ratingRepository.findRatingsByProduct_Id(idProduct).orElse(null);
        return MappingData.mapListObject(ratings, RatingResponse.class);
    }

    @Override
    public List<RatingResponse> getRatingByTreatmentId(Long idTreatment) {
        List<Rating> ratings = this.ratingRepository.findRatingsByTreatment_Id(idTreatment).orElse(null);
        return MappingData.mapListObject(ratings, RatingResponse.class);
    }
}
