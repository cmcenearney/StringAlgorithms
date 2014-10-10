package strings;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SuffixTreeNaive {

    static final String terminatingChar = "$";
    int p = -1;
    HashMap<String, SuffixTreeNaive> children = new HashMap<>();

    public SuffixTreeNaive(){}

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

    public SuffixTreeNaive addNode(String str){
        SuffixTreeNaive node = this;
        if (node.children.isEmpty()) {
            node.children.put(str, new SuffixTreeNaive());
            return node.children.get(str);
        }
        while (!node.children.isEmpty()) {
            List<String> e = getEdgeWithSameFirstChar(node.children, str);
            //if no edges start with the first char -> add a new one
            if (e.isEmpty()) {
                node.children.put(str, new SuffixTreeNaive());
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
                SuffixTreeNaive oldNode = node.children.get(edge);
                SuffixTreeNaive newNode = new SuffixTreeNaive();
                node.children.remove(edge);
                String newEdge = edge.substring(0, getLastMatchingIndex(str, edge) + 1);
                String edgeRemainder = edge.substring(getLastMatchingIndex(str, edge) + 1);
                node.children.put(newEdge, newNode);
                newNode.children.put(edgeRemainder, oldNode);
                if (!edge.contains(str)){
                    String strRemainder = str.substring(getLastMatchingIndex(str, edge) + 1);
                    newNode.children.put(strRemainder, new SuffixTreeNaive());
                    return newNode.children.get(strRemainder);
                }
                return newNode;
            }
        }
        return null;
    }

    public SuffixTreeNaive findNode(String str){
        SuffixTreeNaive node = this;
        while (!node.children.isEmpty()){
            for (String edge : node.children.keySet()){
                if (str.contains(edge)){
                    node = node.children.get(edge);
                    str = str.substring(edge.length());
                } else if (str.equals(edge)){
                    return node.children.get(str);
                }
            }
        }
        return null;
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

    public List<String> getEdgeWithSameFirstChar(HashMap<String, SuffixTreeNaive> edges, String str){
        return edges.keySet().stream()
                .filter(s -> s.startsWith(str.substring(0,1)))
                .collect(Collectors.toList());
    }


}
