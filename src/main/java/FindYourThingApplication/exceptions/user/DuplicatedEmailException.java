package FindYourThingApplication.exceptions.user;

public class DuplicatedEmailException extends RuntimeException
{
    public DuplicatedEmailException()
    {
        super("This email already exists in database");
    }
}
