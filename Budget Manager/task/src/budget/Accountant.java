package budget;

import java.util.Scanner;

public class Accountant {
    Scanner scanner = new Scanner(System.in);
    static boolean auditOver = false;
    private final Purchases myPurchases;
    private final Income myIncome;

    public Accountant(Purchases myPurchases, Income myIncome) {
        this.myPurchases = myPurchases;
        this.myIncome = myIncome;
        while (!auditOver) {
            mainMenu();
        }
        System.out.println("Bye!");
    }

    public Purchases getMyPurchases() {
        return myPurchases;
    }

    public Income getMyIncome() {
        return myIncome;
    }

    public void calculateBalance() {
        double balance = getMyIncome().getIncome() - getMyPurchases().totalPurchases(getMyPurchases().allMap);
        System.out.printf("Balance: $%.2f\n\n", balance);
    }

    public void mainMenu() {
        System.out.print("""
                Choose your action:
                1) Add income
                2) Add purchase
                3) Show list of purchases
                4) Balance
                5) Save
                6) Load
                7) Analyze (Sort)
                0) Exit
                """);
        int option = scanner.nextInt();
        System.out.println();
        switch (option) {
            case 1 -> getMyIncome().readIncome();
            case 2 -> getMyPurchases().readPurchaseMenu();
            case 3 -> getMyPurchases().outputPurchasesMenu();
            case 4 -> calculateBalance();
            case 5 -> getMyPurchases().savePurchasesLog();
            case 6 -> getMyPurchases().loadPurchasesLog(getMyIncome());
            case 7 -> getMyPurchases().analyzePurchasesMenu();
            case 0 -> auditOver = true;
        }
    }
}
