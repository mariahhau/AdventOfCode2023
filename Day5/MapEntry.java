public class MapEntry {
    private long dRangeStart;
    private long sRangeStart;
    private long rangeLength;

    public MapEntry(long dRangeStart, long sRangeStart, long rangeLength) {
        this.dRangeStart = dRangeStart;
        this.sRangeStart = sRangeStart;
        this.rangeLength = rangeLength;
    }

    public long getDRangeStart() {
        return dRangeStart;
    }

    public long getSRangeStart() {
        return sRangeStart;
    }

    public long getRangeLength() {
        return rangeLength;
    }

    public boolean isInRange(long source) {
        return source >= sRangeStart && source <= getSRangeEnd();
    }

    public long map(long source) {
        if (isInRange(source)) {
            long temp = source - sRangeStart;
            return dRangeStart + temp;

        } else {
            return source;
        }
    }

    public long getDRangeEnd() {
        return dRangeStart + rangeLength - 1;
    }

    public long getSRangeEnd() {
        return sRangeStart + rangeLength - 1;
    }

    public String toString() {
        return "\ndestination range start:" + dRangeStart + "\n" +
                "source range start:" + sRangeStart + "\n" +
                "range length:" + rangeLength + "\n";

    }

}
