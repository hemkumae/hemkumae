# DSA Animation Project

## Purpose

A Java-based Data Structures and Algorithms learning platform designed to make DSA understandable through step-by-step animation.

The core algorithms are implemented manually in Java. The animation layer consumes algorithm steps so the learner can see comparisons, swaps, pointer movement, insertions, deletions, rotations, queue operations, stack operations, and search decisions.

## Sorting Algorithms

- Bubble Sort
- Selection Sort
- Insertion Sort
- Merge Sort
- Quick Sort
- Heap Sort

## Searching Algorithms

- Linear Search
- Binary Search
- Jump Search

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

Algorithm classes generate AnimationStep objects. The animation layer can replay those steps at a controlled speed through JavaFX or Swing.

This separates DSA logic from visualization, allowing the same implementation to be tested and animated.

## Learning Goals

- Understand operations instead of memorizing code.
- Observe algorithm state after every important operation.
- Compare time and space complexity.
- Understand recursion and divide-and-conquer visually.
- Understand pointer and reference movement.
- Understand tree rotations and heapification.
- Understand graph traversal decisions.

## Run

Compile the Java files and run wateranimation.Main.

The console version prints algorithm steps and provides the foundation for a graphical animation interface.
