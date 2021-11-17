package com.consumer;

/**
 * @Author: zhaoshuai
 * @Date: 2021/9/1
 */
public class SimpleTest {
    public static class ListNode{
        public ListNode(int n) {
            this.value = n;
        }
        ListNode next;
        int value;
    }

    public static void main(String[] args) {
        // 1. 2 .3 .4 .5
        ListNode listNode = buildListNode(5);
        printList(listNode);
        ListNode rootNode = new ListNode(0);
        rootNode.next = listNode;
        ListNode firstNode = rootNode;
        int n = 2;
        for (int i = 0; i < n; i++) {
            firstNode = firstNode.next;
        }
        System.out.println("firstNode.value=>" + firstNode.value);
        ListNode secondNode = rootNode;
        while (firstNode.next != null) {
            firstNode = firstNode.next;
            secondNode = secondNode.next;
        }
        System.out.println("secondNode.value=>" + secondNode.value);

    }

    public static ListNode buildListNode(int len) {
        ListNode listNode = new ListNode(0);
        ListNode currentNode = listNode;
        for (int i = 0; i < len; i++) {
            currentNode.next = new ListNode(i+1);
            currentNode = currentNode.next;
        }
        return listNode.next;
    }
    public static void printList(ListNode node) {
        while (node != null) {
            System.out.println(node.value);
            node = node.next;
        }

    }
}
