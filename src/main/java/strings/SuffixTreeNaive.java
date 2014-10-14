package strings;

import com.oracle.webservices.internal.api.message.BasePropertySet;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


/*
un-optimized, generalized suffix tree
capacity = 1113988 strings (based on available terminating chars from 0x7b to 0x10ffff)
 */
public class SuffixTreeNaive {

    ArrayList<String> terminatingChars = new ArrayList<>();
    TreeNode root = new TreeNode();
    private static final int FIRST_TERM_CHAR_VALUE = 0x7b;

    public SuffixTreeNaive() {
        this.root.commonToAll = true;
    }

    public void addString(String s) {
        String termChar = nextTerminatingChar();
        terminatingChars.add(termChar);
        s += termChar;
        allSuffixes(s).stream().forEach(sfx -> addSuffix(sfx));
    }

    private String nextTerminatingChar() {
        if (terminatingChars.isEmpty())
            return new String(Character.toChars(FIRST_TERM_CHAR_VALUE));
        String current = currentTermChar();
        return new String(Character.toChars(current.codePointAt(0) + 1));
    }

    public static Set<String> allSuffixes(String s) {
        return IntStream.range(0, s.length() - 1).boxed()
                .map(i -> s.substring(i))
                .collect(Collectors.toSet());
    }

    /*
    add node
    - if no edges start with the first char -> new edge
    - else find edge that starts with first char
        - do char-by-char comparison until mismatch
            - split the edge
                - new node
    returns new node
    */
    public TreeNode addSuffix(String str) {
        TreeNode node = root;
        //setStatus(node);
        //special case - first time through
        if (node.children.isEmpty()) {
            node.children.put(str, new TreeNode(currentTermChar(), node));
            node = node.children.get(str);
            setStatus(node);
            return node;
        }
        while (!node.children.isEmpty()) {
            List<String> e = getEdgeWithSameFirstChar(node.children, str);
            //if no edges start with the first char -> add a new one
            if (e.isEmpty()) {
                //setStatus(node);
                node.children.put(str, new TreeNode(currentTermChar(), node));
                node = node.children.get(str);
                setStatus(node);
                return node;
            }
            //else find edge that starts with first char
            String edge = e.get(0);


            int m = getLastMatchingIndex(str, edge);
            String edgeRemainder = edge.substring(m + 1);
            String strRemainder = str.substring(m + 1);
            String match = edge.substring(0, getLastMatchingIndex(str, edge) + 1);
            //keep traversing?
            if (str.startsWith(edge)) {
                setStatus(node);
                node = node.children.get(edge);
                str = strRemainder;
            }
            //if not, split the edge
            else {
                setStatus(node);
                TreeNode oldNode = node.children.get(edge);
                TreeNode newNode = new TreeNode(currentTermChar(), node);
                node.children.remove(edge);
                String newEdge = edge.substring(0, getLastMatchingIndex(str, edge) + 1);
                node.children.put(newEdge, newNode);
                newNode.children.put(edgeRemainder, oldNode);
                oldNode.parent = newNode;
                newNode.children.put(strRemainder, new TreeNode(currentTermChar(), node));
                newNode.commonToAll = oldNode.commonToAll;
                TreeNode newest =  newNode.children.get(strRemainder);
                setStatus(newNode);
                setStatus(oldNode);
                setStatus(newest);
                return newest;
            }


        }
        return null;
    }

    //TODO: just use a sortedSet for the terminatingChars?
    // - check that it sorts by codepoint for single-char strings
    public boolean subTreeContainsAllInputs(TreeNode node) {
        HashSet<String> tc = new HashSet<>(terminatingChars);
        return getAllTerminatingChars(node).equals(tc);
    }

    public Set<String> getAllTerminatingChars(TreeNode node) {
        Set<String> chars = new HashSet<String>();
        Set<TreeNode> visited = new HashSet<>();
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(node);
        //visited.add(node);
        while (!queue.isEmpty()) {
            TreeNode n = queue.remove();
            for (Map.Entry<String, TreeNode> e : n.children.entrySet()) {
                TreeNode v = e.getValue();
                if (!visited.contains(v)) {
                    queue.add(v);
                }
                if (v.children.isEmpty()) {
                    String s = e.getKey();
                    chars.add(s.substring(s.length() - 1));
                }
            }
            visited.add(n);
        }
        return chars;
    }

    public String nodeValue(TreeNode n) {
        String s = "";
        LinkedList<String> stack = new LinkedList<>();
        //stack.push(n);
        while (n.parent != null) {
            String e = getEdge(n.parent, n);
            stack.push(e);
            n = n.parent;
        }
        while (!stack.isEmpty()) {
            //System.out.println("peek: " + stack.peekFirst());
            s = stack.pop() + s;
            //System.out.println("s: " + s);
        }
        return s;
    }


    private String getEdge(TreeNode parent, TreeNode child) {
        for (Map.Entry<String, TreeNode> edge : parent.children.entrySet()) {
            if (edge.getValue() == child)
                return edge.getKey();
        }
        return null;
    }

    public boolean hasSuffix(String str) {
        TreeNode node = root;
        while (!node.children.isEmpty()) {
            List<String> e = getEdgeWithSameFirstChar(node.children, str);
            if (e.isEmpty())
                return false;
            String edge = e.get(0);
            if (str.equals(edge) && isEndOfSuffix(node.children.get(edge))) {
                return true;
            } else if (str.equals(edge.substring(0, edge.length() - 1)) && edgeContainsTerminus(edge)) {
                return true;
            } else if (str.startsWith(edge)) {
                node = node.children.get(edge);
                str = str.substring(edge.length());
            } else {
                return false;
            }
        }
        return false;
    }

    private boolean isEndOfSuffix(TreeNode node) {
        if (node.children.isEmpty())
            return true;
        return node.children.keySet().stream()
                .anyMatch(k -> terminatingChars.contains(k));
    }

    private boolean edgeContainsTerminus(String edge) {
        return terminatingChars.stream().anyMatch(c -> c.equals(edge.substring(edge.length() - 1)));
    }

    public static Integer getLastMatchingIndex(String s1, String s2) {
        char[] chars1 = s1.toCharArray();
        char[] chars2 = s2.toCharArray();
        int i = 0;
        while (i < chars1.length && i < chars2.length && chars1[i] == chars2[i]) {
            i++;
        }
        return i - 1;
    }

    public List<String> getEdgeWithSameFirstChar(HashMap<String, TreeNode> edges, String str) {
        return edges.keySet().stream()
                .filter(s -> s.startsWith(str.substring(0, 1)))
                .collect(Collectors.toList());
    }

    public Integer countNodes() {
        Set<TreeNode> visited = new HashSet<>();
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        Integer nodes = 0;
        //visited.add(node);
        while (!queue.isEmpty()) {
            TreeNode n = queue.remove();
            n.children.values().stream().forEach(i -> queue.add(i));
            nodes++;
            visited.add(n);
        }
        return nodes;
    }

    public List<TreeNode> getCommonSubStringNodes() {
        Set<TreeNode> visited = new HashSet<>();
        Queue<TreeNode> queue = new LinkedList<>();
        List<TreeNode> results = new ArrayList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            TreeNode n = queue.remove();
            n.children.values().stream().forEach(i -> queue.add(i));
            if (n.checkChar == currentTermChar() && n.parent != null)
                results.add(n);
            visited.add(n);
        }
        return results;
    }

    public Integer countCommonSubStrings() {
        Set<TreeNode> visited = new HashSet<>();
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        Integer nodes = 0;
        //visited.add(node);
        while (!queue.isEmpty()) {
            TreeNode n = queue.remove();
            n.children.values().stream().forEach(i -> queue.add(i));
            if (n.checkChar == currentTermChar())
                nodes++;
            visited.add(n);
        }
        return nodes;
    }

    public String currentTermChar() {
        return terminatingChars.get(terminatingChars.size() - 1);
    }

    public String previousTermChar() {
        if (terminatingChars.size() == 1) {
            return terminatingChars.get(0);
        }
        return terminatingChars.get(terminatingChars.size() - 2);
    }

    private void setStatus(TreeNode n) {
        if (n.checkChar == previousTermChar()) {
            n.checkChar = currentTermChar();
            //n.commonToAll = true;
        }
    }

}
