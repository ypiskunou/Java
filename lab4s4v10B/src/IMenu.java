import TaxiPark.CarHierarchy;
import TaxiPark.TaxiPark;
import Car.Car;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.function.Predicate;

public interface IMenu {
    void action();
}

class Option0
{
    void help()
    {
        System.out.println(" 0 - Help \n" +
                " 1 - Define hierarchy \n" +
                " 2 - Create Taxi park \n" +
                " 3 - Estimate overall price \n" +
                " 4 - Sort cars by fuel consumption \n" +
                " 5 - Find car in speed range \n" +
                " 6 - Exit \n");
    }
}

class Option1
{
    public CarHierarchy getCarHierarchy() {
        return carHierarchy;
    }
    public void setCarHierarchy(CarHierarchy carHierarchy) {
        this.carHierarchy = carHierarchy;
    }
    private CarHierarchy carHierarchy;

    void defineHierarchy()
    {
        carHierarchy= new CarHierarchy();
    }
}

class Option2
{
    private CarHierarchy carHierarchy;

    public TaxiPark getTaxiPark() {
        return taxiPark;
    }
    private TaxiPark taxiPark;

    void createTaxiPark(CarHierarchy carHierarchy)
    {
        taxiPark= new TaxiPark(carHierarchy);
        printAllCars();
    }

    private void printAllCars()
    {
        System.out.println(String.format(" %20s \t: %s \t: %s \t: %s \t: %s"
                ,"NAME", "SPEED", "FUEL", "PRICE", "NUMBER"));
        for(Car car: taxiPark.getCars())
            System.out.println(car);
    }
}

class Option3
{
    private double overallPrice=0;

    public void estimateOverallPrice(Car[] cars)
    {
        for(Car car: cars)
            overallPrice+=car.getPrice();
        System.out.println(String.format("\n Total price of all cars is: %.2f$ \n", overallPrice));
    }
}

class Option4
{
    private Car[] cars;

    private void sortByFuelConsumption()
    {
        Arrays.sort(cars);
    }

    public void printSortedbyConsumption(Car[] cars)
    {
        this.cars= cars;
        sortByFuelConsumption();
        for(Car car:cars)
            System.out.println(car);
    }
}

class Option5
{
    private Car[] carsInFilter;

    private static Predicate<Car> isInRange(int a, int b)
    {
        return o->o.getSpeed()>=a && o.getSpeed()<=b; //->o>=a&&o<=b;
    }

    private static Predicate<Car> commonPlacesTest()
    {
        return o->o.getSpeed()>0;
    }

    public void findCarInSpeedRange(Car[] cars, int speedA, int speedB)
    {
        carsInFilter= cars;
        ArrayList<Car> list= new ArrayList<>(Arrays.asList(carsInFilter));
        list.removeIf(isInRange(speedA, speedB).negate());
        carsInFilter= new Car[list.size()];
        list.toArray(carsInFilter);

        printCarsInFilter(speedA, speedB);
    }
    private void printCarsInFilter(int a, int b)
    {
        System.out.println("\n Cars with a speed in the range "+a+"-"+b+": \n");
        for(Car car: carsInFilter)
            System.out.println(car);
    }
}

class Option6
{
    void exit()
    {

    }
}

class MenuDialog
{
    private Option0 option0= new Option0();
    private Option1 option1= new Option1();
    private Option2 option2= new Option2();
    private Option3 option3= new Option3();
    private Option4 option4= new Option4();
    private Option5 option5= new Option5();
    private Option6 option6= new Option6();

    IMenu[] menus= new IMenu[]{
            new IMenu(){
                @Override
                public void action() {
                    option0.help();
                }
            },
            new IMenu(){
                @Override
                public void action() {
                    option1.defineHierarchy();
                }
            },
            new IMenu(){
                @Override
                public void action() {
                    option2.createTaxiPark(option1.getCarHierarchy());
                }
            },
            new IMenu(){
                @Override
                public void action() {
                    option3.estimateOverallPrice(option2.getTaxiPark().getCars());
                }
            },
            new IMenu(){
                @Override
                public void action() {
                    option4.printSortedbyConsumption(option2.getTaxiPark().getCars());
                }
            },
            new IMenu(){
                @Override
                public void action() {
                    int a=0;
                    int b=0;
                    Scanner scanner= new Scanner(System.in);
                    while (a < 1) {
                        System.out.println(" \n Enter minimum of desired speed: \n ");
                        while (!scanner.hasNextInt())
                            scanner.next();
                        a = scanner.nextInt();
                    }
                    while (b < a) {  // it's possible to enter only one value
                        System.out.println(" \n Enter maximum of desired speed: \n ");
                        while (!scanner.hasNextInt())
                            scanner.next();
                        b = scanner.nextInt();
                    }
                    option5.findCarInSpeedRange(option2.getTaxiPark().getCars(),a,b);
                }
            },
            new IMenu(){
                @Override
                public void action() {
                    option6.exit();
                }
            }
    };

    MenuDialog(){

        int operation=-1;
        boolean grantedOperations= false;
        byte mask;  // up to 8 operations
        byte history= 0;

        Scanner scanner= new Scanner(System.in);
        while (operation != 6) {
            operation=-1;
            while (operation < 0 || operation > 6) {
                System.out.println(" \n Enter the number of desired operation (0 for help): \n ");
                while (!scanner.hasNextInt())
                    scanner.next();
                operation = scanner.nextInt();
            }
            if((history&6)!=6)
                grantedOperations= false;
            mask= 1;
            mask<<=operation;
            if((history&mask)==0)  // not to get interference while adding
                history|=mask;

            if(operation==1&&(history&6)==6)
            {
                history= mask;  // 1 - is the main operation
                grantedOperations= false;
                System.out.println(" \n Now you should create taxi park! \n ");
            }
            // if(history==4)
            if((history&6)==6||operation==1)
                grantedOperations= true;

            // if((history&mask)==2)  // ==0b0000 0010
            if(grantedOperations||operation==0)
                menus[operation].action();
            else System.out.println(" \n Restricted operation! At first you need to define hierarchy and" +
                    " to create taxi park! \n ");
        }
    }
}