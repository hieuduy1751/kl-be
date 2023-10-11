package com.se.kltn.spamanagement.service;

import com.se.kltn.spamanagement.dto.request.RatingRequest;
import com.se.kltn.spamanagement.dto.response.RatingResponse;

import java.util.List;

public interface RatingService {

    RatingResponse addRatingToProduct(RatingRequest ratingRequest, Long idProduct, Long idCustomer );

    List<RatingResponse> getRatingByProductId(Long idCustomer);

}
