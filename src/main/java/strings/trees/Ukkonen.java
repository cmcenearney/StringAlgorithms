package strings.trees;

import java.util.List;


public class Ukkonen extends Tree {

    public void construct(String s){
        root.addChild(s.substring(0,1));
        for (int i = 0; i < s.length(); i++){
            for (int j = 0; j <= i; j++ ){
                extend(s, i, j);
            }
        }
    }

    public void extend(String s, int i, int j){
        String b = s.substring(j,i);
        String x = s.substring(i,i+1);
        TreeNode node = root;
        while (b.length() > 0) {
            List<String> e = getEdgeWithSameFirstChar(node.getChildren(), b);
            if (e.isEmpty()) {
                node.addChild(b);
                continue; //throw exception? - we should not run out of path while there is still b
            }
            String edge = e.get(0);
            int m = getLastMatchingIndex(b, edge);
            String edgeRemainder = edge.substring(m + 1);
            String strRemainder = b.substring(m + 1);
            String match = edge.substring(0, getLastMatchingIndex(b, edge) + 1);
            //if b ends at a leaf
            if (b.equals(edge) && !node.getChild(edge).hasChildren()){
                TreeNode leaf = node.getChild(edge);
                node.addChild(edge + x, leaf);
                node.removeChild(edge);
                return;
            }
            //b ends at a node that has at least one path out
            else if (b.equals(edge) && node.hasChildren()){
                TreeNode next = node.getChild(b);
                if (getEdgeWithSameFirstChar(next.getChildren(), b).size() > 0){
                    return;
                }
                next.addChild(x);
            }
            //b ends inside an edge
            else if (edge.startsWith(b)){
                //if x is there too, we're done
                if (edge.startsWith(b+x)){
                    return;
                }
                //otherwise split the edge
                else {
                    TreeNode oldNode = node.getChild(edge);
                    TreeNode newNode = new TreeNode(match, node);
                    node.removeChild(edge);
                    node.addChild(match, newNode);
                    newNode.addChild(edgeRemainder, oldNode);
                    newNode.addChild(x);
                    return;
                }
            }
            //keep traversing
            else if ( b.startsWith(edge) ) {
                node = node.getChild(edge);
                b = strRemainder;
            }
        }
    }

}
