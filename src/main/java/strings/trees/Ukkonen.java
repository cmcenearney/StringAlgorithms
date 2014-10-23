package strings.trees;

import java.util.List;

public class Ukkonen extends Tree {

    public void construct(String s){
        root.addChild(s.substring(0,1));
        for (int i = 1; i < s.length(); i++){
            for (int j = 1; j < i+1; j++ ){
                //TreeNode node = findInImplicitTree(s.substring(j, i));
                extend(s, i, j);
            }
        }
    }

    public void extend(String s, int i, int j){
        String b = s.substring(j,i);
        TreeNode node = root;
        while (b.length() > 0) {
            List<String> e = getEdgeWithSameFirstChar(node.getChildren(), b);
            if (e.isEmpty())
                return; //throw exception? - we should not run out of path while there is still b
            String edge = e.get(0);
            //b ends at a leaf
            if (b.equals(edge) && !node.getChild(edge).hasChildren()){
                TreeNode leaf = node.getChild(edge);
                node.addChild(b + s.substring(i+1, i+2), leaf);
                node.removeChild(edge);
                return;
            }
            //b ends inside an edge
            else if (edge.startsWith(b)){

            }
        }
    }

    public TreeNode findInImplicitTree(String str) {
        TreeNode node = root;
        while (node.hasChildren()) {
            List<String> e = getEdgeWithSameFirstChar(node.getChildren(), str);
            if (e.isEmpty())
                return null;
//            String edge = e.get(0);
//            if (str.equals(edge) && isEndOfSuffix(node.getChild(edge))) {
//                return node.getChild(edge);
//            } else if (str.equals(edge.substring(0, edge.length() - 1)) && edgeContainsTerminus(edge)) {
//                return node;
//            } else if (str.startsWith(edge)) {
//                node = node.getChild(edge);
//                str = str.substring(edge.length());
//            } else {
//                return null;
//            }
        }
        return null;
    }
}
