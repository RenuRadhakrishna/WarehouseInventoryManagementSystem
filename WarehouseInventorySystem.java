import java.util.*;

// Node for AVL Tree
class ItemNode {
    String itemName;
    int quantity;
    int price;
    int height;
    ItemNode left, right;

    public ItemNode(String itemName, int quantity, int price) {
        this.itemName = itemName;
        this.quantity = quantity;
        this.price = price;
        this.height = 1;
        this.left = this.right = null;
    }
}

// AVL Tree for storing items
class AVLTree {
    private ItemNode root;

    private int height(ItemNode N) {
        return (N == null) ? 0 : N.height;
    }

    private int getBalance(ItemNode N) {
        return (N == null) ? 0 : height(N.left) - height(N.right);
    }

    private ItemNode rightRotate(ItemNode y) {
        ItemNode x = y.left;
        ItemNode T2 = x.right;
        x.right = y;
        y.left = T2;
        y.height = Math.max(height(y.left), height(y.right)) + 1;
        x.height = Math.max(height(x.left), height(x.right)) + 1;
        return x;
    }

    private ItemNode leftRotate(ItemNode x) {
        ItemNode y = x.right;
        ItemNode T2 = y.left;
        y.left = x;
        x.right = T2;
        x.height = Math.max(height(x.left), height(x.right)) + 1;
        y.height = Math.max(height(y.left), height(y.right)) + 1;
        return y;
    }

    public ItemNode insert(ItemNode node, String itemName, int quantity, int price) {
        if (node == null) return new ItemNode(itemName, quantity, price);

        if (itemName.compareTo(node.itemName) < 0)
            node.left = insert(node.left, itemName, quantity, price);
        else if (itemName.compareTo(node.itemName) > 0)
            node.right = insert(node.right, itemName, quantity, price);
        else
            return node;

        node.height = 1 + Math.max(height(node.left), height(node.right));
        int balance = getBalance(node);

        if (balance > 1 && itemName.compareTo(node.left.itemName) < 0)
            return rightRotate(node);
        if (balance < -1 && itemName.compareTo(node.right.itemName) > 0)
            return leftRotate(node);
        if (balance > 1 && itemName.compareTo(node.left.itemName) > 0) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }
        if (balance < -1 && itemName.compareTo(node.right.itemName) < 0) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }
        return node;
    }

    public void insertItem(String itemName, int quantity, int price) {
        root = insert(root, itemName, quantity, price);
    }

    public void inorderTraversal(ItemNode node) {
        if (node != null) {
            inorderTraversal(node.left);
            System.out.println(node.itemName + " | Quantity: " + node.quantity + " | Price: " + node.price);
            inorderTraversal(node.right);
        }
    }

    public void displayInventory() {
        inorderTraversal(root);
    }
}

// Hash Table for quick lookup
class ItemHashTable {
    private HashMap<String, Integer> itemMap = new HashMap<>();

    public void addItem(String itemName, int quantity) {
        itemMap.put(itemName, quantity);
    }

    public int getItemQuantity(String itemName) {
        return itemMap.getOrDefault(itemName, 0);
    }
}

// FIFO Linked List for Expiry Tracking
class ExpiryQueue {
    private LinkedList<String> expiryList = new LinkedList<>();

    public void addItem(String itemName) {
        expiryList.addLast(itemName);
    }

    public String removeExpiredItem() {
        return expiryList.isEmpty() ? "No expired items" : expiryList.removeFirst();
    }
}

// Main Inventory System
public class WarehouseInventorySystem {
    public static void main(String[] args) {
        AVLTree inventory = new AVLTree();
        ItemHashTable lookupTable = new ItemHashTable();
        ExpiryQueue expiryQueue = new ExpiryQueue();

        inventory.insertItem("Apples", 50, 10);
        inventory.insertItem("Bananas", 30, 5);
        inventory.insertItem("Oranges", 40, 7);

        lookupTable.addItem("Apples", 50);
        lookupTable.addItem("Bananas", 30);
        lookupTable.addItem("Oranges", 40);

        expiryQueue.addItem("Apples");
        expiryQueue.addItem("Bananas");

        System.out.println("\nCurrent Inventory:");
        inventory.displayInventory();

        System.out.println("\nChecking Quantity of Bananas: " + lookupTable.getItemQuantity("Bananas"));

        System.out.println("\nRemoving Expired Item: " + expiryQueue.removeExpiredItem());
    }
}
