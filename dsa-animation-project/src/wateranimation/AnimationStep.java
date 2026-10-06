package wateranimation;

public record AnimationStep(String action, int firstIndex, int secondIndex, int[] state) {
    public AnimationStep {
        state = state.clone();
    }
}
