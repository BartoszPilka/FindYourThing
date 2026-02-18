package FindYourThingApplication.exceptions.user;

public class DuplicatedPasswordException extends RuntimeException
{
    public DuplicatedPasswordException()
    {
        super("New password cannot be the same as current password");
    }
}
