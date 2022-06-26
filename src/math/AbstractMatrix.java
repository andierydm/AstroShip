package math;

public abstract class AbstractMatrix<T extends Number> implements MatrixInterface<T> {
    public void runColumnsFirst(MatrixRunner<T> runner) {
        if (runner == null) {
            throw new IllegalArgumentException("runner can't be null");
        }

        int row = 0;
        int column = 0;

        while (true) {
            final T value = runner.run(row, column, getValueAt(row, column));

            if (value != null) {
                setValueAt(row, column, value);
            }

            column++;

            if (column == getColumnsCount()) {
                row++;
                if (row == getRowsCount()) {
                    break;
                }
                column = 0;
            }
        }
    }

    public void fillWithValue(T value) {
        runColumnsFirst((row, column, actualValue) -> value);

    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();

        runColumnsFirst((row, column, actualValue) -> {
            sb.append(actualValue);

            if (column + 1 == getColumnsCount()) {
                sb.append("\n");
            } else {
                sb.append(", ");
            }

            return null;
        });

        return sb.toString();
    }
}
