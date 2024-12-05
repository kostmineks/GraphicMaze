public class Main {

    public static void main(String[] args){
        boolean way = false;
        if(way)
        {
            String filename = "res/lav1.txt";
            Maze.maze(filename);
        }
        else
        {
            Maze.maze(8, 8);
        }
    }
}
