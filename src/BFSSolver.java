
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.Queue;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author sohai
 */
public class BFSSolver extends NPuzzleSolver
{
    
    void solve(PuzzleState puzzleState)
    {
        Queue<PuzzleState> newStates = new LinkedList<>();     //TO STORE NEW STATES OF PUZZLE
        Queue<PuzzleState> exploredStates = new LinkedList<>();     //TO STORE EXPLORED STATES OF PUZZLE
        
        //GET GOAL STATE OF GIVEN PUZZLE
        PuzzleState [] goalState = getGoalState(puzzleState);
        
        newStates.add(puzzleState);
     
        PuzzleState tempState = null;
        
        System.out.println("\nBFS\n");
        
        while(!newStates.isEmpty())
        {
            //DEQUEUE ELEMENT FROM QUEUE
            PuzzleState puzzle = newStates.remove(); 
            
            //ADD THE STATE TO EXPLORED QUEUE
            exploredStates.add(puzzle);
            
            int i = puzzle.zeroPosition.i;      //ROW POSITION OF ZERO 
            int j = puzzle.zeroPosition.j;      //COLUMN POSITION OF ZERO
            
            boolean [] isValidMove = getValidMoves(puzzle);
                        
            if(isValidMove[0])
            {
                tempState = new PuzzleState(puzzle);
                moveTiles(tempState, i, j, tempState.zeroPosition.i - 1, tempState.zeroPosition.j);
                tempState.zeroPosition.i--;
                if(!IsVisited(tempState, newStates))
                {
                    if(!IsVisited(tempState, exploredStates))
                    {
                        if(!isGoalState(goalState, tempState.state))
                            newStates.add(tempState);
                        else 
                        {
                            System.out.println(tempState);
                            break;
                        }
                    }
                }
            }
                        
            if(isValidMove[1])
            {
                tempState = new PuzzleState(puzzle);
                moveTiles(tempState, i, j, tempState.zeroPosition.i + 1, tempState.zeroPosition.j);
                tempState.zeroPosition.i++;
                if(!IsVisited(tempState, newStates))
                {
                    if(!IsVisited(tempState, exploredStates))
                    {
                        if(!isGoalState(goalState, tempState.state))
                            newStates.add(tempState);
                        else 
                        {
                            System.out.println(tempState);
                            break;
                        }
                    }
                }
            }
            
            
            if(isValidMove[2])
            {
                tempState = new PuzzleState(puzzle);
                moveTiles(tempState, i, j, tempState.zeroPosition.i, tempState.zeroPosition.j - 1);
                tempState.zeroPosition.j--;
                if(!IsVisited(tempState, newStates))
                {
                    if(!IsVisited(tempState, exploredStates))
                    {
                        if(!isGoalState(goalState, tempState.state))
                            newStates.add(tempState);
                        else 
                        {
                            System.out.println(tempState);
                            break;
                        }
                    }
                }
            }
                        
            if(isValidMove[3])
            {
                tempState = new PuzzleState(puzzle);
                moveTiles(tempState, i, j, tempState.zeroPosition.i, tempState.zeroPosition.j + 1);
                tempState.zeroPosition.j++;
                if(!IsVisited(tempState, newStates))
                {
                    if(!IsVisited(tempState, exploredStates))
                    {
                        if(!isGoalState(goalState, tempState.state))
                            newStates.add(tempState);
                        else 
                        {
                            System.out.println(tempState);
                            break;
                        }
                    }
                }
            }
            System.out.println(puzzle);
        }
        
        String path = getPath(tempState);
        
        PrintWriter write = initOutputFile();
        write.println("BFS FOR PUZZLE " + puzzleNumber + " \n");
        write.println(path + "\n");
        
        write.close();
    }

    private boolean IsVisited(PuzzleState puzzle, Queue<PuzzleState> queue)
    {
        Queue<PuzzleState> temp = new LinkedList<>(queue);
        
        while(!temp.isEmpty())
        {
            PuzzleState st = temp.remove();
            
            if(st.size != puzzle.size)
                continue;
            if(st.zeroPosition.i != puzzle.zeroPosition.i || st.zeroPosition.j != puzzle.zeroPosition.j)
                continue;
                    
            int count = 0;

            for (int i = 0; i < st.size; i++)
            {
                for (int j = 0; j < st.size; j++)
                {
                    if(st.state[i][j] == puzzle.state[i][j])
                        count++;
                }
            }
            if(count == puzzle.size*puzzle.size)
                return true;
        }
        return false;
    }

}
