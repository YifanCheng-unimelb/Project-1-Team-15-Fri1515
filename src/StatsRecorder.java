package src;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Objects;

public class StatsRecorder {
    protected int round;
    protected int score;
    protected int totalScore;
    protected int averageScore;
    protected String difficulty;
    protected Hashtable<String, Integer> blockRecord = new Hashtable<>();

    public StatsRecorder(String difficulty)
    {
        round = 1;
        score = 0;
        totalScore = 0;
        averageScore = 0;
        this.difficulty = difficulty;
        initBlockRecord();
    }

    protected void initBlockRecord()
    {
        // Initialize block counts
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

    public void printToFile()
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

        averageScore = totalScore / round;

        FileWriter fw = null;
        try {
            // When a new game started, clear the old file if already exist
            if(round == 1)
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
        if(round == 1)
        {
            out.println("Difficulty: " + difficultyText);
            out.println("Average score per round: " + averageScore);
        }

        // Statistics recording for each round of game
        out.println("------------------------------------------             ");
        out.println("Round #" + round);
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
                if(count == 2 && round > 1)
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
}
