package strings.matching;

public class FundamentalPreprocessor {

    String pattern;

    public FundamentalPreprocessor(String pattern){
        this.pattern = pattern;
    }

    public int[] generateZTable(){
        char[] chars = pattern.toCharArray();
        int[] zs = new int[pattern.length()];
        int l = 0;
        int r = 0;
        //int l,r = 0;
        //begin by finding the first z-box
        if (chars[0] == chars[1]){
            int j = 0; int k = 1;
            while (chars[j] == chars[k]){
                j++; k++;
            }
            zs[1] = j;
        } else {
            zs[1] = 0;
        }
        if(zs[1] > 0) {
            r = zs[1];
            l = 0;
        }
        //now process the rest of the string, from position 2 to the end
        for (int i = 2; i < pattern.length(); i++){
            if (i > r){
                int j = 0; int k = i;
                while (chars[j] == chars[k]){
                    j++; k++;
                }
                if (k - i > 0){
                    zs[i] = j;
                    r = i + zs[i] - 1;
                    l = i;
                }
            } else {
                int kPrime = i - l;
                assert(chars[i] == chars[kPrime]);
                int beta = r - i;
                if (zs[kPrime] <= beta){
                    zs[i] = zs[kPrime];
                } else {
                    int p = beta + 1; int q = r + 1;
                    while (chars[p] == chars[q]){
                        p++; q++;
                    }
                    zs[i] = q - i;
                    r = q - 1;
                    l = i;
                }
            }
        }
        return zs;
    }

    //z tables with |S|**2 running time
    public int[] generateZTableExp(){
        int[] zs = new int[pattern.length()];
        char[] chars = pattern.toCharArray();
        //aabcaabxaaz
        for (int i = 1; i < pattern.length(); i++){
            int j = 0;
            int k = i;
            while (k < pattern.length() && chars[j] == chars[k]){
                j++; k++;
            }
            zs[i] = j;
        }
        return zs;
    }
}
