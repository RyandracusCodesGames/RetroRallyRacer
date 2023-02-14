
package com.ryancodesgames.retrorally.fileio;

import com.ryancodesgames.retrorally.gameobject.BrownCar;
import com.ryancodesgames.retrorally.mathlib.Mesh;
import com.ryancodesgames.retrorally.mathlib.PolygonGroup;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Map 
{
    private PolygonGroup polys;
    
    public Map(PolygonGroup polys)
    {
        this.polys = polys;
    }
    
    public String[] loadFile(File file)
    {
        List<String> contents = new ArrayList<>();
        if(file.exists())
        {
            try 
            {
                Scanner scanner = new Scanner(file);
                
                String readLine;
                while(scanner.hasNextLine())
                {
                    readLine = scanner.nextLine();
                    contents.add(readLine);
                }
                scanner.close();
                
            } catch (FileNotFoundException e) 
            {
                e.printStackTrace();
            }
        }
        return contents.toArray(new String[contents.size()]);
    }
    
    public void loadMapFile(File file)
    {
        String[] parse = loadFile(file);
        
        for(String s: parse)
        {
            String[] split = s.split(Pattern.quote(" "));
            
            switch(split[0])
            {
                case "Barrier":
                break;
                case "BrownCar":
                    BrownCar car = new BrownCar();
                    float tX = Float.parseFloat(split[1]), tY = Float.parseFloat(split[2]), tZ = Float.parseFloat(split[3]);
                    float rX = Float.parseFloat(split[4]), rY = Float.parseFloat(split[5]), rZ = Float.parseFloat(split[6]);
                    float sX = Float.parseFloat(split[7]);
                    car.translateCar(tX, tY, tZ);
                    car.getCar().transform.setRotAngleX(rX); car.getCar().transform.setRotAngleY(rY); car.getCar().transform.setRotAngleZ(rZ);
                    car.getCar().transform.setScaleMatrix(sX);
                    polys.addMesh(car.getCar());
                break;
                case "Dome":
                break;
                case "FinishLine":
                break;
                case "FullLine":
                break;
                case "Hallway":
                break;
                case "LineGap":
                break;
                case "Plane":
                break;
                case "Staircase":
                break;
                case "Stairwall":
                break;
                case "StandLogo":
                break;
                case "Stands":
                break;
            }
        }
    }
    
    public void setPolygonGroup(PolygonGroup polys)
    {
        this.polys = polys;
    }
    
    public PolygonGroup getPolygonGroup()
    {
        return this.polys;
    }
    
    public void addMesh(Mesh mesh)
    {
        polys.addMesh(mesh);
    }
    
    public void removeMesh(Mesh mesh)
    {
        polys.removeMesh(mesh);
    }
}
