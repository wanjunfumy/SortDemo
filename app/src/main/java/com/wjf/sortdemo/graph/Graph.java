package com.wjf.sortdemo.graph;

import java.util.ArrayList;

class Graph {

    private ArrayList<String> vertax = null;
    private int[][] edges = null;
    private int numOfEdges;

    public Graph(int n) {
        edges = new int[n][n];
        vertax = new ArrayList<>(n);
        this.numOfEdges = 0;
    }

    public static void main(String[] args) {

    }
}
