package system.collision;

import math.FixedIntegerMatrix;

import java.awt.*;
import java.util.Arrays;

public class CollisionShape {
    private final FixedIntegerMatrix fixedIntegerMatrix;

    private CollisionShape(int rows, int columns) {
        fixedIntegerMatrix = new FixedIntegerMatrix(rows, columns);
    }

    private CollisionShape(FixedIntegerMatrix fixedIntegerMatrix) {
        this.fixedIntegerMatrix = fixedIntegerMatrix;
    }

    public void moveShape(int x, int y) {
        fixedIntegerMatrix.addValueToRow(0, x);
        fixedIntegerMatrix.addValueToRow(1, y);
    }

    public boolean isColliding(CollisionShape collisionShape) {
        int xa0 = collisionShape.fixedIntegerMatrix.getValueAt(0, 0);
        int xb0 = collisionShape.fixedIntegerMatrix.getValueAt(0, 1);
        int xa1 = fixedIntegerMatrix.getValueAt(0, 0);
        int xb1 = fixedIntegerMatrix.getValueAt(0, 1);

        int ya0 = collisionShape.fixedIntegerMatrix.getValueAt(1, 0);
        int yb0 = collisionShape.fixedIntegerMatrix.getValueAt(1, 3);
        int ya1 = fixedIntegerMatrix.getValueAt(1, 0);
        int yb1 = fixedIntegerMatrix.getValueAt(1, 3);

        return xa0 > xa1 && xa0 < xb1;
    }

    public Polygon toPolygon() {

        int[] x = Arrays.stream(fixedIntegerMatrix.getRowValues(0)).mapToInt(Integer::intValue).toArray();
        int[] y = Arrays.stream(fixedIntegerMatrix.getRowValues(1)).mapToInt(Integer::intValue).toArray();

        return new Polygon(x, y, 4);
    }

    public static CollisionShape createBoxShape(int width, int height) {
        final FixedIntegerMatrix fixedIntegerMatrix = new FixedIntegerMatrix(
                new Integer[]{0, width, width, 0},
                new Integer[]{0, 0, height, height}
        );

        return new CollisionShape(fixedIntegerMatrix);
    }
}
