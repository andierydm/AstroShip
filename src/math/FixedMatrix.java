package math;

public abstract class FixedMatrix extends AbstractMatrix<Integer> {
    private final Integer[][] values;

    private FixedMatrix(int rows, int columns) {
        if (rows <= 0) {
            throw new IllegalArgumentException("rows have to be greater than 0");
        } else if (columns <= 0) {
            throw new IllegalArgumentException("columns have to be greater than 0");
        }

        values = new Integer[rows][columns];
    }

    private boolean isPositionValid(int row, int column) {
        return (row >= 0 && row < getRowsCount()) && (column >= 0 && column < getColumnsCount());
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
        if (!isPositionValid(row, column)) {
            throw new IllegalArgumentException("row or column out of range");
        }

        return values[row][column];
    }

    @Override
    public void setValueAt(int row, int column, Integer value) {
        if (!isPositionValid(row, column)) {
            throw new IllegalArgumentException("row or column out of range");
        }

        values[row][column] = value;
    }

    @Override
    public void addValue(Integer value) {
        runColumnsFirst((row, column, actualValue) -> {

            return null;
        });
    }
}
