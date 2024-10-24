package edu.school21.maze.generation;

import edu.school21.maze.model.Maze;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class MazeGenerator {
    private Maze maze;
    private int setFillCounter;
    private List<Integer> decisionArray;
    private List<Integer> list;
    private List<Integer> list2;
    private List<List<Integer>> countBottomWall;
    private int indexDecisionArray;


    public MazeGenerator(Maze maze) {
        this.maze = maze;
        decisionArray = new ArrayList<>();
        countBottomWall = new ArrayList<>();
        setFillCounter = 1;
        indexDecisionArray = 0;
    }

    /**
     * The method generates a random number 0 or 1.
     */
    public void generateRandomNumber() {
        decisionArray = IntStream.generate(() -> ThreadLocalRandom.current().nextInt(2))
                .limit(maze.getNumberOfCols() * maze.getNumberOfRows() * 3L)
                .boxed()
                .collect(Collectors.toList());
        mazeGeneration();
    }

    public void mazeGeneration() {
        list2 = Stream.generate(() -> 0).limit(maze.getNumberOfCols()).collect(Collectors.toCollection(ArrayList::new));
        for (int i = 0; i < 50; i++) {
            countBottomWall.add(new ArrayList<>(Arrays.asList(0, 0)));
        }
        list = new ArrayList<>(list2);
        fillingArrayWithNumbersInOrder(list);
        placeWallOnTheRight(list);
        placeWallOnTheBottom(list);
    }

    private void placeWallOnTheRight(List<Integer> list) {
        for (int i = 0; i < list.size(); i++) {
            if (decisionArray.get(indexDecisionArray++) == 0) {
                if ((Objects.equals(list.get(i), list.get(i + 1))) && (i != list.size() - 1)) {
                    maze.putRightWall(1);
                } else {
                    maze.putRightWall(0);
                    for (int j = i + 1; j < list.size(); j++) {
                        if (list.get(j).equals(list.get(i))) {
                            list.set(j, list.get(i));
                            countBottomWall.get(list.get(i)).set(0, countBottomWall.get(list.get(i)).get(0) + 1);
                        }
                    }
                }
            } else {
                maze.putRightWall(1);
            }
        }
    }

    private void placeWallOnTheBottom(List<Integer> list) {
        for (Integer integer : list) {
            if (decisionArray.get(indexDecisionArray++) == 1) {
                if (countBottomWall.get(integer).get(0) - countBottomWall.get(integer).get(1) > 1) {
                    maze.putBottomWall(1);
                    countBottomWall.get(integer).set(1, countBottomWall.get(integer).get(1) + 1);
                }
            } else {
                maze.putBottomWall(0);
            }
        }
    }

    private void fillingArrayWithNumbersInOrder(List<Integer> list) {
        for (int i = 0; i < maze.getNumberOfCols(); i++) {
            if (list.get(i) == 0) {
                list.set(i, setFillCounter++);
            }
        }
    }
}
