package com.wjf.sortdemo.violence;

class ViolenceMatch {

    public static void main(String[] args) {
        String str = "1231231231777723123123123123123";
        String str1 = "7777";
        int a = violenceMatch(str, str1);
        System.out.println("aaa:" + a);
    }

    /**
     * 暴力匹配
     * @param str
     * @param str1
     * @return
     */
    public static int violenceMatch(String str, String str1) {
        char[] s1 = str.toCharArray();
        char[] s2 = str1.toCharArray();
        int s1Len = s1.length;
        int s2Len = s2.length;
        int i = 0;
        int j = 0;

        while (i < s1Len && j < s2Len) {
            if (s1[i] == s2[j]) {
                i++;
                j++;
            } else {
                i = i - (j - 1);
                j = 0;
            }
        }
        if (j == s2Len) {
            return i - j;
        } else {
            return -1;
        }
    }
}
