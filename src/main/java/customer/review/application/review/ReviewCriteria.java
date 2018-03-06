package customer.review.application.review;

/**
 * Created by amazimpaka on 2018-03-05
 */
public class ReviewCriteria {

    private double minimumRating;
    private double maximumRating;

    public double getMinimumRating() {
        return minimumRating;
    }

    public void setMinimumRating(double minimumRating) {
        this.minimumRating = minimumRating;
    }

    public double getMaximumRating() {
        return maximumRating;
    }

    public void setMaximumRating(double maximumRating) {
        this.maximumRating = maximumRating;
    }
}
