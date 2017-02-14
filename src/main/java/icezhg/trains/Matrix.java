package icezhg.trains;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Created by zhongjibing on 2017/2/14.
 */
public class Matrix implements Serializable {
    private static final long serialVersionUID = 702359154428466177L;

    private int rank;

    private int[][] array;

    public Matrix(int rank) {
        if (rank < 0) {
            throw new IllegalArgumentException("rank should not be negative");
        }
        this.rank = rank;
        this.array = new int[rank][rank];

        for (int i = 0; i < rank; i++) {
            System.out.println(Arrays.toString(array[i]));
        }
    }

    public Matrix() {
        this(0);
    }

    public int getRank() {
        return rank;
    }

    public int[][] getArray() {
        return array;
    }

    public int[] getRow(int idx) {
        if (idx < 0 || idx > rank - 1) {
            throw new IllegalArgumentException("idx is out of range");
        }

        int[] row = new int[rank];
        System.arraycopy(array[idx], 0, row, 0, rank);
        return row;
    }

    public int[] getCol(int idx) {
        if (idx < 0 || idx > rank - 1) {
            throw new IllegalArgumentException("idx is out of range");
        }

        int[] col = new int[rank];
        for (int i = 0; i < rank; i++) {
            col[i] = array[i][idx];
        }
        return col;
    }

    private int increaseRank(int newRank) {
        if (rank < newRank) {
            int[][] newArray = new int[newRank][newRank];
            for (int i = 0; i < rank; i++) {
                System.arraycopy(array[i], 0, newArray[i], 0, array[i].length);
            }

            this.rank = newRank;
            this.array = newArray;
        }
        return this.rank;
    }

    public void setVal(int rowIdx, int colIdx, int value) {
        int edge = rowIdx > colIdx ? rowIdx : colIdx;
        if (edge >= rank) {
            increaseRank(edge + 1);
        }

        array[rowIdx][colIdx] = value;
    }

    public int getVal(int rowIdx, int colIdx) {
        if (rowIdx < 0 || rowIdx > rank - 1) {
            throw new IllegalArgumentException("rowIdx is out of range");
        }
        if (colIdx < 0 || colIdx > rank - 1) {
            throw new IllegalArgumentException("colIdx is out of range");
        }

        return array[rowIdx][colIdx];
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("[");
        for (int i = 0; i < rank; i++) {
            builder.append(Arrays.toString(array[i]));
            if (i < rank - 1) {
                builder.append(", ");
            }
        }
        builder.append("]");
        return builder.toString();
    }

}
