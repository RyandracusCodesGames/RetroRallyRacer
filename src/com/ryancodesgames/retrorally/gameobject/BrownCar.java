
package com.ryancodesgames.retrorally.gameobject;

import com.ryancodesgames.retrorally.mathlib.Mesh;
import com.ryancodesgames.retrorally.mathlib.Vec3D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

public class BrownCar 
{
    private final Mesh car;
    private final Mesh tire1;
    private final Mesh tire2;
    private final Mesh tire3;
    private final Mesh tire4;
    private Vec3D t1, t2, t3, t4;
    
    public BrownCar()
    {
        BufferedImage img = null;
        try 
        {
            img = ImageIO.read(new File("./assets/car_data/car5/car5.png"));
        } catch (IOException ex) 
        {
            ex.printStackTrace();
        }
        
        t1 = new Vec3D(-1, -0.5f, -2.3f);
        t2 = new Vec3D(-1, -0.5f, 1.8f);
        t3 = new Vec3D(1, -0.5f, 1.8f);
        t4 = new Vec3D(1, -0.5f, -2.3f);
        
        car = new Mesh("./assets/car_data/car5/car5.obj", img);
        tire1 = new Mesh("./assets/car_data/car5/Tire5/tire5.obj", img);
        tire2 = new Mesh("./assets/car_data/car5/Tire5/tire5.obj", img);
        tire3 = new Mesh("./assets/car_data/car5/Tire5/tire5.obj", img);
        tire4 = new Mesh("./assets/car_data/car5/Tire5/tire5.obj", img);
        
        tire1.transform.setTranslationMatrix(t1.x, t1.y, t1.z);
        tire2.transform.setTranslationMatrix(t2.x, t2.y, t2.z);
        tire3.transform.setTranslationMatrix(t3.x, t3.y, t3.z);
        tire4.transform.setTranslationMatrix(t4.x, t4.y, t4.z);
    }

    public void translateCar(float x, float y, float z)
    {
        car.transform.setTranslationMatrix(x, y, z);
        tire1.transform.setTranslationMatrix(t1.x + x, t1.y + y, t1.z + z);
        tire2.transform.setTranslationMatrix(t2.x + x, t2.y + y, t2.z + z);
        tire3.transform.setTranslationMatrix(t3.x + x, t3.y + y, t3.z + z);
        tire4.transform.setTranslationMatrix(t4.x + x, t4.y + y, t4.z + z);
    }
    
    public void rotateTires(float x, float y, float z)
    {
        tire1.transform.setRotAngleX(y);
        tire2.transform.setRotAngleX(y);
        tire3.transform.setRotAngleX(y);
        tire4.transform.setRotAngleX(y);
    }
    
    public Mesh getCar()
    {
        return this.car;
    }
    
    public Mesh getTire1()
    {
        return this.tire1;
    }
    
    public Mesh getTire2()
    {
        return this.tire2;
    }
    
    public Mesh getTire3()
    {
        return this.tire3;
    }
    
    public Mesh getTire4()
    {
        return this.tire4;
    }
}
