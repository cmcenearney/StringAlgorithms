package strings.trees;


import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/*
The "implicit" suffix tree is an intermediate step in Ukkonen's algorithm for linear time construction of a
full suffix tree.
This implementation is not generalized.
 */
public class ImplicitSuffixTree extends Tree {

    //TreeNode root = new TreeNode();

    public ImplicitSuffixTree() {}

    public void addString(String s){
        allSuffixes(s).stream().forEach(sfx -> addSuffix(sfx));
    }

    Set<String> allSuffixes(String s) {
        return IntStream.range(0, s.length()).boxed()
                .map(i -> s.substring(i))
                .collect(Collectors.toSet());
    }

    public TreeNode addSuffix(String s){
        TreeNode node = root;
        //special case - first time through
        if (!node.hasChildren()) {
            return node.addChild(s);
        }
        while (node.hasChildren()) {
            List<String> e = getEdgeWithSameFirstChar(node.getChildren(), s);
            //if no edges start with the first char -> add a new one
            if (e.isEmpty()) {
                return node.addChild(s);
            }
            //else find edge that starts with first char
            String edge = e.get(0);
            //if s is a prefix of the edge we're done
            if (edge.startsWith(s)){
                return node.getChild(edge);
            }
            //else if the edge is a prefix of s, replace it with s
            else if (s.startsWith(edge)) {
                node.addChild(s, node.getChild(edge));
                node.removeChild(edge);
                return node.getChild(s);
            }
            //otherwise split the edge
            else {
                int m = getLastMatchingIndex(s, edge);
                String edgeRemainder = edge.substring(m + 1);
                String strRemainder = s.substring(m + 1);
                String match = edge.substring(0, getLastMatchingIndex(s, edge) + 1);
                TreeNode oldNode = node.getChild(edge);
                TreeNode newNode = new TreeNode(match, node);
                node.removeChild(edge);
                node.addChild(match, newNode);
                newNode.addChild(edgeRemainder, oldNode);
                return newNode.addChild(strRemainder);
            }
        }
        return null;
    }

}
