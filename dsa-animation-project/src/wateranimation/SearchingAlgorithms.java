package wateranimation;

public class SearchingAlgorithms {
    public static int linearSearch(int[] a, int target, AnimationRecorder recorder) {
        for (int i = 0; i < a.length; i++) {
            recorder.record("CHECK", i, -1, a);
            if (a[i] == target) {
                recorder.record("FOUND", i, -1, a);
                return i;
            }
        }
        recorder.record("NOT_FOUND", -1, -1, a);
        return -1;
    }

    public static int binarySearch(int[] a, int target, AnimationRecorder recorder) {
        int low = 0, high = a.length - 1;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            recorder.record("CHECK_MIDDLE", mid, -1, a);
            if (a[mid] == target) {
                recorder.record("FOUND", mid, -1, a);
                return mid;
            }
            if (a[mid] < target) {
                recorder.record("MOVE_RIGHT", mid, high, a);
                low = mid + 1;
            } else {
                recorder.record("MOVE_LEFT", low, mid, a);
                high = mid - 1;
            }
        }
        recorder.record("NOT_FOUND", -1, -1, a);
        return -1;
    }

    public static int jumpSearch(int[] a, int target, AnimationRecorder recorder) {
        int n = a.length;
        int block = Math.max(1, (int) Math.sqrt(n));
        int prev = 0;

        while (prev < n && a[Math.min(block, n) - 1] < target) {
            recorder.record("JUMP", Math.min(block, n) - 1, -1, a);
            prev = block;
            block += Math.max(1, (int) Math.sqrt(n));
            if (prev >= n) {
                recorder.record("NOT_FOUND", -1, -1, a);
                return -1;
            }
        }

        while (prev < Math.min(block, n)) {
            recorder.record("CHECK", prev, -1, a);
            if (a[prev] == target) {
                recorder.record("FOUND", prev, -1, a);
                return prev;
            }
            prev++;
        }

        recorder.record("NOT_FOUND", -1, -1, a);
        return -1;
    }
}
