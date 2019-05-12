package TaxiPark;

import java.util.Scanner;

public class CarHierarchy {
    public int[] getEachTypeQty() {
        return eachTypeQty;
    }
    public void setEachTypeQty(int[] eachTypeQty) {
        this.eachTypeQty = eachTypeQty;
    }
    private int[] eachTypeQty;

    public CarHierarchy() {
        int quantity;
        eachTypeQty= new int[CarType.values().length];
        Scanner scanner = new Scanner(System.in);
        for (int i = 0; i < CarType.values().length; i++) {
            quantity=-1;
            // camel case splitter to get string of class name
            String[] typeName = CarType.values()[i].name().split("(?<!^)(?=[A-Z])");
            StringBuilder typeNameResult = new StringBuilder();
            for (int j = 0; j < typeName.length; j++)//; int a=2; a=true?5:3;
                if (j == typeName.length - 1)
                    typeNameResult.append(typeName[j]);
                else typeNameResult.append(typeName[j]).append(" ");
                String s= typeNameResult.toString();
            while (quantity < 1) {
                System.out.println(String.format(" \n " +
                        " Enter the number of cars in group \"%s\" of car hierarchy: \n ", typeNameResult));
                while (!scanner.hasNextInt())
                    scanner.next();
                quantity = scanner.nextInt();
            }
            eachTypeQty[i]= quantity;
        }
    }
}
