package FindYourThingApplication.exceptions.review;

import FindYourThingApplication.exceptions.user.DuplicatedPasswordException;

public class DuplicatedReviewException extends RuntimeException
{
    public DuplicatedReviewException()
    {
        super("This reviewer already reviewed this founder about this item");
    }
}
