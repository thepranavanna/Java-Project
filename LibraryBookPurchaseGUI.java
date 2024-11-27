
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;
public class LibraryBookPurchaseGUI {
private JFrame frame;
private JComboBox<String> bookComboBox;
private JComboBox<String> actionComboBox;
private JComboBox<String> packingComboBox;
private JComboBox<String> paymentMethodComboBox;
private JTextField nameField;
private JTextField paymentField;
private JTextArea outputArea;
private Connection connection;
public static void main(String[] args) {
SwingUtilities.invokeLater(() -> {
try {
LibraryBookPurchaseGUI window = new LibraryBookPurchaseGUI();
window.frame.setVisible(true);
} catch (Exception e) {
e.printStackTrace();
}
});
}
public LibraryBookPurchaseGUI() {
initialize();
connectToDatabase();
}
private void initialize() {
frame = new JFrame();
frame.setBounds(100, 100, 600, 500);
frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
frame.getContentPane().setLayout(null);
JLabel lblTitle = new JLabel("Library Book Purchase System");
lblTitle.setFont(new Font("Tahoma", Font.BOLD, 18));
lblTitle.setBounds(160, 10, 280, 30);
frame.getContentPane().add(lblTitle);
JLabel lblBook = new JLabel("Select Book:");

lblBook.setBounds(30, 60, 100, 25);
frame.getContentPane().add(lblBook);
bookComboBox = new JComboBox<>();
bookComboBox.setBounds(140, 60, 200, 25);
frame.getContentPane().add(bookComboBox);
JLabel lblAction = new JLabel("Action (Purchase/Rent):");
lblAction.setBounds(30, 100, 150, 25);
frame.getContentPane().add(lblAction);
actionComboBox = new JComboBox<>(new String[]{"Purchase", "Rent"});
actionComboBox.setBounds(180, 100, 160, 25);
frame.getContentPane().add(actionComboBox);
JLabel lblPacking = new JLabel("Select Packing:");
lblPacking.setBounds(30, 140, 100, 25);
frame.getContentPane().add(lblPacking);
packingComboBox = new JComboBox<>(new String[]{"Simple (₹20)", "Gift (₹50)"});
packingComboBox.setBounds(140, 140, 200, 25);
frame.getContentPane().add(packingComboBox);
JLabel lblName = new JLabel("Enter Name:");
lblName.setBounds(30, 180, 100, 25);
frame.getContentPane().add(lblName);
nameField = new JTextField();
nameField.setBounds(140, 180, 200, 25);
frame.getContentPane().add(nameField);
JLabel lblPaymentMethod = new JLabel("Payment Method:");
lblPaymentMethod.setBounds(30, 220, 150, 25);
frame.getContentPane().add(lblPaymentMethod);
paymentMethodComboBox = new JComboBox<>(new String[]{"Cash", "Card"});
paymentMethodComboBox.setBounds(180, 220, 160, 25);
frame.getContentPane().add(paymentMethodComboBox);
JLabel lblPayment = new JLabel("Enter Payment (₹):");
lblPayment.setBounds(30, 260, 150, 25);
frame.getContentPane().add(lblPayment);
paymentField = new JTextField();
paymentField.setBounds(180, 260, 160, 25);
frame.getContentPane().add(paymentField);
JButton btnSubmit = new JButton("Submit");

btnSubmit.setBounds(180, 300, 100, 30);
btnSubmit.addActionListener(this::handleSubmit);
frame.getContentPane().add(btnSubmit);
outputArea = new JTextArea();
outputArea.setBounds(30, 340, 500, 100);
outputArea.setEditable(false);
frame.getContentPane().add(outputArea);
}
private void connectToDatabase() {
try {
String url = "jdbc:mysql://localhost:3306/library";
connection = DriverManager.getConnection(url);
loadBooks();
} catch (SQLException e) {
e.printStackTrace();
}
}
private void loadBooks() {
try {
String query = "SELECT book_name FROM books";
PreparedStatement preparedStatement = connection.prepareStatement(query);
ResultSet resultSet = preparedStatement.executeQuery();
while (resultSet.next()) {
bookComboBox.addItem(resultSet.getString("book_name"));
}
} catch (SQLException e) {
e.printStackTrace();
}
}
private void handleSubmit(ActionEvent event) {
String bookName = (String) bookComboBox.getSelectedItem();
String action = (String) actionComboBox.getSelectedItem();
double bookPrice = getBookPrice(bookName);
double totalCost = action.equals("Purchase") ? bookPrice : bookPrice * 0.10;
if (action.equals("Purchase")) {
String packing = (String) packingComboBox.getSelectedItem();
totalCost += packing.contains("Simple") ? 20 : 50;
}
String name = nameField.getText();
String paymentMethod = (String) paymentMethodComboBox.getSelectedItem();
double paymentAmount = Double.parseDouble(paymentField.getText());

if (paymentAmount >= totalCost) {
outputArea.setText(name + " has successfully " + action.toLowerCase() + "ed the book: "
+ bookName + "\nTotal cost: ₹" + totalCost);
if (paymentAmount > totalCost) {
outputArea.append("\nChange returned: ₹" + (paymentAmount - totalCost));
}
} else {
outputArea.setText("Insufficient payment. Transaction failed.");
}
}
private double getBookPrice(String bookName) {
double price = 0.0;
try {
String query = "SELECT book_price FROM books WHERE book_name = ?";
PreparedStatement preparedStatement = connection.prepareStatement(query);
preparedStatement.setString(1, bookName);
ResultSet resultSet = preparedStatement.executeQuery();
if (resultSet.next()) {
price = resultSet.getDouble("book_price");
}
} catch (SQLException e) {
e.printStackTrace();
}
return price;
}
}

