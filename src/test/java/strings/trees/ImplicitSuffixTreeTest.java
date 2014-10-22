package strings.trees;

import org.junit.Test;

public class ImplicitSuffixTreeTest {

    @Test
    public void testImplicitSuffixTreeHasExpectedNodes(){
        ImplicitSuffixTree t = new ImplicitSuffixTree();
        t.addString("xabxa");
        assert(t.countNodes() == 4);
    }

    @Test
    public void testImplicitSuffixTreeHasExpectedNodes2(){
        ImplicitSuffixTree t = new ImplicitSuffixTree();
        t.addString("axabx");
        assert(t.countNodes() == 6);
    }


}