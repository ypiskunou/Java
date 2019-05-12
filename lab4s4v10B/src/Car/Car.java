package Car;

public abstract class Car implements Comparable<Car>{

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    private String name;

    public double getPrice() {
        return price;
    }
    public void setPrice(double price) throws ExceptionNonPositiveValue {
        if(price>0)
            this.price = price;
        else throw new ExceptionNonPositiveValue();
    }
    private double price;

    public double getConsumption() {
        return consumption;
    }
    public void setConsumption(double consumption) throws ExceptionNonPositiveValue {
        if(consumption>0)
            this.consumption = consumption;
        else throw new ExceptionNonPositiveValue();
    }
    private double consumption;

    public int getSpeed() {
        return speed;
    }
    public void setSpeed(int speed) throws ExceptionNonPositiveValue {
        if(speed>0)
            this.speed = speed;
        else throw new ExceptionNonPositiveValue();
    }
    private int speed;

    public int getNumber() {
        return number;
    }
    public void setNumber(int number) {
        this.number = number;
    }
    private int number;

    public Car(String name, int number, double price, double consumption, int speed)
    {
        try {
            setName(name);
            setNumber(number);
            setPrice(price);
            setConsumption(consumption);
            setSpeed(speed);
        }
        catch (ExceptionNonPositiveValue exc)
        {
            System.out.println(exc.getMessage());
        }
    }

    @Override
    public String toString() {
        return String.format(" %20s \t: %-5d \t: %.2f \t: %.2f \t: %d ", name, speed, consumption, price, number);
    }

    @Override
    public int compareTo(Car car)
    {
        return Double.compare(consumption, car.getConsumption());
    }

}