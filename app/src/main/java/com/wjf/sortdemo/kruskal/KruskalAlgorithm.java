package com.wjf.sortdemo.kruskal;

import java.util.Arrays;

/**
 * 求加权连通图的最小生成树的算法
 */
class KruskalAlgorithm {

    private int edgeNum; // 边的数量
    private char[] vertexs; // 顶点的数组
    private int[][] matrix; // 邻接矩阵
    private static final int INF = Integer.MAX_VALUE;

    public KruskalAlgorithm(char[] vertexs, int[][] matrix) {
        int vlen = vertexs.length;
        this.vertexs = new char[vlen];
        this.matrix = new int[vlen][vlen];
        // copy
        for (int i = 0; i < vlen; i++) {
            this.vertexs[i] = vertexs[i];
        }
        for (int i = 0; i < vlen; i++) {
            for (int j = 0; j < vlen; j++) {
                this.matrix[i][j] = matrix[i][j];
            }
        }

        for (int i = 0; i < vlen; i++) {
            for (int j = i + 1; j < vlen; j++) {
                if (this.matrix[i][j] != INF) {
                    edgeNum++;
                }
            }
        }
    }

    private void print() {
        System.out.println("邻接矩阵：");
        for (int i = 0; i < vertexs.length; i++) {
            for (int j = 0; j < vertexs.length; j++) {
                System.out.printf("%12d", matrix[i][j]);
            }
            System.out.println();
        }
    }

    public void kruskal() {
        int index = 0; // 表示最后结果数组的索引
        int[] ends = new int[edgeNum];// 用于保存已有最小生产树中的每个顶点在最小生成树的终点
        EData[] rets = new EData[edgeNum];

        EData[] edges = getEdges();
        sortEdges(edges);
        for (int i = 0; i < edgeNum; i++) {
            int p1 = getPosition(edges[i].start);
            int p2 = getPosition(edges[i].end);

            int m = getEnd(ends, p1);
            int n = getEnd(ends, p2);

            if (m != n) { // 没有构成回路
                ends[m] = n;
                rets[index++] = edges[i];
            }
        }
        System.out.println("最小生成树：" + Arrays.toString(rets));
    }

    public static void main(String[] args) {
        char[] data = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G'};
        int[][] matrix = {
                {0, 12, INF, INF, INF, 16, 14},
                {12, 0, 10, INF, INF, 7, INF},
                {INF, 10, 0, 3, 5, 6, INF},
                {INF, INF, 3, 0, 4, INF, INF},
                {INF, INF, 5, 4, 0, 2, 8},
                {16, 7, 6, INF, 2, 0, 9},
                {14, INF, INF, INF, 8, 9, 0},
        };
        KruskalAlgorithm kruskalAlgorithm =
                new KruskalAlgorithm(data, matrix);
        kruskalAlgorithm.print();
        EData[] edges = kruskalAlgorithm.getEdges();
        System.out.println("排序前：" + Arrays.toString(edges));
        kruskalAlgorithm.sortEdges(edges);
        System.out.println("排序后：" + Arrays.toString(edges));
        kruskalAlgorithm.kruskal();
    }


    /**
     * @param edges 边的几个
     */
    private void sortEdges(EData[] edges) {
        for (int i = 0; i < edges.length - 1; i++) {
            for (int j = 0; j < edges.length - 1 - i; j++) {
                if (edges[j].weight > edges[j + 1].weight) {
                    EData tem = edges[j];
                    edges[j] = edges[j + 1];
                    edges[j + 1] = tem;
                }
            }

        }
    }

    /**
     * @param ch 传入顶点的值
     * @return 返回ch的顶点对的下边。找不到，返回-1
     */
    private int getPosition(char ch) {
        for (int i = 0; i < vertexs.length; i++) {
            if (vertexs[i] == ch) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 获取图中的边，
     *
     * @return
     */
    private EData[] getEdges() {
        int index = 0;
        EData[] edges = new EData[edgeNum];
        for (int i = 0; i < vertexs.length; i++) {
            for (int j = i + 1; j < vertexs.length; j++) {
                if (matrix[i][j] != INF) {
                    edges[index++] = new EData(vertexs[i], vertexs[j], matrix[i][j]);
                }
            }
        }
        return edges;
    }

    /**
     * 获取下标为i的顶点的重点。用于判断2个顶点的终点是否相同
     *
     * @param ends
     * @param i
     * @return
     */
    private int getEnd(int[] ends, int i) {
        while (ends[i] != 0) {
            i = ends[i];
        }
        return i;
    }
}


class EData {
    char start;
    char end;
    int weight;

    public EData(char start, char end, int weight) {
        this.start = start;
        this.end = end;
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "边信息：{" +
                "start=" + start +
                ", end=" + end +
                ", weight=" + weight +
                '}';
    }

}