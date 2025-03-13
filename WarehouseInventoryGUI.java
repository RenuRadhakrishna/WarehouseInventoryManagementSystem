import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.LinkedList;

public class WarehouseInventoryGUI {
    private JFrame frame;
    private JTable table;
    private DefaultTableModel model;
    private JTextField nameField, quantityField, priceField;
    private JTextArea expiryListArea;
    private HashMap<String, Integer> inventory;
    private LinkedList<String> expiryQueue;

    public WarehouseInventoryGUI() {
        inventory = new HashMap<>();
        expiryQueue = new LinkedList<>();
        frame = new JFrame("Warehouse Inventory System");
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Table Setup
        model = new DefaultTableModel(new String[]{"Item Name", "Quantity", "Price"}, 0);
        table = new JTable(model);
        JScrollPane tablePane = new JScrollPane(table);

        // Input Fields
        JPanel inputPanel = new JPanel(new GridLayout(2, 3));
        nameField = new JTextField();
        quantityField = new JTextField();
        priceField = new JTextField();
        inputPanel.add(new JLabel("Item Name:"));
        inputPanel.add(nameField);
        inputPanel.add(new JLabel("Quantity:"));
        inputPanel.add(quantityField);
        inputPanel.add(new JLabel("Price:"));
        inputPanel.add(priceField);

        // Buttons
        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Add Item");
        JButton removeButton = new JButton("Remove Expired Item");
        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);

        // Expiry List
        expiryListArea = new JTextArea(5, 20);
        JScrollPane expiryPane = new JScrollPane(expiryListArea);
        expiryListArea.setEditable(false);

        // Adding components to frame
        frame.add(tablePane, BorderLayout.CENTER);
        frame.add(inputPanel, BorderLayout.NORTH);
        frame.add(buttonPanel, BorderLayout.SOUTH);
        frame.add(expiryPane, BorderLayout.EAST);

        // Button Actions
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                int quantity = Integer.parseInt(quantityField.getText());
                int price = Integer.parseInt(priceField.getText());

                if (!name.isEmpty()) {
                    inventory.put(name, quantity);
                    expiryQueue.add(name);
                    model.addRow(new Object[]{name, quantity, price});
                    expiryListArea.append(name + "\n");
                }
            }
        });

        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!expiryQueue.isEmpty()) {
                    String expiredItem = expiryQueue.poll();
                    inventory.remove(expiredItem);
                    expiryListArea.setText(expiryListArea.getText().replace(expiredItem + "\n", ""));
                    for (int i = 0; i < model.getRowCount(); i++) {
                        if (model.getValueAt(i, 0).equals(expiredItem)) {
                            model.removeRow(i);
                            break;
                        }
                    }
                }
            }
        });

        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new WarehouseInventoryGUI();
    }
}
