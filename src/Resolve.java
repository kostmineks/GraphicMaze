import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Resolve {

    private final int WALL = -11;
    private final int EXIT = -99;

    private int x;
    private int y;

    private int[][] lab;//   Matrica iz fajla
    private boolean[][] visited;//   Matrica posecenosti

    private List<Point> path = new ArrayList<Point>();
    private List<Point> res;
    private boolean have = false;

    private void visitedInit()//   Inicijalizuje celu matricu posecenosti na flase
    {
        for(int i = 0; i < y; i++)
        {
            for(int j = 0; j < x; j++)
            {
                visited[i][j] = false;
            }
        }
    }

    private boolean checkBounds(int i, int j)//true - van granica je, false - unutar granica je
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
            if(res == null || res.size() > path.size())
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
    public Resolve(String filename) throws Exception//   konstruktor - ucitava lavirint - resava ga - postavlja da se trazi najkraci put
    {
        load(filename);
        solve(0, 0, path, visited);
    }

    public Resolve(int y, int x) throws Exception
    {
        this.y = y;
        this.x = x;

        lab = new int[y][x];
        visited = new boolean[y][x];
        visitedInit();

        Generate gen = new Generate(x, y);
        lab = gen.getLavirint();

        //save();
        solve(0, 0, path, visited);
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

    private void load(String filename) throws Exception//   Ucitava lavirint iz fajla
    {
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
    }


}
