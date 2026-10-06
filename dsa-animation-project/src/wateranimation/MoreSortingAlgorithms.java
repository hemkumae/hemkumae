package wateranimation;

public class MoreSortingAlgorithms {
    public static int[] countingSort(int[] input, AnimationRecorder recorder) {
        if (input.length == 0) return new int[0];
        int min = input[0], max = input[0];
        for (int value : input) { min = Math.min(min, value); max = Math.max(max, value); }
        int[] count = new int[max - min + 1], result = new int[input.length];
        for (int value : input) count[value - min]++;
        int index = 0;
        for (int i = 0; i < count.length; i++) {
            while (count[i]-- > 0) {
                result[index] = i + min;
                recorder.record("WRITE", index, -1, result);
                index++;
            }
        }
        recorder.record("SORTED", -1, -1, result);
        return result;
    }

    public static int[] radixSort(int[] input, AnimationRecorder recorder) {
        int[] a = input.clone();
        int max = 0;
        for (int value : a) if (value >= 0) max = Math.max(max, value);
        for (int exp = 1; max / exp > 0; exp *= 10) {
            int[] output = new int[a.length], count = new int[10];
            for (int value : a) count[(value / exp) % 10]++;
            for (int i = 1; i < 10; i++) count[i] += count[i - 1];
            for (int i = a.length - 1; i >= 0; i--) {
                int digit = (a[i] / exp) % 10;
                output[--count[digit]] = a[i];
            }
            System.arraycopy(output, 0, a, 0, a.length);
            recorder.record("DIGIT_PASS", exp, -1, a);
        }
        recorder.record("SORTED", -1, -1, a);
        return a;
    }
}
