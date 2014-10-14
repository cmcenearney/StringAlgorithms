package strings;

import org.junit.Test;
import util.Fasta;
import util.Utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.IntStream;

import static org.junit.Assert.*;

public class SuffixTreeNaiveTest {

    @Test
    public void testAllSuffixes(){
        String s = "abcdefg$";
        HashSet<String> expected = new HashSet<String>(Arrays.asList("abcdefg$", "bcdefg$", "cdefg$", "defg$", "efg$","fg$", "g$"));
        assertEquals(expected, SuffixTreeNaive.allSuffixes(s));
    }

    @Test
    public void testTerminatingCharProgession(){
        SuffixTreeNaive t = new SuffixTreeNaive();
        t.addString("one");
        String expected = new String(Character.toChars(0x7b));
        assertEquals(expected, t.terminatingChars.get(t.terminatingChars.size() - 1));
        t.addString("two");
        expected = new String(Character.toChars(0x7c));
        assertEquals(expected, t.terminatingChars.get(t.terminatingChars.size() - 1));
    }

    /*
    >Rosalind_1
GATTACA
>Rosalind_2
TAGACCA
>Rosalind_3
ATACA
     */

    @Test
    public void testAllNodesAreCommonToAllWithSingleInput(){
        SuffixTreeNaive t = new SuffixTreeNaive();
        t.addString("GATTACA");
        List<TreeNode> lcs = t.getCommonSubStringNodes();
        t.getCommonSubStringNodes().stream()
                .forEach(n -> assertTrue(n.commonToAll));
    }

    @Test
    public void testNodeValueSimple(){
        SuffixTreeNaive t = new SuffixTreeNaive();
        t.addString("GA");
        t.addString("AT");
        List<TreeNode> lcs = t.getCommonSubStringNodes();
        for(TreeNode n : lcs){
            String v = t.nodeValue(n);
            System.out.println(v);
        }

    }


    @Test
    public void testSmallCase2(){
        SuffixTreeNaive t = new SuffixTreeNaive();
        t.addString("GATTACA");
        t.addString("TAGACCA");
        t.addString("ATACA");
        List<TreeNode> lcs = t.getCommonSubStringNodes();
        t.getCommonSubStringNodes().stream()
                .forEach(n -> System.out.println(t.nodeValue(n)));
    }

    @Test
    public void testWithRosalindData() throws IOException {
        String raw = new String(Files.readAllBytes(Paths.get("src/test/resources/rosalind_lcsm.txt")));
        List<Fasta> inputs = Utils.parseRawFastas(raw);
        System.out.println(inputs.size());
        SuffixTreeNaive t = new SuffixTreeNaive();
        for(Fasta f : inputs){
            t.addString(f.getSeq());
        }
        System.out.println(t.terminatingChars.size());
        assertEquals(true, t.hasSuffix("GACCAAACCGAACCTC"));
        assertEquals(true, t.hasSuffix("AGTTCGTCTCCCTAACGTGGTGCTGTTGTCATCTGTAAAG"));
        String sfx = "TGTCGTTAAAATTGATAAGCATAGACAGGTCTTTAGCGACACCTCAGAAATCACTTCAGCAGTTCGTCTCCCTAACGTGGTGCTGTTGTCATCTGTAAAG";
        assertTrue(t.hasSuffix(sfx));
        System.out.println(t.countNodes());
        System.out.println(t.countCommonSubStrings());
    }

    @Test
    public void testing(){
        String s = "abcdefg";
        String t = "abc";
        System.out.println(s.substring(t.length()));
        System.out.println(SuffixTreeNaive.getLastMatchingIndex("onesies", "onerous"));
        SuffixTreeNaive tr = new SuffixTreeNaive();
        tr.addString(s);
        tr.addString("abde");
        assertEquals(true, tr.hasSuffix("defg"));
        tr.addString("abdedefg");
        tr.addString("defghaha");
        assertEquals(true, tr.hasSuffix("defg"));
        assertEquals(false, tr.hasSuffix("def"));
        System.out.println(tr.getAllTerminatingChars(tr.root));
        assertEquals(true, tr.subTreeContainsAllInputs(tr.root));
    }

    @Test
    public void testUnicodeLiterals(){
        System.out.println("\u00ad");
        System.out.println(Integer.toHexString(123));
        System.out.println(0x7b);
        System.out.println(0xFFFF);
        System.out.println(new String(Character.toChars(0x10FFFF)));
        String s = "abcdefg";
        System.out.print(  s.substring(0,s.length()));
        //IntStream.range(0x7b, 0xff).forEachOrdered(i -> System.out.println( "\\u00" + Integer.toHexString(i) ));
    }

    @Test
    public void exp(){
        String t = "{a";
        System.out.print( t.substring(t.length() - 1));
    }


}