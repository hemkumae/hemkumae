package wateranimation;

import java.util.*;

public class AnimationRecorder {
    private final List<AnimationStep> steps = new ArrayList<>();

    public void record(String action, int firstIndex, int secondIndex, int[] state) {
        steps.add(new AnimationStep(action, firstIndex, secondIndex, state));
    }

    public List<AnimationStep> getSteps() {
        return Collections.unmodifiableList(steps);
    }
}
