package wateranimation;

public class MoreSearchingAlgorithms {
    public static int interpolationSearch(int[] a, int target, AnimationRecorder recorder) {
        int low = 0, high = a.length - 1;
        while (low <= high && target >= a[low] && target <= a[high]) {
            if (a[high] == a[low]) {
                if (a[low] == target) {
                    recorder.record("FOUND", low, -1, a);
                    return low;
                }
                break;
            }
            int position = low + (int)((long)(target - a[low]) * (high - low) / (a[high] - a[low]));
            recorder.record("PROBE", position, -1, a);
            if (a[position] == target) {
                recorder.record("FOUND", position, -1, a);
                return position;
            }
            if (a[position] < target) low = position + 1;
            else high = position - 1;
        }
        recorder.record("NOT_FOUND", -1, -1, a);
        return -1;
    }
}
