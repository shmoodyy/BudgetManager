package budget;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Income {
    Scanner scanner = new Scanner(System.in);
    List<Double> incomeList = new ArrayList<>();
    private double income;
    public double getIncome() {
        return income;
    }

    public void setIncome(double income) {
        this.income = income;
    }

    public void readIncome() {
        System.out.println("Enter income:");
        double incomeInput = scanner.nextDouble();
        setIncome(incomeInput);
        incomeList.add(getIncome());
        System.out.println("Income was added!\n");
    }
}