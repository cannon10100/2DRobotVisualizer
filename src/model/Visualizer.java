package model;

import javafx.scene.transform.Affine;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;

/**
 * Class representing a visualization of robot configurations in a 2D environment with obstacles.
 */
public class Visualizer implements Observer {

    /**
     * The type of the robot to be drawn.
     */
    private RobotType _type;

    /**
     * List of obstacles to be drawn as rectangles.
     */
    private ArrayList<Shape> _obstacleList;

    /**
     * List of configuration shapes to be drawn.
     */
    private ArrayList<Shape> _configList;

    /**
     * File from which to draw rectangular obstacles.
     */
    private String _obstacleFile;

    /**
     * Display the Visualizer when notified by the model.
     * @param o observable within the model which passes a Graphics object.
     * @param arg the Graphics object to paint on.
     */
    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof Graphics) {
            Graphics g = (Graphics) arg;

            g.translate(g.getClipBounds().width / 2, g.getClipBounds().height / 2);

            // Draw all obstacles
            g.setColor(Color.BLACK);
            for (Shape rect : _obstacleList) {
                ((Graphics2D)g).draw(rect);
            }

            // Draw all configurations
            g.setColor(Color.BLUE);
            for (Shape config : _configList) {
                switch (_type) {
                    case POINT:
                        ((Graphics2D)g).fill(config);
                        break;
                    case CIRCLE:
                        ((Graphics2D)g).draw(config);
                        break;
                    case SQUARE:
                        ((Graphics2D)g).draw(config);
                        break;
                }
            }
        }
    }

    public void drawConfigurations(String filename) {
        File configFile = new File(filename);
        double scaleFactor = 100.0;

        try {
            Scanner scanner = new Scanner(configFile);

            switch(_type) {
                case POINT:
                    AffineTransform transform1 = new AffineTransform();
                    while (scanner.hasNext()) {
                        String line = scanner.nextLine();
                        String[] tokens = line.split(" ");

                        if (tokens.length != 4 || tokens[0].charAt(0) != 'p') {
                            break;
                        }

                        transform1.setToScale(scaleFactor, scaleFactor);

                        Ellipse2D.Double tempCircle = new Ellipse2D.Double(Double.parseDouble(tokens[2]),
                                Double.parseDouble(tokens[3]), 10/scaleFactor, 10/scaleFactor);

                        this._configList.add(transform1.createTransformedShape(tempCircle));
                    }
                    break;
                case CIRCLE:
                    AffineTransform transform2 = new AffineTransform();
                    while (scanner.hasNext()) {
                        String line = scanner.nextLine();
                        String[] tokens = line.split(" ");

                        if (tokens.length != 5 || tokens[0].charAt(0) != 'c') {
                            break;
                        }

                        transform2.setToScale(scaleFactor, scaleFactor);

                        Ellipse2D.Double tempCircle = new Ellipse2D.Double(Double.parseDouble(tokens[2]),
                                Double.parseDouble(tokens[3]), Double.parseDouble(tokens[4]),
                                Double.parseDouble(tokens[4]));

                        this._configList.add(transform2.createTransformedShape(tempCircle));
                    }
                    break;
                case SQUARE:
                    AffineTransform transform3 = new AffineTransform();
                    while (scanner.hasNext()) {
                        String line = scanner.nextLine();
                        String[] tokens = line.split(" ");

                        if (tokens.length != 6 || tokens[0].charAt(0) != 's') {
                            break;
                        }

                        transform3.setToScale(scaleFactor, scaleFactor);
                        transform3.translate(Double.parseDouble(tokens[2]), Double.parseDouble(tokens[3]));
                        transform3.rotate(Double.parseDouble(tokens[4]));

                        double sideLength = Double.parseDouble(tokens[5]);

                        Rectangle2D tempRect = new Rectangle2D.Double(-sideLength/2.0, -sideLength/2.0,
                                sideLength, sideLength);

                        _configList.add(transform3.createTransformedShape(tempRect));
                    }
                    break;
            }
        } catch (Exception e) {
            System.out.println("Config File not found");
        }

        try {
            Scanner scanner = new Scanner(new File(_obstacleFile));

            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                String[] tokens = line.split(" ");

                assert(tokens.length == 4);

                double width = Double.parseDouble(tokens[2]);
                double height = Double.parseDouble(tokens[3]);

                Rectangle2D tempRect = new Rectangle2D.Double(-width/2.0, -height/2.0, width, height);

                AffineTransform transform = new AffineTransform();
                transform.setToScale(scaleFactor, scaleFactor);
                transform.translate(Double.parseDouble(tokens[0]), Double.parseDouble(tokens[1]));

                _obstacleList.add(transform.createTransformedShape(tempRect));
            }
        } catch (Exception e) {
            System.out.println("Couldn't find obstacle file:");
            e.printStackTrace();
        }
    }

    /**
     * Constructor which initializes an ArrayList for lines to be displayed.
     */
    public Visualizer(RobotType type) {
        this._type = type;
        this._configList = new ArrayList<>();
        this._obstacleList = new ArrayList<>();
        this._obstacleFile = "src/model/obstacles/default.txt";
    }
}
