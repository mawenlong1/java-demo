package com.mwl.lru;

import java.util.Hashtable;

/**
 * @author mawenlong
 * @date 2019/04/26
 */
public class LRUCache {
    private Hashtable<String, DLinkedNode> cache = new Hashtable<>();
    private int count;
    private int capacity;
    private DLinkedNode head, tail;


    public LRUCache() {
        this(10);
    }

    public LRUCache(int capacity) {
        count = 0;
        this.capacity = capacity;
        head = new DLinkedNode();
        head.pre = null;

        tail = new DLinkedNode();
        tail.next = null;

        head.next = tail;
        tail.pre = head;
    }

    public int get(String key) {
        DLinkedNode node = cache.get(key);
        if (node == null) {
            return -1;
        }
        moveToHead(node);
        return node.value;
    }

    private void moveToHead(DLinkedNode node) {
        if (head.next == node) {
            return;
        }
        removeNode(node);
        addNodeToHead(node);
    }

    private void removeNode(DLinkedNode node) {
        DLinkedNode pre = node.pre;
        DLinkedNode next = node.next;

        pre.next = next;
        next.pre = pre;
    }

    private void addNodeToHead(DLinkedNode node) {
        node.pre = head;
        node.next = head.next;

        head.next.pre = node;
        head.next = node;
    }

    public void set(String key, int value) {
        DLinkedNode node = cache.get(key);

        if (node == null) {
            DLinkedNode node1 = new DLinkedNode();
            node1.key = key;
            node1.value = value;

            cache.put(key, node);
            addNodeToHead(node1);

            count++;
            if (count > capacity) {
                DLinkedNode tail = popTail();
                cache.remove(tail.key);
                count--;
            }
        } else {
            node.value = value;
            moveToHead(node);
        }
    }

    private DLinkedNode popTail() {
        DLinkedNode res = tail.pre;
        removeNode(res);
        return res;
    }
}
