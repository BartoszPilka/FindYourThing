package FindYourThingApplication.exceptions.review;

public class SelfReviewException extends RuntimeException
{
    public SelfReviewException()
    {
        super("You cannot review yourself");
    }
}
