package com.projects.reviewms.reviews;

import com.projects.reviewms.reviews.messaging.ReviewMessageProducer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
public class ReviewController {
    ReviewService reviewService;

    private ReviewMessageProducer reviewMessageProducer;
    public ReviewController(ReviewService reviewService,
                            ReviewMessageProducer reviewMessageProducer) {
        this.reviewService = reviewService;
        this.reviewMessageProducer=reviewMessageProducer;
    }


    @GetMapping
    public ResponseEntity<List<Review>> getAllReviews(@RequestParam Long companyId) {
        return new ResponseEntity<>(reviewService.getAllReviews(companyId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> addReview(@RequestParam Long companyId, @RequestBody Review review) {

        Boolean reviewAdd = reviewService.addReview(companyId, review);
        if (reviewAdd) {
            reviewMessageProducer.sendMessage(review);
            return new ResponseEntity<>("Review Added Successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Failed To Add Review", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{reviewId}")
    public ResponseEntity<Review> getReview(@PathVariable Long reviewId){

        return new ResponseEntity<>(reviewService.getReview(reviewId),HttpStatus.OK);
    }

    @PutMapping("/{reviewId}")
    public ResponseEntity<String> updateReview(@PathVariable Long reviewId,
                                               @RequestBody Review updatedReview){

        boolean isReviewUpdated= reviewService.updateReview(reviewId,updatedReview);

        if(isReviewUpdated){
            return new ResponseEntity<>("Review Successfully Updated",HttpStatus.OK);
        }else{
            return new ResponseEntity<>("Review not Updated",HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<String> deleteReview(@PathVariable Long reviewId){
        Boolean delete=reviewService.deleteReview(reviewId);
        if(delete){
            return new ResponseEntity<>("Review deleted successfully",HttpStatus.OK);
        }else{
            return new ResponseEntity<>("Not found",HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/averageRating")
    public Double getAverageReview(@RequestParam Long companyId){
       List<Review> reviewList=reviewService.getAllReviews(companyId);
       return reviewList.stream().mapToDouble(Review::getRating).average().orElse(0.0);
    }
}