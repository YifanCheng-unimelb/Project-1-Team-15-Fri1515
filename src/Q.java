// P.java
package src;

import ch.aplu.jgamegrid.Location;

/**
 * @Author Group 15 - Mingyue Jiang, Yutong CHEN, Yifan Cheng
 * @Date 2022 2022/8/30
 * @Version 1.0
 */
public class Q extends Block
{
    private final int blockId = 8;
    private Location[][] r = new Location[5][4];
    private final String blockName = "Q";
    private int rotId = 0;

    public Q(Tetris tetris)
    {
        super();

        this.tetris = tetris;
        // rotId 0
        r[0][0] = new Location(new Location(-1, -1));
        r[1][0] = new Location(new Location(-1, 0));
        r[2][0] = new Location(new Location(0, 0));
        r[3][0] = new Location(new Location(0, 1));
        r[4][0] = new Location(new Location(0, -1));
        // rotId 1
        r[0][1] = new Location(new Location(1, -1));
        r[1][1] = new Location(new Location(0, -1));
        r[2][1] = new Location(new Location(1, 0));
        r[3][1] = new Location(new Location(0, 0));
        r[4][1] = new Location(new Location(-1,0));
        // rotId 2
        r[0][2] = new Location(new Location(1, 0));
        r[1][2] = new Location(new Location(1, 1));
        r[2][2] = new Location(new Location(0, 0));
        r[3][2] = new Location(new Location(0, 1));
        r[4][2] = new Location(new Location(0, -1));
        // rotId 3
        r[0][3] = new Location(new Location(-1, 1));
        r[1][3] = new Location(new Location(0, 1));
        r[2][3] = new Location(new Location(1, 0));
        r[3][3] = new Location(new Location(0, 0));
        r[4][3] = new Location(new Location(-1, 0));

        for (int i = 0; i < r.length; i++)
            blocks.add(new TetroBlock(blockId, r[i]));
    }
    public String toString() {
        return "For testing, do not change: Block: " + blockName + ". Location: " + blocks + ". Rotation: " + rotId;
    }
}
