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
/**
 * @Author Group 15 - Mingyue Jiang, Yutong CHEN, Yifan Cheng
 * @Date 2022 2022/9/8
 * @Version 3.0
 */
//俄罗斯方块主类
public class Tetris extends JFrame implements GGActListener {
    private Actor currentBlock;  // Currently active block
    private Actor blockPreview = null;   // block in preview window
    private int score;
    private int slowDown = 5;
    private Random random = new Random(0);
    private StatsRecorder stats;

    private TetrisGameCallback gameCallback;

    private boolean isAuto = false;

    private int seed;
    // For testing mode, the block will be moved automatically based on the blockActions.
    // L is for Left, R is for Right, T is for turning (rotating), and D for down
    //用于存储所有方块的数组
    private String[] blockActions = new String[10];
    //用于存储当前方块的变量
    private int blockActionIndex = 0;
    private Parent shapes;
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
        // Initialize statistics
        stats = new StatsRecorder(difficulty);
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
                // Update block count in statistics
                stats.blockRecord.put("I", stats.blockRecord.get("I") + 1);
                if (isAuto) {
                    ((I) t).setAutoBlockMove(currentBlockMove);
                }

                I previewI = new I(this);
                previewI.display(gameGrid2, new Location(2, 1));
                blockPreview = previewI;
                break;
            case 1:
                t = new J(this);
                stats.blockRecord.put("J", stats.blockRecord.get("J") + 1);
                if (isAuto) {
                    ((J) t).setAutoBlockMove(currentBlockMove);
                }
                J previewJ = new J(this);
                previewJ.display(gameGrid2, new Location(2, 1));
                blockPreview = previewJ;
                break;
            case 2:
                t = new L(this);
                stats.blockRecord.put("L", stats.blockRecord.get("L") + 1);
                if (isAuto) {
                    ((L) t).setAutoBlockMove(currentBlockMove);
                }
                L previewL = new L(this);
                previewL.display(gameGrid2, new Location(2, 1));
                blockPreview = previewL;
                break;
            case 3:
                t = new O(this);
                stats.blockRecord.put("O", stats.blockRecord.get("O") + 1);
                if (isAuto) {
                    ((O) t).setAutoBlockMove(currentBlockMove);
                }
                O previewO = new O(this);
                previewO.display(gameGrid2, new Location(2, 1));
                blockPreview = previewO;
                break;
            case 4:
                t = new S(this);
                stats.blockRecord.put("S", stats.blockRecord.get("S") + 1);
                if (isAuto) {
                    ((S) t).setAutoBlockMove(currentBlockMove);
                }
                S previewS = new S(this);
                previewS.display(gameGrid2, new Location(2, 1));
                blockPreview = previewS;
                break;
            case 5:
                t = new T(this);
                stats.blockRecord.put("T", stats.blockRecord.get("T") + 1);
                if (isAuto) {
                    ((T) t).setAutoBlockMove(currentBlockMove);
                }
                T previewT = new T(this);
                previewT.display(gameGrid2, new Location(2, 1));
                blockPreview = previewT;
                break;
            case 6:
                t = new Z(this);
                stats.blockRecord.put("Z", stats.blockRecord.get("Z") + 1);
                if (isAuto) {
                    ((Z) t).setAutoBlockMove(currentBlockMove);
                }
                Z previewZ = new Z(this);
                previewZ.display(gameGrid2, new Location(2, 1));
                blockPreview = previewZ;
                break;
            case 7:
                t = new P(this);
                stats.blockRecord.put("P", stats.blockRecord.get("P") + 1);
                if (isAuto) {
                    ((P) t).setAutoBlockMove(currentBlockMove);
                }
                P previewP = new P(this);
                previewP.display(gameGrid2, new Location(2, 1));
                blockPreview = previewP;
                break;
            case 8:
                t = new Q(this);
                stats.blockRecord.put("Q", stats.blockRecord.get("Q") + 1);
                if (isAuto) {
                    ((Q) t).setAutoBlockMove(currentBlockMove);
                }
                Q previewQ = new Q(this);
                previewQ.display(gameGrid2, new Location(2, 1));
                blockPreview = previewQ;
                break;
            case 9:
                t = new Plus(this);
                stats.blockRecord.put("+", stats.blockRecord.get("+") + 1);
                if (isAuto) {
                    ((Plus) t).setAutoBlockMove(currentBlockMove);
                }
                Plus previewPlus = new Plus(this);
                previewPlus.display(gameGrid2, new Location(2, 1));
                blockPreview = previewPlus;
                break;
        }
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
        else t.setSlowDown(slowDown);
        return t;
    }

    void setCurrentTetrisBlock(Actor t) {
        gameCallback.changeOfBlock(currentBlock);
        currentBlock = t;
    }

    // Handle user input to move block. Arrow left to move left, Arrow right to move right, Arrow up to rotate and
    // Arrow down for going down
    private void moveBlock(int keyEvent) {
        switch (keyEvent) {
            case KeyEvent.VK_UP:
                if (!"madness".equals(this.difficulty)) {
                    ((Parent) currentBlock).rotate();
                }
                break;
            case KeyEvent.VK_LEFT:
                ((Parent) currentBlock).left();
                break;
            case KeyEvent.VK_RIGHT:
                ((Parent) currentBlock).right();
                break;
            case KeyEvent.VK_DOWN:
                ((Parent) currentBlock).drop();
                break;
            default:
                break;
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
                // Update statistics
                stats.score = score;
                stats.totalScore++;
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

    public void gameOver() {
        gameGrid1.addActor(new Actor("sprites/gameover.gif"), new Location(5, 5));
        gameGrid1.doPause();
        newRound = true;
        if(isAuto)
        {
            // Output statistics to file
            stats.printToFile();
            System.exit(0);
        }
    }

<<<<<<< Updated upstream
=======
    private void printToFile()
    {
        // Record difficulty level
        String difficultyText = "";
        if(Objects.equals(difficulty, "easy")){
            difficultyText = "Easy";
        }else if(Objects.equals(difficulty, "medium")){
            difficultyText = "Medium";
        }else{
            difficultyText = "Madness";
        }

        FileWriter fw = null;
        try {
            // When a new game started, clear the old file if already exist
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
        if(roundNum == 1)
        {
            out.println("Difficulty: " + difficultyText);
            out.println("Average score per round: " + averageScore);
        }

        // Statistics recording for each round of game
        out.println("------------------------------------------             ");
        out.println("Round #" + roundNum);
        out.println("Score: " + score);

        // Write shape count to statistics in ascending order
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

        // Read everything in statistics and change average score if necessary
        String data = "";
        String line = "";
        int count = 1;
        try {
            BufferedReader br = new BufferedReader(new FileReader("Statistics.txt"));
            while((line = br.readLine()) != null) {
                // Change average score when more than one round had been played
                if(count == 2 && roundNum > 1)
                {
                    line = "Average score per round: " + averageScore;
                }
                data += line + "\n";
                count++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Write edited data to statistics file
        try {
            BufferedWriter output = new BufferedWriter(new FileWriter("Statistics.txt"));
            output.write(data);
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
>>>>>>> Stashed changes

    // Start a new game
    public void startBtnActionPerformed(java.awt.event.ActionEvent evt) {
        // Output statistics to file
        stats.printToFile();
        // Prepare statistics for new round
        stats.initBlockRecord();
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
        score = 0;
        // Update statistics
        stats.score = score;
        stats.round++;
        showScore(score);
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
