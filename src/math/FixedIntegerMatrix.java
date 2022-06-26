package math;

import java.util.ArrayList;
import java.util.List;

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

    public FixedIntegerMatrix(int rows, int columns, Integer value) {
        if (rows <= 0) {
            throw new IllegalArgumentException("rows have to be greater than 0");
        } else if (columns <= 0) {
            throw new IllegalArgumentException("columns have to be greater than 0");
        }

        values = new Integer[rows][columns];
        fillWithValue(value);
    }

    public FixedIntegerMatrix(Integer[]... columnValues) {
        values = new Integer[columnValues.length][columnValues[0].length];

        runColumnsFirst((row, column, actualValue) -> columnValues[row][column]);
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
    public Integer[] getRowValues(int row) {
        if (row < 0 || row >= getRowsCount()) {
            throw new IllegalArgumentException("row have to be greater than 0 and less than %d\n".formatted(getRowsCount()));
        }

        List<Integer> values = new ArrayList<>();

        runColumnsFirst((r, column, actualValue) -> {
            if (r == row) {
                values.add(actualValue);
            }

            return null;
        });

        return values.toArray(Integer[]::new);
    }

    @Override
    public Integer[] getColumnsValues(int column) {
        if (column < 0 || column >= getRowsCount()) {
            throw new IllegalArgumentException("column have to be greater than 0 and less than %d\n".formatted(getColumnsCount()));
        }

        List<Integer> values = new ArrayList<>();

        runColumnsFirst((row, c, actualValue) -> {
            if (c == column) {
                values.add(actualValue);
            }

            return null;
        });

        return values.toArray(Integer[]::new);
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
    public void addValueToRow(int row, Integer value) {
        runColumnsFirst((r, column, actualValue) -> {
            if (r == row) {
                return actualValue + value;
            } else {
                return null;
            }
        });
    }

    @Override
    public void addValueToColumn(int column, Integer value) {
        runColumnsFirst((row, c, actualValue) -> {
            if (c == column) {
                return actualValue + value;
            } else {
                return null;
            }
        });
    }

    @Override
    public void subtractValue(Integer value) {
        runColumnsFirst((row, column, actualValue) -> actualValue - value);
    }
}
