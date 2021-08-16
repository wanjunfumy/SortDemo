package com.wjf.sortdemo.threadedbinary;

/**
 * 线索化二叉树
 */
class ThreadedBinaryTree {

    public static void main(String[] args) {
        HeroBinaryTree binaryTree = new HeroBinaryTree();
        HeroMode node1 = new HeroMode(1, "貂蝉");
        HeroMode node2 = new HeroMode(2, "吕奉先");
        HeroMode node3 = new HeroMode(3, "曹操");
        HeroMode node4 = new HeroMode(4, "张孟德");
        HeroMode node5 = new HeroMode(5, "关羽");
        node1.setLeft(node2);
        node1.setRight(node3);
        node3.setLeft(node4);
        node3.setRight(node5);
        binaryTree.setRoot(node1);

        binaryTree.infixList();

        binaryTree.threadedNodes();
        System.out.println("s:" + node5.getLeft());
    }
}

class HeroBinaryTree {
    private HeroMode root;

    private HeroMode pre; // temp

    public HeroMode getRoot() {
        return root;
    }

    public void threadedNodes() {
        this.threadedNodes(root);
    }

    /**
     * 中序吧，后续不大适合
     * 1、特性就是先找到上一个node值，中序搜索方式刚好符合特性。完美。
     * 2、当left为null的时候，要链接的是左边的上一个，
     * 3、当right为null的时候，就让上一个node，把当前的note加进去。这个不能理解，但在递归中的代码位置能男理解。这个要非常理解递归的特性
     * @param node
     */
    public void threadedNodes(HeroMode node) {
        if (node == null) {
            return;
        }
        threadedNodes(node.getLeft());
        // 为什么要在递归的后面呢？这利用了递归的特性，
        // 他会从左侧的叶节点开始执行。
        if (node.getLeft() == null) {
            node.setLeft(pre);
            node.setLeftType(1);
        }
        // 这里如果是第一次，不会执行。因为pre == null 等pre赋值了，才会
        if (pre != null && pre.getRight() == null) { // 这个不能理解，但是要知道，为什么 pre，一定要在这个判断和赋值以后再覆盖
            pre.setRight(node);
            pre.setLeftType(1);
        }
        pre = node; // 我的位置很关键，这个位置适合中序搜索的线索化二叉树。
        System.out.println("节点顺序：" + pre);
        threadedNodes(node.getRight());
    }

    public boolean deleteNO(int no) {
        if (this.root != null) {
            if (this.root.getNo() == no) {
                this.root = null;
                return true;
            }
            return this.root.deleteNo(no);
        }
        return false;
    }

    public void setRoot(HeroMode root) {
        this.root = root;
    }

    public void preList() {
        if (this.root != null) {
            this.root.preOrder();
        }
    }

    public void infixList() {
        if (this.root != null) {
            this.root.infixOrder();
        }
    }

    public void rearList() {
        if (this.root != null) {
            this.root.rearOrder();
        }
    }

    public HeroMode preFind(int no) {
        if (this.root != null) {
            return this.root.preFind(no);
        }
        return null;
    }

    public HeroMode infixFind(int no) {
        if (this.root != null) {
            return this.root.infixFind(no);
        }
        return null;
    }

    public HeroMode rearFind(int no) {
        if (this.root != null) {
            return this.root.rearFind(no);
        }
        return null;
    }
}

class HeroMode {
    private int no;
    private String name = "";
    private HeroMode left;
    private HeroMode right;

    private int leftType;
    private int rightType;

    public int getLeftType() {
        return leftType;
    }

    public void setLeftType(int leftType) {
        this.leftType = leftType;
    }

    public int getRightType() {
        return rightType;
    }

    public void setRightType(int rightType) {
        this.rightType = rightType;
    }

    public HeroMode(int no, String name) {
        super();
        this.no = no;
        this.name = name;
    }

    @Override
    public String toString() {
        return "HeroMode{" +
                "no=" + no +
                ", name='" + name + '\'' +
                '}';
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HeroMode getLeft() {
        return left;
    }

    public void setLeft(HeroMode left) {
        this.left = left;
    }

    public HeroMode getRight() {
        return right;
    }

    public void setRight(HeroMode right) {
        this.right = right;
    }

    /**
     * 编写前序遍历的方法
     */
    public void preOrder() {
        System.out.println("前序遍历：" + this);
        if (this.left != null) {
            this.left.preOrder();
        }
        if (this.right != null) {
            this.right.preOrder();
        }
    }

    /**
     * 中序遍历
     */
    public void infixOrder() {
        // 先递归向左子树中序遍历
        if (this.left != null) {
            this.left.infixOrder();
        }
        System.out.println("中序遍历：" + this);
        if (this.right != null) {
            this.right.infixOrder();
        }
    }

    /**
     * 后续遍历
     */
    public void rearOrder() {
        if (this.left != null) {
            this.left.rearOrder();
        }
        if (this.right != null) {
            this.right.rearOrder();
        }
        System.out.println("后续遍历：" + this);
    }

    /**
     * 前序遍历查找
     *
     * @param no
     * @return
     */
    public HeroMode preFind(int no) {
        if (this.no == no) {
            return this;
        }
        HeroMode temp = null;
        if (this.left != null) {
            temp = this.left.preFind(no);
        }
        if (temp != null) {
            return temp;
        }
        if (this.right != null) {
            temp = this.right.preFind(no);
        }
        return temp;
    }

    /**
     * 中序遍历
     *
     * @param no
     * @return
     */
    public HeroMode infixFind(int no) {
        HeroMode temp = null;
        if (this.left != null) {
            temp = this.left.infixFind(no);
        }
        if (temp != null) {
            return temp;
        }
        if (this.no == no) {
            return this;
        }
        if (this.right != null) {
            temp = this.right.infixFind(no);
        }
        return temp;
    }

    /**
     * 后续遍历
     *
     * @param no
     * @return
     */
    public HeroMode rearFind(int no) {
        HeroMode temp = null;
        if (this.left != null) {
            temp = this.left.rearFind(no);
        }
        if (temp != null) {
            return temp;
        }
        if (this.right != null) {
            temp = this.right.rearFind(no);
        }
        if (temp != null) {
            return temp;
        }
        if (this.no == no) {
            return this;
        }

        return temp;
    }

    /**
     * 删除节点
     * 二叉树是单向的，
     * 所以删除的时候，只能判断子节点是否可以删除。所以只能用前序遍历查找删除
     *
     * @param no
     */
    public boolean deleteNo(int no) {
        if (this.left != null &&
                this.left.no == no) {
            this.left = null;
            return true;
        }
        boolean b = false;
        if (this.left != null) {
            b = this.left.deleteNo(no);
        }
        if (b) {
            return b;
        }
        if (this.right != null &&
                this.right.no == no) {
            this.right = null;
            return true;
        }
        if (this.right != null) {
            b = this.right.deleteNo(no);
        }
        return b;
    }

}
