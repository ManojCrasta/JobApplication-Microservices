package com.projects.reviewms.reviews.impl;

import com.projects.reviewms.reviews.Review;
import com.projects.reviewms.reviews.ReviewRepository;
import com.projects.reviewms.reviews.ReviewService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImplementation implements ReviewService {
    private ReviewRepository reviewRepository;

    public ReviewServiceImplementation(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @Override
    public List<Review> getAllReviews(Long companyId) {


        List<Review> reviews=reviewRepository.findByCompanyId(companyId);
        return reviews;
    }

    @Override
    public boolean addReview(Long companyId, Review review) {

        if(companyId!=null){
            review.setCompanyId(companyId);
            reviewRepository.save(review);
            return true;
        }else {
            return false;
        }

    }

    @Override
    public Review getReview(Long reviewId) {
        return reviewRepository.findById(reviewId).orElse(null);


    }


    @Override
    public boolean updateReview(Long reviewId, Review updatedReview) {
        Review review= reviewRepository.findById(reviewId).orElse(null);

        if(review !=null) {
            review.setCompanyId(updatedReview.getCompanyId());
            review.setDescription(updatedReview.getDescription());
            review.setRating(updatedReview.getRating());
            review.setTitle(updatedReview.getTitle());
            reviewRepository.save(review);
            return true;
        }else {
            return false;
    }
    }

    @Override
    public boolean deleteReview(Long reviewId) {

        Review review=reviewRepository.findById(reviewId).orElse(null);
        if(review != null){
            reviewRepository.delete(review);
            return true;
        }
        return false;
    }
}
