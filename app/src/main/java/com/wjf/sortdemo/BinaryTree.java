package com.wjf.sortdemo;

/**
 * 二叉树相关
 */
class BinaryTree {

    public static void main(String[] args) {
        // 创建一个二叉树
        HeroBinaryTree binaryTree = new HeroBinaryTree();
        HeroMode node1 = new HeroMode(1, "貂蝉");
        HeroMode node2 = new HeroMode(2, "吕奉先");
        HeroMode node3 = new HeroMode(3, "曹操");
        HeroMode node4 = new HeroMode(4, "张孟德");
        HeroMode node5 = new HeroMode(5, "关羽");

        node3.setLeft(node5);
        binaryTree.setRoot(node2);
        binaryTree.getRoot().setLeft(node1);
        binaryTree.getRoot().setRight(node3);
        binaryTree.getRoot().getRight().setRight(node4);

        /*
        父节点：HeroMode{no=2, name='吕奉先'}
        父节点：HeroMode{no=1, name='貂蝉'}
        父节点：HeroMode{no=3, name='曹操'}
        父节点：HeroMode{no=4, name='张孟德'}
         */
//        binaryTree.preList();
        /*
        中序遍历：HeroMode{no=1, name='貂蝉'}
        中序遍历：HeroMode{no=2, name='吕奉先'}
        中序遍历：HeroMode{no=3, name='曹操'}
        中序遍历：HeroMode{no=4, name='张孟德'}
        */
//        System.out.println();
//        binaryTree.infixList();
        /*
        后续遍历：HeroMode{no=1, name='貂蝉'}
        后续遍历：HeroMode{no=4, name='张孟德'}
        后续遍历：HeroMode{no=3, name='曹操'}
        后续遍历：HeroMode{no=2, name='吕奉先'}
         */
//        System.out.println();
//        binaryTree.rearList();

//        HeroMode tem = binaryTree.preFind(5);
//        HeroMode tem = binaryTree.infixFind(5);
//        HeroMode tem = binaryTree.rearFind(5);
//        System.out.println("查找 :" + (tem != null ? tem : "啥也不是"));
        binaryTree.preList();
        boolean s = binaryTree.deleteNO(5);
        System.out.println("删除 :" + (s ? "删除成功" : "删除失败"));
        binaryTree.preList();
    }
}

class HeroBinaryTree {
    private HeroMode root;

    public HeroMode getRoot() {
        return root;
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
