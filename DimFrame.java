import java.awt.*;
import javax.swing.*;
import java.util.*;

public class DimFrame extends JPanel{
    static int depth = 800;
    static int height = 800;
    static int width = 801;
    static boolean[][][] matrix3D = new boolean[width][height][depth];
    static boolean[][][] matrixPinched = new boolean[width][height][depth];
    static boolean[][] matrix2D = new boolean[width][height];
    static double phi = 0.0;
    static int phiCoeff = 0;


    public void paintComponent(Graphics g){
        super.paintComponent(g);
        initMatrices();

        Scanner input = new Scanner(System.in);
        int xC = 400, zC = 100;
        double thetaA = 0.0, thetaB = Math.PI / 2.0, thetaC = Math.PI / 1.0, thetaD = Math.PI * 3.0 / 2.0;

        defineCubeFromTop(
                xC + (int)(100.0 * Math.cos(thetaA + phi)),
                zC + (int)(100.0 * Math.sin(thetaA + phi)),
                xC + (int)(100.0 * Math.cos(thetaB + phi)),
                zC + (int)(100.0 * Math.sin(thetaB + phi)),
                xC + (int)(100.0 * Math.cos(thetaC + phi)),
                zC + (int)(100.0 * Math.sin(thetaC + phi)),
                xC + (int)(100.0 * Math.cos(thetaD + phi)),
                zC + (int)(100.0 * Math.sin(thetaD + phi)),
                500, 150);

        pinchCompress();
        draw2DMatrix(g);



        phiCoeff+=5;
        phi = phiCoeff / 100.0;
    }



    public static void drawDot(int x, int y, Graphics g){
        g.drawLine(x,y,x,y);
    }

    public static void define2DPoint(int i, int j){
        matrix2D[i][j] = true;
    }

    public static void define2DLine(int x1, int y1, int x2, int y2){
        double dX = x2 - x1, dY = y2 - y1;
        double hypotenuse = Math.sqrt(dX * dX + dY * dY);
        double dInstX = dX / hypotenuse, dInstY = dY / hypotenuse;

        double theoryX = x1 + 0.0, theoryY = y1 + 0.0;
        double closeX = x2, closeY = y2;
        while(closeX >= 1.0 || closeY >= 1.0){
            theoryX += dInstX;
            theoryY += dInstY;
            define2DPoint((int)theoryX, (int)theoryY);

            closeX = Math.abs(theoryX - x2);
            closeY = Math.abs(theoryY - y2);
        }
    }

    public static void define2DRect(int x1, int y1, int x2, int y2){
        define2DLine(x1, y1, x2, y1);
        define2DLine(x2, y1, x2, y2);
        define2DLine(x2, y2, x1, y2);
        define2DLine(x1, y2, x1, y1);
    }

    public static void define3DPoint(int i, int j, int k){
        matrix3D[i][j][k] = true;
    }
    public static void define3DLine(int x1, int y1, int z1, int x2, int y2, int z2){
        double dX = x2 - x1, dY = y2 - y1, dZ = z2 - z1;
        double hypotenuse = Math.sqrt(dX * dX + dY * dY + dZ * dZ);
        double dInstX = dX / hypotenuse, dInstY = dY / hypotenuse, dInstZ = dZ / hypotenuse;

        double theoryX = x1 + 0.0, theoryY = y1 + 0.0, theoryZ = z1 + 0.0;
        double closeX = x2, closeY = y2, closeZ = z2;
        while(closeX >= 1.0 || closeY >= 1.0 || closeZ >= 1.0){
            theoryX += dInstX;
            theoryY += dInstY;
            theoryZ += dInstZ;
            define3DPoint((int)theoryX, (int)theoryY, (int)theoryZ);

            closeX = Math.abs(theoryX - x2);
            closeY = Math.abs(theoryY - y2);
            closeZ = Math.abs(theoryZ - z2);
        }
    }
    public static void define3DRect(int x1, int y1, int z1, int x2, int y2, int z2) {
        define3DLine(x1, y1, z1, x2, y1, z2);
        define3DLine(x2, y1, z2, x2, y2, z2);
        define3DLine(x2, y2, z2, x1, y2, z1);
        define3DLine(x1, y2, z1, x1, y1, z1);
    }

    public static void define3DTopPlane(int x1, int z1, int x2, int z2, int x3, int z3, int x4, int z4, int y) {
        define3DLine(x1, y, z1, x2, y, z2);
        define3DLine(x2, y, z2, x3, y, z3);
        define3DLine(x3, y, z3, x4, y, z4);
        define3DLine(x4, y, z4, x1, y, z1);
    }

    public static void defineCubeFromTop(int x1, int z1, int x2, int z2, int x3, int z3, int x4, int z4, int y, int d){
        define3DTopPlane(x1, z1, x2, z2, x3, z3, x4, z4, y);
        define3DTopPlane(x1, z1, x2, z2, x3, z3, x4, z4, y+d);

        define3DLine(x1, y, z1, x1, y+d, z1);
        define3DLine(x2, y, z2, x2, y+d, z2);
        define3DLine(x3, y, z3, x3, y+d, z3);
        define3DLine(x4, y, z4, x4, y+d, z4);
    }

    public static void draw2DMatrix(Graphics g){
        for(int i = 0; i < 800; i++){
            for(int j = 0; j < 800; j++){
                if(matrix2D[i][j]){
                    drawDot(i, j, g);
                }
            }
        }
    }
    public static void initMatrices(){
        for(int i = 0; i < 800; i++)
            for(int j = 0; j < 800; j++){
                for(int k = 0; k < depth; k++) {
                    matrix3D[i][j][k] = false;
                    matrixPinched[i][j][k] = false;
                }
                matrix2D[i][j] = false;
            }
    }

    public static void simpleCompress(){
        for(int i = 0; i < 800; i++) {
            for (int j = 0; j < 800; j++) {
                for (int k = 0; k < depth; k++) {
                    if (matrix3D[i][j][k]) {
                        define2DPoint(i,j);
                    }
                }
            }
        }
    }
    public static void pinchCompress(){



        for(int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                for (int k = 0; k < depth; k++) {
                    if (matrix3D[i][j][k]) {
                        double xC = width/2;
                        double yC = height/2;

                        double xZ = xC - (1-k/(width + 0.0)) * (xC - i);
                        double yZ = yC - (1-k/(height + 0.0)) * (yC - j);


                        define2DPoint((int)xZ, (int)yZ);
                    }
                }
            }
        }
    }
}

