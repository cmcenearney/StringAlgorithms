package util;

import java.util.*;
import java.util.stream.Collectors;

public class Utils {

    static final Comparator<String> compareByLengthDesc = ((s1, s2) -> s2.length() - s1.length());

    public static String bruteForceLCS(List<String> inputs){
        //TreeSet<String> commonSubStrings = new TreeSet<>(compareByLength);
        Set<String> commonSubStrings = new HashSet<>();
        List<HashSet<String>> subStringSets = new LinkedList<HashSet<String>>();
        for (String s : inputs) {
            HashSet<String> subStrings = new HashSet<>();
            for (int i = 0; i < s.length(); i++){
                for (int j = i+1; j <= s.length();j++){
                    String subString = s.substring(i,j);
                    subStrings.add(subString);
                }
            }
            subStringSets.add(subStrings);
        }
        commonSubStrings.addAll(subStringSets.get(0));
        for (int i = 1; i < subStringSets.size(); i++){
            commonSubStrings.retainAll(subStringSets.get(i));
        }
        List<String> sorted = commonSubStrings.stream()
                .sorted(compareByLengthDesc)
                .collect(Collectors.toList());
        return sorted.get(0);
    }


    public static List<Fasta> parseRawFastas(String raw){
        List<Fasta> f = new ArrayList<>();
        for (String s : raw.split(">")){
            if (s.equals(""))
                continue;
            String[] lines = s.split("\n");
            String id = lines[0];
            String seq = "";
            for (int i = 1; i < lines.length;i++){
                seq += lines[i];
            }
            f.add(new Fasta(seq,id));
        }
        return f;
    }

    public static List<String> getRawFastaStrings(String raw){
        return parseRawFastas(raw).stream()
                .map(f -> f.getSeq())
                .collect(Collectors.toList());
    }
}

