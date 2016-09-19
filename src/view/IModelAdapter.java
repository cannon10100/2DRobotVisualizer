package view;

import model.RobotType;

import java.awt.*;

/**
 * Adapter that allows a view to communicate with the model.
 */
public interface IModelAdapter {

    /**
     * Method to cause the model to paint itself.
     * @param g Graphics object to paint on.
     */
    public void paint(Graphics g);

    /**
     * Method to cause the model to make a new Visualizer.
     */
    public void makeVisualizer(RobotType type, String filename);

    /**
     * Method to clear the current visualizer.
     */
    public void clearVisualizer();
}
