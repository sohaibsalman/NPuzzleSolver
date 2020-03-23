
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;
import java.util.StringTokenizer;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author sohai
 */
public class NPuzzleSolver
{
    private final Queue<PuzzleState> initPuzzleStates;
    static int puzzleNumber = 1;

    public NPuzzleSolver()
    {
        initPuzzleStates = new LinkedList<>();
    }
    
    void start()
    {
        getFileData();
        clearOutputFile();
        solve();
    }

      
    private void solve()
    {
        while(!initPuzzleStates.isEmpty())
        {
            PuzzleState state = initPuzzleStates.remove();
            new BFSSolver().solve(state);
            //new DFSSolver().solve(state);
            new UCSSolver().solve(state);
            new GreedySolver().solve(state);
            new ASolver().solve(state);
                        
            puzzleNumber++;
        }
    }
    
    
    private void getFileData()
    {
        try
        {
            //OPEN FILE TO READ DATA
            BufferedReader in = new BufferedReader(new FileReader(new File("Input.txt")));
            //GET NO OF INPUTS
            int noOfInputs = Integer.parseInt(in.readLine());
            
            //GET STATES OF PUZZLES
            for (int i = 0; i < noOfInputs; i++)
            {
                //GET FULL LINE, SEPERATED BY COMMAS
                String line = in.readLine();
                //TOKENIZE THE LINE WITH COMMAS
                StringTokenizer token = new StringTokenizer(line, ",");
                
                int count = token.countTokens();    //TO HOLD NO OF PEICES IN PUZZLE
                int size = (int) Math.sqrt(count);  //STORES THE SIZE OF 2D ARRAY
                int [][] array = new int[size][size];   //ARRAY TO HOLD STATE OF PUZZLE
                
                int x = -1, y = -1;     //TO STORE THE POSITION OF '0' IN PUZZLE
                
                //INITIALIZE THE ARRAY ACCORDING TO PUZZLE STATES
                for (int j = 0; j < size; j++)
                {
                    for (int k = 0; k < size; k++)
                    {
                        int value = Integer.parseInt(token.nextToken());
                        if(value == 0)
                        {
                            x = j;
                            y = k;
                        }
                        array[j][k] = value;
                    }
                }
                PuzzleState puzzle = new PuzzleState(array, size);
                setZeroPostion(puzzle, x, y);
                //ADD THE STATE TO QUEUE OF STATES
                initPuzzleStates.add(puzzle);
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

  
    
    protected void setZeroPostion(PuzzleState puzzle, int x, int y)
    {
        puzzle.zeroPosition.i = x;
        puzzle.zeroPosition.j = y;
    }

    protected boolean[] getValidMoves(PuzzleState puzzle)
    {
        /*
            0 -> UP
            1 -> DOWN
            2 -> LEFT
            3 -> RIGHT
        */

        int i = puzzle.zeroPosition.i;
        int j = puzzle.zeroPosition.j;
        
        boolean validMoves [] = {true, true, true, true};
        
        if(i == puzzle.size - 1)    //CANNOT MOVE DOWN
        {
            validMoves[1] = false;
        }
        if(i == 0)      //CANNOT MOVE UP
        {
            validMoves[0] = false;
        }
        if(j == puzzle.size - 1)        //CANNOT MOVE RIGHT       
        {
            validMoves[3] = false;
        }
        if(j == 0)      //CANNOT MOVE LEFT
        {
            validMoves[2] = false;
        }
        return validMoves;
    }

    protected void moveTiles(PuzzleState puzzle, int i0, int j0, int i, int j)
    {
        puzzle.state[i0][j0] = puzzle.state[i][j];
        puzzle.state[i][j] = 0;
    }
    
    protected int [] arrayConverter1D(PuzzleState st)
    {
         List<Integer> list = new ArrayList<>();
        for (int i = 0; i < st.state.length; i++) 
        {
            for (int j = 0; j < st.state[i].length; j++) 
            { 
                list.add(st.state[i][j]); 
            }
        }
        
        int[] arr = new int[list.size()];
        for (int i = 0; i < arr.length; i++) 
        {
            arr[i] = list.get(i);
        }
        return arr;
    }
    
    protected PuzzleState[] getGoalState(PuzzleState st)
    {
       
        int [] arr = arrayConverter1D(st).clone();
        
        java.util.Arrays.sort(arr);
        
        //FOR 0 AT BEGINING
        int [][] goal1 = arrayConverter2D(arr, st.size).clone();
        
        //FOR 0 AT END
        arr[0] = arr[arr.length - 1];
        arr[arr.length - 1] = 0;
        
        Arrays.sort(arr, 0, arr.length - 1);
        
        int [][] goal2 = arrayConverter2D(arr, st.size);
        
        
        PuzzleState [] goals = new PuzzleState[2];
        goals[0] = new PuzzleState(st.size);
        goals[1] = new PuzzleState(st.size);
        
        goals[0].state = goal1.clone();
        goals[1].state = goal2.clone();

        return goals;
    }


    protected boolean isGoalState(PuzzleState[] goals, int [][] curState)
    {
        for (int i = 0; i < curState.length; i++)
        {
            for (int j = 0; j < curState[i].length; j++)
            {
                if(goals[0].state[i][j] != curState[i][j] && goals[1].state[i][j] != curState[i][j])
                    return false;
            }
   
        }
        
        return true;
    }

    protected PrintWriter initOutputFile()
    {
        try
        {
            PrintWriter write = new PrintWriter(new FileWriter(new File("Output.txt"), true));
            return write;
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return null;
    }

    protected String getPath(PuzzleState state)
    {
        Stack<PuzzleState> s = new Stack<>();
        while(state != null)
        {
            s.push(state);
            state = state.parent;
        }
        String path = "";
        while(!s.isEmpty())
        {
            path += s.pop();
            if(!s.isEmpty())
                path += " -> ";
        }  
        return path;
    }

    private void clearOutputFile()
    {
        File f = new File("Output.txt");
        if(f.exists())
        {
            f.delete();
        }
    }

    private int[][] arrayConverter2D(int[] arr, int size)
    {
        int [][] goal = new int[size][size];
      
        int index = 0;
        
        for (int i = 0; i < size; i++)
        {
            for (int j = 0; j < size; j++)
            {
                goal[i][j] = arr[index++];
            }
        }
        return goal;
    }
    
    protected List<Integer> converToListArray(int[] arr)
    {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < arr.length; i++)
        {
            list.add(arr[i]);
        }
        return list;
    }
    
    protected int getDepth(PuzzleState newState)
    {
        int depth = 0;
        while (newState.parent != null)
        {            
            depth++;
            newState = newState.parent;
        }
        return depth;
    }
    
    protected void displayPath(PuzzleState tempState, String algoName, int num)
    {
        String path = getPath(tempState);
        
        PrintWriter write = initOutputFile();
        write.println(algoName + " FOR PUZZLE " + puzzleNumber + " [" + num + " States Processed]" + " \n");
        write.println(path + "\n");
        
        write.close();
    }
    
}
