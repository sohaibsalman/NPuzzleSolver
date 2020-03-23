
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
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
public class UCSSolver extends NPuzzleSolver
{
    void solve(PuzzleState puzzleState)
    {
        List<PuzzleState> newStates = new ArrayList<>();     //TO STORE NEW STATES OF PUZZLE
        List<PuzzleState> exploredStates = new ArrayList<>();     //TO STORE EXPLORED STATES OF PUZZLE
        
        //GET GOAL STATE OF GIVEN PUZZLE
        PuzzleState [] goalState = getGoalState(puzzleState);
        
        newStates.add(puzzleState);
     
        PuzzleState tempState = null;
        
        System.out.println("\nUCS\n");
        
        while(!newStates.isEmpty())
        {
            //DEQUEUE ELEMENT FROM QUEUE
            PuzzleState puzzle = getMinimalCostState(newStates, goalState);
            
            if(isGoalState(goalState, puzzle.state))
            {
                System.out.println(puzzle);
                tempState = new PuzzleState(puzzle);
                break;
            }
            
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
                        newStates.add(tempState);
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
                        newStates.add(tempState);
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
                        newStates.add(tempState);
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
                        newStates.add(tempState);
                    }
                }
            }
            System.out.println(puzzle);
        }
        
        String path = getPath(tempState);
        
        PrintWriter write = initOutputFile();
        write.println("UCS FOR PUZZLE " + puzzleNumber + " \n");
        write.println(path + "\n");
        
        write.close();
    }

    private boolean IsVisited(PuzzleState puzzle, List<PuzzleState> queue)
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

    private PuzzleState getMinimalCostState(List<PuzzleState> newStates, PuzzleState[] goalStates)
    {
        List<Integer> costArray = new ArrayList<>();
        
        for (PuzzleState newState : newStates)
        {
            int cost = getStateCost(newState, goalStates);
            costArray.add(cost);
        }
        
        int index = 0;
        int min = costArray.get(0);
        for (int i = 0; i < costArray.size(); i++)
        {
            if(costArray.get(i) < min)
            {
                min = costArray.get(i);
                index++;
            }
        }
        
        return newStates.remove(index);
    }

    private int getStateCost(PuzzleState newState, PuzzleState[] goalStates)
    {
        int cost1 = 0;
        int cost2 = 0;
        for (int i = 0; i < newState.size; i++)
        {
            for (int j = 0; j < newState.size; j++)
            {
                if(newState.state[i][j] == goalStates[0].state[i][j])
                    cost1++;
                if(newState.state[i][j] == goalStates[1].state[i][j])
                    cost2++;
            }
        }
        return Integer.max(cost1, cost2);
    }

}
