import java.util.ArrayList;
import java.util.Arrays;

public class SharedRepository {

    private  final ArrayList<ArrayList<Integer>> sharedRepository;
    private  final int maxThreads;
    private int waiting;
    private boolean mergingComplete;

    // Constructor.
    public SharedRepository(int[] scrambledArray, int m) {

        sharedRepository = new ArrayList<>();

        for(int i: scrambledArray) {

            // Convert the received int[] scrambledArray from Main into ArrayList, and add it into sharedRepository which is an ArrayList-of-ArrayLists.
            sharedRepository.add(new ArrayList<>(Arrays.asList(i)));
        }

        mergingComplete = false; // Boolean flag that when true, represent a merging process that has been completed.
        waiting = 0;
        maxThreads = m; // Set the number of threads received from Main.
    }

    // Removes two ArrayLists from sharedRepository, merges them, and sends the mergedArrayList-of-ArrayLists to MergeThread.
    public synchronized ArrayList<ArrayList<Integer>> removeTwoArrays() {

        while(sharedRepository.size() <= 1 && !mergingComplete) {

            if(waiting < maxThreads - 1) {

                waiting++;

                try {

                    wait();
                }
                catch(InterruptedException e) {

                    System.out.println("Error at removeTwoArrays() waiting process.");
                }

                waiting--;
            }
            else {

                mergingComplete = true;
            }
        }

        // When the merging is finished, it notifies all threads.
        if(mergingComplete) {

            notifyAll();

            return null;
        }
            ArrayList<ArrayList<Integer>> mergedArrayList = new ArrayList<>();

            // Here is the removal of two ArrayLists from sharedRepository and the merging of them into one UNSORTED ArrayList-of-ArrayLists.
            mergedArrayList.add(sharedRepository.remove(0));
            mergedArrayList.add(sharedRepository.remove(0));

            return mergedArrayList;
    }

    // Inserts a sortedArrayList-of-ArrayLists received back from MergeThread's run() into sharedRepository and notify all threads.
    public synchronized void insert(ArrayList<Integer> sortedArrayList) {

        sharedRepository.add(sortedArrayList);
        notifyAll();
    }

    // Returns the final sortedArrayList, which is by now the sharedRepository itself.
    public synchronized ArrayList<Integer> getSortedArrayList() {

        while(!mergingComplete) {

            try {

                wait();
            }
            catch(InterruptedException e) {

                System.out.println("Error at getSortedArrayList() waiting process."); }
        }

        return sharedRepository.get(0);
    }
}
