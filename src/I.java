// I.java
package src;

import ch.aplu.jgamegrid.Location;
/**
 * @Author Group 15 - Mingyue Jiang, Yutong CHEN, Yifan Cheng
 * @Date 2022 2022/8/30
 * @Version 1.0
 */
class I extends Block
{
  private final int blockId = 0;
  private final String blockName = "I";
  //7个不同形状类的初始化出现位置坐标
  private Location[][] r = new Location[4][4];

  private int rotId = 0;

  I(Tetris tetris)
  {
    super();
    this.tetris = tetris;
    //构造中以下代码为7个不同形状类的初始化出现位置坐标
    //注意，他这个地方和常规认知时相反的，
    // rotId 0
    r[0][0] = new Location(new Location(-1, 0));
    r[1][0] = new Location(new Location(0, 0));
    r[2][0] = new Location(new Location(1, 0));
    r[3][0] = new Location(new Location(2, 0));
    // rotId 1
    r[0][1] = new Location(new Location(0, -1));
    r[1][1] = new Location(new Location(0, 0));
    r[2][1] = new Location(new Location(0, 1));
    r[3][1] = new Location(new Location(0, 2));
    // rotId 2
    r[0][2] = new Location(new Location(-1, 0));
    r[1][2] = new Location(new Location(0, 0));
    r[2][2] = new Location(new Location(1, 0));
    r[3][2] = new Location(new Location(2, 0));
    // rotId 3
    r[0][3] = new Location(new Location(0, -1));
    r[1][3] = new Location(new Location(0, 0));
    r[2][3] = new Location(new Location(0, 1));
    r[3][3] = new Location(new Location(0, 2));

    for (int i = 0; i < r.length; i++)
      blocks.add(new TetroBlock(blockId, r[i]));
  }

  public String toString() {
    return "For testing, do not change: Block: " + blockName + ". Location: " + blocks + ". Rotation: " + rotId;
  }

}
