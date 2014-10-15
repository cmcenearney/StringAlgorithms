package strings;

import java.util.HashMap;

public class TreeNode {

    private HashMap<String, TreeNode> children = new HashMap<>();
    private HashMap<String, TreeNode> parent = new HashMap<>();

    public TreeNode(){}

    public TreeNode(String s, TreeNode parent){
        setParent(s, parent);
    }

    public void setParent(String s, TreeNode p){
        parent.clear();
        this.parent.put(s, p);
    }

    public TreeNode getParent(){
        if (parent.isEmpty())
            return null;
        return (TreeNode) parent.values().toArray()[0];
    }

    public TreeNode addChild(String s){
        children.put(s, new TreeNode(s, this));
        return children.get(s);
    }

    public TreeNode addChild(String s, TreeNode n){
        children.put(s, n);
        n.setParent(s, this);
        return children.get(s);
    }

    public TreeNode getChild(String s){
        return children.get(s);
    }

    public void removeChild(String s){
        children.remove(s);
    }

    public HashMap<String, TreeNode> getChildren(){
        return children;
    }

    public boolean hasChildren(){
        return !children.isEmpty();
    }

}
