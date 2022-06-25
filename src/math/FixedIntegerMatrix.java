package math;

public class FixedIntegerMatrix extends AbstractMatrix<Integer> {
    private final Integer[][] values;

    public FixedIntegerMatrix(int rows, int columns) {
        if (rows <= 0) {
            throw new IllegalArgumentException("rows have to be greater than 0");
        } else if (columns <= 0) {
            throw new IllegalArgumentException("columns have to be greater than 0");
        }

        values = new Integer[rows][columns];
        fillWithValue(0);
    }

    private boolean isNotValidPosition(int row, int column) {
        return (row < 0 || row >= getRowsCount()) || (column < 0 || column >= getColumnsCount());
    }

    @Override
    public int getRowsCount() {
        return values.length;
    }

    @Override
    public int getColumnsCount() {
        return values[0].length;
    }

    @Override
    public Integer getValueAt(int row, int column) {
        if (isNotValidPosition(row, column)) {
            throw new IllegalArgumentException("row or column out of range");
        }

        return values[row][column];
    }

    @Override
    public void setValueAt(int row, int column, Integer value) {
        if (isNotValidPosition(row, column)) {
            throw new IllegalArgumentException("row or column out of range");
        }

        values[row][column] = value;
    }

    @Override
    public void addValue(Integer value) {
        runColumnsFirst((row, column, actualValue) -> actualValue + value);
    }

    @Override
    public void subtractValue(Integer value) {
        runColumnsFirst((row, column, actualValue) -> actualValue - value);
    }
}
