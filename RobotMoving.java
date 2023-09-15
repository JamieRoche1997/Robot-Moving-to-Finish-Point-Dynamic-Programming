// Author: Jamie Roche
// Date: 28/04/23
// Purpose: Non-Linear Data Struct & Algorithms
// Student Number: R00151829
// Class: SDH2-B

public class RobotMoving
{
    public static void main (String[]args)
    {

        int matrixSize = 20;		// Change this value to test different matrix sizes
        int[][] matrix = new int[matrixSize][matrixSize];

        // Print matrix
        System.out.println ("Matrix:");
        for (int row = 0; row < matrixSize; row++)
        {
            for (int col = 0; col < matrixSize; col++)
            {
                System.out.print (matrix[row][col] + "\t");
            }
            System.out.println ();
        }

        // Find minimum cost using Cost1
        double cost1Weight = 1.1;
        double cost2Weight = 1.3;
        double cost3Weight = 2.5;
        System.out.println ("Minimum cost using Cost1: "
                + String.format ("€%,.2f",
                findMinCost (matrix, matrixSize,
                        cost1Weight,
                        cost2Weight,
                        cost3Weight)));

        // Find minimum cost using Cost2
        cost1Weight = 1.5;
        cost2Weight = 1.2;
        cost3Weight = 2.3;
        System.out.println ("Minimum cost using Cost2: "
                + String.format ("€%,.2f",
                findMinCost (matrix, matrixSize,
                        cost1Weight,
                        cost2Weight,
                        cost3Weight)));
    }

    // Function to find minimum cost using dynamic programming approach
    public static double findMinCost (int[][]matrix, int matrixSize,
                                      double cost1Weight, double cost2Weight,
                                      double cost3Weight)
    {
        double[][] dynamicProgram = new double[matrixSize][matrixSize];
        int[][] direction = new int[matrixSize][matrixSize];

        // Initialize first row and column of dp matrix
        dynamicProgram[0][0] = 0;

        for (int row = 1; row < matrixSize; row++)
        {
            dynamicProgram[row][0] = dynamicProgram[row - 1][0] + cost2Weight;
            direction[row][0] = 1;
            dynamicProgram[0][row] = dynamicProgram[0][row - 1] + cost1Weight;
            direction[0][row] = 2;
        }

        // Fill rest of dp matrix using bottom-up approach
        for (int row = 1; row < matrixSize; row++)
        {
            for (int col = 1; col < matrixSize; col++)
            {
                double cost1 = dynamicProgram[row][col - 1] + cost1Weight;
                double cost2 = dynamicProgram[row - 1][col] + cost2Weight;
                double cost3 = dynamicProgram[row - 1][col - 1] + cost3Weight;

                if (cost1 <= cost2 && cost1 <= cost3)
                {
                    dynamicProgram[row][col] = cost1;
                    direction[row][col] = 1;
                }
                else if (cost2 <= cost1 && cost2 <= cost3)
                {
                    dynamicProgram[row][col] = cost2;
                    direction[row][col] = 2;
                }
                else
                {
                    dynamicProgram[row][col] = cost3;
                    direction[row][col] = 3;
                }
            }
        }

        // Find path taken by robot
        StringBuilder path = new StringBuilder ();
        int row = matrixSize - 1;
        int col = matrixSize - 1;
        while (row >= 0 && col >= 0)
        {
            if (direction[row][col] == 1)
            {
                path.insert (0, "Right \n");
                col--;
            }
            else if (direction[row][col] == 2)
            {
                path.insert (0, "Down \n");
                row--;
            }
            else
            {
                path.insert (0, "Diagonal \n");
                row--;
                col--;
            }
        }

        // Print path taken by robot
        System.out.println ("\nPath taken by robot: \n" + path);

        // Return minimum cost
        return dynamicProgram[matrixSize - 1][matrixSize - 1];
    }

}
