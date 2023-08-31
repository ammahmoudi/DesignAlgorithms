import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String string = scanner.nextLine();
        MinimumSwapCounter finder = new MinimumSwapCounter();
        System.out.println(finder.find(string));
    }

}

class MinimumSwapCounter {
    public long find(String string) {
        List<Integer> starterPositions = new ArrayList<>();
        for (int i = 0; i < string.length(); i++) {
            if (string.charAt(i) == '(') {
                starterPositions.add(i);
            }
        }
        long result = 0;
        int counter = 0;
        int nextStarter = 0;
        char[] chars = string.toCharArray();
        for (int i = 0; i < string.length(); i++) {
            if (chars[i] == '(') {
                counter++;
                nextStarter++;
            } else if (chars[i] == ')') {
                counter--;
            }
            //if counter get negative start swapping
            if (counter < 0) {
                int nextStarterIndex = starterPositions.get(nextStarter);
                result += nextStarterIndex - i;
                char temp = chars[i];
                chars[i] = chars[nextStarterIndex];
                chars[nextStarterIndex] = temp;
                nextStarter++;
                counter = 1;
            }
        }
        return result;
    }

    public int find2(String string) {
        int result = 0;
        int counter = 0;
        char[] chars = string.toCharArray();
        for (int i = 0; i < string.length(); i++) {
            if (chars[i] == '(') {
                counter++;
            } else if (chars[i] == ')') {
                counter--;
            }
            //if counter get negative start swapping
            if (counter < 0) {
                int j = i + 1;
                while (j < string.length() & chars[j] == ')') {
                    j++;
                }
                result += j - i;
                while (j > i) {
                    chars[j] = chars[j - 1];
                    j--;
                }
                chars[i] = '(';
                counter = 1;
            }
        }
        return result;
    }
    public int find3(String string){
        int starterCounter=0;
        int enderCounter=0;
        int imbalanceCounter=0;
        int result=0;
        char[]chars=string.toCharArray();
        for (int i = 0; i < string.length(); i++) {
            if (chars[i] == '(') {
                starterCounter++;
                if(imbalanceCounter>0){
                result+=imbalanceCounter;
                imbalanceCounter--;
                }
            }
             else if (chars[i] == ')') {
                 enderCounter++;
                 imbalanceCounter=(enderCounter-starterCounter);

            }
    }
    return result;
    }
}