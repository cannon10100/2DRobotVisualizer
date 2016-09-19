package controller;

import model.IViewAdapter;
import model.RobotType;
import model.VisualizerModel;
import view.IModelAdapter;
import view.VisualizerGUI;

import java.awt.*;

/**
 * Created by cannon on 9/8/16.
 *
 * Controller for the Visualizer application, responsible for creating the GUI and model and
 * connecting the two.
 */
public class VisualizerController {

    /**
     * The model representing the state of this application.
     */
    private VisualizerModel _model;

    /**
     * The Swing view for the application.
     */
    private VisualizerGUI _view;

    /**
     * Method to start the application.
     */
    private void start() {
        _model.start();
        _view.start();
    }

    public VisualizerController() {
        _model = new VisualizerModel(new IViewAdapter() {

            @Override
            public void update() {
                _view.update();
            }
        });

        _view = new VisualizerGUI(new IModelAdapter() {

            @Override
            public void paint(Graphics g) {
                _model.paint(g);
            }

            @Override
            public void makeVisualizer(RobotType type, String filename) {
                _model.makeVisualizer(type, filename);
            }

            @Override
            public void clearVisualizer() {
                _model.clearVisualizer();
            }
        });
    }

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    VisualizerController controller = new VisualizerController();
                    controller.start();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
