package FindYourThingApplication.exceptions.review;

public class OwnerMismatchException extends RuntimeException
{
    public OwnerMismatchException()
    {
        super("Item owner and reviewer do not match");
    }
}
