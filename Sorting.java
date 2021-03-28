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
