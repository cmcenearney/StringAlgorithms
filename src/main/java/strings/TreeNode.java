package strings;

import java.util.HashMap;

public class TreeNode {

    String checkChar;
    HashMap<String, TreeNode> children = new HashMap<>();
    TreeNode parent;
    boolean commonToAll = false;

    public TreeNode(){}

    public TreeNode( TreeNode parent){
        this.parent = parent;
    }

    public TreeNode(String checkChar, TreeNode parent){
        this.checkChar = checkChar;
        this.parent = parent;
    }

}
