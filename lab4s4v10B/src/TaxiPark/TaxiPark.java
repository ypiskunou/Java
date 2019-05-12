package TaxiPark;
import Car.*;

import java.lang.reflect.Constructor;

public class TaxiPark { // but what if it is electric cars and ordinary gasoline ones? Not division in luxury and others

    private int quantity;

    public Car[] getCars() {
        return cars;
    }
    private Car[] cars;
    private String[] namesCommon= new String[]
            {"Volkswagen Beetle", "Smart", "Lada", "Samand", "Geely", "Nissan Sunny", "Tuc-tuc"};
    private String[] namesLuxury= new String[] {"BMW", "Rangerover", "Porshe", "Ferrari", "Lamborghini", "Rolls-Roys"};
    private String[][] wholeStock= new String[][]{namesCommon, namesLuxury};

    public TaxiPark(CarHierarchy carHierarchy) {
        //cars=generateCarTestArray();
        cars= appendCarArray(cars, generateCarArray(carHierarchy));
    }

    public double getOverallPrice()
    {
        double total=0;
        for(Car car:cars)
            total+=car.getPrice();
        return total;
    }

    private Car[] generateCarTestArray()
    {
        quantity=7;
        int speed=150;
        Car[] cars= new Car[quantity+4];
        for(int i=0; i<quantity; i++)
        {
            if(i>4) speed/=4;
            cars[i]= new LuxuryCar(namesLuxury[i],10000*i+1000*i,
                    5000*i*i+30000,4*i,150*i+20);

        }
        for(int i=quantity; i<cars.length; i++)
        {
            cars[i]= new CommonCar(namesCommon[i],10000*i+1000*i,
                    2000*i+20000,i/2,100+10*i);
        }
        return cars;
    }

    public void printAllCars()
    {
        System.out.println(String.format(" %20s \t: %s \t: %s \t: %s \t: %s"
                ,"NAME", "SPEED", "CONSUMPTION", "PRICE", "NUMBER"));
        for(Car car:cars)
            System.out.println(car);
    }

    private  Car[] appendCarArray(Car[] dest, Car[] src)
    {
        int destLength=0;
        if(dest!=null)
            destLength=dest.length;
        int total= destLength+src.length;
        Car[] result= new Car[total];
        for(int i=0; i<destLength; i++)
            result[i] = dest[i];
        for(int i=destLength; i<src.length; i++)
            result[i] = src[i];
        return result;
    }

    private Car[] generateCarArray(CarHierarchy carHierarchy) {
        /*Scanner scanner= new Scanner(System.in);
        while (quantity < 1) {
            System.out.println(" \n Enter the number of desired operation (0 for help): \n ");
            while (!scanner.hasNextInt())
                scanner.next();
            quantity = scanner.nextInt();
        }*/
        quantity = 0;
        for (int i = 0; i < carHierarchy.getEachTypeQty().length; i++)
            quantity += carHierarchy.getEachTypeQty()[i];
        Car[] cars = new Car[quantity];
        int typeQty;
        int number;
        double price;
        double consumption;
        int speed;
        int counterStart= 0;
        int counter;
        String modelName;

        for (int j = 0; j < carHierarchy.getEachTypeQty().length; j++) {
            if(j>0)
                counterStart+=carHierarchy.getEachTypeQty()[j-1];
            counter=counterStart;
            for (int i = 0; i < carHierarchy.getEachTypeQty()[j]; i++, counter++) {
                typeQty = carHierarchy.getEachTypeQty()[j];
                modelName= wholeStock[j][(int) (Math.random() * wholeStock[j].length)];
                number = 10000 + (int) (Math.random() * 90000);
                price = 10000 + Math.round(Math.random() * 20000 * 100) / 100.0;
                consumption = 5 + Math.round(Math.random() * 500) / 100.0;
                speed = 120 + (int) (Math.random() * 100);

                try {
                    String name = CarType.values()[j].name();
                    StringBuilder fullName = new StringBuilder("Car.");
                    fullName.append(name);
                    Class<?> clazz = Class.forName(fullName.toString());//(CarType.values()[j].name());
                    Constructor[] constructors = clazz.getConstructors();
                    Constructor<?> constructor = clazz.getConstructor(String.class, int.class,
                            double.class, double.class, int.class);//constructors[0];//
                    cars[counter] = (Car) constructor.newInstance(modelName, number, price, consumption, speed);
                    //System.out.println(cars[i].getClass().toString());  // test
                } catch (ClassNotFoundException exc) {
                    System.out.println(" Given class not found! \n");
                } catch (Exception exc) {
                    System.out.println(" Constructor was not found! \n");
                }
                //cars[i]= new LuxuryCar(Math.random()*)
            }
        }
        return cars;
    }
}
