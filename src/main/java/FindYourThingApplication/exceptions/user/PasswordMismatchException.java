package FindYourThingApplication.exceptions.user;

public class PasswordMismatchException extends RuntimeException
{
    public PasswordMismatchException()
    {
        super("Passwords do not match");
    }
}
