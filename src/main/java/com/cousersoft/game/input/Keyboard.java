package com.cousersoft.game.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Keyboard implements KeyListener {

    private boolean[] keys = new boolean[1000];
    public boolean up, down, left, right, space;
    public boolean k1, k2, k3, k4, k5;
    public boolean bracketLeft, bracketRight;
    public boolean enter, escape, tab;
    public boolean kR, kH, kY, kS; // Weather triggers: Rain, Heat, sunnY, Snow

    public void update() {
        up = keys[KeyEvent.VK_UP];
        down = keys[KeyEvent.VK_DOWN];
        left = keys[KeyEvent.VK_LEFT];
        right = keys[KeyEvent.VK_RIGHT];
        space = keys[KeyEvent.VK_SPACE];
        enter = keys[KeyEvent.VK_ENTER];
        escape = keys[KeyEvent.VK_ESCAPE];
        tab = keys[KeyEvent.VK_TAB];
        k1 = keys[KeyEvent.VK_1];
        k2 = keys[KeyEvent.VK_2];
        k3 = keys[KeyEvent.VK_3];
        k4 = keys[KeyEvent.VK_4];
        k5 = keys[KeyEvent.VK_5];
        bracketLeft = keys[KeyEvent.VK_OPEN_BRACKET];
        bracketRight = keys[KeyEvent.VK_CLOSE_BRACKET];
        kR = keys[KeyEvent.VK_R];
        kH = keys[KeyEvent.VK_H];
        kY = keys[KeyEvent.VK_Y];
        kS = keys[KeyEvent.VK_S];
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() < keys.length)
            keys[e.getKeyCode()] = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() < keys.length)
            keys[e.getKeyCode()] = false;
    }
}
