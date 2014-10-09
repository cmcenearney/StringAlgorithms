package strings;

import org.junit.Test;

public class FundamentalPreprocessorTest {

    @Test
    public void testGenerateZTableCase1() {
        String pattern = "aabcdef";
        FundamentalPreprocessor f = new FundamentalPreprocessor(pattern);
        int[] zTable = f.generateZTable();
        assert( zTable[1] == 1);
    }

    @Test
    public void testGenerateZTableCase2() {
        String pattern = "aaaadef";
        FundamentalPreprocessor f = new FundamentalPreprocessor(pattern);
        int[] zTable = f.generateZTable();
        assert( zTable[1] == 3);
    }

    @Test
    public void testGenerateZTable(){
        String pattern = "aabcaabxaaz";
        FundamentalPreprocessor f = new FundamentalPreprocessor(pattern);
        int[] zTable = f.generateZTable();
        for (int i : zTable) {
            System.out.println(i);
        }
        assert( zTable[0] == 0 );
        assert( zTable[1] == 1 );
        assert( zTable[4] == 3 );
        assert( zTable[5] == 1 );
        assert( zTable[6] == 0 );
        assert( zTable[7] == 0 );
        assert( zTable[8] == 2 );
    }

//    @Test
//    public void testGenerateZTableLonger(){
//        String pattern = "aaabcaaabxaaz";
//        FundamentalPreprocessor f = new FundamentalPreprocessor(pattern);
//        int[] zTable = f.generateZTable();
//        for (int i : zTable) {
//            System.out.println(i);
//        }
//    }

    //abcde
    //abcdebabddabcdeff
    @Test
    public void testGenerateZTableExp(){
        String pattern = "aabcaabxaaz";
        FundamentalPreprocessor f = new FundamentalPreprocessor(pattern);
        int[] zTable = f.generateZTableExp();
        assert( zTable[0] == 0 );
        assert( zTable[1] == 1 );
        assert( zTable[2] == 0 );
        assert( zTable[3] == 0 );
        assert( zTable[4] == 3 );
        assert( zTable[5] == 1 );
        assert( zTable[6] == 0 );
        assert( zTable[7] == 0 );
        assert( zTable[8] == 2 );
    }

}