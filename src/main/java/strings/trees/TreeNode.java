package strings.trees;

import java.util.HashMap;

class TreeNode {

    private HashMap<String, TreeNode> children = new HashMap<>();
    private HashMap<String, TreeNode> parent = new HashMap<>();

    TreeNode(){}

    TreeNode(String s, TreeNode parent){
        setParent(s, parent);
    }

    void setParent(String s, TreeNode p){
        parent.clear();
        this.parent.put(s, p);
    }

    TreeNode getParent(){
        if (parent.isEmpty())
            return null;
        return (TreeNode) parent.values().toArray()[0];
    }

    TreeNode addChild(String s){
        children.put(s, new TreeNode(s, this));
        return children.get(s);
    }

    TreeNode addChild(String s, TreeNode n){
        children.put(s, n);
        n.setParent(s, this);
        return children.get(s);
    }

    String getValue(){
        return (String) parent.keySet().toArray()[0];
    }

    TreeNode getChild(String s){
        return children.get(s);
    }

    void removeChild(String s){
        children.remove(s);
    }

    HashMap<String, TreeNode> getChildren(){
        return children;
    }

    boolean hasChildren(){
        return !children.isEmpty();
    }

}
