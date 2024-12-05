import java.io.IOException;

public class Main {

    public static void main(String[] args) throws Exception{
        boolean way = false;
        if(way)
        {
            String filename = "res/lav7.txt";
            Maze.maze(filename);
        }
        else
        {
            Maze.maze(8, 8);
        }
    }
}
