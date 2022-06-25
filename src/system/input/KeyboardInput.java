package system.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyboardInput implements KeyListener {
    private final boolean[] keys;
    public static boolean right, left, up, down, exits;

    public KeyboardInput() {
        keys = new boolean[255];
        right = false;
        left = false;
        up = false;
        down = false;
        exits = false;
    }

    public void refresh() {
        right = keys[KeyEvent.VK_RIGHT];
        left = keys[KeyEvent.VK_LEFT];
        up = keys[KeyEvent.VK_UP];
        down = keys[KeyEvent.VK_DOWN];
        exits = keys[KeyEvent.VK_ESCAPE];
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        keys[e.getKeyCode()] = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        keys[e.getKeyCode()] = false;
    }
}
