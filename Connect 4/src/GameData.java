public class GameData
{
    private char[][] grid = {{' ',' ',' ',' ',' ',' ',' '},
                             {' ',' ',' ',' ',' ',' ',' '},
                             {' ',' ',' ',' ',' ',' ',' '},
                             {' ',' ',' ',' ',' ',' ',' '},
                             {' ',' ',' ',' ',' ',' ',' '},
                             {' ',' ',' ',' ',' ',' ',' '}};

//          Tie Game Grid
//        {{' ',' ',' ',' ',' ',' ',' '},
//        {'B','R','B','R','B','R','B'},
//        {'B','R','B','R','B','R','B'},
//        {'R','B','R','B','R','B','R'},
//        {'R','B','R','B','R','B','R'},
//        {'R','B','R','B','R','B','R'}};

    public char[][] getGrid()
    {
        return grid;
    }

    public void reset()
    {
        grid = new char[6][7];
        for(int r=0;r<grid.length; r++)
            for(int c=0; c<grid[0].length; c++)
                grid[r][c]=' ';
    }

   //is cat has been completed for connect four
    public boolean isCat()
    {
        int isEmpty = 0;
        for(int r=0;r<grid.length; r++)
        {
            for(int c=0; c<grid[0].length; c++)
            {
                if(grid[r][c]==' ')
                {
                    isEmpty = 1;
                    break;
                }
            }
        }
        if(isEmpty != 1 && !isWinner(grid,'X') && !isWinner(grid, 'O'))
            return true;
        else
            return false;
    }

    public boolean isWinner(char[][] arr, char player)
    {
        for (int row = 0; row < arr.length; row++) {
            for (int col = 0; col < arr[0].length; col++) {
                if(arr[row][col] == player) {
                    int horiz = recurLeft(arr, row, col, player) + recurRight(arr, row, col, player) + 1;
                    int vert = recurUp(arr, row, col, player) + recurDown(arr, row, col, player) + 1;
                    int rightDiagonal = recurNE(arr, row, col, player) + recurSW(arr, row, col, player) + 1;
                    int leftDiagonal = recurNW(arr, row, col, player) + recurSE(arr, row, col, player) + 1;
                    if (horiz >= 4) {
                        return true;
                    } else if (vert >= 4) {
                        return true;
                    } else if (rightDiagonal >= 4) {
                        return true;
                    } else if (leftDiagonal >= 4) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    public static int recurRight(char[][] arr, int row, int col, char player){
        int l = arr[0].length;
        if(col + 1 < l && arr[row][col + 1] == player){
            return recurRight(arr, row, col + 1, player) + 1;
        }
        else {
            return 0;
        }
    }
    public static int recurLeft(char[][] arr, int row, int col, char player){
        int l = arr[0].length;
        if(col - 1 >= 0 && arr[row][col - 1] == player){
            return recurLeft(arr, row, col - 1, player) + 1;
        }
        else {
            return 0;
        }
    }
    public static int recurDown(char[][] arr, int row, int col, char player){
        int h = arr.length;
        if(row + 1 < h && arr[row+1][col] == player){
            return recurDown(arr, row + 1, col, player) + 1;
        }
        else{
            return 0;
        }
    }
    public static int recurUp(char[][] arr, int row, int col, char player){
        int h = arr.length;
        if(row - 1  >= 0 && arr[row-1][col] == player){
            return recurUp(arr, row - 1, col, player) + 1;
        }
        else{
            return 0;
        }
    }
    public static int recurNE(char[][] arr, int row, int col, char player){
        int h = arr.length;
        int l = arr[0].length;
        if(row - 1 >= 0 && col + 1 < l && arr[row-1][col+1] == player){
            return recurNE(arr, row - 1, col + 1, player) + 1;
        }
        else{
            return 0;
        }
    }
    public static int recurNW(char[][] arr, int row, int col, char player){
        int h = arr.length;
        int l = arr[0].length;
        if(row - 1 >= 0 && col - 1 >= 0 && arr[row-1][col-1] == player){
            return recurNW(arr, row - 1, col - 1, player) + 1;
        }
        else{
            return 0;
        }
    }
    public static int recurSW(char[][] arr, int row, int col, char player){
        int h = arr.length;
        int l = arr[0].length;
        if((row + 1 < h && col - 1 >= 0) && arr[row+1][col-1] == player){
            return recurSW(arr, row + 1, col - 1, player) + 1;
        }
        else{
            return 0;
        }
    }
    public static int recurSE(char[][] arr, int row, int col, char player){
        int h = arr.length;
        int l = arr[0].length;
        if(row + 1 < h && col + 1 < l && arr[row+1][col+1] == player){
            return recurSE(arr, row + 1, col + 1, player) + 1;
        }
        else{
            return 0;
        }
    }

}
