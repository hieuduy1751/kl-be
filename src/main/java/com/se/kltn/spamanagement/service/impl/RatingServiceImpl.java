package com.se.kltn.spamanagement.service.impl;

import com.se.kltn.spamanagement.dto.request.RatingRequest;
import com.se.kltn.spamanagement.dto.response.RatingResponse;
import com.se.kltn.spamanagement.exception.ResourceNotFoundException;
import com.se.kltn.spamanagement.model.Rating;
import com.se.kltn.spamanagement.repository.CustomerRepository;
import com.se.kltn.spamanagement.repository.ProductRepository;
import com.se.kltn.spamanagement.repository.RatingRepository;
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

    private final CustomerRepository customerRepository;

    @Autowired
    public RatingServiceImpl(RatingRepository ratingRepository, ProductRepository productRepository, CustomerRepository customerRepository) {
        this.ratingRepository = ratingRepository;
        this.productRepository = productRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public RatingResponse addRatingToProduct(RatingRequest ratingRequest, Long idProduct, Long idCustomer) {
        Rating rating = mapRating(ratingRequest);
        rating.setProduct(this.productRepository.findById(idProduct).orElseThrow(
                () -> new ResourceNotFoundException(PRODUCT_NOT_FOUND)
        ));
        rating.setCustomer(this.customerRepository.findById(idCustomer).orElseThrow(
                () -> new ResourceNotFoundException(CUSTOMER_NOT_FOUND)
        ));
        return MappingData.mapObject(this.ratingRepository.save(rating), RatingResponse.class);
    }

    private Rating mapRating(RatingRequest ratingRequest) {
        Rating rating = MappingData.mapObject(ratingRequest, Rating.class);
        rating.setCreatedDate(new Date());
        return rating;
    }

    @Override
    public List<RatingResponse> getRatingByProductId(Long idProduct) {
        List<Rating> ratings = this.ratingRepository.findRatingsByProduct_Id(idProduct).orElse(null);
        return MappingData.mapListObject(ratings, RatingResponse.class);
    }
}
