
package com.ryancodesgames.retrorally.mathlib;

public class Transformation 
{
    //ROTATION ANGLE TRANSFORMS
    private float rotAngleX;
    private float rotAngleY;
    private float rotAngleZ;
    //TRANSFORMATION MATRIX DATA
    private float transX;
    private float transY;
    private float transZ;
    private float scale = 1;
    //ORIGIN POINT OF MODEL TO UNIFORMELY APPLY ALL TRANSFORMATION TO
    private Vec3D origin;
    //ACCUMULATION OF ALL TRANSFORMATION
    private Mat4x4 matWorld;
    
    //BAISC SETTER METHHODS
    public void setRotAngleX(float angle)
    {
        this.rotAngleX = angle;
    }
    
    public void setRotAngleZ(float angle)
    {
        this.rotAngleZ = angle;
    }
    
    public void setRotAngleY(float angle)
    {
        this.rotAngleY = angle;
    }
    
    public void setTranslationMatrix(float x, float y, float z)
    {
        this.transX = x;
        this.transY = y;
        this.transZ = z;
    }
    
    public void setScaleMatrix(float scale)
    {
        this.scale = scale;
    }
    
    //BASIC GETTER METHODS
    public Mat4x4 getWorldMatrix()
    {
        Mat4x4 m = new Mat4x4();
        Mat4x4 matZ = m.slRotZ(getRotAngleZ());
        Mat4x4 matZX = m.slRotX(getRotAngleX());
        Mat4x4 matYaw = m.slRotY(getRotAngleY());
        
        Mat4x4 matWorld = new Mat4x4();
        matWorld = m.slIdentity();
        matWorld = m.matrixMatrixMultiplication(matWorld, getScaleMatrix());
        matWorld = m.matrixMatrixMultiplication(matWorld, matZX);
        matWorld = m.matrixMatrixMultiplication(matWorld, matYaw);
        matWorld = m.matrixMatrixMultiplication(matWorld, matZ);
        matWorld = m.matrixMatrixMultiplication(matWorld, getTranslationMatrix());
        
        return matWorld;
    }
    
    public Mat4x4 getTranslationMatrix()
    {
        Mat4x4 m = new Mat4x4();
        Mat4x4 translationMatrix = m.slTranslation(transX, transY, transZ);
        
        return translationMatrix;
    }
    
    public Mat4x4 getScaleMatrix()
    {
        Mat4x4 m = new Mat4x4();
        Mat4x4 scaleMatrix = m.slScale(scale, scale, scale);
        
        return scaleMatrix;
    }
    
    public float getRotAngleX()
    {
        return rotAngleX;
    }
    
    public float getRotAngleY()
    {
        return rotAngleY;
    }
    
    public float getRotAngleZ()
    {
        return rotAngleZ;
    }
    
    public float getTransX()
    {
        return transX;
    }
    
    public float getTransY()
    {
        return transY;
    }
    
    public float getTransZ()
    {
        return transZ;
    }
}
