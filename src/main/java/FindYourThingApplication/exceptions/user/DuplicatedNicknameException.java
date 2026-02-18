package FindYourThingApplication.exceptions.user;

public class DuplicatedNicknameException extends RuntimeException
{
    public DuplicatedNicknameException()
    {
        super("This nickname already exists in database");
    }
}

