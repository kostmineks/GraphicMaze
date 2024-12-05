import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Resolve {

    private final int WALL = -11;
    private final int EXIT = -99;

    private int x;
    private int y;

    private int[][] lab;//   Matrica lavirinta
    private boolean[][] visited;//   Matrica posecenosti

    private List<Point> path = new ArrayList<Point>();
    private List<Point> res;

    private void visitedInit()//   Inicijalizuje celu matricu posecenosti na false
    {
        for(int i = 0; i < y; i++)
        {
            for(int j = 0; j < x; j++)
            {
                visited[i][j] = false;
            }
        }
    }

    private boolean checkBounds(int i, int j)//true - polje je van granica, false - polje je unutar granica
    {
        return i < 0 || i >= y || j < 0 || j >= x;
    }

    private void solve(int i, int j, List<Point> path, boolean[][] visited)
    {
        if(checkBounds(i, j))
            return;
        if(lab[i][j] == WALL)
            return;
        if(visited[i][j])
            return;

        visited[i][j] = true;
        Point p = new Point(i, j,true);
        path.add(p);

        if(lab[i][j] == EXIT)
        {
            if(res == null)
                res = new ArrayList<Point>(path);
        }

        solve(i + 1, j, path, visited);//gore
        solve(i, j - 1, path, visited);//levo
        solve(i, j + 1, path, visited);//desno
        solve(i - 1, j, path, visited);//dole


        visited[i][j] = false;
        path.add(new Point(i,j,false));

    }


//
//      KONSTRUKTORI i GETERI
//
    public Resolve(String filename)//   konstruktor - ucitava lavirint - resava ga
    {
        load(filename);
        try {
            solve(0, 0, path, visited);
        }catch(StackOverflowError e) {
            System.out.println("Stack Overflow Resolve");
        }
    }

    public Resolve(int y, int x)
    {
        this.y = y;
        this.x = x;

        lab = new int[y][x];
        visited = new boolean[y][x];
        visitedInit();
        Generate gen = new Generate(x, y);
        lab = gen.getLavirint();

        try {
            solve(0, 0, path, visited);
        }catch(StackOverflowError e) {
            System.out.println("Stack Overflow Resolve");
        }
    }

    public Resolve(int y, int x, long seed)
    {
        this.y = y;
        this.x = x;

        lab = new int[y][x];
        visited = new boolean[y][x];
        visitedInit();
        Generate gen = new Generate(x, y, seed);
        lab = gen.getLavirint();

        try {
            solve(0, 0, path, visited);
        }catch(StackOverflowError e) {
            System.out.println("Stack Overflow Resolve");
        }
    }


    public int[][] getLab() //   Vraca lavirint iz fajla
    {
        return lab;
    }
    public int getX()//   Vraca sirinu lavirinta
    {
        return x;
    }
    public int getY()//   Vraca visinu lavirinta
    {
        return y;
    }
    public List<Point> getRes()
    {
        return res;
    }

//
//      RAD SA FAJLOVIMA
//

    private void load(String filename)//   Ucitava lavirint iz fajla
    {
        try {
            BufferedReader br = new BufferedReader(new FileReader(filename));
            String[] line = br.readLine().split(" ");

            this.x = Integer.parseInt(line[0]);
            this.y = Integer.parseInt(line[1]);
            lab = new int[y][x];
            visited = new boolean[y][x];

            for(int i = 0; i < y; i++)
            {
                line = br.readLine().split(" ");
                for(int j = 0; j < x; j++)
                {
                    lab[i][j] = Integer.parseInt(line[j].trim());
                    visited[i][j] = false;
                }
            }
            br.close();
        }catch (IOException e) {
            System.out.println("Problem reading file");
        }
    }
}
