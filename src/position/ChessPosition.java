package position;

public class ChessPosition implements Position{
    private int row;
    private int column;

    public ChessPosition(int row, int column){
        setPosition(row , column);

    }
    public  boolean isValidPosition (int row,int column) {
        return (row >=1 && row <=8 && column >=1  && column <=8 );
    }
    public void setPosition(int row,int column) {

        if (isValidPosition(row,column)){
            this.row = row-1;
            this.column = column-1;
        }
        else
            throw new IndexOutOfBoundsException();

    }

    public int getRow() {
        return this.row;
    }

    public int getColumn() {
        return this.column;
    }

    @Override
    public String toString() {
        return "("+ (getRow()+1)+","+(getColumn()+1) +")";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        else if (!(obj instanceof Position))
            return false;

        return this.row == ((Position) obj).getRow() && this.column == ((Position) obj).getColumn();

    }
}
