import java.util.ArrayList;

public class MergeThread extends Thread {

    private final SharedRepository repository;

    public MergeThread(SharedRepository repository) {

        this.repository = repository;
    }

    @Override
    public void run() {

        ArrayList<ArrayList<Integer>> twoArrays;

        // Here we receive the mergedArrayList-of-ArrayLists from removeTwoArrays().
        twoArrays = repository.removeTwoArrays();

        while(twoArrays != null) {

            // Here we call MergeSort algorithm with the mergedArrayList-of-ArrayLists, sort them and insert them back into SharedRepository.
            repository.insert(mergeSort(twoArrays.remove(0), twoArrays.remove(0)));

            // Here we receive new mergedArrayList from removeTwoArrays() and repeat the process.
            twoArrays = repository.removeTwoArrays();
        }
    }

    // Implementation of merge sort algorithm.
    private ArrayList<Integer> mergeSort(ArrayList<Integer> arrayListOne, ArrayList<Integer> arrayListTwo) {

        ArrayList<Integer> mergedArray = new ArrayList<>();

        int i, j;

        for(i = 0, j = 0; i < arrayListOne.size() && j < arrayListTwo.size();) {

            if(arrayListOne.get(i) < arrayListTwo.get(j)) {

                mergedArray.add(arrayListOne.get(i));
                i++;
            }
            else {

                mergedArray.add(arrayListTwo.get(j));
                j++;
            }
        }

        while(i < arrayListOne.size()) {

            mergedArray.add(arrayListOne.get(i++));
        }

        while(j < arrayListTwo.size()) {

            mergedArray.add(arrayListTwo.get(j++));
        }

        return mergedArray;
    }
}
