/* @Author Luka Alhonen, luka.alhonen@protonmail.com
*
* Class for all the sorting algorithms used in the modules class.
* All written by me, just for fun.
*
*/

public class Sorting {
    private int[] elements = new int[10];

    public int[] heapSort(){
        int heapSize = elements.length;
        while(reHeapDown(elements[0], heapSize - 1)) {
            heapSize--;
        }

        return null;
    }

    public boolean reHeapDown(int root, int last){
        if(root == last){
            return false;
        }


        return true;
    }
}
