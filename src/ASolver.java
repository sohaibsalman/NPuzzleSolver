
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author sohai
 */
public class ASolver extends InformedSearch
{
        void solve(PuzzleState puzzleState)
    {
        List<PuzzleState> newStates = new ArrayList<>();     //TO STORE NEW STATES OF PUZZLE
        List<PuzzleState> exploredStates = new ArrayList<>();     //TO STORE EXPLORED STATES OF PUZZLE
        
        //GET GOAL STATE OF GIVEN PUZZLE
        PuzzleState [] goalState = getGoalState(puzzleState);
        
        newStates.add(puzzleState);
     
        PuzzleState tempState = null;
        
        System.out.println("\nA*\n");
                
        while(!newStates.isEmpty())
        {
            //DEQUEUE ELEMENT FROM QUEUE
            PuzzleState puzzle = getNoOfMisplacedTiles(newStates, goalState);
            
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
        write.println("A* FOR PUZZLE " + puzzleNumber + " \n");
        write.println(path + "\n");
        
        write.close();
    }   
}
