
package com.ryancodesgames.retrorally;

import static com.ryancodesgames.retrorally.RetroRallyRacer.getFPS;
import static com.ryancodesgames.retrorally.RetroRallyRacer.getFrameHeight;
import static com.ryancodesgames.retrorally.RetroRallyRacer.getFrameWidth;
import static com.ryancodesgames.retrorally.RetroRallyRacer.getImageHeight;
import static com.ryancodesgames.retrorally.RetroRallyRacer.getImageWidth;
import com.ryancodesgames.retrorally.camera.Camera;
import com.ryancodesgames.retrorally.gameobject.Barrier;
import com.ryancodesgames.retrorally.gameobject.BrownCar;
import com.ryancodesgames.retrorally.gameobject.Dome;
import com.ryancodesgames.retrorally.gameobject.FinishLine;
import com.ryancodesgames.retrorally.gameobject.FullLine;
import com.ryancodesgames.retrorally.gameobject.Hallway;
import com.ryancodesgames.retrorally.gameobject.LineGap;
import com.ryancodesgames.retrorally.gameobject.Staircase;
import com.ryancodesgames.retrorally.gameobject.Stairwall;
import com.ryancodesgames.retrorally.gameobject.StandLogo;
import com.ryancodesgames.retrorally.gameobject.Stands;
import com.ryancodesgames.retrorally.gameobject.TV;
import com.ryancodesgames.retrorally.gameobject.TVStand;
import com.ryancodesgames.retrorally.gfx.Attribute;
import com.ryancodesgames.retrorally.gfx.Attribute.Culling;
import com.ryancodesgames.retrorally.gfx.Attribute.DrawMode;
import com.ryancodesgames.retrorally.gfx.Attribute.FOG;
import com.ryancodesgames.retrorally.gfx.Attribute.LIGHTING;
import com.ryancodesgames.retrorally.gfx.Attribute.ZSort;
import com.ryancodesgames.retrorally.gfx.Building;
import static com.ryancodesgames.retrorally.gfx.ColorUtils.CD_BLACK;
import static com.ryancodesgames.retrorally.gfx.DrawUtils.slFill;
import com.ryancodesgames.retrorally.gfx.Sprite;
import com.ryancodesgames.retrorally.gfx.ZBuffer;
import com.ryancodesgames.retrorally.input.KeyHandler;
import com.ryancodesgames.retrorally.mathlib.Mat4x4;
import com.ryancodesgames.retrorally.mathlib.Mesh;
import com.ryancodesgames.retrorally.mathlib.PolygonGroup;
import com.ryancodesgames.retrorally.gameobject.TunnelSide;
import com.ryancodesgames.retrorally.gameobject.TunnelTop;
import static com.ryancodesgames.retrorally.gfx.DrawUtils.toBufferedImage;
import com.ryancodesgames.retrorally.gfx.Texture;
import static com.ryancodesgames.retrorally.mathlib.RallyMath.degToRad;
import com.ryancodesgames.retrorally.mathlib.Transformation;
import com.ryancodesgames.retrorally.mathlib.Vec3D;
import com.ryancodesgames.retrorally.renderer.Rasterizer;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.MemoryImageSource;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable
{
    Thread gameThread;
    //FRAMES PER SECOND
    int fps = 60;
    //CLASS THAT HANDLES KEYBOARD USER INPUT
    KeyHandler keyH = new KeyHandler(this);
    //SIZE OF WINDOW
    int frameWidth = getImageWidth();
    int frameHeight = getImageHeight();
    //PROJECTION MATRIX DATA
    float a = (float)frameHeight/(float)frameWidth;
    float fov = 90.00f;
    float fNear = 0.1f;
    float fFar = 1000.00f;
    //PROJECTION MATRIX
    Mat4x4 m = new Mat4x4();
    Mat4x4 matProj = m.slPerspective(a, fov, fNear, fFar);
    PolygonGroup polygon = new PolygonGroup();
    //ANGLE TO ROTATE OBJECTS AROUND
    float fTheta;
    //ROTATION AROUND Y-AXIS FOR CAMERA
    float fYaw = 3.14f;
    Vec3D vLookDir = new Vec3D(0,0,1);
    //CLASS THAT HOLDS TRANSFORMATION MATRICES
    Transformation t = new Transformation();
    //CAMERA
    Camera vCamera = new Camera(0,-2,-29); 
    Camera TVCamera = new Camera(0.55f, -6.95f, -139.5f);
    float tvYaw = 3.14f;
    //DEPTH BUFFER
    ZBuffer zBuffer = new ZBuffer(frameWidth, frameHeight);
    //GRAPHICS DATA
    private int[] pixels;
    private ColorModel cm;
    private Image imageBuffer;
    private MemoryImageSource mImageProducer;
    BufferedImage img, img2;
    //ATTRIBUTE LIST FOR EACH TRIANGLE
    Attribute attribute = new Attribute(DrawMode.TEXTURED,Culling.BACK_FACE_CULLING, ZSort.ZSORT, LIGHTING.POINT_LIGHTING, FOG.FOG_OFF);
    //FOG INTENSITY
    public double intense = 0.055;
    public float move = -1;
    float moveSpeed = 0.55f;
    //SPRIES
    Sprite sprite;
    //BROWN CAR
    BrownCar brownCar = new BrownCar();
    //FINISH LINE
    FinishLine finishLine = new FinishLine(), finishLine2 = new FinishLine(), finishLine3 = new FinishLine();
    //BARRIER
    Barrier barrier = new Barrier(30, 3, 0.1f), barrier2 = new Barrier(30, 3, 0.1f);
    Barrier barrier3 = new Barrier(30, 3, 0.1f), barrier4 = new Barrier(30, 3, 0.1f);
    Barrier barrier5 = new Barrier(30, 3, 0.1f), barrier6 = new Barrier(30, 3, 0.1f); Barrier barrier7 = new Barrier(30, 3, 0.1f), barrier8 = new Barrier(30, 3, 0.1f);
    Barrier barrier9 = new Barrier(30, 3, 0.1f), barrier10 = new Barrier(30, 3, 0.1f);
    Barrier barrier11 = new Barrier(30, 3, 0.1f), barrier12 = new Barrier(30, 3, 0.1f);
    //TUNNEL SIDE
    TunnelSide side = new TunnelSide(1, 30, 3, 0.1f), side2 = new TunnelSide(1,30, 3, 0.1f), side3 = new TunnelSide(2, 30, 3, 0.1f);
    TunnelSide side4 = new TunnelSide(2, 30, 3, 0.1f), side5 = new TunnelSide(3, 30, 3, 0.1f), side6 = new TunnelSide(3, 30, 3, 0.1f);
    TunnelSide side7 = new TunnelSide(1, 30, 3, 0.1f), side8 = new TunnelSide(1,30, 3, 0.1f), side9 = new TunnelSide(2, 30, 3, 0.1f);
    TunnelSide side10 = new TunnelSide(2, 30, 3, 0.1f), side11 = new TunnelSide(3, 30, 3, 0.1f), side12 = new TunnelSide(3, 30, 3, 0.1f);
    //TUNNEL TOP
    TunnelTop top = new TunnelTop(1, 0.001f, -1), top2 = new TunnelTop(1, 0.001f, -1), top3 = new TunnelTop(1, 0.001f, -1);
    //TV STAND
    TVStand stand = new TVStand();
    //TV
    TV tv = new TV(1.2f, 0.7f, 0.01f);
    //LINE GAP
    LineGap line = new LineGap(), line2 = new LineGap(), line3 = new LineGap(), line4 = new LineGap(), line5 = new LineGap();
    LineGap line6 = new LineGap(), line7 = new LineGap(), line8 = new LineGap(), line9 = new LineGap(), line10 = new LineGap();
    LineGap line11 = new LineGap(), line12 = new LineGap(), line13 = new LineGap(), line14 = new LineGap(), line15 = new LineGap();
    LineGap line16 = new LineGap(), line17 = new LineGap(), line18 = new LineGap(), line19 = new LineGap(), line20 = new LineGap();
    //FULL LINE
    FullLine fullLine = new FullLine(), fullLine2 = new FullLine(), fullLine3 = new FullLine(), fullLine4 = new FullLine();
    FullLine fullLine5 = new FullLine(), fullLine6 = new FullLine(), fullLine7 = new FullLine(), fullLine8 = new FullLine();
    FullLine fullLine9 = new FullLine(), fullLine10 = new FullLine(), fullLine11 = new FullLine(), fullLine12 = new FullLine();
    FullLine fullLine13 = new FullLine(), fullLine14 = new FullLine(), fullLine15 = new FullLine(), fullLine16 = new FullLine();
    FullLine fullLine17 = new FullLine(), fullLine18 = new FullLine(), fullLine19 = new FullLine(), fullLine20 = new FullLine();
    //STAND LOGO
    StandLogo logo = new StandLogo(20, 1.5f, 0.1f), logo2 = new StandLogo(20, 1.5f, 0.1f), logo3 = new StandLogo(20, 1.5f, 0.1f);
    StandLogo logo4 = new StandLogo(20, 1.5f, 0.1f), logo5 = new StandLogo(20, 1.5f, 0.1f), logo6 = new StandLogo(20, 1.5f, 0.1f);
    //STANDS
    Stands stands = new Stands(20, 3, 0.1f), stands2 = new Stands(20, 3, 0.1f), stands3 = new Stands(20, 3, 0.1f);
    Stands stands4 = new Stands(20, 3, 0.1f), stands5 = new Stands(20, 3, 0.1f), stands6 = new Stands(20, 3, 0.1f);
    //BUILDINGS
    String b2 = "./assets/map_data/building/building2.bmp";
    String b3 = "./assets/map_data/building/building3.bmp";
    String b4 = "./assets/map_data/building/building4.bmp";
    String b5 = "./assets/map_data/building/building5.bmp";
    Building building = new Building(b2, 22, 6, 0.01f), building2 = new Building(b2, 22, 6, 0.01f), building3 = new Building(b2, 22, 6, 0.01f);
    Building building4 = new Building(b3, 30, 30, 20), building5 = new Building(b4, 20, 10, 10), building6 = new Building(b5, 12, 8, 10);
    //DOME
    Dome dome = new Dome(), dome2 = new Dome();
    //STAIRCASE
    String v1 = "./assets/map_data/stairwell/Staircase/staircase1.png";
    String v2 =  "./assets/map_data/stairwell/Staircase/staircase.bmp";
    Staircase stairs = new Staircase(v1, 5, 5, 0.01f), stairs2 = new Staircase(v2, 5, 5, 0.01f);
    Staircase stairs3 = new Staircase(v2, 5, 5, 0.01f), stairs4 = new Staircase(v1, 5, 5, 0.01f);
    //STAIRWALL
    Stairwall sw = new Stairwall(5, 5, 0.01f), sw2 = new Stairwall(5, 5,  0.01f);
    //HALLWAY
    Hallway hw = new Hallway(11.6f, 2, 0.01f), hw2 = new Hallway(11.6f, 2, 0.01f),  hw3 = new Hallway(11.6f, 5, 0.01f);
    
    public GamePanel()
    {   
        this.addKeyListener(keyH);
        this.setFocusable(true);
        this.setDoubleBuffered(true);
        initializeMesh();
        init();
    }
    
    public void startGameThread()
    {
        gameThread = new Thread(this);
        gameThread.start();
    }
    
    @Override
    public void run()
    {
        double drawInterval = 1000000000/(60 / getFPS());
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        int timer = 0;
        int drawCount = 0;
  
        while(gameThread != null)
        {
            currentTime = System.nanoTime();
            
            delta += (currentTime - lastTime)/drawInterval;
            timer += (currentTime - lastTime);
            
            lastTime = currentTime;
            
            if(delta >= 1)
            {
                update();
                repaint();
                delta--;
                drawCount++;
            }
            
            if(timer >= 1000000000)
            {
                drawCount = 0;
                timer = 0;
            }
        }
    }
    
    protected static ColorModel getCompatibleColorModel(){        
        GraphicsConfiguration gfx_config = GraphicsEnvironment.
                getLocalGraphicsEnvironment().getDefaultScreenDevice().
                getDefaultConfiguration();        
        return gfx_config.getColorModel();
    }
    
    Mesh mesh;
    
    public void initializeMesh()
    {
        getRGB();

        finishLine2.translateFinishLine(0, 0, -9.7f);
        line.translateGapLine(0.7f, -0.01f, -16.5f);
        fullLine.translateGapLine(0.68f, -0.01f, -26.0f);
        line2.translateGapLine(0.7f, -0.01f, -33.3f);
        fullLine2.translateGapLine(0.68f, -0.01f, -40.1f);
        line3.translateGapLine(0.7f, -0.01f, -47.4f);
        fullLine3.translateGapLine(0.68f, -0.01f, -54.7f);
        line4.translateGapLine(0.7f, -0.01f, -62f);
        fullLine4.translateGapLine(0.68f, -0.01f, -69.3f);
        line5.translateGapLine(0.7f, -0.01f, -76.6f);
        fullLine5.translateGapLine(0.68f, -0.01f, -83.9f);
        line6.translateGapLine(0.7f, -0.01f, -91.2f);
        fullLine6.translateGapLine(0.68f, -0.01f, -98.5f);
        line7.translateGapLine(0.7f, -0.01f, -105.8f);
        fullLine7.translateGapLine(0.68f, -0.01f, -113.1f);
        line8.translateGapLine(0.7f, -0.01f, -120.4f);
        fullLine8.translateGapLine(0.68f, -0.01f, -127.7f);
        line9.translateGapLine(0.7f, -0.01f, -135);
        fullLine9.translateGapLine(0.68f, -0.01f, -142.3f);
        line10.translateGapLine(0.7f, -0.01f, -149.6f);
        fullLine10.translateGapLine(0.68f, -0.01f, -156.9f);
        line11.translateGapLine(0.7f, -0.01f, -164.2f);
        fullLine11.translateGapLine(0.68f, -0.01f, -171.5f);
        line12.translateGapLine(0.7f, -0.01f, -178.8f);
        fullLine12.translateGapLine(0.68f, -0.01f, -186.1f);
        line13.translateGapLine(0.7f, -0.01f, -193.4f);
        fullLine13.translateGapLine(0.68f, -0.01f, -200.7f);
        line14.translateGapLine(0.7f, -0.01f, -208f);
        fullLine14.translateGapLine(0.68f, -0.01f, -215.3f);
        line15.translateGapLine(0.7f, -0.01f, -222.6f);
        fullLine15.translateGapLine(0.68f, -0.01f, -229.9f);
        line16.translateGapLine(0.7f, -0.01f, -237.2f);
        fullLine16.translateGapLine(0.68f, -0.01f, -244.5f);
        line17.translateGapLine(0.7f, -0.01f, -251.8f);
        fullLine17.translateGapLine(0.68f, -0.01f, -259.1f);
        line18.translateGapLine(0.7f, -0.01f, -266.4f);
        fullLine18.translateGapLine(0.68f, -0.01f, -273.7f);
        line19.translateGapLine(0.7f, -0.01f, -281f);
        
        barrier.getBarrier().transform.setTranslationMatrix(-10, -2.8f, -10);
        barrier2.getBarrier().transform.setTranslationMatrix(11.1f, -2.8f, -10);
        barrier3.getBarrier().transform.setTranslationMatrix(-10, -2.8f, -40);
        barrier4.getBarrier().transform.setTranslationMatrix(11.1f, -2.8f, -40);
        barrier5.getBarrier().transform.setTranslationMatrix(-10, -2.8f, -70);
        barrier6.getBarrier().transform.setTranslationMatrix(11.1f, -2.8f, -70);
        barrier7.getBarrier().transform.setTranslationMatrix(-10, -2.8f, -160);
        barrier8.getBarrier().transform.setTranslationMatrix(11.1f, -2.8f, -160);
        barrier9.getBarrier().transform.setTranslationMatrix(-10, -2.8f, -190);
        barrier10.getBarrier().transform.setTranslationMatrix(11.1f, -2.8f, -190);
        barrier11.getBarrier().transform.setTranslationMatrix(-10, -2.8f, -220);
        barrier12.getBarrier().transform.setTranslationMatrix(11.1f, -2.8f, -220);
        
        side.getTunnelSide().transform.setTranslationMatrix(-10, -2.8f, -250);
        side2.getTunnelSide().transform.setTranslationMatrix(11.1f, -2.8f, -250);
        side3.getTunnelSide().transform.setTranslationMatrix(-9, -2.8f * 2, -250);
        side4.getTunnelSide().transform.setTranslationMatrix(10.1f, -2.8f * 2, -250);
        side3.getTunnelSide().transform.setRotAngleX(degToRad(-20));
        side4.getTunnelSide().transform.setRotAngleX(degToRad(20));
        side5.getTunnelSide().transform.setTranslationMatrix(-9, -2.8f * 2, -280);
        side6.getTunnelSide().transform.setTranslationMatrix(10.1f, -2.8f * 2, -280);
        side5.getTunnelSide().transform.setRotAngleX(degToRad(-20));
        side6.getTunnelSide().transform.setRotAngleX(degToRad(20));
        side7.getTunnelSide().transform.setTranslationMatrix(-10, -2.8f, -280);
        side8.getTunnelSide().transform.setTranslationMatrix(11.1f, -2.8f, -280);
        
        top.getTunnelTop().transform.setTranslationMatrix(-9, -2.8f * 2, -240);
        top2.getTunnelTop().transform.setTranslationMatrix(-9, -2.8f * 2, -250);
        top3.getTunnelTop().transform.setTranslationMatrix(-9, -2.8f * 2, -260);
        
        stand.getTVStand().transform.setScaleMatrix(20);
        stand.getTVStand().transform.setRotAngleY(degToRad(180));
        stand.getTVStand().transform.setTranslationMatrix(0, -6.2f, -220);
        
        tv.getTV().transform.setScaleMatrix(10);
        tv.getTV().transform.setRotAngleY(degToRad(180));
        tv.getTV().transform.setTranslationMatrix(6, -13.2f, -226);
        
        logo.getStandLogo().transform.setTranslationMatrix(11.1f, -1.3f, -90);
        logo2.getStandLogo().transform.setTranslationMatrix(11.1f, -1.3f, -110);
        logo3.getStandLogo().transform.setTranslationMatrix(11.1f, -1.3f, -130);
        logo4.getStandLogo().transform.setTranslationMatrix(-10, -1.3f, -90);
        logo5.getStandLogo().transform.setTranslationMatrix(-10, -1.3f, -110);
        logo6.getStandLogo().transform.setTranslationMatrix(-10, -1.3f, -130);
        
        stands.getStands().transform.setTranslationMatrix(11.1f, -1.3f, -90);
        stands2.getStands().transform.setTranslationMatrix(11.1f, -1.3f, -110);
        stands3.getStands().transform.setTranslationMatrix(11.1f, -1.3f, -130);
        stands4.getStands().transform.setTranslationMatrix(-10, -1.3f, -90);
        stands5.getStands().transform.setTranslationMatrix(-10, -1.3f, -110);
        stands6.getStands().transform.setTranslationMatrix(-10, -1.3f, -130);
 
        stands.getStands().transform.setRotAngleX(-10);
        stands2.getStands().transform.setRotAngleX(-10);
        stands3.getStands().transform.setRotAngleX(-10);
        stands4.getStands().transform.setRotAngleX(10);
        stands5.getStands().transform.setRotAngleX(10);
        stands6.getStands().transform.setRotAngleX(10);
        
        logo.getStandLogo().transform.setRotAngleX(degToRad(-10));
        logo2.getStandLogo().transform.setRotAngleX(degToRad(-10));
        logo3.getStandLogo().transform.setRotAngleX(degToRad(-10));
        logo4.getStandLogo().transform.setRotAngleX(degToRad(10));
        logo5.getStandLogo().transform.setRotAngleX(degToRad(10));
        logo6.getStandLogo().transform.setRotAngleX(degToRad(10));
        
        building.getBuilding().transform.setTranslationMatrix(-15, -8.8f, -110);
        building2.getBuilding().transform.setTranslationMatrix(-36, -8.8f, -84.3f);
        building2.getBuilding().transform.setRotAngleY(degToRad(-10));
        building3.getBuilding().transform.setTranslationMatrix(-36, -8.8f, -113.6f);
        building3.getBuilding().transform.setRotAngleY(degToRad(10));
        building4.getBuilding().transform.setTranslationMatrix(-30, -28.8f, -80.6f);
        building5.getBuilding().transform.setTranslationMatrix(23, -10.8f, -80.6f);
        building6.getBuilding().transform.setTranslationMatrix(23, -10.8f, -100.6f);
        
        stairs.getStaircase().transform.setScaleMatrix(2);
        stairs.getStaircase().transform.setTranslationMatrix(21.6f, -10.8f, -141.4f);
        stairs.getStaircase().transform.setRotAngleY(degToRad(180));
        
        stairs2.getStaircase().transform.setScaleMatrix(2);
        stairs2.getStaircase().transform.setTranslationMatrix(21.6f, -10.8f, -151.4f);
        stairs2.getStaircase().transform.setRotAngleY(degToRad(180));
        
        stairs3.getStaircase().transform.setScaleMatrix(2);
        stairs3.getStaircase().transform.setTranslationMatrix(-11.6f, -10.8f, -141.4f);
        stairs3.getStaircase().transform.setRotAngleY(degToRad(180));
        
        stairs4.getStaircase().transform.setScaleMatrix(2);
        stairs4.getStaircase().transform.setTranslationMatrix(-11.6f, -10.8f, -151.4f);
        stairs4.getStaircase().transform.setRotAngleY(degToRad(180));
        
        sw.getStairwall().transform.setTranslationMatrix(11.6f, -10.8f, -151.2f);
        sw.getStairwall().transform.setScaleMatrix(2);
        
        sw2.getStairwall().transform.setTranslationMatrix(-11.6f, -10.8f, -151.2f);
        sw2.getStairwall().transform.setScaleMatrix(2);
        
        hw.getHallway().transform.setTranslationMatrix(-11.6f, -10.8f, -141.2f);
        hw.getHallway().transform.setScaleMatrix(2);
        
        hw2.getHallway().transform.setTranslationMatrix(-11.6f, -10.8f, -151.2f);
        hw2.getHallway().transform.setScaleMatrix(2);
        
        hw3.getHallway().transform.setRotAngleX(degToRad(90));
        hw3.getHallway().transform.setTranslationMatrix(-11.6f, -6.8f, -141.2f);
        hw3.getHallway().transform.setScaleMatrix(2);
        
        dome2.getDome().transform.setScaleMatrix(15);
        dome2.getDome().transform.setRotAngleY(degToRad(270));
        dome2.getDome().transform.setTranslationMatrix(-35, -16.8f, -100);
        
        polygon.addMesh(brownCar.getCar());
        polygon.addMesh(brownCar.getTire1());
        polygon.addMesh(brownCar.getTire2());
        polygon.addMesh(brownCar.getTire3());
        polygon.addMesh(brownCar.getTire4());
        polygon.addMesh(finishLine.getFinishLine());
        polygon.addMesh(finishLine2.getFinishLine());
        polygon.addMesh(barrier.getBarrier());
        polygon.addMesh(barrier3.getBarrier());
        polygon.addMesh(barrier4.getBarrier());
        polygon.addMesh(barrier5.getBarrier());
        polygon.addMesh(barrier6.getBarrier());
        polygon.addMesh(barrier7.getBarrier());
        polygon.addMesh(barrier8.getBarrier());
        polygon.addMesh(barrier9.getBarrier());
        polygon.addMesh(barrier10.getBarrier());
        polygon.addMesh(barrier11.getBarrier());
        polygon.addMesh(barrier12.getBarrier());
        polygon.addMesh(side.getTunnelSide());
        polygon.addMesh(side2.getTunnelSide());
        polygon.addMesh(side3.getTunnelSide());
        polygon.addMesh(side4.getTunnelSide());
        polygon.addMesh(side5.getTunnelSide());
        polygon.addMesh(side6.getTunnelSide());
        polygon.addMesh(side7.getTunnelSide());
        polygon.addMesh(side8.getTunnelSide());
        polygon.addMesh(top.getTunnelTop());
        polygon.addMesh(top2.getTunnelTop());
        polygon.addMesh(top3.getTunnelTop());
        polygon.addMesh(stand.getTVStand());
        polygon.addMesh(tv.getTV());
        polygon.addMesh(logo.getStandLogo());
        polygon.addMesh(logo2.getStandLogo());
        polygon.addMesh(logo3.getStandLogo());
        polygon.addMesh(logo4.getStandLogo());
        polygon.addMesh(logo5.getStandLogo());
        polygon.addMesh(logo6.getStandLogo());
        polygon.addMesh(stands.getStands());
        polygon.addMesh(stands2.getStands());
        polygon.addMesh(stands3.getStands());
        polygon.addMesh(stands4.getStands());
        polygon.addMesh(stands5.getStands());
        polygon.addMesh(stands6.getStands());
        polygon.addMesh(dome2.getDome());
        polygon.addMesh(building.getBuilding());
        polygon.addMesh(building2.getBuilding());
        polygon.addMesh(building3.getBuilding());
        polygon.addMesh(building4.getBuilding());
        polygon.addMesh(building5.getBuilding());
        polygon.addMesh(building6.getBuilding());
        polygon.addMesh(stairs.getStaircase());
        polygon.addMesh(stairs2.getStaircase());
        polygon.addMesh(stairs3.getStaircase());
        polygon.addMesh(stairs4.getStaircase());
        polygon.addMesh(sw.getStairwall());
        polygon.addMesh(sw2.getStairwall());
        polygon.addMesh(hw.getHallway());
        polygon.addMesh(hw2.getHallway());
        polygon.addMesh(hw3.getHallway());
        polygon.addMesh(line.getLineGap());
        polygon.addMesh(fullLine.getFullLine());
        polygon.addMesh(barrier2.getBarrier());
        polygon.addMesh(line2.getLineGap());
        polygon.addMesh(fullLine2.getFullLine());
        polygon.addMesh(line3.getLineGap());
        polygon.addMesh(fullLine3.getFullLine());
        polygon.addMesh(line4.getLineGap());
        polygon.addMesh(fullLine4.getFullLine());
        polygon.addMesh(line5.getLineGap());
        polygon.addMesh(fullLine5.getFullLine());
        polygon.addMesh(line6.getLineGap());
        polygon.addMesh(fullLine6.getFullLine());
        polygon.addMesh(line7.getLineGap());
        polygon.addMesh(fullLine7.getFullLine());
        polygon.addMesh(line8.getLineGap());
        polygon.addMesh(fullLine8.getFullLine());
        polygon.addMesh(line9.getLineGap());
        polygon.addMesh(fullLine9.getFullLine());
        polygon.addMesh(line10.getLineGap());
        polygon.addMesh(fullLine10.getFullLine());
        polygon.addMesh(line11.getLineGap());
        polygon.addMesh(fullLine11.getFullLine());
        polygon.addMesh(line12.getLineGap());
        polygon.addMesh(fullLine12.getFullLine());
        polygon.addMesh(line13.getLineGap());
        polygon.addMesh(fullLine13.getFullLine());
        polygon.addMesh(line14.getLineGap());
        polygon.addMesh(fullLine14.getFullLine());
        polygon.addMesh(line15.getLineGap());
        polygon.addMesh(fullLine15.getFullLine());
        polygon.addMesh(line16.getLineGap());
        polygon.addMesh(fullLine16.getFullLine());
        polygon.addMesh(line17.getLineGap());
        polygon.addMesh(fullLine17.getFullLine());
        polygon.addMesh(line18.getLineGap());
       // polygon.addMesh(mesh);
    }
    
    public void init()
    {
        cm = getCompatibleColorModel();
        
        int width = frameWidth;
        int height = frameHeight;
        
        int screenSize = width * height;
        
        if(pixels == null || pixels.length < screenSize)
        {
             pixels = new int[screenSize];
        }
        // This class is an implementation of the ImageProducer interface which uses an array 
        // to produce pixel values for an Image.
        mImageProducer =  new MemoryImageSource(width, height, cm, pixels,0, width);
        mImageProducer.setAnimated(true);
        mImageProducer.setFullBufferUpdates(true);  
        imageBuffer = Toolkit.getDefaultToolkit().createImage(mImageProducer); 
    }

    public void getRGB()
    {
        try
        {
            img = ImageIO.read(new File("./assets/texture_data/night.png"));
            img2 = ImageIO.read(new File("./assets/map_data/building/building2.bmp"));
            sprite = new Sprite(img);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public void update()
    {
        //move -= 0.1;
        
        if(keyH.rightPressed)
        {
            vCamera.addCameraX(moveSpeed);
        }

        if(keyH.leftPressed)
        {
            vCamera.subtractCameraX(moveSpeed);
        }

        if(keyH.downPressed)
        {
            vCamera.addCameraY(moveSpeed);
        }

        if(keyH.upPressed)
        {
            vCamera.subtractY(moveSpeed);
        }

        Vec3D vFoward = new Vec3D(0,0,0);
        vFoward = vFoward.vec3f_mul_by_scalar(vLookDir, 0.8f);

        if(keyH.frontPressed)
        {
           vCamera.setForwardDirection(vFoward);
        }

        if(keyH.backPressed)
        {
           vCamera.setForwardDirectionBack(vFoward);
        }

        if(keyH.rightTurn)
        {
            fYaw -= 0.02;
        }

        if(keyH.leftTurn)
        {
            fYaw += 0.02;
        }
    
    }
    
    float offset;
    float offset2;
    
    boolean yes;

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        
        int[] pi = pixels; // this avoid crash when resizing
        if(pi.length != frameWidth * frameHeight) return;        
        slFill(pixels, CD_BLACK);
        
        Graphics2D g2 = (Graphics2D)g;      

        Vec3D vTarget = new Vec3D(0,0,1);
        Mat4x4 matCameraRotated = new Mat4x4();
        matCameraRotated = m.slRotY(fYaw);
        vLookDir = m.multiplyMatrixVector(vTarget, matCameraRotated);
        
        Mat4x4 matCameraRotated2 = new Mat4x4();
        matCameraRotated2 = m.slRotY(tvYaw);
        Vec3D vLookDir2 = m.multiplyMatrixVector(vTarget, matCameraRotated);
        
//        offset -= 1.00;
//        offset2 -= 0.50;
//        
//        brownCar.translateCar(0, 0, offset);
//        brownCar.rotateTires(0, offset2, 0);
//        
       // slDispSprite(sprite, pixels, 0, 0, 0.17f, 0.195f);
        Rasterizer renderer = new Rasterizer(attribute, polygon, vCamera, matProj, vLookDir, zBuffer, g2, pixels, 0.045, move);
        renderer.draw();
        // ask ImageProducer to update image
         mImageProducer.newPixels();        
        // draw it on panel     
        g2.drawImage(this.imageBuffer, 0, 0, getFrameWidth(), getFrameHeight(), this);
        
        BufferedImage img = toBufferedImage(imageBuffer);
        BufferedImage img2 = toBufferedImage(img.getScaledInstance(512, 256, 1));
        
        tv.getTV().tex = new Texture(img2);
        
        g2.setColor(Color.green);
        g2.setFont(new Font("Arial", Font.BOLD, 20));
        g2.drawString("Coordinates:", 10, 50);
        g2.drawString("X:"+" "+String.valueOf(vCamera.getCamera().x), 10, 70);
        g2.drawString("Y:"+" "+String.valueOf(vCamera.getCamera().y), 10, 90);
        g2.drawString("Z:"+" "+String.valueOf(vCamera.getCamera().z), 10, 110);
        g2.drawString("Triangles:"+" "+String.valueOf(renderer.getTriangleCount()), 10, 150);
        g2.drawString("Textured = TRUE", 10, 180);
        g2.drawString("Yaw:"+" "+String.format("%.4f", fYaw), 10, 210);

        g.dispose();
    }
}
