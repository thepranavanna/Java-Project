import java.sql.*;
import java.util.Scanner;
public class LibraryBookPurchase3 {
public static void main(String[] args) {
Scanner scanner = new Scanner(System.in);
Connection connection = null;
PreparedStatement preparedStatement = null;
ResultSet resultSet = null;
String url = "jdbc:mysql://localhost:3306/library";
double simplePackingCharge = 20, giftPackingCharge = 50;
int userCount = 0;
try {
connection = DriverManager.getConnection(url);
while (userCount < 3) {
System.out.println("Available Books:");

String query = "SELECT book_id, book_name, book_price FROM books";
preparedStatement = connection.prepareStatement(query);
resultSet = preparedStatement.executeQuery();
while (resultSet.next()) {
int bookId = resultSet.getInt("book_id");
String bookName = resultSet.getString("book_name");
double bookPrice = resultSet.getDouble("book_price");
System.out.println(bookId + ". " + bookName + " (₹" + bookPrice + ")");
}
System.out.print("\nEnter book number to purchase/rent: ");
int choice = scanner.nextInt();
String bookName = "";
double bookPrice = 0.0;
query = "SELECT book_name, book_price FROM books WHERE book_id = ?";
preparedStatement = connection.prepareStatement(query);
preparedStatement.setInt(1, choice);
resultSet = preparedStatement.executeQuery();
if (resultSet.next()) {
bookName = resultSet.getString("book_name");
bookPrice = resultSet.getDouble("book_price");
} else {
System.out.println("Invalid choice. Try again.");
continue;
}
System.out.println("\n1. Purchase\n2. Rent");
int actionChoice = scanner.nextInt();
double totalCost = (actionChoice == 1) ? bookPrice : bookPrice * 0.10;
if (actionChoice == 1) {
System.out.println("Choose packing:\n1. Simple (₹" + simplePackingCharge +
")\n2. Gift (₹" + giftPackingCharge + ")");
int packingChoice = scanner.nextInt();
totalCost += (packingChoice == 1) ? simplePackingCharge : (packingChoice == 2)
? giftPackingCharge : 0;
}
scanner.nextLine();
System.out.print("Enter your name: ");
String userName = scanner.nextLine();
System.out.println("\n1. Cash\n2. Card");

int paymentMethod = scanner.nextInt();
String paymentMode = (paymentMethod == 1) ? "Cash" : (paymentMethod == 2) ?
"Card" : "Invalid";
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
} catch (SQLException e) {
e.printStackTrace();
} finally {
try {
if (resultSet != null) resultSet.close();
if (preparedStatement != null) preparedStatement.close();
if (connection != null) connection.close();
} catch (SQLException e) {
e.printStackTrace();
}
}
scanner.close();
}
}

