import java.util.Scanner;

public class LibraryBookPurchase {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] books = {
            "1. Bible by Various Authors (Christian)",
            "2. Bhagavad Gita by Vyasa (Hindu)",
            "3. Quran by Various Authors (Muslim)",
            "4. Guru Granth Sahib by Sikh Gurus (Sikh)"
        };
        double[] prices = {250, 200, 300, 350};
        double simplePackingCharge = 20, giftPackingCharge = 50;
        int userCount = 0;

        while (userCount < 3) {
            System.out.println("Available Books:");
            for (String book : books) {
                System.out.println(book);
            }

            System.out.print("\nEnter book number to purchase/rent: ");
            int choice = scanner.nextInt();

            if (choice < 1 || choice > books.length) {
                System.out.println("Invalid choice. Try again.");
                continue;
            }

            System.out.println("\n1. Purchase\n2. Rent");
            int actionChoice = scanner.nextInt();
            double totalCost = (actionChoice == 1) ? prices[choice - 1] : prices[choice - 1] * 0.10;

            if (actionChoice == 1) {
                System.out.println("Choose packing:\n1. Simple (₹" + simplePackingCharge + ")\n2. Gift (₹" + giftPackingCharge + ")");
                int packingChoice = scanner.nextInt();
                totalCost += (packingChoice == 1) ? simplePackingCharge : (packingChoice == 2) ? giftPackingCharge : 0;
            }

            scanner.nextLine(); // Consume newline character after nextInt()

            System.out.print("Enter your name: ");
            String userName = scanner.nextLine();  // Read user's name

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
