package minesweeper;

import java.util.*;

public class Field {
    final int SIZE = 9;
    final char UNKNOWN = '.';
    final char EMPTY = '/';
    final char MINE = 'X';
    final char MARKED = '*';

    Cell[][] cells;
    List<Cell> minesArray = new ArrayList<>();
    Stack<Cell> stack = new Stack<>();

    public Field(int mines) {
        cells = new Cell[SIZE][SIZE];
        int minesQuantity = 0;

        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells.length; j++) {
                cells[i][j] = new Cell(i, j);
            }
        }

        while (minesQuantity < mines) {
            Random random = new Random();
            int row = random.nextInt(SIZE);
            int col = random.nextInt(SIZE);
            if (!cells[row][col].isMine()) {
                cells[row][col].setMine(true);
                minesArray.add(cells[row][col]);
                minesQuantity++;
            }
        }
    }

    public void printField() {
        System.out.println(" │123456789│\n—│—————————│");
        for (int i = 0; i < cells.length; i++) {
            System.out.print(i + 1 + "|");
            for (int j = 0; j < cells.length; j++) {
                if (cells[i][j].isOpened()) {
                    if (cells[i][j].isMine()) {
                        System.out.print(MINE);
                    } else {
                        int numberOfMine = checkMineAround(i, j);
                        if (numberOfMine == 0) {
                            System.out.print(EMPTY);
                        } else {
                            System.out.print(numberOfMine);
                        }
                    }
                } else if (cells[i][j].isMarked()) {
                    System.out.print(MARKED);
                } else {
                    System.out.print(UNKNOWN);
                }
            }
            System.out.println("|");
        }
        System.out.println("—│—————————│");
    }

    public void checkFirstTurn(int row, int col) {
        if (cells[row][col].isMine()) {
            for (int i = 0; i < (SIZE * SIZE); i++) {
                int findingRow = i / cells.length;
                int findingCol = i % cells.length;
                if (findingRow != row || findingCol != col && (!cells[findingRow][findingCol].isMine())) {
                        cells[findingRow][findingCol].setMine(true);
                        cells[row][col].setMine(false);
                        minesArray.remove(cells[row][col]);
                        minesArray.add(cells[findingRow][findingCol]);
                        break;

                }
            }
        }
    }

    public int checkMineAround(int row, int col) {
        int result = 0;

        if (cells[row][col].isMine()) {
            return -1;
        }

        int upShift = 1;
        int downShift = 1;
        int leftShift = 1;
        int rightShift = 1;

        if (row == 0) {
            upShift = 0;
        }
        if (row == cells.length - 1) {
            downShift = 0;
        }
        if (col == 0) {
            leftShift = 0;
        }
        if (col == cells.length - 1) {
            rightShift = 0;
        }
        for (int i = row - upShift; i <= row + downShift; i++) {
            for (int j = col - leftShift; j <= col + rightShift; j++) {
                if (cells[i][j].isMine()) {
                    result++;
                }
            }
        }
        return result;
    }

    public void checkAllAround(int row, int col) {
        int upShift = 1;
        int downShift = 1;
        int leftShift = 1;
        int rightShift = 1;

        if (row == 0) {
            upShift = 0;
        }
        if (row == cells.length - 1) {
            downShift = 0;
        }
        if (col == 0) {
            leftShift = 0;
        }
        if (col == cells.length - 1) {
            rightShift = 0;
        }
        for (int i = row - upShift; i <= row + downShift; i++) {
            for (int j = col - leftShift; j <= col + rightShift; j++) {
                if (!cells[i][j].isOpened()) {
                    if (checkMineAround(i, j) == 0) {
                        cells[i][j].setOpened(true);
                        stack.push(cells[i][j]);

                    } else if (checkMineAround(i, j) > 0) {
                        cells[i][j].setOpened(true);
                    }
                }
            }
        }
    }


    public void openArea(int row, int col) {
        if (checkMineAround(row, col) > 0) {
            cells[row][col].setOpened(true);
        } else {
            cells[row][col].setOpened(true);
            checkAllAround(row, col);

            while (!stack.empty()) {
                Cell nextCell = stack.pop();
                checkAllAround(nextCell.getRow(), nextCell.getCol());
            }
        }
    }

    public void setAllBombOpened() {
        for (Cell each : minesArray) {
            each.setOpened(true);
        }
    }

    public int markCellAsMine(Cell cell) {
        if (!cell.isOpened()) {
            if (!cell.isMarked()) {
                cell.setMarked(true);
                return 1;
            } else {
                cell.setMarked(false);
                return -1;
            }
        } else {
            System.out.println("There is a number here!");
            return 0;
        }
    }
}
