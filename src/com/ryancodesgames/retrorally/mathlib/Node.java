
package com.ryancodesgames.retrorally.mathlib;

public class Node 
{
    private Mat4x4 matrix;
    private Node nextNode;
    
    public Node(Mat4x4 matrix)
    {
        this.matrix = matrix;
    }
    
    public void setMatrix(Mat4x4 matrix)
    {
        this.matrix = matrix;
    }
    
    public Mat4x4 getMatrix()
    {
        return this.matrix;
    }
    
    public void setNextNode(Node node)
    {
        this.nextNode = node;
    }
    
    public Node getNode()
    {
        return this.nextNode;
    }
      
}
