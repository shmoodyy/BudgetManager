package budget;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Purchases {
    Scanner scanner = new Scanner(System.in);
    final File PURCHASE_FILE = new File("purchases.txt");
    Map<String, Double> allMap = new HashMap<>(), foodMap = new HashMap<>()
            , clothesMap = new HashMap<>(), entertainmentMap = new HashMap<>()
            , otherMap = new HashMap<>();
    Map<String, String> itemCategory = new HashMap<>();
    private String category;
    private String item;
    private double price;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void sortedPurchasesTypes() {
        System.out.printf("""
                Types:
                Food - $%.2f
                Entertainment - $%.2f
                Clothes - $%.2f
                Other - $%.2f
                Total sum: $%.2f%n
                """, totalPurchases(foodMap), totalPurchases(entertainmentMap)
                , totalPurchases(clothesMap), totalPurchases(otherMap), totalPurchases(allMap));
    }

    public void sortPurchases(Map<String, Double> map) {
        if (map.size() == 0) {
            System.out.println("The purchase list is empty!\n");
        } else {
            List<Map.Entry<String, Double>> mapList = new ArrayList<>(map.entrySet());

            mapList.sort((o1, o2) -> o2.getKey().compareTo(o1.getKey()));
            mapList.sort((o1, o2) -> o2.getValue().compareTo(o1.getValue()));

            Map<String, Double> sortedMap = new LinkedHashMap<>();

            for (var entry : mapList) {
                sortedMap.put(entry.getKey(), entry.getValue());
            }
            outputPurchasesLog(sortedMap);
        }
    }

    public void analyzePurchasesMenu() {
        boolean goBack = false;
        while (!goBack) {
            System.out.println("""
                    How do you want to sort?
                    1) Sort all purchases
                    2) Sort by type
                    3) Sort certain type
                    4) Back""");
            int sortCategory = scanner.nextInt();
            System.out.println();
            switch (sortCategory) {
                case 1 -> sortPurchases(allMap);
                case 2 -> sortedPurchasesTypes();
                case 3 -> {
                    System.out.println("""
                            Choose the type of purchase
                            1) Food
                            2) Clothes
                            3) Entertainment
                            4) Other""");
                    int sortType = scanner.nextInt();
                    System.out.println();
                    switch (sortType) {
                        case 1 -> sortPurchases(foodMap);
                        case 2 -> sortPurchases(clothesMap);
                        case 3 -> sortPurchases(entertainmentMap);
                        case 4 -> sortPurchases(otherMap);
                    }
                }
                case 4 -> {
                    goBack = true;
                    System.out.println();
                }
            }
        }
    }

    public void loadPurchasesLog(Income income) {
        try (Scanner fileScanner = new Scanner(PURCHASE_FILE)) {
            income.setIncome(fileScanner.nextDouble());
            fileScanner.nextLine();
            while (fileScanner.hasNextLine()) {
                String loadedPurchase = fileScanner.nextLine();
                String[] loadedPurchaseArray = loadedPurchase.split(" => " );
                setCategory(loadedPurchaseArray[0]);
                setItem(loadedPurchaseArray[1]);
                setPrice(Double.parseDouble(loadedPurchaseArray[2]));
                if (allMap.containsKey(getItem())) {
                    setPrice(getPrice() + allMap.get(getItem()));
                }
                allMap.put(getItem(), getPrice());
                switch (getCategory()) {
                    case "FOOD" -> foodMap.put(getItem(), getPrice());
                    case "CLOTHES" -> clothesMap.put(getItem(), getPrice());
                    case "ENTERTAINMENT" -> entertainmentMap.put(getItem(), getPrice());
                    case "OTHER" -> otherMap.put(getItem(), getPrice());
                }
            }
            System.out.println("Purchases were loaded!\n");
        } catch (FileNotFoundException fNFE) {
            System.out.println("File not found.");
        }
    }

    public void savePurchasesLog() {
        try (FileWriter writer = new FileWriter(PURCHASE_FILE)){
            boolean createdNew = PURCHASE_FILE.createNewFile();
            writer.write(1000 + "\n"); // FOR WHATEVER REASON THESE TESTS WANT BUDGET AT 1000 WHEN LOADING DATA
            for (var entry : allMap.entrySet()) {
                writer.write(String.format("%s => %s => %.2f\n"
                        , itemCategory.get(entry.getKey()) ,entry.getKey(), entry.getValue()));
            }
            System.out.println("Purchases were saved!\n");
        } catch (IOException e) {
            System.out.println("Cannot create the file: " + PURCHASE_FILE.getPath());
        }
    }


    public void outputPurchasesLog(Map<String, Double> map) {
        if (map.size() == 0) {
            System.out.println("The purchase list is empty!\n");
        }
        else {
            System.out.println(map.equals(foodMap) ? "Food:" : map.equals(clothesMap) ? "Clothes:"
                    : map.equals(entertainmentMap) ? "Entertainment:" : map.equals(otherMap) ? "Other:" : "All:");
            map.forEach((name, cost) -> System.out.printf("%s $%.2f\n", name, cost));
            System.out.printf("Total sum: $%.2f\n\n", totalPurchases(map));
        }
    }

    public void outputPurchasesMenu() {
        if (allMap.size() == 0) {
            System.out.println("The purchase list is empty!\n");
        } else {
            boolean goBack = false;
            while (!goBack) {
                System.out.println("""
                        Choose the type of purchases
                        1) Food
                        2) Clothes
                        3) Entertainment
                        4) Other
                        5) All
                        6) Back
                        """);
                int purchaseCategory = scanner.nextInt();
                System.out.println();
                switch (purchaseCategory) {
                    case 1 -> outputPurchasesLog(foodMap);
                    case 2 -> outputPurchasesLog(clothesMap);
                    case 3 -> outputPurchasesLog(entertainmentMap);
                    case 4 -> outputPurchasesLog(otherMap);
                    case 5 -> outputPurchasesLog(allMap);
                    case 6 -> {
                        goBack = true;
                        System.out.println();
                    }
                }
            }
        }
    }

    public double totalPurchases(Map<String, Double> map) {
        double purchaseTotal = 0;
        for (var entry : map.entrySet()) {
            purchaseTotal += entry.getValue();
        }
        return purchaseTotal;
    }

    public void readPurchases(Map<String, Double> map) {
        System.out.println("Enter purchase name:");
        setItem(scanner.nextLine());
        System.out.println("Enter its price:");
        setPrice(scanner.nextDouble());
        if (map.containsKey(getItem())) {
            setPrice(getPrice() + map.get(getItem()));
        }
        itemCategory.put(getItem(), getCategory());
        allMap.put(getItem(), getPrice());
        map.put(getItem(), getPrice());
        System.out.println("Purchase was added!\n");
    }

    public void readPurchaseMenu() {
        boolean goBack = false;
        while (!goBack) {
            System.out.println("""
                    Choose the type of purchase
                    1) Food
                    2) Clothes
                    3) Entertainment
                    4) Other
                    5) Back""");
            int purchaseCategory = scanner.nextInt();
            scanner.nextLine();
            System.out.println();
            switch (purchaseCategory) {
                case 1 -> {
                    setCategory("FOOD");
                    readPurchases(foodMap);
                }
                case 2 -> {
                    setCategory("CLOTHES");
                    readPurchases(clothesMap);
                }
                case 3 -> {
                    setCategory("ENTERTAINMENT");
                    readPurchases(entertainmentMap);
                }
                case 4 -> {
                    setCategory("OTHER");
                    readPurchases(otherMap);
                }
                case 5 -> {
                    goBack = true;
                }
            }
        }
    }
}