package strings.trees;

import org.junit.Test;

public class UkkonenTest {

    @Test
    public void basicCase(){
        Ukkonen t = new Ukkonen();
        t.construct("axabx");
        System.out.println( t.countNodes());
    }

    @Test
    public void basicCase2(){
        Ukkonen t = new Ukkonen();
        t.construct("axabxb");
        System.out.println( t.countNodes());
    }

}