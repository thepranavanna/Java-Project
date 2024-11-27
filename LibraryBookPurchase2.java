import java.util.Scanner;
import java.util.Vector;

public class LibraryBookPurchase2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Vector<String> books = new Vector<>();
        books.add("1. Bible by Various Authors (Christian)");
        books.add("2. Bhagavad Gita by Vyasa (Hindu)");
        books.add("3. Quran by Various Authors (Muslim)");
        books.add("4. Guru Granth Sahib by Sikh Gurus (Sikh)");
        
        Vector<Double> prices = new Vector<>();
        prices.add(250.0);
        prices.add(200.0);
        prices.add(300.0);
        prices.add(350.0);
        
        double simplePackingCharge = 20, giftPackingCharge = 50;
        int userCount = 0;

        while (userCount < 3) {
            System.out.println("Available Books:");
            for (String book : books) {
                System.out.println(book);
            }

            System.out.print("\nEnter book number to purchase/rent: ");
            int choice = scanner.nextInt();
            if (choice < 1 || choice > books.size()) {
                System.out.println("Invalid choice. Try again.");
                continue;
            }

            System.out.println("\n1. Purchase\n2. Rent");
            int actionChoice = scanner.nextInt();
            double totalCost = (actionChoice == 1) ? prices.get(choice - 1) : prices.get(choice - 1) * 0.10;

            if (actionChoice == 1) {
                System.out.println("Choose packing:\n1. Simple (₹" + simplePackingCharge + ")\n2. Gift (₹" + giftPackingCharge + ")");
                int packingChoice = scanner.nextInt();
                totalCost += (packingChoice == 1) ? simplePackingCharge : (packingChoice == 2) ? giftPackingCharge : 0;
            }

            scanner.nextLine();
            System.out.print("Enter your name: ");
            String userName = scanner.nextLine();

            System.out.println("\n1. Cash\n2. Card");
            int paymentMethod = scanner.nextInt();
            String paymentMode = (paymentMethod == 1) ? "Cash" : (paymentMethod == 2) ? "Card" : "Invalid";

            if (paymentMode.equals("Invalid")) {
                System.out.println("Invalid payment method. Try again.");
                continue;
            }

            System.out.print("Enter payment amount: ₹");
            double payment = scanner.nextDouble();

            if (payment >= totalCost) {
                System.out.println((actionChoice == 1 ? "Purchase" : "Rental") + " successful!");
                if (payment > totalCost) {
                    System.out.println("Change returned: ₹" + (payment - totalCost));
                }
                userCount++;
            } else {
                System.out.println("Insufficient payment. Transaction failed.");
            }

            if (userCount < 3) {
                System.out.println("\nWould you like to make another transaction? (y/n)");
                if (!scanner.next().equalsIgnoreCase("y")) break;
            }
        }
        scanner.close();
    }
}
