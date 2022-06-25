package math;

public interface MatrixRunner<T extends Number> {
    T run(int row, int column, T actualValue);
}
