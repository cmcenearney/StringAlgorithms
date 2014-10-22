package strings.matching;

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

    public boolean matches(String s){
        for (char c : s.toCharArray()){
            nextChar(c);
        }
        return matched;
    }

    public Boolean getMatched() {
        return matched;
    }

}
