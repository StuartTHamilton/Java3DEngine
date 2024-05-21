import java.awt.*;
import javax.swing.*;
import java.util.*;

public class Launcher {
        static Date now;
        static Long time = 0L, stone = 0L;

    public static void main(String[] args){
        JFrame window = new JFrame();
        DimFrame frame = new DimFrame();

        window.add(frame);
        window.setSize(800,800);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);

        while(true){
            updateStone();
            while(time < stone + 1) {
                updateTime();
            }
            frame.repaint();
        }
    }

    public static void updateTime(){
        now = new Date();
        time = now.getTime();
    }

    public static void updateStone(){
        now = new Date();
        stone = now.getTime();
    }
}
