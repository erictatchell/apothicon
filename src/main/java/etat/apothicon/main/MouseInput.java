package etat.apothicon.main;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseInput implements MouseListener {

    public boolean leftMousePressed, rightMousePressed;

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            leftMousePressed = true;
            System.out.println("left press");
        }
        if (e.getButton() == MouseEvent.BUTTON3) {
            rightMousePressed = true;
            System.out.println("right press");
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            leftMousePressed = false ;
            System.out.println("left release");
        }
        if (e.getButton() == MouseEvent.BUTTON3) {
            rightMousePressed = false;
            System.out.println("right release");
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

}
