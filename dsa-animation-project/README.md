# DSA Animation Project

## Purpose

A Java-based Data Structures and Algorithms learning platform designed to make DSA understandable through step-by-step animation.

The core algorithms are implemented manually in Java. The visualization layer consumes algorithm steps so learners can see comparisons, swaps, recursion, pointer movement, writes, heap operations, and search decisions.

## Sorting Algorithms

- Bubble Sort
- Selection Sort
- Insertion Sort
- Merge Sort
- Quick Sort
- Heap Sort
- Counting Sort
- Radix Sort

## Searching Algorithms

- Linear Search
- Binary Search
- Jump Search
- Interpolation Search

## Data Structures

- Array
- Dynamic Array
- Linked List
- Doubly Linked List
- Stack
- Queue
- Circular Queue
- Deque
- Hash Table
- Binary Search Tree
- AVL Tree
- Heap
- Graph

## Architecture

Algorithm classes generate AnimationStep objects. The JavaFX animation layer replays those steps at a controlled speed.

This separates DSA logic from visualization. The algorithms can therefore be tested independently while the same logic powers the visualizer.

## Visualizer

The project includes a JavaFX application with:

- Algorithm selection
- Animated array bars
- Highlighted elements
- Step-by-step state changes
- Adjustable animation speed
- Operation status display

The visualizer currently animates the major comparison-based sorting algorithms. The same AnimationStep architecture can be extended to searching, trees, linked lists, stacks, queues, heaps, and graphs.

## Learning Goals

- Understand every operation instead of memorizing code.
- Observe the array state after important operations.
- Understand time and space complexity.
- Understand recursion and divide-and-conquer visually.
- Understand pointer and reference movement.
- Understand tree rotations and heapification.
- Understand graph traversal decisions.

## Run

Install Java 17 and Maven.

From the dsa-animation-project directory:

mvn javafx:run

For the console algorithm demonstration:

Compile the Java sources and run wateranimation.Main.

## Project Structure

- AnimationStep.java
- AnimationRecorder.java
- SortingAlgorithms.java
- MoreSortingAlgorithms.java
- SearchingAlgorithms.java
- MoreSearchingAlgorithms.java
- Stack.java
- Queue.java
- AnimationApp.java
- Main.java
- pom.xml
