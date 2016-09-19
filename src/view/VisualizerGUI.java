package view;

import model.RobotType;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Created by cannon on 9/8/16.
 *
 * Main GUI class for displaying Visualizer results.
 */
public class VisualizerGUI extends JFrame {

    /**
     * Adapter defining an interface to the Visualizer model.
     */
    private IModelAdapter _modelAdpt;

    /**
     * The highest level panel, containing all other panels.
     */
    private JPanel _contentPane;

    /**
     * The center panel, where shapes are drawn.
     */
    private final JPanel _pnlCenter = new JPanel() {
        /**
         * Randomly generated serial version id.
         */
        private static final long serialVersionUID = -872444218515942499L;

        /**
         * Override paintComponent method to paint shape in panel.
         * @param g the Graphics object to pain on.
         */
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            _modelAdpt.paint(g);
        }
    };

    /**
     * Panel containing controls for displaying different configurations.
     */
    private final JPanel _pnlNorth = new JPanel();

    /**
     * Button setting display type to point robots.
     */
    private final JButton _btnPoint = new JButton("Point");

    /**
     * Button setting display type to circle robots.
     */
    private final JButton _btnCircle = new JButton("Circle");

    /**
     * Button setting display type to square robots.
     */
    private final JButton _btnSquare = new JButton("Square");

    /**
     * Text field for entering the shortened file name of a configuration to load.
     */
    private final JTextField _txtFldFile = new JTextField();

    /**
     * Initialize the GUI components.
     */
    private void initGUI() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBounds(100, 100, 900, 700);

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        _contentPane = new JPanel();
        _contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        _contentPane.setLayout(new BorderLayout(0, 0));
        _contentPane.setBackground(new Color(102, 40, 115));

        setContentPane(_contentPane);

        _pnlCenter.setBackground(UIManager.getColor("CheckBox.background"));
        _contentPane.add(_pnlCenter, BorderLayout.CENTER);

        _pnlNorth.setBackground(new Color(102, 40, 115));

        _btnPoint.addActionListener(e -> {
            _modelAdpt.clearVisualizer();
            _modelAdpt.makeVisualizer(RobotType.POINT, "src/model/configurations/" + _txtFldFile.getText());
        });

        _btnCircle.addActionListener(e -> {
            _modelAdpt.clearVisualizer();
            _modelAdpt.makeVisualizer(RobotType.CIRCLE, "src/model/configurations/" + _txtFldFile.getText());
        });

        _btnSquare.addActionListener(e -> {
            _modelAdpt.clearVisualizer();
            _modelAdpt.makeVisualizer(RobotType.SQUARE, "src/model/configurations/" + _txtFldFile.getText());
        });

        _pnlNorth.add(_btnPoint);
        _pnlNorth.add(_btnCircle);
        _pnlNorth.add(_btnSquare);

        _txtFldFile.setText("points_random.txt");
        _txtFldFile.setColumns(20);
        _pnlNorth.add(_txtFldFile);

        _contentPane.add(_pnlNorth, BorderLayout.NORTH);
    }

    public void update() {
        _pnlCenter.repaint();
    }

    /**
     * Start the GUI.
     */
    public void start() {
        setVisible(true);
    }

    /**
     * Constructor to initialize the view with an adapter to the model.
     * @param adapter the model adapter for the view to use.
     */
    public VisualizerGUI(IModelAdapter adapter) {
        this._modelAdpt = adapter;

        initGUI();
    }
}
