package com.wjf.sortdemo;

import java.util.Random;

class Sorts {

    public static void main(String[] args) {
        int num = 8000000;
        int[] arr = new int[num];
        for (int i = 0; i < num; i++) {
            arr[i] = (int) (Math.random() * 80000);
        }
//        int[] arr = {8, 9, 1, 7, 6, 11, 5, 4, 2, 0};
        long s = System.currentTimeMillis();
//        quickSort(arr, 0, arr.length - 1); // 800w数据耗时1300ms左右
//        int[] temp = new int[arr.length];
//        mergeSort(arr, 0, arr.length - 1, temp); // 800w数据耗时1600ms左右
//        testRadix();
        radixSort(arr); // 800w数据耗时1200ms左右
        long ss = System.currentTimeMillis();
        System.out.println("耗时：" + (ss - s));

    }

    /**
     * 快排序，圈定left和right，便于递归
     * @param arr
     * @param left
     * @param right
     */
    public static void quickSort(int[] arr, int left, int right) {
        int l = left;
        int r = right;
        int pivot = arr[(l + r) / 2];
        int temp = 0;
        // 如果反过来了，就退出
        while (l < r) {
            // 这两个while，是为了找到左边和右边比pivot大、小的位置，
            //
            // 找到比pivot大/等于的数据
            while (arr[l] < pivot) {
                l += 1;
            }
            // 找到比pivot的小/等于的数据
            while (arr[r] > pivot) {
                r -= 1;
            }
            if (l >= r) {
                break;
            }
            // 左右都找了一个，那就调换下
            temp = arr[l];
            arr[l] = arr[r];
            arr[r] = temp;

            // 某种情况下，l或者r先达到pivot的位置，
            if (arr[l] == pivot) {
                r -= 1;
            } // 使其相等后，会退出while循环

            if (arr[r] == pivot) {
                l += 1;
            }
        }
        if (r == l) {
            l += 1;
            r -= 1;
        }
//        System.out.println("left :" + left + " right = " + right + " l " + l + " r " + r);
        if (left < r) {
            quickSort(arr, left, r);
        }
        if (l < right) {
            quickSort(arr, l, right);
        }
        printArr(arr);
    }

    private static void printArr(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.print(" " + arr[i]);
        }
        System.out.println();
    }

    //region 归并递归


    private static void mergeSort(int[] arr, int left, int right, int[] temp) {
        if (left < right) {
            int mid = (left + right) / 2;
            // 想左递归分解，分解，只是为了找出位置，并不是真的切割了数据，temp没有实际作用，只是透传
            // 第一次分解左边的，left == 0，mid == 4
            // 第二次分解左边的，left == 0，mid == 2
            // 第三次分解左边的，left == 0，mid == 1
            // 第四次次分解左边的，left == 0，mid == 0
            // 此时，递归不会再进入if，后面的有效代码，将给右边执行
            /*
            *
                cut left ==> left 0 mid 2 right 4
                cut left ==> left 0 mid 1 right 2
                cut left ==> left 0 mid 0 right 1
            * */
//            System.out.println();
//            System.out.printf("cut left ==> left %d mid %d right %d", left, mid, right);
            mergeSort(arr, left, mid, temp);
//            System.out.println();
//            System.out.printf("right left ==> left %d mid %d right %d", left, mid, right);

            // 向右递归分解。注意。mid传入的时候，+ 1了
            // 第一次分解右边，mid == 5，right == 9
            // 第二次分解右边，mid == (6 + 9) / 2 == 7，right == 9 妙啊，递归加上这个，秒啊
            // 第三次分解右边，mid == (8 + 9) / 2 == 8，right == 9
            // 第四次分解右边，mid == (9 + 9) / 2 == 9，right == 9
            // 此时递归不会再进入if，执行权交给了merge
            mergeSort(arr, mid + 1, right, temp);
//            System.out.println();
//            System.out.printf("merge ==> left %d mid %d right %d", left, mid, right);

            /*
            left 0 mid 0 right 1
            left 0 mid 1 right 2
            left 3 mid 3 right 4
            left 0 mid 2 right 4
            left 5 mid 5 right 6
            left 5 mid 6 right 7
            left 8 mid 8 right 9
            left 5 mid 7 right 9
            left 0 mid 4 right 9
            */
            // 这里体现了递归的魅力，第一次执行左边的分解完毕后，
            // 断点打在这里，你看看第一次执行到这里的是什么值，你发现，是left 0 mid 0 right 1，是左侧分解的最终值
            // 第二次呢？...
            /*
            * merge ==> left 0 mid 0 right 1
            merge ==> left 0 mid 1 right 2
            *
            merge ==> left 3 mid 3 right 4
            merge ==> left 0 mid 2 right 4
            * 以上4个是左边的merge
            merge ==> left 5 mid 5 right 6
            merge ==> left 5 mid 6 right 7
            *
            merge ==> left 8 mid 8 right 9
            merge ==> left 5 mid 7 right 9
            * 以上4个是右边的merge
            *
            * 最后一次 最后一次，left和right并不是计算出来的，二是第一次传入的值。
            merge ==> left 0 mid 4 right 9
            * */
            /* 有个地方需要注意的，为先执行merge的，是left 0 mid 0 right 1 （这个是左分解的最后一次输入）
             *  ,而不是left 0 mid 2 right 4 （这个是左分解的第一次输入）因为，递归其实是开辟了另一个栈，栈可是先进后出的（当然，是在
             * 递归后的，如果是在递归之前，那执行的是栈里面的东西，并不是递归方法），利用这个特写，完美了运行了先分解完，再合并的逻辑。
             */
            merge(arr, left, mid, right, temp);
//            printArr(arr);
            // 不得不说，归并算法，把递归运用到了极致。太完美了。艺术，感叹
        }
    }

    /**
     *
     * @param arr 排序的原始数组
     * @param left 左边有序序列的初始所以
     * @param mid 中间索引
     * @param right 右边索引
     * @param temp 中转数组
     */
    private static void merge(int[] arr, int left, int mid, int right, int[] temp) {
//        System.out.println();
        System.out.printf("merge ==> left %d mid %d right %d", left, mid, right);
        int i = left; // 初始化i，左边有序序列的初始索引
        int j = mid + 1; // 初始化j，右边边有序序列的初始索引
        int t = 0; // 指向temp数组的当前索引
        // 一
        // 先把左右两边的有序数组数据按照 规则填充到temp数组中去
        // 知道左右两边的有序序列有一边处理完毕为止
        while (i <= mid && j <= right) {
            // 如果分数组中的左边小于等于右边的，那就把小的付给temp存起来
            if (arr[i] <= arr[j]) {
                temp[t] = arr[i];
                i += 1;
                t += 1;
            } else { // 反之，将右边的有序序列的当前元素，填充到temp数组
                temp[t] = arr[j];
                t += 1;
                j += 1;
            }
        }

        // 二
        // 把有剩余数据的一边的数据依次全部填充到temp中
        while (i <= mid) {
            temp[t] = arr[i];
            t += 1;
            i += 1;
        }
        while (j <= right) {
            temp[t] = arr[j];
            j += 1;
            t += 1;
        }
        // 三
        // 将temp的数组的元素拷贝到arr中去
        t = 0;
        int tempLeft = left;
        while (tempLeft <= right) {
            arr[tempLeft] = temp[t];
            t += 1;
            tempLeft += 1;
        }
    }

    private static void testRadix() {
        final int arr[] = {53, 3, 542, 748, 14, 214};
        radixSort(arr);
    }

    /**
     * 基数排序-桶排序
     * @param arr
     */
    private static void radixSort(int[] arr) {
        int index = 0;
        int[][] bucket; // 为用arr.length ? 只能是用空间换效益了
        int[] bucketEle;
        bucket = new int[10][arr.length]; // 为用arr.length ? 只能是用空间换效益了
        bucketEle = new int[10];
        for (int i = 0; i < 11; i++) {
            boolean allNotZero = false;
            for (int value : arr) {
                int per = (int) Math.pow(10, i);
                int digitEle = (value % (10 * per)); // 取各位上的值
                if (digitEle != 0) {
                    allNotZero = true;
                }
                int ind = digitEle / per;
                bucket[ind][bucketEle[ind]] = value;
                bucketEle[ind] += 1;
            }
            if (!allNotZero) {
                break;
            }
            index = 0;
            // 从桶中取出来
            for (int k = 0; k < bucketEle.length; k++) {
                if (bucketEle[k] != 0) {
                    for (int t = 0; t < bucketEle[k]; t++) {
                        arr[index] = bucket[k][t];
                        index++;
                    }
                    bucketEle[k] = 0;
                }
            }
        }

        /*for (int j = 0; j < arr.length; j++) {
            int digitEle = arr[j] % 10; // 取各位上的值
            bucket[digitEle][bucketEle[digitEle]] = arr[j];
            bucketEle[digitEle] += 1;
        }
        // 从桶中取出来
        int index = 0;
        for (int k = 0; k < bucketEle.length; k++) {
            if (bucketEle[k] != 0) {
                for (int t = 0; t < bucketEle[k]; t++) {
                    arr[index] = bucket[k][t];
                    index++;
                }
            }
        }
        printArr(arr);

        // 第二次
        index = 0;
        bucket = new int[10][arr.length]; // 为用arr.length ? 只能是用空间换效益了
        bucketEle = new int[10];
        for (int j = 0; j < arr.length; j++) {
            int digitEle = (arr[j] % (10 * 10)); // 取各位上的值
            bucket[digitEle / 10][bucketEle[digitEle / 10]] = arr[j];
            bucketEle[digitEle / 10] += 1;
        }
        // 从桶中取出来
        for (int k = 0; k < bucketEle.length; k++) {
            if (bucketEle[k] != 0) {
                for (int t = 0; t < bucketEle[k]; t++) {
                    arr[index] = bucket[k][t];
                    index++;
                }
            }
        }
*/
//        printArr(arr);
    }

}
