package strings;

public class StringAmaton {

    private Boolean matched = false;
    private int cntr = 0;
    char[] pattern;
    public StringAmaton(String pattern){
        this.pattern = pattern.toCharArray();
    }

    public void nextChar(char ch){
        if (!matched){
            if (pattern[cntr] == ch){
                cntr++;
            }
            if (cntr == pattern.length){
                matched = true;
            }
        }
    }


    public Boolean getMatched() {
        return matched;
    }

}
