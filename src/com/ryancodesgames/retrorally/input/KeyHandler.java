
package com.ryancodesgames.retrorally.input;

import com.ryancodesgames.retrorally.GamePanel;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener
{
    private GamePanel gp;
    
    public boolean rightPressed, leftPressed, upPressed, downPressed, frontPressed, backPressed, rightTurn, leftTurn;
    
    public KeyHandler(GamePanel gp)
    {
        this.gp = gp;
    }
    
    @Override
    public void keyPressed(KeyEvent e)
    {
        if(e.getKeyCode() == KeyEvent.VK_D)
        {
            rightPressed = true;
        }

        if(e.getKeyCode() == KeyEvent.VK_A)
        {
            leftPressed = true;
        }

        if(e.getKeyCode() == KeyEvent.VK_SPACE)
        {
            upPressed = true;
        }

        if(e.getKeyCode() == KeyEvent.VK_C)
        {
            downPressed = true;
        }

        if(e.getKeyCode() == KeyEvent.VK_W)
        {
            frontPressed = true;
        }

        if(e.getKeyCode() == KeyEvent.VK_S)
        {
            backPressed = true;
        }

        if(e.getKeyCode() == KeyEvent.VK_E)
        {
            rightTurn = true;
        }

        if(e.getKeyCode() == KeyEvent.VK_Q)
        {
            leftTurn = true;
        }
    }
    
    @Override
    public void keyTyped(KeyEvent e)
    {
        
    }
    
    @Override
    public void keyReleased(KeyEvent e)
    {
        if(e.getKeyCode() == KeyEvent.VK_D)
        {
            rightPressed = false;
        }

        if(e.getKeyCode() == KeyEvent.VK_A)
        {
            leftPressed = false;
        }

        if(e.getKeyCode() == KeyEvent.VK_SPACE)
        {
            upPressed = false;
        }

        if(e.getKeyCode() == KeyEvent.VK_C)
        {
            downPressed = false;
        }

        if(e.getKeyCode() == KeyEvent.VK_W)
        {
            frontPressed = false;
        }

        if(e.getKeyCode() == KeyEvent.VK_S)
        {
            backPressed = false;
        }

        if(e.getKeyCode() == KeyEvent.VK_E)
        {
            rightTurn = false;
        }

        if(e.getKeyCode() == KeyEvent.VK_Q)
        {
            leftTurn = false;
        }
    }
    
}
