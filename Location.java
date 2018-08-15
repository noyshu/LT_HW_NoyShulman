import static java.lang.Math.*;

public class Location {
    //members
    private final int row;
    private final int col;

    //constructors
    public Location(int Row, int Col){
        row = Row;
        col = Col;
    }

    //member functions
    public int getCol() {
        return col;
    }

    public int getRow() {
        return row;
    }

    public boolean equals(Location location) {
        return (getRow() == location.getRow() && getCol() == location.getCol());
    }

    public Float secondNormDist(Location y){
        return (float)sqrt(pow(row - y.getRow(),2) + pow(col - y.getCol(),2));
    }
}
