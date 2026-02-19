package FindYourThingApplication.exceptions.user;

public class NotUniqueFieldException extends RuntimeException
{
    public NotUniqueFieldException(String fieldName)
    {
        super("This " + fieldName + " already exists in database");
    }
}

