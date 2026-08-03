package javapro;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.FileWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FinanceTracker extends JFrame {

    JTextField incomeField, amountField;

    JLabel incomeCard, expenseCard, balanceCard, statusLabel;

    JButton foodButton, travelButton, movieButton;
    JButton shoppingButton, rechargeButton, billsButton;

    JButton calculateButton, deleteButton;
    JButton downloadButton, clearButton;

    JTable table;
    DefaultTableModel model;

    int totalExpense = 0;
    int income = 0;

    public FinanceTracker() {

        setTitle("Smart Personal Finance Management System");
        setSize(1100, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(new Color(20, 20, 30));

        // ===== HEADER =====
        JLabel title = new JLabel(
                "Smart Personal Finance Management System",
                JLabel.CENTER);

        title.setFont(new Font("Arial", Font.BOLD, 28));
        title.setForeground(Color.WHITE);
        title.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));

        mainPanel.add(title, BorderLayout.NORTH);

        // ===== LEFT PANEL =====
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new GridLayout(12, 1, 10, 10));
        leftPanel.setBackground(new Color(35, 35, 50));
        leftPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel incomeLabel = new JLabel("Monthly Income");
        incomeLabel.setForeground(Color.WHITE);

        incomeField = new JTextField();

        JLabel amountLabel = new JLabel("Expense Amount");
        amountLabel.setForeground(Color.WHITE);

        amountField = new JTextField();

        // Category Buttons
        foodButton = createButton("🍔 Food", new Color(255, 140, 0));
        travelButton = createButton("✈ Travel", new Color(52, 152, 219));
        movieButton = createButton("🎬 Movie", new Color(155, 89, 182));

        shoppingButton = createButton("🛒 Shopping",
                new Color(46, 204, 113));

        rechargeButton = createButton("📱 Recharge",
                new Color(241, 196, 15));

        billsButton = createButton("💡 Bills",
                new Color(231, 76, 60));

        calculateButton = createButton("Calculate Balance",
                new Color(0, 153, 76));

        deleteButton = createButton("Delete Expense",
                new Color(204, 0, 0));

        downloadButton = createButton("Download CSV",
                new Color(255, 140, 0));

        clearButton = createButton("Clear All",
                new Color(128, 128, 128));

        leftPanel.add(incomeLabel);
        leftPanel.add(incomeField);

        leftPanel.add(amountLabel);
        leftPanel.add(amountField);

        leftPanel.add(foodButton);
        leftPanel.add(travelButton);
        leftPanel.add(movieButton);
        leftPanel.add(shoppingButton);
        leftPanel.add(rechargeButton);
        leftPanel.add(billsButton);

        leftPanel.add(calculateButton);

        mainPanel.add(leftPanel, BorderLayout.WEST);

        // ===== CENTER PANEL =====
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BorderLayout());
        centerPanel.setBackground(new Color(20, 20, 30));

        // ===== CARDS =====
        JPanel cardPanel = new JPanel();
        cardPanel.setLayout(new GridLayout(1, 3, 15, 15));
        cardPanel.setBackground(new Color(20, 20, 30));
        cardPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        incomeCard = createCard("Income: ₹0");
        expenseCard = createCard("Expense: ₹0");
        balanceCard = createCard("Balance: ₹0");

        cardPanel.add(incomeCard);
        cardPanel.add(expenseCard);
        cardPanel.add(balanceCard);

        centerPanel.add(cardPanel, BorderLayout.NORTH);

        // ===== TABLE =====
        model = new DefaultTableModel();

        model.addColumn("Date & Time");
        model.addColumn("Amount");
        model.addColumn("Category");

        table = new JTable(model);

        table.setRowHeight(30);
        table.setFont(new Font("Arial", Font.PLAIN, 15));

        table.getTableHeader().setFont(
                new Font("Arial", Font.BOLD, 16));

        JScrollPane pane = new JScrollPane(table);

        centerPanel.add(pane, BorderLayout.CENTER);

        // ===== BOTTOM PANEL =====
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(new Color(20, 20, 30));

        statusLabel = new JLabel("Welcome!");
        statusLabel.setForeground(Color.WHITE);
        statusLabel.setFont(new Font("Arial", Font.BOLD, 18));

        bottomPanel.add(deleteButton);
        bottomPanel.add(downloadButton);
        bottomPanel.add(clearButton);
        bottomPanel.add(statusLabel);

        centerPanel.add(bottomPanel, BorderLayout.SOUTH);

        mainPanel.add(centerPanel, BorderLayout.CENTER);

        add(mainPanel);

        // ===== BUTTON FUNCTIONS =====
        addExpense(foodButton, "Food");
        addExpense(travelButton, "Travel");
        addExpense(movieButton, "Movie");
        addExpense(shoppingButton, "Shopping");
        addExpense(rechargeButton, "Recharge");
        addExpense(billsButton, "Bills");

        // ===== CALCULATE =====
        calculateButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                if(incomeField.getText().isEmpty()) {

                    JOptionPane.showMessageDialog(null,
                            "Enter Monthly Income");
                }
                else {

                    income = Integer.parseInt(
                            incomeField.getText());

                    int balance = income - totalExpense;

                    incomeCard.setText("Income: ₹" + income);

                    expenseCard.setText(
                            "Expense: ₹" + totalExpense);

                    balanceCard.setText(
                            "Balance: ₹" + balance);

                    if(balance < 0) {

                        statusLabel.setText(
                                "⚠ Budget Exceeded!");

                        statusLabel.setForeground(Color.RED);
                    }
                    else {

                        statusLabel.setText(
                                "✅ You Saved ₹" + balance);

                        statusLabel.setForeground(Color.GREEN);
                    }
                }
            }
        });

        // ===== DELETE BUTTON =====
        deleteButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                int row = table.getSelectedRow();

                if(row != -1) {

                    int amount = Integer.parseInt(
                            model.getValueAt(row, 1).toString());

                    totalExpense -= amount;

                    model.removeRow(row);

                    JOptionPane.showMessageDialog(null,
                            "Expense Deleted");
                }
                else {

                    JOptionPane.showMessageDialog(null,
                            "Select Row First");
                }
            }
        });

        // ===== DOWNLOAD CSV =====
        downloadButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                try {

                    FileWriter writer =
                            new FileWriter("ExpenseHistory.csv");

                    writer.write("Date,Amount,Category\n");

                    for(int i = 0; i < model.getRowCount(); i++) {

                        writer.write(
                                model.getValueAt(i, 0).toString()
                                + "," +
                                model.getValueAt(i, 1).toString()
                                + "," +
                                model.getValueAt(i, 2).toString()
                                + "\n");
                    }

                    writer.close();

                    JOptionPane.showMessageDialog(null,
                            "CSV Downloaded Successfully");
                }
                catch(Exception ex) {

                    JOptionPane.showMessageDialog(null,
                            "Error Downloading CSV");
                }
            }
        });

        // ===== CLEAR BUTTON =====
        clearButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                model.setRowCount(0);

                totalExpense = 0;

                incomeCard.setText("Income: ₹0");
                expenseCard.setText("Expense: ₹0");
                balanceCard.setText("Balance: ₹0");

                incomeField.setText("");
                amountField.setText("");

                statusLabel.setText("Data Cleared");
            }
        });

        setVisible(true);
    }

    // ===== CREATE BUTTON =====
    public JButton createButton(String text, Color color) {

        JButton button = new JButton(text);

        button.setFont(new Font("Arial", Font.BOLD, 15));

        button.setBackground(color);

        button.setForeground(Color.WHITE);

        button.setFocusPainted(false);

        return button;
    }

    // ===== CREATE CARD =====
    public JLabel createCard(String text) {

        JLabel label = new JLabel(text, JLabel.CENTER);

        label.setOpaque(true);

        label.setBackground(new Color(45, 45, 60));

        label.setForeground(Color.WHITE);

        label.setFont(new Font("Arial", Font.BOLD, 20));

        label.setBorder(
                BorderFactory.createEmptyBorder(25, 10, 25, 10));

        return label;
    }

    // ===== ADD EXPENSE =====
    public void addExpense(JButton button, String category) {

        button.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                if(amountField.getText().isEmpty()) {

                    JOptionPane.showMessageDialog(null,
                            "Enter Expense Amount");
                }
                else {

                    int amount = Integer.parseInt(
                            amountField.getText());

                    totalExpense += amount;

                    DateTimeFormatter format =
                            DateTimeFormatter.ofPattern(
                                    "dd-MM-yyyy HH:mm");

                    String date =
                            LocalDateTime.now().format(format);

                    model.addRow(new Object[]{
                            date,
                            amount,
                            category
                    });

                    amountField.setText("");

                    JOptionPane.showMessageDialog(null,
                            category + " Expense Added");
                }
            }
        });
    }

    public static void main(String[] args) {

        new FinanceTracker();
    }
}