package math;

public interface MatrixInterface<T extends Number> {
    int getRowsCount();

    int getColumnsCount();

    T getValueAt(int row, int column);

    void setValueAt(int row, int column, T value);

    void addValue(T value);

    void substractValue(T value);
}
