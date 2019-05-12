package Car;

public class ExceptionNonPositiveValue extends Exception{
    @Override
    public String getMessage() {
        return "\n You've entered negative value while positive is expected! ";
    }
}
