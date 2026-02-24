package FindYourThingApplication.exceptions.review;

public class ReviewNotFoundException extends RuntimeException
{
    public ReviewNotFoundException()
    {
        super("This review does not exist");
    }
}
