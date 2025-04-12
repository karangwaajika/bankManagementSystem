package database;

import java.util.HashMap;

public class Node {
    //{amount:200, type:saving,}
    HashMap<String, String> transaction;
    Node next;

    Node(HashMap<String, String> transaction) {
        this.transaction = transaction;
    }

}

