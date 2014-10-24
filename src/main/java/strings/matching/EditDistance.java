package strings.matching;

import java.util.Arrays;

public class EditDistance {

    public static int levenshtein(String s1, String s2){
        char[] s = s1.toCharArray();
        char[] t = s2.toCharArray();
        int m = s1.length();
        int n = s2.length();
        if (n == 0)
            return m;
        if (m == 0)
            return n;
        int[][] matrix = new int[m+1][n+1];
        for (int i = 1; i <= m; i++){
            matrix[i][0] = i;
        }
        for (int j = 1; j <= n; j++){
            matrix[0][j] = j;
        }
        for (int j = 1; j <= n; j++){
            for (int i = 1; i <= m; i++){
                if (s[i-1] == t[j-1]){
                    matrix[i][j] = matrix[i-1][j-1];
                } else {
                    matrix[i][j] = minimum(
                            matrix[i-1][j] + 1,  // a deletion
                            matrix[i][j-1] + 1,  // an insertion
                            matrix[i-1][j-1] + 1 // a substitution
                    );
                }
            }
        }
        return matrix[m][n];
    }

    private static int minimum(Integer... items){
        Arrays.sort(items);
        return items[0];
    }
}
