package edu.school21.maze.controller;

import edu.school21.maze.generation.MazeGenerator;
import edu.school21.maze.model.Maze;
import edu.school21.maze.model.Solution;
import edu.school21.maze.view.MazeCanvas;
import edu.school21.maze.model.Point;
import edu.school21.maze.waveAlgoritm.WaveAlgorithm;
import javafx.scene.Scene;
import javafx.scene.control.Spinner;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class Controller {
    private MazeCanvas mazeCanvas;
    private Scene scene;
    private Maze maze;

    public void start(Stage stage) {
        Spinner<Integer> rowsSpinner = new Spinner<>(2, 50, 2);
        Spinner<Integer> colsSpinner = new Spinner<>(2, 50, 2);
        Button generateButton = new Button("GENERATE MAZE");
        mazeCanvas = new MazeCanvas();
        mazeCanvas.createUI(rowsSpinner, colsSpinner, generateButton, stage);
        scene = mazeCanvas.getMazeScene();
        generateButton.setOnAction(event -> generateMaze(rowsSpinner.getValue(), colsSpinner.getValue()));
        List<Point> coordinates = new ArrayList<>();
        scene.setOnMouseClicked(event -> {
            int x = (int)event.getSceneX();
            int y = (int)event.getSceneY();
            coordinates.add(new Point(x, y));
            if(coordinates.size() == 2){
                generateSolution(coordinates);
                coordinates.clear();
            }
        });
    }

    private void generateSolution(List<Point> coordinates) {
        Solution solution = new Solution(coordinates);
        solution.convertingPixelsToCells(mazeCanvas.getCellHeight(), mazeCanvas.getCellWidth());
        WaveAlgorithm waveAlgorithm = new WaveAlgorithm(maze, solution);
        waveAlgorithm.findPath();
        solution.convertingCellsToPixels(mazeCanvas.getCellHeight(), mazeCanvas.getCellWidth());
        mazeCanvas.drawSolution(solution);
    }

    private void generateMaze(Integer rows, Integer cols) {
        maze = new Maze(rows, cols);
        MazeGenerator mazeGenerator = new MazeGenerator(maze);
        mazeGenerator.mazeGeneration();
        mazeCanvas.drawMaze(maze);
    }
}
