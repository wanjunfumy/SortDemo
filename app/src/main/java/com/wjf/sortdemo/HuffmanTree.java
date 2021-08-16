package com.wjf.sortdemo;

import java.util.ArrayList;
import java.util.Collections;

/**
 * 哈夫曼树
 */
class HuffmanTree {

    public static void main(String[] args) {
        int[] arr = {13, 7, 8, 3, 29, 6, 1};
        Node root = createHuffmanTree(arr);
        preOrder(root);
    }

    public static void preOrder(Node root) {
        if (root != null) {
            root.preOrder();
        }
    }

    public static Node createHuffmanTree(int[] arr) {
        // 遍历arr数组,将每个元素构建成一个Node，放到ArrayList
        ArrayList<Node> nodes = new ArrayList<>(arr.length);
        for (int i = 0; i < arr.length; i++) {
            nodes.add(new Node(arr[i]));
        }
        while (nodes.size() > 1) {
            Collections.sort(nodes);

            Node left = nodes.get(0);
            Node right = nodes.get(1);

            Node parent = new Node(left.getValue() + right.getValue());
            parent.setLeft(left);
            parent.setRight(right);
            // 删除
            nodes.remove(left);nodes.remove(right);
            // 加入parent
            nodes.add(parent);
        }
        return nodes.get(0);
    }
}

class Node implements Comparable<Node> {
    private int value; // 节点权值

    private Node left;
    private Node right;

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void preOrder() {
        System.out.println(this);
        if (this.left!= null) {
            this.left.preOrder();
        }
        if (this.right != null) {
            this.right.preOrder();
        }

    }

    public Node getLeft() {
        return left;
    }

    public void setLeft(Node left) {
        this.left = left;
    }

    public Node getRight() {
        return right;
    }

    public void setRight(Node right) {
        this.right = right;
    }

    public Node(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Node{" +
                "value=" + value +
                '}';
    }

    @Override
    public int compareTo(Node o) {
        return this.value - o.value;
    }
}
