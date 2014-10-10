package util;

public class Fasta {

    private String id;
    private String seq;

    public Fasta(String seq, String id) {
        this.seq = seq;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getSeq() {
        return seq;
    }
}