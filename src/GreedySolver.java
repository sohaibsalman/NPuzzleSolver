
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
public class GreedySolver extends NPuzzleSolver
{
    void solve(PuzzleState puzzleState)
    {
        List<PuzzleState> newStates = new ArrayList<>();     //TO STORE NEW STATES OF PUZZLE
        List<PuzzleState> exploredStates = new ArrayList<>();     //TO STORE EXPLORED STATES OF PUZZLE
        
        //GET GOAL STATE OF GIVEN PUZZLE
        PuzzleState [] goalState = getGoalState(puzzleState);
        
        newStates.add(puzzleState);
     
        PuzzleState tempState = null;
        
        System.out.println("\nGreedy\n");
        
        while(!newStates.isEmpty())
        {
            //DEQUEUE ELEMENT FROM QUEUE
            
            //PuzzleState puzzle = getNoOfMisplacedTiles(newStates, goalState);
            PuzzleState puzzle = getSumOfMarathonDistance(newStates, goalState);
            
            //ADD THE STATE TO EXPLORED QUEUE
            exploredStates.add(puzzle);
            
            newStates.clear();
            
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
        write.println("Greedy BFS FOR PUZZLE " + puzzleNumber + " \n");
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

    private PuzzleState getNoOfMisplacedTiles(List<PuzzleState> newStates, PuzzleState[] goalStates)
    {
        //This function will count no of tiles that are not misplaced
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

    private PuzzleState getSumOfMarathonDistance(List<PuzzleState> newStates, PuzzleState[] goalState)
    {
        List<Integer> distanceArray = new ArrayList<>();
                
        int distance;
        
        for (PuzzleState newState : newStates)
        {
            distance = getTileDistance(newState, goalState);
            distanceArray.add(distance);
        }
        
        int index = 0;
        int min = distanceArray.get(0);
        for (int i = 0; i < distanceArray.size(); i++)
        {
            if(distanceArray.get(i) < min)
            {
                min = distanceArray.get(i);
                index++;
            }
        }
        
        return newStates.remove(index);
        
    }

    private int getTileDistance(PuzzleState newState, PuzzleState[] goalState)
    {
        int iState, jState;
        int iGoal1, jGoal1;
        int iGoal2, jGoal2;
        
        int [] arr = arrayConverter1D(newState);
        int [] arrGoal1 = arrayConverter1D(goalState[0]);
        int [] arrGoal2 = arrayConverter1D(goalState[1]);
        
        List<Integer> list = converToListArray(arr);
        List<Integer> lGoal1 = converToListArray(arrGoal1);
        List<Integer> lGoal2 = converToListArray(arrGoal2);
                        
        int element;
        int index;
        
        int sumGoal1, sumGoal2;     //TO STORE SUM OF DISTANCE FOR BOTH GOAL STATES
        sumGoal1 = sumGoal2 = 0;
        
        for (int i = 0; i < list.size(); i++)
        {
            //GET 2D INDEX OF ELEMENT FROM CURRENT STATE
            element = list.get(i);
            index = list.indexOf(element);
            iState = index / newState.size;
            jState = index % newState.size;
            
            //GET 2D INDEX OF ELEMENT FROM GOAL STATE 1
            index = lGoal1.indexOf(element);
            iGoal1 = index / newState.size;
            jGoal1 = index % newState.size;
            
            //GET 2D INDEX OF ELEMENT FROM GOAL STATE 2
            index = lGoal2.indexOf(element);
            iGoal2 = index / newState.size;
            jGoal2 = index % newState.size;
            
            //GET SUM
            sumGoal1 += Math.abs(iState - iGoal1) + Math.abs(jState - jGoal1);
            sumGoal2 += Math.abs(iState - iGoal2) + Math.abs(jState - jGoal2);
        }
                
        return Integer.min(sumGoal1, sumGoal2);
    }

   
}
