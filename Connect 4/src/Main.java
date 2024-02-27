import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Main
{
    public static void main(String[] args){
        new TTTFrame(new GameData(), null, 'x');
        char[][] arr = {{' ', ' ', ' ', ' ', ' ', ' ', ' '},
                        {' ', ' ', ' ', ' ', ' ', ' ', ' '},
                        {' ', ' ', ' ', ' ', ' ', 'x', ' '},
                        {' ', ' ', ' ', 'x', ' ', 'x', ' '},
                        {' ', 'x', ' ', 'x', ' ', 'x', ' '},
                        {'1', 'x', 'x', 'x', 'x', 'x', ' '}};
        char[][] check = {{'x', 'x', 'x', 'x'}};
        System.out.println(rower(arr, 0));
        System.out.println(arr[rower(arr, 0)][0]);


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
