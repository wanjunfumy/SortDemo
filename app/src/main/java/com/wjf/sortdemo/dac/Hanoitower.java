package com.wjf.sortdemo.dac;

/**
 * 汉诺塔
 */
class Hanoitower {

    public static void main(String[] args) {
//        hanoitower(3, 'A', 'B', 'C');
        anay(4);
    }

    /**
     * 汉诺塔延时分治算法
     * @param num level
     * @param a 站点A
     * @param b 站点B
     * @param c 站点C
     */
    public static void hanoitower(int num, char a, char b, char c) {
        if (num == 1) {
            System.out.println(a + " -> " + c);
        } else {
            hanoitower(num - 1, a, c, b);
            System.out.println(a + " -> " + c);
            hanoitower(num - 1, b, a, c);
        }
    }

    private static void anay(int num) {
        if (num == 1) { // 第一眼看过去，似乎没有退出递归的地方，实际上，num == 1后，没有再执行递归。递归的生命周期结束。
            System.out.println("&num : " + num);
        } else {
            anay(num - 1);
            System.out.println("*num : " + num);
            anay(num - 1);
        }
    }

}
