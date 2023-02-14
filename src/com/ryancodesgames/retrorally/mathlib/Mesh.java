
package com.ryancodesgames.retrorally.mathlib;

import com.ryancodesgames.retrorally.gfx.Texture;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

public class Mesh 
{
     public List<Triangle> triangles = new ArrayList<>();
    
    public BufferedImage img;
    
    public Texture tex;
    
    public Transformation transform =  new Transformation();
    
    public Mesh(List<Triangle> tris, BufferedImage img)
    {
        this.triangles = tris;
        this.img = img;
        this.tex = new Texture(img);
    }
    
    public Mesh(String s, BufferedImage img)
    {
        this.triangles = ReadOBJFile(s,true);
        this.img = img;
        this.tex = new Texture(img);
    }
    
    public Mesh()
    {
        
    }

    public static String[] readFromFile(String filename) 
    {
	File file = new File(filename);
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
            } catch (Exception e) 
            {
                e.printStackTrace();
            }
        }
	return contents.toArray(new String[contents.size()]);
    }
    
    public static List<Triangle> ReadOBJFile(String f,boolean textured) {
        String[] data = readFromFile(f);
        List<float[]> vertices = new ArrayList<>();
        List<float[]> texs = new ArrayList<>();
        List<Triangle> tris = new ArrayList<>();
        for (String s : data) {
            String[] split = s.split(Pattern.quote(" "));
            if (split[0].equalsIgnoreCase("v")) {
                vertices.add(new float[]{Float.parseFloat(split[1]),Float.parseFloat(split[2]),Float.parseFloat(split[3])});
            } else
            if (split[0].equalsIgnoreCase("vt")) {
                float u=Math.min(1,Math.max(0,Float.parseFloat(split[1])));
                float v=Math.min(1,Math.max(0,Float.parseFloat(split[2])));
                texs.add(new float[]{u,v});
            } else
            if (split[0].equalsIgnoreCase("f")) {
                if (textured) {
                    String[] spl1=split[1].split(Pattern.quote("/"));
                    String[] spl2=split[2].split(Pattern.quote("/"));
                    String[] spl3=split[3].split(Pattern.quote("/"));
                    Triangle tri = new Triangle(
                        new Vec3D(
                            vertices.get(Integer.parseInt(spl1[0])-1)[0],
                            vertices.get(Integer.parseInt(spl1[0])-1)[1],
                            vertices.get(Integer.parseInt(spl1[0])-1)[2]),
                        new Vec3D(
                            vertices.get(Integer.parseInt(spl2[0])-1)[0],
                            vertices.get(Integer.parseInt(spl2[0])-1)[1],
                            vertices.get(Integer.parseInt(spl2[0])-1)[2]),
                        new Vec3D(
                            vertices.get(Integer.parseInt(spl3[0])-1)[0],
                            vertices.get(Integer.parseInt(spl3[0])-1)[1],
                            vertices.get(Integer.parseInt(spl3[0])-1)[2]),
                        new Vec2D(
                            texs.get(Integer.parseInt(spl1[1])-1)[0],
                            texs.get(Integer.parseInt(spl1[1])-1)[1]),
                        new Vec2D(
                            texs.get(Integer.parseInt(spl2[1])-1)[0],
                            texs.get(Integer.parseInt(spl2[1])-1)[1]),
                        new Vec2D(
                            texs.get(Integer.parseInt(spl3[1])-1)[0],
                            texs.get(Integer.parseInt(spl3[1])-1)[1]));
                    tris.add(tri);
                } else {
                    String[] spl1=split[1].split(Pattern.quote("/"));
                    String[] spl2=split[2].split(Pattern.quote("/"));
                    String[] spl3=split[3].split(Pattern.quote("/"));
                    Triangle tri = new Triangle(
                        new Vec3D(
                            vertices.get(Integer.parseInt(spl1[0])-1)[0],
                            vertices.get(Integer.parseInt(spl1[0])-1)[1],
                            vertices.get(Integer.parseInt(spl1[0])-1)[2]),
                        new Vec3D(
                            vertices.get(Integer.parseInt(spl2[0])-1)[0],
                            vertices.get(Integer.parseInt(spl2[0])-1)[1],
                            vertices.get(Integer.parseInt(spl2[0])-1)[2]),
                        new Vec3D(
                            vertices.get(Integer.parseInt(spl3[0])-1)[0],
                            vertices.get(Integer.parseInt(spl3[0])-1)[1],
                            vertices.get(Integer.parseInt(spl3[0])-1)[2]));
                    tris.add(tri);
                }
            }
        }
        return tris;
    }
}
