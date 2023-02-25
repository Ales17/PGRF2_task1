package model;

public class Part {

    private int index ; // počáteční index v index bufferu
    private int count ; // počet primitiv, nikoli indexů
    private TopologyType type; // typ topologie
    public Part(TopologyType type, int index, int count) {
        this.type = type;
        this.index = index;
        this.count = count;
    }
    public TopologyType getType() {
        return type;
    }

    public int getIndex() {
        return index;
    }

    public int getCount() {
        return count;
    }

}
