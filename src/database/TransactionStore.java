package database;

import java.util.HashMap;

public class TransactionStore {
    private Node head;
    private int size = 0;

    boolean isEmpty() {
        return this.size == 0;
    }

    public void insertFirst(HashMap<String, String> transaction) { // insert at the beginning
        Node node = new Node(transaction);
        if (isEmpty()) {
            this.head = node;
        } else {
            node.next = this.head;
            this.head = node;
        }
        this.size++;
    }

    // display the last deposit transaction
    public HashMap<String, String> displayLastTransaction() {
        Node current = this.head;
        HashMap<String, String> lastDepositTransaction = null;
        boolean isDepositTransaction = false;
        while (current != null) {
            if (current.transaction.get("operationType").equals("deposit")) {
                isDepositTransaction = true;
                lastDepositTransaction = current.transaction;
                break;
            }
            current.transaction.get("operationType");
            current = current.next;
        }
        if (isDepositTransaction) {
            return lastDepositTransaction;
        }
        // in case there is no deposit transaction yet, return {status: false}
        HashMap<String, String> noDepositTransaction = new HashMap<>();
        noDepositTransaction.put("status", "false");
        return noDepositTransaction;
    }

    public boolean deleteTransaction(int index) {
        if (this.isEmpty()) {
            return false;
        }
        Node current = this.head;
        Node predecessor = this.head;
        int count = 1;
        while (count < index) {
            predecessor = current;
            current = current.next;
            count++;
        }
        predecessor.next = current.next;
        this.size--;
        return true;

    }

    public void displayTransactions() {
        Node current = this.head;

        while (current != null) {
            System.out.printf("|id: %s, date: %s, amount: %s, operationType: %s|\n", current.transaction.get("transId"),
                    current.transaction.get("date"), current.transaction.get("amount"),
                    current.transaction.get("operationType"));
            current = current.next;
        }

    }
}
