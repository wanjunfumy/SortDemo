package com.wjf.sortdemo.kmp;

import java.util.Arrays;

class KMPFind {

    public static void main(String[] args) {
        String str1 = "BBC ABCDAB ABCDABCDABDE";
        String str2 = "ABCDABD";
        int[] next = kmpNext(str2);
        System.out.println("匹配表---> " + Arrays.toString(next));
        int a = kmpSearch(str1, str2, next);
        System.out.println("匹配：" + a);
    }

    private static int kmpSearch(String str1, String str2, int[] next) {
        for (int i = 0, j = 0; i < str1.length(); i++) {
            while (j > 0 && str1.charAt(i) != str2.charAt(j)) {
                j = next[j - 1];
            }
            if (str1.charAt(i) == str2.charAt(j)) {
                j++;
            }
            if (j == str2.length()) {
                return i - j + 1;
            }
        }
        return -1;
    }

    private static int[] kmpNext(String str) {
        int[] next = new int[str.length()];
        next[0] = 0;
        for (int i = 1, j = 0; i < str.length(); i++) {
            while (j > 0 && str.charAt(i) != str.charAt(j)) {
                j = next[j - 1]; //TODO 这里，很奇怪，为什么要这么做？
                // 用作回溯，那为什么要回溯？回溯是什么？
                // 运行到这里，是有前提的，for循环中，str.charAt(i) 与 str.charAt(j)
                // 两个数据，只是不同的判断，要进入到这里，那之前的一定是相等的，j++了
                // 到了这里，那就是遇到了不相等的，那就把j的位置回溯下，那回溯到哪里？
                // 一直回溯到和i不相等的位置。
            }
            if (str.charAt(i) == str.charAt(j)) {
                j++;
            }
            next[i] = j;
        }
        return next;
    }
}
