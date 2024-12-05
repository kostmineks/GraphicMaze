import javafx.scene.paint.Color;
import mars.drawingx.application.DrawingApplication;
import mars.drawingx.drawing.Drawing;
import mars.drawingx.drawing.DrawingUtils;
import mars.drawingx.drawing.View;
import mars.drawingx.gadgets.annotations.GadgetAnimation;
import mars.drawingx.gadgets.annotations.GadgetBoolean;
import mars.geometry.Vector;

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


    private final Color WALLColor = Color.BLACK;
    private final Color EXITColor = new Color(0 , 1, 0 , 1);
    private final Color PATHColor = Color.WHITE;


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
            try {
                save();
            } catch (IOException _){}
        }

        //Iscrtavanje lavirinta
        DrawingUtils.clear(view, Color.BLACK);
        for (int i = 0; i < y; i++) {
            for (int j = 0; j < x; j++) {
                if (lab[i][j] == EXIT)
                    view.setFill(EXITColor);
                else if (lab[i][j] == WALL)
                    view.setFill(WALLColor);
                else
                    view.setFill(PATHColor);
                view.fillRect(new Vector(dim, dim).mul(new Vector(j - x / 2, y / 2 - i - 1)), new Vector(dim));
            }
        }

        // Iscrtavanje resenja
        if(!have)
            return;

        int m = path.size();//   duzina puta koja se iscrtava srazmerna vremenu

        if(time <= timeMax - 2)
        {
            double l = m / (timeMax - 2.0);//   polja u sekundi
            m = (int) (time * l);//   polja za trenutno vreme
        }

        for(int i = 0; i < m; i++)
        {
            Point p = path.get(i);
            if(p.isPut())
            {
                view.setFill(Color.YELLOW);
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

    public static void  maze(String filename) throws Exception
    {
        Resolve resMaze = new Resolve(filename);
        maze(resMaze);
    }
    public static void maze(int y, int x) throws Exception
    {
        //seed - 115205115129181198L, 150, 150 / 15,15
        Resolve resMaze = new Resolve(y,x);
        maze(resMaze);
    }

    private static void maze(Resolve maze) throws Exception
    {
        lab = maze.getLab();
        x = maze.getX();
        y = maze.getY();
        path = maze.getRes();
        have = (path != null);
        dim = (int)Math.min(650 / y, 1500 / x);
        DrawingApplication.launch(x * dim, y * dim);
    }

    private void save() throws IOException {
        int n = 1;
        String path = "res/lav";
        while(new File(path + n + ".txt").exists())
        {
            n++;
        }

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
    }
}
