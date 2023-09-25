package com.se.kltn.spamanagement.service;

import com.se.kltn.spamanagement.dto.request.RatingRequest;
import com.se.kltn.spamanagement.dto.response.RatingResponse;

public interface RatingService {

    RatingResponse addRatingToProduct(RatingRequest ratingRequest, Long idProduct);

    RatingResponse addRatingToTreatment(RatingRequest ratingRequest, Long idTreatment);

    RatingResponse getRatingByProductId(Long idCustomer);

    RatingResponse getRatingByTreatmentId(Long idTreatment);
}
