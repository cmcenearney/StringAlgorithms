package strings.trees;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;

abstract class Tree {

    TreeNode root = new TreeNode();

    List<TreeNode> breadthFirstTraversal(TreeNode node){
        List<TreeNode> results = new LinkedList<TreeNode>();
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(node);
        while (!queue.isEmpty()) {
            TreeNode n = queue.remove();
            n.getChildren().values().stream().forEach(i -> queue.add(i));
            results.add(n);
        }
        return results;
    }

    public Integer countNodes() {
        return breadthFirstTraversal(root).size();
    }

    Integer getLastMatchingIndex(String s1, String s2) {
        char[] chars1 = s1.toCharArray();
        char[] chars2 = s2.toCharArray();
        int i = 0;
        while (i < chars1.length && i < chars2.length && chars1[i] == chars2[i]) {
            i++;
        }
        return i - 1;
    }

    List<String> getEdgeWithSameFirstChar(HashMap<String, TreeNode> edges, String str) {
        return edges.keySet().stream()
                .filter(s -> s.startsWith(str.substring(0, 1)))
                .collect(Collectors.toList());
    }

}
