package budget;

public class Main {
    public static void main(String[] args) {
        Purchases purchases = new Purchases();
        Income income = new Income();
        new Accountant(purchases, income);
    }
}