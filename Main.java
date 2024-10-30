import java.util.Random;
import java.util.Scanner;
import java.util.Arrays;

public class Main {

    private static final int LOWER_BOUND = 1;

    private static final int UPPER_BOUND = 101;

    public  static void main(String[] args) {

        Scanner scan = new Scanner(System.in);

        Random random = new Random();

        int n, m;

        System.out.println("Enter the size of the array:");

        n = getUserInput(scan);

        System.out.println("Enter wanted number of Threads:");

        m = getUserInput(scan);

        scan.close();

        // Create an array of random integers.
        int[] scrambledArray = random.ints(n, LOWER_BOUND, UPPER_BOUND).toArray();

        // Initialise SharedRepository with the scrambledArray and the number of wanted threads.
        SharedRepository repository = new SharedRepository(scrambledArray, m);

        // Create an array of merging threads, length as the chosen number of threads.
        MergeThread[] threads = new MergeThread[m];

        for(int i = 0; i < threads.length; i++) {

            // Create an independent MergeThread processes that take the repository object.
            threads[i] = new MergeThread(repository);

            // Activate the threads.
            threads[i].start();
        }

        System.out.println("Original array: " + Arrays.toString(scrambledArray));

        // Print final result.
        System.out.println("Result array: " + repository.getSortedArrayList());
    }

    // Get input from user in an orderly fashion.
    private static int getUserInput(Scanner scan) {

        int userInput = 0;

        while(userInput <= 0) {

            if(scan.hasNextInt()) {

                userInput = scan.nextInt();
            }

            if(userInput <= 0) {

                System.out.println("Error, enter an integer value bigger then 0.");
                scan.nextLine();
            }
        }

        return userInput;
    }
}
