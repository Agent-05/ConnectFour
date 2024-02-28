import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Main
{
    public static void main(String[] args){
//        new TTTFrame(new GameData(), null, 'x');
        char[][] arr = {{' ', ' ', ' ', ' ', ' ', ' ', ' '},
                        {' ', ' ', ' ', ' ', ' ', ' ', ' '},
                        {' ', ' ', ' ', ' ', ' ', ' ', ' '},
                        {'x', ' ', ' ', ' ', ' ', ' ', ' '},
                        {'x', 'x', ' ', ' ', ' ', ' ', ' '},
                        {'x', 'x', 'x', ' ', 'x', 'x', 'x'}};


        int row = rower(arr, 0);
        System.out.println(isWinner(arr, row + 1, 0));
        System.out.println(row);
        arr[row][0] = 'x';
        System.out.println("New Spot = " + arr[row][0]);
        System.out.println(arr[row]);
        System.out.println(isWinner(arr, row, 0));

    }
    public static boolean isWinner(char[][] arr, int row, int col){
        char player = 'x';
        int horiz = recurLeft(arr, row, col, player) + recurRight(arr, row, col, player) + 1;
        int vert = recurUp(arr, row, col, player) + recurDown(arr, row, col, player) + 1;
        int rightDiagonal = recurNE(arr,row,col,player) + recurSW(arr, row, col, player) + 1;
        int leftDiagonal = recurNW(arr,row,col,player) + recurSE(arr, row, col, player) + 1;
        if(horiz >= 4){
            return true;
        }
        else if(vert >= 4){
            return true;
        } else if (rightDiagonal >= 4) {
            return true;
        } else if (leftDiagonal >= 4) {
            return true;
        } else{
            return false;
        }

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
    public static int rower(char[][] arr, int col){
        for (int i = arr.length-1; i != 0; i--){
            if (arr[i][col] == ' '){
                return i;
            }
        }
        return -1;
    }
}
