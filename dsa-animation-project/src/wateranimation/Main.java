package wateranimation;

public class Main {
    public static void main(String[] args) {
        int[] input = {7, 3, 9, 2, 5, 1, 8};

        AnimationRecorder bubbleRecorder = new AnimationRecorder();
        SortingAlgorithms.bubbleSort(input, bubbleRecorder);
        print("BUBBLE SORT", bubbleRecorder);

        AnimationRecorder quickRecorder = new AnimationRecorder();
        SortingAlgorithms.quickSort(input, quickRecorder);
        print("QUICK SORT", quickRecorder);

        int[] sorted = {1, 2, 3, 5, 7, 8, 9};
        AnimationRecorder searchRecorder = new AnimationRecorder();
        SearchingAlgorithms.binarySearch(sorted, 7, searchRecorder);
        print("BINARY SEARCH", searchRecorder);

        Stack stack = new Stack();
        stack.push(10);
        stack.push(20);
        System.out.println("STACK: " + stack.values());

        Queue queue = new Queue();
        queue.enqueue(10);
        queue.enqueue(20);
        System.out.println("QUEUE FRONT: " + queue.front());
    }

    private static void print(String title, AnimationRecorder recorder) {
        System.out.println();
        System.out.println(title);
        for (AnimationStep step : recorder.getSteps()) {
            System.out.println(step.action() + " " + step.firstIndex() + " " + step.secondIndex());
        }
    }
}
