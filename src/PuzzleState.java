/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author sohai
 */
public class PuzzleState
{
    int [][] state;
    int size;
    Position zeroPosition = new Position();
    PuzzleState parent = null;
    
    public PuzzleState(int size)
    {
        this.size = size;
        state = new int[size][size];
    }

    public PuzzleState(int[][] state, int size)
    {
        this.state = state;
        this.size = size;
    }

    public PuzzleState(PuzzleState ref)
    {
        this.size = ref.size;
        this.state = new int[size][size];
        for (int i = 0; i < size; i++)
        {
            for (int j = 0; j < size; j++)
            {
                this.state[i][j] = ref.state[i][j];
            }
        }
        this.zeroPosition.i = ref.zeroPosition.i;
        this.zeroPosition.j = ref.zeroPosition.j;
        parent = ref;
    }
    
    

    @Override
    public String toString()
    {
        String str = "(";
        int count = 0;
        for (int i = 0; i < size; i++)
        {
            for (int j = 0; j < size; j++)
            {
                str += state[i][j];
                if(++count != size*size)
                    str += ", ";
            }
        }
        str += ")";
        return str;
    }
  
}

class Position 
{
    int i;
    int j;
}