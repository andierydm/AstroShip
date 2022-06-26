package system.input;

import java.awt.event.KeyEvent;

public class AnotherKeyboardInput {
    private final boolean[] keys;
    private final int[] polled;
    private static AnotherKeyboardInput anotherKeyboardInput = null;

    private AnotherKeyboardInput() {
        keys = new boolean[256];
        polled = new int[256];
    }

    public boolean keyDown(int keyCode) {
        return polled[keyCode] > 0;
    }

    public boolean keyDownOnce(int keyCode) {
        return polled[keyCode] == 1;
    }

    public synchronized void poll() {
        for (int i = 0; i < keys.length; i++) {
            if (keys[i]) {
                polled[i]++;
            } else {
                polled[i] = 0;
            }
        }
    }

    public synchronized void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode >= 0 && keyCode < keys.length) {
            keys[keyCode] = true;
        }
    }

    public synchronized void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode >= 0 && keyCode < keys.length) {
            keys[keyCode] = false;
        }
    }

    public static AnotherKeyboardInput getInstance() {
        if (anotherKeyboardInput == null) {
            anotherKeyboardInput = new AnotherKeyboardInput();
        }

        return anotherKeyboardInput;
    }
}
