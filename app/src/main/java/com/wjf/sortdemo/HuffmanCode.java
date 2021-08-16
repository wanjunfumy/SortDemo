package com.wjf.sortdemo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 通信的哈夫曼编码
 * 压缩，避免了二义性
 */
class HuffmanCode {
    private static String sendString = "i like like like java do you like a java";

    public static void main(String[] args) {
        System.out.println("原始发送数据：" + sendString);
        byte[] bytes = sendString.getBytes();
        List<HuffmanNode> lists = getNodes(bytes);
        HuffmanNode root = createHuffmanTree(lists);
//        root.preOrder();
        huffmanMap(root);
        byte[] over = appendString();
        System.out.println("huffman编码后:" + Arrays.toString(over));
        byte[] decodeString = decode(huffmanCode, over);
        System.out.println("还原后的:" + new String(decodeString));
    }

    /**
     * 解码
     *
     * @param huffmanCode
     * @param huffmanBytes
     * @return
     */
    private static byte[] decode(Map<Byte, String> huffmanCode, byte[] huffmanBytes) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < huffmanBytes.length; i++) {
            boolean flag = (i == huffmanBytes.length - 1); // 最后一个
            stringBuilder.append(bytesToString(!flag, huffmanBytes[i]));
        }
        System.out.println("stringBuilder " + stringBuilder.toString());

        // ============= 把收到的字符串，按照huffman编码进行解码
        // 把huffman编码表进行调换，因为反向查询
        Map<String, Byte> map = new HashMap<>();
        for (Map.Entry<Byte, String> item : huffmanCode.entrySet()) {
            map.put(item.getValue(), item.getKey());
        }
        System.out.println("反向编码表：" + map);
        List<Byte> list = new ArrayList<>();
        for (int i = 0; i < stringBuilder.length();) {
            int count = 1;
            boolean flag = true;
            Byte b = null;
            while (flag) {
                String key = stringBuilder.substring(i, i + count);
                b = map.get(key);
                if (b == null) {
                    count++;
                } else {
                    flag = false;
                }
            }
            list.add(b);
            i += count;
        }
        byte[] b = new byte[list.size()];
        for (int i = 0; i < b.length; i++) {
            b[i] = list.get(i);
        }
        return b;
    }

    /**
     * 涉及到补码，转码
     *
     * @param flag
     * @param b
     * @return
     */
    private static String bytesToString(boolean flag, byte b) {
        int temp = b;
        if (flag) {
            temp |= 256;
        }
        String str = Integer.toBinaryString(temp); // 返回的是temp的二进制的补码。
        if (flag) {
            return str.substring(str.length() - 8);
        }
        return str;
    }

    private static byte[] appendString() {
        StringBuilder sb = new StringBuilder();
        byte[] s = sendString.getBytes();
        for (int i = 0; i < s.length; i++) {
            sb.append(huffmanCode.get(s[i]));
        }
        System.out.println("sb ->" + sb.toString());

        int len;
        if (sb.toString().length() % 8 == 0) {
            len = sb.length() / 8;
        } else {
            len = sb.length() / 8 + 1;
        }

        byte[] by = new byte[len];
        int index = 0; // 记录是第几个byte
        for (int i = 0; i < sb.length(); i += 8) {
            String sByte;
            if (i + 8 > sb.length()) {
                sByte = sb.substring(i);
            } else {
                sByte = sb.substring(i, i + 8);
            }
            by[index] = (byte) Integer.parseInt(sByte, 2);
            index += 1;
        }
        return by;
    }


    static Map<Byte, String> huffmanCode = new HashMap<>();

    static StringBuilder temp = new StringBuilder();

    /**
     *
     */
    private static Map<Byte, String> huffmanMap(HuffmanNode root) {
        getCode(root, "", temp);
        return null;
    }

    /**
     * 收集叶子结点编码，编码开始是从root节点
     *
     * @param node
     * @param code
     * @param stringBuilder
     */
    private static void getCode(HuffmanNode node, String code, StringBuilder stringBuilder) {
        StringBuilder sb2 = new StringBuilder(stringBuilder);
        sb2.append(code);
        if (node != null) {
            if (node.getValue() == null) {
                getCode(node.getLeft(), "0", sb2);
                getCode(node.getRight(), "1", sb2);
            } else {
                huffmanCode.put(node.getValue(), sb2.toString());
                System.out.println(node.getValue() + "===> " + sb2.toString() + " length: " + sb2.length());
            }
        }
    }

    private static HuffmanNode createHuffmanTree(List<HuffmanNode> lists) {
        while (lists.size() > 1) {
            Collections.sort(lists);

            HuffmanNode leftNode = lists.get(0);
            HuffmanNode rightNode = lists.get(1);

            HuffmanNode parent = new HuffmanNode(null, leftNode.getWeight() + rightNode.getWeight());
            parent.setLeft(leftNode);
            parent.setRight(rightNode);
            lists.remove(leftNode);
            lists.remove(rightNode);
            lists.add(parent);
        }
        return lists.get(0);
    }

    private static List<HuffmanNode> getNodes(byte[] bytes) {
        ArrayList<HuffmanNode> nodes = new ArrayList<>();
        Map<Byte, Integer> nodeMaps = new HashMap<>();
        for (int i = 0; i < bytes.length; i++) {
            if (nodeMaps.containsKey(bytes[i])) {
                Integer count = nodeMaps.get(bytes[i]);
                if (count != null) {
                    nodeMaps.put(bytes[i], count + 1);
                }
            } else {
                nodeMaps.put(bytes[i], 1);
            }
        }

        for (Map.Entry<Byte, Integer> item : nodeMaps.entrySet()) {
            HuffmanNode node = new HuffmanNode(item.getKey(), item.getValue());
            nodes.add(node);
        }
        return nodes;
    }

}

class HuffmanNode implements Comparable<HuffmanNode> {
    private Byte value; // 节点权值
    private int weight; // 字符出现的个数

    private HuffmanNode left;
    private HuffmanNode right;

    public HuffmanNode(Byte value, int weight) {
        this.value = value;
        this.weight = weight;
    }

    public Byte getValue() {
        return value;
    }

    public void setValue(Byte value) {
        this.value = value;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public void preOrder() {
        System.out.println(this);
        if (this.left != null) {
            this.left.preOrder();
        }
        if (this.right != null) {
            this.right.preOrder();
        }
    }

    public HuffmanNode getLeft() {
        return left;
    }

    public void setLeft(HuffmanNode left) {
        this.left = left;
    }

    public HuffmanNode getRight() {
        return right;
    }

    public void setRight(HuffmanNode right) {
        this.right = right;
    }

    @Override
    public String toString() {
        return "HuffmanNode{" +
                "value=" + value +
                ", weight=" + weight +
                '}';
    }

    @Override
    public int compareTo(HuffmanNode o) {
        return this.weight - o.weight;
    }
}