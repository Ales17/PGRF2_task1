package model;

public class Part { ////

    private int startIndex; // počáteční index v index bufferu
    private int count ; // počet primitiv, nikoli indexů
    private TopologyType type; // typ topologie
    public Part(TopologyType type, int index, int count) {
        this.type = type;
        this.startIndex = index;
        this.count = count;
    }
    public TopologyType getType() {
        return type;
    }

    public int getStartIndex() {
        return startIndex;
    }

    public int getCount() {
        return count;
    }


}
