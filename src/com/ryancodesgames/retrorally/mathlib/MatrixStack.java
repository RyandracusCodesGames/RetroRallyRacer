
package com.ryancodesgames.retrorally.mathlib;

public class MatrixStack 
{
    private int length = 0;
    private Node top = null;
    
    public boolean isEmpty()
    {
        return length == 0;
    }
    
    public void slPushMatrix(Mat4x4 matrix)
    {
        Node temp = new Node(matrix);
        temp.setNextNode(top);
        top = temp;
        length++;
    }
    
    public Mat4x4 slPopMatrix()
    {
        Node node = null;
        if(length == 0)
        {
            System.out.println("Stack is currently empty!");
        }
        else
        {
            node = top;
            top = top.getNode();
        }
        
        return node.getMatrix();
    }
}
