package etat.apothicon.main;

import java.awt.RenderingHints.Key;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyInput implements KeyListener {

    // pressed keys
    public boolean upPressed;
    public boolean leftPressed;
    public boolean rightPressed;
    public boolean switchWpnPressed;
    public boolean enterPressed;
    public boolean reloadPressed;
    public boolean _2Pressed;
    public boolean _3Pressed;
    public boolean downPressed;
    public boolean fPressed;
    public boolean debugPressed;

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_DELETE) {
            debugPressed = true;
        }
        if (code == KeyEvent.VK_ENTER) {
            enterPressed = true;
        }
        if (code == KeyEvent.VK_F) {
            fPressed = true;
        }
        if (code == KeyEvent.VK_R) {
            reloadPressed = true;
        }

        if (code == KeyEvent.VK_1) {
            switchWpnPressed = true;
        }
        if (code == KeyEvent.VK_W) {
            upPressed = true;
        }
        if (code == KeyEvent.VK_A) {
            leftPressed = true;
        }
        if (code == KeyEvent.VK_S) {
            downPressed = true;
        }
        if (code == KeyEvent.VK_D) {
            rightPressed = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_ENTER) {
            enterPressed = false;
        }
        if (code == KeyEvent.VK_R) {
            reloadPressed = false;
        }
        if (code == KeyEvent.VK_F) {
            fPressed = false;
        }
        if (code == KeyEvent.VK_W) {
            upPressed = false;
        }
        if (code == KeyEvent.VK_1) {
            switchWpnPressed = false;
        }
        if (code == KeyEvent.VK_A) {
            leftPressed = false;
        }
        if (code == KeyEvent.VK_S) {
            downPressed = false;
        }
        if (code == KeyEvent.VK_D) {
            rightPressed = false;
        }
    }
}
