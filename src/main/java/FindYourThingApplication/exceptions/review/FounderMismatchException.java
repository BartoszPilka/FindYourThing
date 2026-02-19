package FindYourThingApplication.exceptions.review;

public class FounderMismatchException extends RuntimeException
{
    public FounderMismatchException()
    {
        super("Item founder and review receiver do not match");
    }
}
