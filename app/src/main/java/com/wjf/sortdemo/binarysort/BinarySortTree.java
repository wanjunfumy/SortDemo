package com.wjf.sortdemo.binarysort;

class BinarySortTree {

    public static void main(String[] args) {
        int[] arr = {7, 3, 10, 12, 5, 1, 9, 0, 2};
        BinarySortTreeDemo binarySortTreeDemo = new BinarySortTreeDemo();
        for (int i = 0; i < arr.length; i++) {
            Node tem = new Node(arr[i]);
            binarySortTreeDemo.add(tem);
        }
        binarySortTreeDemo.infixOrder();
        binarySortTreeDemo.deleteNode(1);
        System.out.println();
        binarySortTreeDemo.infixOrder();

    }
}

class BinarySortTreeDemo {
    private Node root;

    public void deleteNode(int value) {
        if (root == null) {
            return;
        }
        Node targetNode = search(value);
        if (targetNode == null) {
            return;
        }
        if (root.left == null && root.right == null) {
            root = null;
            return;
        }
        Node parentNode = searchParent(value);
        if (parentNode == null) {
            return;
        }
        // 找到了父节点
        if (targetNode.left == null && targetNode.right == null) { // 我是叶子节点
            if (parentNode.left != null && parentNode.left.value == value) {
                parentNode.left = null;
            } else if (parentNode.right != null && parentNode.right.value == value) {
                parentNode.right = null;
            }
        } else if (targetNode.left != null && targetNode.right != null) {
            targetNode.value = delRightTreeMin(targetNode.right);
        } else { // 只有一个子节点
            // 如果要删除的由左子节点
            if (targetNode.left != null) {
                if (parentNode != null) {
                    if (parentNode.left.value == value) {
                        parentNode.left = targetNode.left;
                    } else if (parentNode.right.value == value) {
                        parentNode.right = targetNode.left;
                    }
                } else {
                    root = targetNode.left;
                }
            } else { // 右子节点
                if (parentNode != null) {
                    if (parentNode.left.value == value) {
                        parentNode.left = targetNode.right;
                    } else if (parentNode.right.value == value) {
                        parentNode.right = targetNode.right;
                    }
                } else {
                    root = targetNode.right;
                }
            }
        }
    }

    /**
     * @param node
     * @return
     */
    public int delRightTreeMin(Node node) {
        Node target = node;
        while (target.left != null) {
            target = target.left;
        }
        deleteNode(target.value);

        return target.value;
    }

    public Node search(int value) {
        if (root == null) {
            return null;
        }
        return root.search(value);
    }

    public Node searchParent(int value) {
        if (this.root == null) {
            return null;
        }
        return this.root.searchParent(value);
    }

    public void add(Node node) {
        if (root == null) {
            root = node;
        } else {
            root.add(node);
        }
    }

    public void infixOrder() {
        if (root != null) {
            root.infixOrder();
        } else {
            System.out.println("没有数据");
        }
    }
}

class Node {
    int value;
    Node left;
    Node right;

    public Node searchParent(int value) {
        if ((this.left != null && this.left.value == value) ||
                this.right != null && this.right.value == value) {
            return this;
        } else {
            if (value < this.value && this.left != null) {
                return this.left.searchParent(value);
            } else if (value > this.value && this.right != null) {
                return this.right.searchParent(value);
            }
        }
        return null;
    }

    public Node search(int value) {
        if (this.value == value) {
            return this;
        } else if (this.value > value) {
            if (this.left == null) {
                return null;
            }
            return this.left.search(value);
        } else if (this.value < value) {
            if (this.right == null) {
                return null;
            }
            return this.right.search(value);
        }
        return null;
    }

    public Node(int value) {
        this.value = value;
    }

    public void add(Node node) {
        if (node == null) {
            return;
        }
        if (node.value > value) {
            if (getRight() == null) {
                this.setRight(node);
            } else {
                this.right.add(node);
            }
        } else {
            if (this.left == null) {
                this.left = node;
            } else {
                this.left.add(node);
            }
        }
    }

    public void infixOrder() {
        if (this.left != null) {
            this.left.infixOrder();
        }
        System.out.println(this);
        if (this.right != null) {
            this.right.infixOrder();
        }
    }

    @Override
    public String toString() {
        return "Node{" +
                "value=" + value +
                '}';
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
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
}