package math;

public interface MatrixInterface<T extends Number> {
    int getRowsCount();

    int getColumnsCount();

    T getValueAt(int row, int column);

    T[] getRowValues(int row);

    T[] getColumnsValues(int column);

    void setValueAt(int row, int column, T value);

    void addValue(T value);

    void addValueToRow(int row, T value);

    void addValueToColumn(int column, T value);

    void subtractValue(T value);
}
