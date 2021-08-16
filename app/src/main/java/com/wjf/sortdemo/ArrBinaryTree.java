package com.wjf.sortdemo;

import java.util.Collections;
import java.util.Comparator;

/**
 *
 */
class ArrBinaryTree {

    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 4, 5, 6, 7};
        ArrBinary arrBinary = new ArrBinary(arr);
        arrBinary.preOrder(0);
//        dede(10);
        Collections.sort(null, new Comparator<Object>() {
            @Override
            public int compare(Object o1, Object o2) {
                return 0;
            }
        });
    }

    /**
     * 递归的特性
     * @param a
     */
    private static void dede(int a) {
        System.out.println("栈里面的打印：" + a); // 当然，第一次不是，后面的都是站里面执行的。
        if (a < 2) {
            return;
        }
        a -= 1;
        dede(a);
        System.out.println("次方法中的打印：" + a);
    }

}

class ArrBinary {
    private int[] arr;

    public ArrBinary(int[] arr) {
        this.arr = arr;
    }

    /**
     * 前序遍历，把线性数组，当做二叉树来遍历。
     * 如果是查找，是不是也要快一些的呢？这个是前析
     * @param index
     */
    public void preOrder(int index) {
        if (this.arr == null || this.arr.length == 0) {
            return;
        }
        System.out.print(this.arr[index] + " ");
        if ((2 * index + 1) < this.arr.length) {
            this.preOrder(2 * index + 1);
        }
        if ((2 * index + 2) < this.arr.length) {
            this.preOrder(2 * index + 2);
        }
    }


}