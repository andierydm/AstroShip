package input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyBoard implements KeyListener {
    private boolean[] keys;
    public static boolean right, left, up, down;

    public KeyBoard(){
        keys = new boolean[255];
        right = false;
        left = false;
        up = false;
        down = false;
    }


    /**
     * Invoked when a key has been typed.
     * See the class description for {@link KeyEvent} for a definition of
     * a key typed event.
     *
     * @param e the event to be processed
     */
    @Override
    public void keyTyped(KeyEvent e) {

    }

    /**
     * Invoked when a key has been pressed.
     * See the class description for {@link KeyEvent} for a definition of
     * a key pressed event.
     *
     * @param e the event to be processed
     */
    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key){
            case 25->{
                keys[key] = true;
                left = keys[key];
            }
            case 26->{
                keys[key] = true;
                up = keys[key];
            }
            case 27->{
                keys[key] = true;
                right = keys[key];
            }case 28->{
                keys[key] = true;
                down = keys[key];
            }
        }
    }

    /**
     * Invoked when a key has been released.
     * See the class description for {@link KeyEvent} for a definition of
     * a key released event.
     *
     * @param e the event to be processed
     */
    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key){
            case 25->{
                keys[key] = false;
                left = keys[key];
            }
            case 26->{
                keys[key] = false;
                up = keys[key];
            }
            case 27->{
                keys[key] = false;
                right = keys[key];
            }case 28->{
                keys[key] = false;
                down = keys[key];
            }
        }
    }
}
