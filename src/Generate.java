import mars.random.RNG;

public class Generate {

    private int[][] lavirint;
    private boolean [][] genLab;// true - prolaz, false - zid
    private final int y;//y -> i
    private final int x;// x -> j
    public final int[][] rule = new int[][] {{-1, 0}, {1, 0}, {0, -1}, {0, 1}, {-1, 0}, {1, 0}, {0, -1}, {0, 1}};//   matrica koja nam sluzi za nasumican pravac

    private void genLabInit()//   Inicijalizujemo boolean matricu za 2 polja vecu u sirinu i duzinu i svim poljima dodeljujemo false koji oznacava zid
    {
        genLab = new boolean[y + 2][x + 2];
        for(int i = 0; i < y + 2; i++)
        {
            for(int j = 0; j < x + 2; j++)
            {
                genLab[i][j] = false;
            }
        }
    }

    private boolean checkBounds(int i, int j)//true - polje je van granica, false - polje je unutar granica
    {
        return i < 1 || i > y || j < 1 || j > x;
    }

    private boolean checkWall(int i, int j) // true ima vise od 1 nule u okruzenju
    {
        return  (genLab[i - 1][j] ? 1 : 0) +
                (genLab[i + 1][j] ? 1 : 0) +
                (genLab[i][j - 1] ? 1 : 0) +
                (genLab[i][j + 1] ? 1 : 0) > 1;
    }

    private void generate(int i, int j, RNG rng)
    {
        if(checkBounds(i, j))
            return;
        if(checkWall(i, j))
            return;

        genLab[i][j] = true;
        int r = rng.nextInt(4);

        generate(i + rule[r][0], j + rule[r][1], rng);
        generate(i + rule[r + 1][0], j + rule[r + 1][1], rng);
        generate(i + rule[r + 2][0], j + rule[r + 2][1], rng);
        generate(i + rule[r + 3][0], j + rule[r + 3][1], rng);
    }

    private void translate()//   Pretvara boolean matricu u int matricu sa vrednostima -11 : zid, -99 : izlaz, 0 : put
    {
        lavirint = new int[y][x];
        for(int i = 0; i < y; i++) {
            for (int j = 0; j < x; j++) {
                lavirint[i][j] = genLab[i + 1][j + 1] ? 0 : -11;
            }
        }
        lavirint[0][x - 1] = -99;
    }

    public Generate(int x, int y)
    {
        this.x = x;
        this.y = y;
        genLabInit();
        RNG rng = new RNG();
        long seed = rng.nextLong();
        System.out.println("Seed: " + seed);
        rng = new RNG(seed);
        generate(1, 1, rng);
        translate();
    }

    public Generate(int x, int y, long seed)
    {
        this.x = x;
        this.y = y;
        genLabInit();
        RNG rng = new RNG(seed);
        generate(1, 1, rng);
        translate();
    }

    public int[][] getLavirint() {
        return lavirint;
    }
}
