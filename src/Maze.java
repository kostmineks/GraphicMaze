import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import mars.drawingx.application.DrawingApplication;
import mars.drawingx.drawing.Drawing;
import mars.drawingx.drawing.DrawingUtils;
import mars.drawingx.drawing.View;
import mars.drawingx.gadgets.annotations.GadgetAnimation;
import mars.drawingx.gadgets.annotations.GadgetBoolean;
import mars.geometry.Vector;
import mars.input.InputEvent;
import mars.input.InputState;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class Maze implements Drawing {


    private static int[][] lab;//   Matrica lavirinta
    private static int x;//   Sirina lavirinta
    private static int y;//   Visina lavirinta
    private static List<Point> path;//   Lista polja puta


    private static final int WALL = -11;
    private static final int EXIT = -99;

    private final Color BACKGROUND = Color.hsb(0, 0, 0.3);
    private final Color WALLColor = Color.hsb(0, 0, 0);
    private final Color EXITColor = Color.hsb(120, 1, 1);
    private final Color WAYColor = Color.hsb(0,0,1);
    private final Color PATHColor = Color.hsb(50, 1, 1);


    private static int dim;

    private final int timeMax = 15;
    private static boolean have;


    @GadgetBoolean
    boolean save = false;
    private boolean saved = false;

    @GadgetAnimation(start = true, loop = true,min = 0, max = timeMax)
    double time = 0;


    public void draw(View view)
    {
        if(save && !saved)
        {
            saved = true;
            save();
        }

        //   Iscrtavanje lavirinta
        DrawingUtils.clear(view, BACKGROUND);
        for (int i = 0; i < y; i++) {
            for (int j = 0; j < x; j++) {
                if (lab[i][j] == EXIT)
                    view.setFill(EXITColor);
                else if (lab[i][j] == WALL)
                    view.setFill(WALLColor);
                else
                    view.setFill(WAYColor);
                view.fillRect(new Vector(dim, dim).mul(new Vector(j - x / 2, y / 2 - i - 1)), new Vector(dim));
            }
        }

        //   Iscrtavanje resenja
        if(!have)
            return;

        int m = path.size();//   duzina puta koja se iscrtava srazmerna vremenu

        if(time <= timeMax - 2)
        {
            double l = m / (timeMax - 2.0);//   broj polja u sekundi
            m = (int) (time * l);//   polja za trenutno vreme
        }

        for(int i = 0; i < m; i++)
        {
            Point p = path.get(i);
            if(p.isPut())
            {
                view.setFill(PATHColor);
                if(time > timeMax - 2)
                    view.setFill(EXITColor);
            }
            else
            {
                view.setFill(Color.RED);
            }
            view.fillRect(new Vector(dim, dim).mul(new Vector(p.getX() - x / 2, y / 2 - p.getY() - 1)), new Vector(dim));
        }
    }


    public static void maze(String filename)//   Iscrtavanje lavirinta iscitanog iz fajla
    {
        Resolve resMaze = new Resolve(filename);
        maze(resMaze);
    }
    public static void maze(int y, int x)//   Iscrtavanje generisanog lavirinta velicine X * Y
    {
        Resolve resMaze = new Resolve(y, x);
        maze(resMaze);
    }
    public static void maze(int y, int x, long seed)//   Iscrtavanje generisanog lavirinta velicine X * Y sa vec odredjenim seed-om
    {
        Resolve resMaze = new Resolve(y, x, seed);
        maze(resMaze);
    }

    private static void maze(Resolve maze)
    {
        lab = maze.getLab();
        x = maze.getX();
        y = maze.getY();
        path = maze.getRes();
        have = (path != null);
        dim = (int)Math.min(650 / y, 1500 / x);
        DrawingApplication.launch(x * dim, y * dim);
    }

    private void save()//   metod za snimanje lavirinta koji prvo prelazi preko postojecih fajlova i nalazi sledec ia zatim red po red ispisuje u .txt fajl
    {
        int n = 1;
        String path = "res/lav";
        while(new File(path + n + ".txt").exists())
        {
            n++;
        }
        try
        {
            BufferedWriter bw = new BufferedWriter(new FileWriter(path + n + ".txt"));
            bw.write(x + " " + y + "\n" );
            for(int i = 0; i < y; i++)
            {
                for (int j = 0; j < x; j++)
                {
                    bw.write(lab[i][j] + " ");
                }
                bw.newLine();
            }
            bw.close();
        }catch (IOException _){}

    }
}
