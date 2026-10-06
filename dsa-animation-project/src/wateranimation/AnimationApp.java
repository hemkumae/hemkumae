package wateranimation;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.util.*;

public class AnimationApp extends Application {
    private final int[] input = {7, 3, 9, 2, 5, 1, 8};
    private final HBox bars = new HBox(6);
    private final Label status = new Label("Choose an algorithm");
    private final ComboBox<String> algorithms = new ComboBox<>();
    private final Slider speed = new Slider(50, 1000, 350);
    private Timeline timeline;

    @Override
    public void start(Stage stage) {
        algorithms.getItems().addAll("Bubble Sort", "Selection Sort", "Insertion Sort", "Merge Sort", "Quick Sort", "Heap Sort");
        algorithms.getSelectionModel().selectFirst();
        Button start = new Button("Animate");
        start.setOnAction(e -> animate());
        VBox top = new VBox(10, new HBox(10, algorithms, start), speed, status);
        top.setPadding(new Insets(15));
        bars.setAlignment(javafx.geometry.Pos.BOTTOM_CENTER);
        bars.setPadding(new Insets(30));
        draw(input, -1, -1);
        BorderPane root = new BorderPane();
        root.setTop(top);
        root.setCenter(new StackPane(bars));
        root.setStyle("-fx-background-color: #20242b;");
        stage.setTitle("DSA Animation Lab");
        stage.setScene(new Scene(root, 900, 600));
        stage.show();
    }

    private void animate() {
        if (timeline != null) timeline.stop();
        AnimationRecorder recorder = new AnimationRecorder();
        switch (algorithms.getValue()) {
            case "Bubble Sort" -> SortingAlgorithms.bubbleSort(input, recorder);
            case "Selection Sort" -> SortingAlgorithms.selectionSort(input, recorder);
            case "Insertion Sort" -> SortingAlgorithms.insertionSort(input, recorder);
            case "Merge Sort" -> SortingAlgorithms.mergeSort(input, recorder);
            case "Quick Sort" -> SortingAlgorithms.quickSort(input, recorder);
            case "Heap Sort" -> SortingAlgorithms.heapSort(input, recorder);
        }
        List<AnimationStep> steps = recorder.getSteps();
        timeline = new Timeline();
        for (int i = 0; i < steps.size(); i++) {
            AnimationStep step = steps.get(i);
            timeline.getKeyFrames().add(new KeyFrame(Duration.millis((i + 1) * speed.getValue()), e -> {
                draw(step.state(), step.firstIndex(), step.secondIndex());
                status.setText(step.action());
            }));
        }
        timeline.play();
    }

    private void draw(int[] values, int first, int second) {
        bars.getChildren().clear();
        int max = Arrays.stream(values).max().orElse(1);
        for (int i = 0; i < values.length; i++) {
            Rectangle bar = new Rectangle(70, Math.max(20, values[i] * 35.0 / max));
            bar.setFill(i == first || i == second ? Color.ORANGE : Color.STEELBLUE);
            Label label = new Label(String.valueOf(values[i]));
            VBox item = new VBox(5, bar, label);
            item.setAlignment(javafx.geometry.Pos.BOTTOM_CENTER);
            bars.getChildren().add(item);
        }
    }

    public static void main(String[] args) { launch(args); }
}
