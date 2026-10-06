package wateranimation;

public class SortingAlgorithms {
    public static int[] bubbleSort(int[] input, AnimationRecorder recorder) {
        int[] a = input.clone();
        for (int i = 0; i < a.length - 1; i++) {
            boolean swapped = false;
            for (int j = 0; j < a.length - 1 - i; j++) {
                recorder.record("COMPARE", j, j + 1, a);
                if (a[j] > a[j + 1]) {
                    int temp = a[j]; a[j] = a[j + 1]; a[j + 1] = temp;
                    swapped = true;
                    recorder.record("SWAP", j, j + 1, a);
                }
            }
            if (!swapped) break;
        }
        recorder.record("SORTED", -1, -1, a);
        return a;
    }

    public static int[] selectionSort(int[] input, AnimationRecorder recorder) {
        int[] a = input.clone();
        for (int i = 0; i < a.length - 1; i++) {
            int min = i;
            for (int j = i + 1; j < a.length; j++) {
                recorder.record("COMPARE", min, j, a);
                if (a[j] < a[min]) min = j;
            }
            if (min != i) {
                int temp = a[i]; a[i] = a[min]; a[min] = temp;
                recorder.record("SWAP", i, min, a);
            }
        }
        recorder.record("SORTED", -1, -1, a);
        return a;
    }

    public static int[] insertionSort(int[] input, AnimationRecorder recorder) {
        int[] a = input.clone();
        for (int i = 1; i < a.length; i++) {
            int key = a[i];
            int j = i - 1;
            recorder.record("SELECT_KEY", i, -1, a);
            while (j >= 0 && a[j] > key) {
                recorder.record("COMPARE", j, i, a);
                a[j + 1] = a[j];
                recorder.record("SHIFT", j, j + 1, a);
                j--;
            }
            a[j + 1] = key;
            recorder.record("INSERT", j + 1, -1, a);
        }
        recorder.record("SORTED", -1, -1, a);
        return a;
    }

    public static int[] mergeSort(int[] input, AnimationRecorder recorder) {
        int[] a = input.clone();
        mergeSort(a, 0, a.length - 1, recorder);
        recorder.record("SORTED", -1, -1, a);
        return a;
    }

    private static void mergeSort(int[] a, int left, int right, AnimationRecorder recorder) {
        if (left >= right) return;
        int mid = left + (right - left) / 2;
        recorder.record("DIVIDE", left, right, a);
        mergeSort(a, left, mid, recorder);
        mergeSort(a, mid + 1, right, recorder);
        merge(a, left, mid, right, recorder);
    }

    private static void merge(int[] a, int left, int mid, int right, AnimationRecorder recorder) {
        int[] temp = new int[right - left + 1];
        int i = left, j = mid + 1, k = 0;
        while (i <= mid && j <= right) {
            recorder.record("COMPARE", i, j, a);
            if (a[i] <= a[j]) temp[k++] = a[i++];
            else temp[k++] = a[j++];
        }
        while (i <= mid) temp[k++] = a[i++];
        while (j <= right) temp[k++] = a[j++];
        for (int x = 0; x < temp.length; x++) {
            a[left + x] = temp[x];
            recorder.record("MERGE_WRITE", left + x, -1, a);
        }
    }

    public static int[] quickSort(int[] input, AnimationRecorder recorder) {
        int[] a = input.clone();
        quickSort(a, 0, a.length - 1, recorder);
        recorder.record("SORTED", -1, -1, a);
        return a;
    }

    private static void quickSort(int[] a, int low, int high, AnimationRecorder recorder) {
        if (low >= high) return;
        int pivotIndex = partition(a, low, high, recorder);
        quickSort(a, low, pivotIndex - 1, recorder);
        quickSort(a, pivotIndex + 1, high, recorder);
    }

    private static int partition(int[] a, int low, int high, AnimationRecorder recorder) {
        int pivot = a[high], i = low;
        recorder.record("PIVOT", high, -1, a);
        for (int j = low; j < high; j++) {
            recorder.record("COMPARE_PIVOT", j, high, a);
            if (a[j] <= pivot) {
                int temp = a[i]; a[i] = a[j]; a[j] = temp;
                recorder.record("SWAP", i, j, a);
                i++;
            }
        }
        int temp = a[i]; a[i] = a[high]; a[high] = temp;
        recorder.record("PLACE_PIVOT", i, high, a);
        return i;
    }

    public static int[] heapSort(int[] input, AnimationRecorder recorder) {
        int[] a = input.clone();
        for (int i = a.length / 2 - 1; i >= 0; i--) heapify(a, a.length, i, recorder);
        for (int end = a.length - 1; end > 0; end--) {
            int temp = a[0]; a[0] = a[end]; a[end] = temp;
            recorder.record("EXTRACT_MAX", 0, end, a);
            heapify(a, end, 0, recorder);
        }
        recorder.record("SORTED", -1, -1, a);
        return a;
    }

    private static void heapify(int[] a, int size, int root, AnimationRecorder recorder) {
        while (true) {
            int largest = root;
            int left = 2 * root + 1, right = 2 * root + 2;
            if (left < size) recorder.record("COMPARE", root, left, a);
            if (left < size && a[left] > a[largest]) largest = left;
            if (right < size) recorder.record("COMPARE", root, right, a);
            if (right < size && a[right] > a[largest]) largest = right;
            if (largest == root) return;
            int temp = a[root]; a[root] = a[largest]; a[largest] = temp;
            recorder.record("HEAP_SWAP", root, largest, a);
            root = largest;
        }
    }
}
