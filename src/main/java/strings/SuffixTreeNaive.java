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

    public SuffixTreeNaive(){}

    public void addString(String s){
        String termChar = nextTerminatingChar();
        terminatingChars.add(termChar);
        s += termChar;
        allSuffixes(s).stream().forEach(sfx -> addSuffix(sfx));
    }

    private String nextTerminatingChar(){
        if (terminatingChars.isEmpty())
            return new String(Character.toChars(FIRST_TERM_CHAR_VALUE));
        String lastUsed = terminatingChars.get(terminatingChars.size() - 1);
        return new String(Character.toChars(lastUsed.codePointAt(0) + 1));
    }

    public static Set<String> allSuffixes(String s){
        return IntStream.range(0, s.length()).boxed()
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
    */
    public TreeNode addSuffix(String str){
        TreeNode node = root;
        if (node.children.isEmpty()) {
            node.children.put(str, new TreeNode());
            return node.children.get(str);
        }
        while (!node.children.isEmpty()) {
            List<String> e = getEdgeWithSameFirstChar(node.children, str);
            //if no edges start with the first char -> add a new one
            if (e.isEmpty()) {
                node.children.put(str, new TreeNode());
                return node.children.get(str);
            }
            //else find edge that starts with first char
            String edge = e.get(0);
            //if it's totally contained with str, keep traversing
            if (str.contains(edge)) {
                node = node.children.get(edge);
                str = str.substring(edge.length());
            }
            // make sure it doesn't already exist
            else if (str.equals(edge)){
                throw new IllegalArgumentException("This edge already exists: " + str);
            }
            //otherwise, split the edge
            // - if the edge here fully contains the string just insert the new node
            // - otherwise insert the new node and attach another new node to it, with the remainder from str
            else {
                TreeNode oldNode = node.children.get(edge);
                TreeNode newNode = new TreeNode();
                node.children.remove(edge);
                String newEdge = edge.substring(0, getLastMatchingIndex(str, edge) + 1);
                String edgeRemainder = edge.substring(getLastMatchingIndex(str, edge) + 1);
                node.children.put(newEdge, newNode);
                newNode.children.put(edgeRemainder, oldNode);
                if (!edge.contains(str)){
                    String strRemainder = str.substring(getLastMatchingIndex(str, edge) + 1);
                    newNode.children.put(strRemainder, new TreeNode());
                    return newNode.children.get(strRemainder);
                }
                return newNode;
            }
        }
        return null;
    }

    //TODO: just use a sortedSet for the terminatingChars?
    // - check that it sorts by codepoint for single-char strings
    public boolean subTreeContainsAllInputs(TreeNode node){
        HashSet<String> tc = new HashSet<>(terminatingChars);
        return getAllTerminatingChars(node).equals(tc);
    }

    public Set<String> getAllTerminatingChars(TreeNode node){
        Set<String> chars = new HashSet<String>();
        Set<TreeNode> visited = new HashSet<>();
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(node);
        //visited.add(node);
        while (!queue.isEmpty()){
            TreeNode n = queue.remove();
            for ( Map.Entry<String,TreeNode> e : n.children.entrySet()){
                TreeNode v = e.getValue();
                if(!visited.contains(v)){
                    queue.add(v);
                }
                if (v.children.isEmpty()){
                    String s = e.getKey();
                    chars.add(s.substring(s.length() - 1));
                }
            }
            visited.add(n);
        }
        return chars;
    }

    public boolean hasSuffix(String str){
        TreeNode node = root;
        while (!node.children.isEmpty()){
            List<String> e = getEdgeWithSameFirstChar(node.children, str);
            if (e.isEmpty())
                return false;
            String edge = e.get(0);
            if (str.equals(edge) && isEndOfSuffix(node.children.get(edge))){
                return true;
            } else if (str.equals(edge.substring(0, edge.length() - 1)) && edgeContainsTerminus(edge)){
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

    private boolean isEndOfSuffix(TreeNode node){
        if (node.children.isEmpty())
            return true;
        return node.children.keySet().stream()
                .anyMatch(k -> terminatingChars.contains(k));
    }

    private boolean edgeContainsTerminus(String edge){
        return terminatingChars.stream().anyMatch(c -> c.equals(edge.substring(edge.length()-1)));
    }

    public static Integer getLastMatchingIndex(String s1, String s2){
        char[] chars1 = s1. toCharArray();
        char[] chars2 = s2.toCharArray();
        int i = 0;
        while(i < chars1.length && i < chars2.length && chars1[i] == chars2[i]){
            i++;
        }
        return i - 1;
    }

    public List<String> getEdgeWithSameFirstChar(HashMap<String, TreeNode> edges, String str){
        return edges.keySet().stream()
                .filter(s -> s.startsWith(str.substring(0,1)))
                .collect(Collectors.toList());
    }

    public Integer countNodes(){
        Set<TreeNode> visited = new HashSet<>();
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        Integer nodes = 0;
        //visited.add(node);
        while (!queue.isEmpty()){
            TreeNode n = queue.remove();
            n.children.values().stream().forEach(i -> queue.add(i));
            nodes++;
            visited.add(n);
        }
        return nodes;
    }

}
