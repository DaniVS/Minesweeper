package minesweeper;

public class Cell {
    private boolean isMine;

    private boolean isMarked;
    private boolean isOpened;
    public Cell(int row, int col){
        this.row = row;
        this.col = col;
        this.isMine = false;
        this.isMarked = false;
        this.isOpened = false;
    }

    private final int row;
    private final int col;

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public void setMine(boolean isMine){
        this.isMine = isMine;
    }

    public boolean isMine() {
        return isMine;
    }

    public boolean isMarked() {
        return isMarked;
    }

    public void setMarked(boolean marked) {
        isMarked = marked;
    }

    public boolean isOpened() {
        return isOpened;
    }

    public void setOpened(boolean opened) {
        isOpened = opened;
    }
}
