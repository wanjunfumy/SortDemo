package com.wjf.sortdemo.prim;

import java.util.Arrays;

/**
 * 普利姆算法，求解最小路径
 * 思想：
 *  1、把实际情况，转成数据结构，图
 *  2、再把图转成2维数组
 * 算法起点不同，实际得出的最短路径也不同
 */
class PrimAlgorithm {

    public static void main(String[] args) {

        char[] data = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G'};
        int verxs = data.length;
        int max = Integer.MAX_VALUE;
        int[][] weight = new int[][]{
                {max,5,7,max,max,max,2},
                {5,max,max,9,max,max,3},
                {7,max,max,max,8,max,max},
                {max,9,max,max,max,4,max},
                {max,max,8,max,max,5,4},
                {max,max,max,4,5,max,6},
                {2,3,max,max,4,6,max},
        };
        
        MGraph mGraph = new MGraph(verxs);
        MinTree minTree = new MinTree();
        minTree.createGraph(mGraph, verxs, data, weight);
        minTree.showGraph(mGraph);

        minTree.prim(mGraph, 4);
    }



}

class MinTree {

    /**
     *
     * @param mGraph 图的对象
     * @param vers 顶点
     * @param data 顶点的值
     * @param weight 图的邻接矩阵
     */
    public void createGraph(MGraph mGraph, int vers, char[] data, int[][] weight) {
        int i, j;
        for (i = 0; i < vers; i++) {
            mGraph.data[i] = data[i];
            for (j = 0; j < vers; j++) {
                mGraph.weight[i][j] = weight[i][j];
            }
        }
    }

    public void showGraph(MGraph mGraph) {
        for (int[] item : mGraph.weight) {
            System.out.println(Arrays.toString(item));
        }
    }

    /**
     * 算法
     * @param mGraph 数据矩阵
     * @param v 从哪个顶点开始
     */
    public void prim(MGraph mGraph, int v) {
        // 标记顶点是否被访问过
        int[] visited = new int[mGraph.vertex];

        visited[v] = 1;
        // 记录2个顶点的下标
        int h1 = -1;
        int h2 = -1;
        int minWeight = Integer.MAX_VALUE; // 如果有比这个小的，就替换

        // 普利姆算法的结果是n(顶点个数) -1 个边
        for (int k = 1; k < mGraph.vertex; k++) {

            // 确定每次生成的子图，和哪个节点距离最近
            for (int i = 0; i < mGraph.vertex; i++) { // i节点表示被访问过的节点
                for (int j = 0; j < mGraph.vertex; j++) { // j是还没有访问过的
                    if (visited[i] == 1/*注意visited[v] == 1,这里就是要寻找顶点*/ &&
                            visited[j] == 0 &&
                            mGraph.weight[i][j] < minWeight){
                        minWeight = mGraph.weight[i][j];
                        h1 = i;
                        h2 = j;
                    }
                }
            }
            // 找到了一条最小的
            System.out.println("边<" + mGraph.data[h1] + ", " + mGraph.data[h2] + "> 权值：" + minWeight);
            visited[h2] = 1;
            minWeight = Integer.MAX_VALUE;
        }


    }
}

class MGraph {
    int vertex;
    char[] data;
    int[][] weight;

    public MGraph(int vertex) {
        this.vertex = vertex;
        data = new char[vertex];
        weight = new int[vertex][vertex];
    }
}