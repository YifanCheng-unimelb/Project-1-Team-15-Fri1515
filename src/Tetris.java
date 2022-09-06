package src;// Tetris.java

import ch.aplu.jgamegrid.Actor;
import ch.aplu.jgamegrid.GGActListener;
import ch.aplu.jgamegrid.Location;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

//俄罗斯方块主类
public class Tetris extends JFrame implements GGActListener {
    private Actor currentBlock = null;  // Currently active block
    private Actor blockPreview = null;   // block in preview window
    private int totalScore = 0;
    private int averageScore = 0;
    private int score = 0;
    private int roundNum = 0;
    private int slowDown = 20;
    private Random random = new Random(0);
    private Hashtable<String, Integer> blockRecord = new Hashtable<String, Integer>();

    private TetrisGameCallback gameCallback;

    private boolean isAuto = false;

    private int seed;
    // For testing mode, the block will be moved automatically based on the blockActions.
    // L is for Left, R is for Right, T is for turning (rotating), and D for down
    //用于存储所有方块的数组
    private String[] blockActions = new String[10];
    //用于存储当前方块的变量
    private int blockActionIndex = 0;

    //private GameLevel gameLevel;

    private String difficulty;

    private Boolean newRound = false;

    // Initialise object
    private void initWithProperties(Properties properties) {
        this.seed = Integer.parseInt(properties.getProperty("seed", "30006"));
        random = new Random(seed);
        isAuto = Boolean.parseBoolean(properties.getProperty("isAuto"));
        String blockActionProperty = properties.getProperty("autoBlockActions", "");
        blockActions = blockActionProperty.split(",");
        //this.gameLevel = GameLevel.selectGameLevel(properties.getProperty("madness"));
        this.difficulty = properties.getProperty("difficulty", "easy");
        initBlockRecord();
        roundNum = 1;
    }

    private void initBlockRecord()
    {
        blockRecord.put("I", 0);
        blockRecord.put("J", 0);
        blockRecord.put("L", 0);
        blockRecord.put("O", 0);
        blockRecord.put("S", 0);
        blockRecord.put("T", 0);
        blockRecord.put("Z", 0);
        blockRecord.put("P", 0);
        blockRecord.put("Q", 0);
        blockRecord.put("+", 0);
    }

    public Tetris(TetrisGameCallback gameCallback, Properties properties) {
        // Initialise value
        initWithProperties(properties);
        this.gameCallback = gameCallback;
        blockActionIndex = 0;

        // Set up the UI components. No need to modify the UI Components
        tetrisComponents = new TetrisComponents();
        tetrisComponents.initComponents(this);
        gameGrid1.addActListener(this);
        gameGrid1.setSimulationPeriod(getSimulationTime());

        // Add the first block to start
        currentBlock = createRandomTetrisBlock();
        gameGrid1.addActor(currentBlock, new Location(6, 0));
        gameGrid1.doRun();

        // Do not lose keyboard focus when clicking this window
        gameGrid2.setFocusable(false);
        setTitle("SWEN30006 Tetris Madness");
        score = 0;
        showScore(score);
        if(newRound){
            slowDown = 20;
            newRound = false;
        };
    }

    // create a block and assign to a preview mode  随机生成7个shape block
    Actor createRandomTetrisBlock() {
        if (blockPreview != null)
            blockPreview.removeSelf();

        // If the game is in auto test mode, then the block will be moved according to the blockActions
        String currentBlockMove = "";
        if (blockActions.length > blockActionIndex) {
            currentBlockMove = blockActions[blockActionIndex];
        }

        blockActionIndex++;
        Actor t = null;
        int rnd = random.nextInt(7);
        if (!"easy".equals(this.difficulty))
            rnd = random.nextInt(10);
        switch (rnd) {
            case 0:
                t = new I(this);
                blockRecord.put("I", blockRecord.get("I") + 1);
                if (isAuto) {
                    ((I) t).setAutoBlockMove(currentBlockMove);
                }

                I previewI = new I(this);
                previewI.display(gameGrid2, new Location(2, 1));
                blockPreview = previewI;
                break;
            case 1:
                t = new J(this);
                blockRecord.put("J", blockRecord.get("J") + 1);
                if (isAuto) {
                    ((J) t).setAutoBlockMove(currentBlockMove);
                }
                J previewJ = new J(this);
                previewJ.display(gameGrid2, new Location(2, 1));
                blockPreview = previewJ;
                break;
            case 2:
                t = new L(this);
                blockRecord.put("L", blockRecord.get("L") + 1);
                if (isAuto) {
                    ((L) t).setAutoBlockMove(currentBlockMove);
                }
                L previewL = new L(this);
                previewL.display(gameGrid2, new Location(2, 1));
                blockPreview = previewL;
                break;
            case 3:
                t = new O(this);
                blockRecord.put("O", blockRecord.get("O") + 1);
                if (isAuto) {
                    ((O) t).setAutoBlockMove(currentBlockMove);
                }
                O previewO = new O(this);
                previewO.display(gameGrid2, new Location(2, 1));
                blockPreview = previewO;
                break;
            case 4:
                t = new S(this);
                blockRecord.put("S", blockRecord.get("S") + 1);
                if (isAuto) {
                    ((S) t).setAutoBlockMove(currentBlockMove);
                }
                S previewS = new S(this);
                previewS.display(gameGrid2, new Location(2, 1));
                blockPreview = previewS;
                break;
            case 5:
                t = new T(this);
                blockRecord.put("T", blockRecord.get("T") + 1);
                if (isAuto) {
                    ((T) t).setAutoBlockMove(currentBlockMove);
                }
                T previewT = new T(this);
                previewT.display(gameGrid2, new Location(2, 1));
                blockPreview = previewT;
                break;
            case 6:
                t = new Z(this);
                blockRecord.put("Z", blockRecord.get("Z") + 1);
                if (isAuto) {
                    ((Z) t).setAutoBlockMove(currentBlockMove);
                }
                Z previewZ = new Z(this);
                previewZ.display(gameGrid2, new Location(2, 1));
                blockPreview = previewZ;
                break;
            case 7:
                t = new P(this);
                blockRecord.put("P", blockRecord.get("P") + 1);
                if (isAuto) {
                    ((P) t).setAutoBlockMove(currentBlockMove);
                }
                P previewP = new P(this);
                previewP.display(gameGrid2, new Location(2, 1));
                blockPreview = previewP;
                break;
            case 8:
                t = new Q(this);
                blockRecord.put("Q", blockRecord.get("Q") + 1);
                if (isAuto) {
                    ((Q) t).setAutoBlockMove(currentBlockMove);
                }
                Q previewQ = new Q(this);
                previewQ.display(gameGrid2, new Location(2, 1));
                blockPreview = previewQ;
                break;
            case 9:
                t = new Plus(this);
                blockRecord.put("+", blockRecord.get("+") + 1);
                if (isAuto) {
                    ((Plus) t).setAutoBlockMove(currentBlockMove);
                }
                Plus previewPlus = new Plus(this);
                previewPlus.display(gameGrid2, new Location(2, 1));
                blockPreview = previewPlus;
                break;
        }
        // Show preview tetrisBlock

        // Set speed of tetrisBlocks
        if (score > 10)
            slowDown = (int)(slowDown*0.8);
        if (score > 20)
            slowDown = (int)(slowDown*0.6);
        if (score > 30)
            slowDown = (int)(slowDown*0.4);
        if (score > 40)
            slowDown = (int)(slowDown*0.2);
        if (score > 50)
            slowDown = 0;


        if ("medium".equals(this.difficulty))
            t.setSlowDown(Medium.getSpeed(slowDown));
        else if ("madness".equals(this.difficulty))
            t.setSlowDown(Madness.getSpeed(slowDown));
        return t;
    }

    void setCurrentTetrisBlock(Actor t) {
        gameCallback.changeOfBlock(currentBlock);
        currentBlock = t;
    }

    // Handle user input to move block. Arrow left to move left, Arrow right to move right, Arrow up to rotate and
    // Arrow down for going down
    private void moveBlock(int keyEvent) {
        if (currentBlock instanceof I) {
            switch (keyEvent) {
                case KeyEvent.VK_UP:
                    if (!"madness".equals(this.difficulty))
                        ((I) currentBlock).rotate();
                    break;
                case KeyEvent.VK_LEFT:
                    ((I) currentBlock).left();
                    break;
                case KeyEvent.VK_RIGHT:
                    ((I) currentBlock).right();
                    break;
                case KeyEvent.VK_DOWN:
                    ((I) currentBlock).drop();
                    break;
                default:
                    return;
            }
        } else if (currentBlock instanceof J) {
            switch (keyEvent) {
                case KeyEvent.VK_UP:
                    if (!"madness".equals(this.difficulty))
                        ((J) currentBlock).rotate();
                    break;
                case KeyEvent.VK_LEFT:
                    ((J) currentBlock).left();
                    break;
                case KeyEvent.VK_RIGHT:
                    ((J) currentBlock).right();
                    break;
                case KeyEvent.VK_DOWN:
                    ((J) currentBlock).drop();
                    break;
                default:
                    return;
            }
        } else if (currentBlock instanceof L) {
            switch (keyEvent) {
                case KeyEvent.VK_UP:
                    if (!"madness".equals(this.difficulty))
                        ((L) currentBlock).rotate();
                    break;
                case KeyEvent.VK_LEFT:
                    ((L) currentBlock).left();
                    break;
                case KeyEvent.VK_RIGHT:
                    ((L) currentBlock).right();
                    break;
                case KeyEvent.VK_DOWN:
                    ((L) currentBlock).drop();
                    break;
                default:
                    return;
            }
        } else if (currentBlock instanceof O) {
            switch (keyEvent) {
                case KeyEvent.VK_UP:
                    if (!"madness".equals(this.difficulty))
                        ((O) currentBlock).rotate();
                    break;
                case KeyEvent.VK_LEFT:
                    ((O) currentBlock).left();
                    break;
                case KeyEvent.VK_RIGHT:
                    ((O) currentBlock).right();
                    break;
                case KeyEvent.VK_DOWN:
                    ((O) currentBlock).drop();
                    break;
                default:
                    return;
            }
        } else if (currentBlock instanceof S) {
            switch (keyEvent) {
                case KeyEvent.VK_UP:
                    if (!"madness".equals(this.difficulty))
                        ((S) currentBlock).rotate();
                    break;
                case KeyEvent.VK_LEFT:
                    ((S) currentBlock).left();
                    break;
                case KeyEvent.VK_RIGHT:
                    ((S) currentBlock).right();
                    break;
                case KeyEvent.VK_DOWN:
                    ((S) currentBlock).drop();
                    break;
                default:
                    return;
            }
        } else if (currentBlock instanceof T) {
            switch (keyEvent) {
                case KeyEvent.VK_UP:
                    if (!"madness".equals(this.difficulty))
                        ((T) currentBlock).rotate();
                    break;
                case KeyEvent.VK_LEFT:
                    ((T) currentBlock).left();
                    break;
                case KeyEvent.VK_RIGHT:
                    ((T) currentBlock).right();
                    break;
                case KeyEvent.VK_DOWN:
                    ((T) currentBlock).drop();
                    break;
                default:
                    return;
            }
        } else if (currentBlock instanceof Z) {
            switch (keyEvent) {
                case KeyEvent.VK_UP:
                    if (!"madness".equals(this.difficulty))
                        ((Z) currentBlock).rotate();
                    break;
                case KeyEvent.VK_LEFT:
                    ((Z) currentBlock).left();
                    break;
                case KeyEvent.VK_RIGHT:
                    ((Z) currentBlock).right();
                    break;
                case KeyEvent.VK_DOWN:
                    ((Z) currentBlock).drop();
                    break;
                default:
                    return;
            }
        } else if (currentBlock instanceof P) {
            switch (keyEvent) {
                case KeyEvent.VK_UP:
                    if (!"madness".equals(this.difficulty))
                        ((P) currentBlock).rotate();
                    break;
                case KeyEvent.VK_LEFT:
                    ((P) currentBlock).left();
                    break;
                case KeyEvent.VK_RIGHT:
                    ((P) currentBlock).right();
                    break;
                case KeyEvent.VK_DOWN:
                    ((P) currentBlock).drop();
                    break;
                default:
                    return;
            }
        } else if (currentBlock instanceof Q) {
            switch (keyEvent) {
                case KeyEvent.VK_UP:
                    if (!"madness".equals(this.difficulty))
                        ((Q) currentBlock).rotate();
                    break;
                case KeyEvent.VK_LEFT:
                    ((Q) currentBlock).left();
                    break;
                case KeyEvent.VK_RIGHT:
                    ((Q) currentBlock).right();
                    break;
                case KeyEvent.VK_DOWN:
                    ((Q) currentBlock).drop();
                    break;
                default:
                    return;
            }
        } else if (currentBlock instanceof Plus) {
            switch (keyEvent) {
                case KeyEvent.VK_UP:
                    if (!"madness".equals(this.difficulty))
                        ((Plus) currentBlock).rotate();
                    break;
                case KeyEvent.VK_LEFT:
                    ((Plus) currentBlock).left();
                    break;
                case KeyEvent.VK_RIGHT:
                    ((Plus) currentBlock).right();
                    break;
                case KeyEvent.VK_DOWN:
                    ((Plus) currentBlock).drop();
                    break;
                default:
                    return;
            }
        }
    }

    public void act() {
        removeFilledLine();
        moveBlock(gameGrid1.getKeyCode());
    }

    private void removeFilledLine() {
        for (int y = 0; y < gameGrid1.nbVertCells; y++) {
            boolean isLineComplete = true;
            TetroBlock[] blocks = new TetroBlock[gameGrid1.nbHorzCells];   // One line
            // Calculate if a line is complete
            for (int x = 0; x < gameGrid1.nbHorzCells; x++) {
                blocks[x] =
                        (TetroBlock) gameGrid1.getOneActorAt(new Location(x, y), TetroBlock.class);
                if (blocks[x] == null) {
                    isLineComplete = false;
                    break;
                }
            }
            if (isLineComplete) {
                // If a line is complete, we remove the component block of the shape that belongs to that line
                for (int x = 0; x < gameGrid1.nbHorzCells; x++)
                    gameGrid1.removeActor(blocks[x]);
                ArrayList<Actor> allBlocks = gameGrid1.getActors(TetroBlock.class);
                for (Actor a : allBlocks) {
                    int z = a.getY();
                    if (z < y)
                        a.setY(z + 1);
                }
                gameGrid1.refresh();
                score++;
                totalScore++;
                gameCallback.changeOfScore(score);
                showScore(score);

            }
        }
    }

    // Show Score and Game Over

    private void showScore(final int score) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                scoreText.setText(score + " points");
            }
        });

    }

    void gameOver() {
        gameGrid1.addActor(new Actor("sprites/gameover.gif"), new Location(5, 5));
        gameGrid1.doPause();
        newRound = true;
        averageScore = totalScore / roundNum;
    }

    void printToFile()
    {
        FileWriter fw = null;
        try {
            if(roundNum == 1)
            {
                fw = new FileWriter("Statistics.txt", false);
            }
            fw = new FileWriter("Statistics.txt", true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        BufferedWriter bw = new BufferedWriter(fw);
        PrintWriter out = new PrintWriter(bw);

        ArrayList<String> keys = new ArrayList<String>();
        out.println("------------------------------------------");
        out.println("Round #" + roundNum);
        out.println("Score: " + score);
        for(String key : blockRecord.keySet())
        {
            keys.add(key);
        }
        Collections.sort(keys);
        for(String key : keys)
        {
            out.println(key + ": " + blockRecord.get(key));
        }
        out.close();

        RandomAccessFile f = null;
        try {
            f = new RandomAccessFile(new File("Statistics.txt"), "rw");
            f.seek(0); // to the beginning
            String difficultyText = "";
            if(Objects.equals(difficulty, "easy")){
                difficultyText = "easy";
            }else if(Objects.equals(difficulty, "medium")){
                difficultyText = "medium";
            }else{
                difficultyText = "Madness";
            }

            f.write(("Difficulty: " + difficultyText + "\n" + "Average score per round: "
                    + averageScore + "\n").getBytes());
            f.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Start a new game
    public void startBtnActionPerformed(java.awt.event.ActionEvent evt) {
        averageScore = totalScore / roundNum;

        printToFile();
        initBlockRecord();
        gameGrid1.doPause();
        gameGrid1.removeAllActors();
        gameGrid2.removeAllActors();
        gameGrid1.refresh();
        gameGrid2.refresh();
        gameGrid2.delay(getDelayTime());
        blockActionIndex = 0;
        currentBlock = createRandomTetrisBlock();
        gameGrid1.addActor(currentBlock, new Location(6, 0));
        gameGrid1.doRun();
        gameGrid1.requestFocus();
        roundNum++;
        score = 0;
        showScore(score);
        if ("medium".equals(this.difficulty))
            slowDown = (int)(slowDown*0.8);
        else slowDown = slowDown;
    }

    // Different speed for manual and auto mode
    private int getSimulationTime() {
        if (isAuto) {
            return 10;
        } else {
            return 100;
        }
    }

    private int getDelayTime() {
        if (isAuto) {
            return 200;
        } else {
            return 2000;
        }
    }

    // AUTO GENERATED - do not modify//GEN-BEGIN:variables
    public ch.aplu.jgamegrid.GameGrid gameGrid1;
    public ch.aplu.jgamegrid.GameGrid gameGrid2;
    public javax.swing.JPanel jPanel1;
    public javax.swing.JPanel jPanel2;
    public javax.swing.JPanel jPanel3;
    public javax.swing.JPanel jPanel4;
    public javax.swing.JScrollPane jScrollPane1;
    public javax.swing.JTextArea jTextArea1;
    public javax.swing.JTextField scoreText;
    public javax.swing.JButton startBtn;
    private TetrisComponents tetrisComponents;
    // End of variables declaration//GEN-END:variables

}
