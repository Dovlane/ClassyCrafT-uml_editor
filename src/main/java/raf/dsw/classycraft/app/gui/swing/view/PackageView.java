package raf.dsw.classycraft.app.gui.swing.view;

import raf.dsw.classycraft.app.model.ClassyRepository.*;
import raf.dsw.classycraft.app.model.ClassyRepository.Package;
import raf.dsw.classycraft.app.model.MessageGenerator.MessageType;
import raf.dsw.classycraft.app.model.StatePattern.StateManager;
import raf.dsw.classycraft.app.model.compositePattern.ClassyNode;
import raf.dsw.classycraft.app.model.elements.Interclass.Interclass;
import raf.dsw.classycraft.app.model.observerPattern.IListener;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PackageView extends JSplitPane implements IListener {

    private Package currentPackage;
    private final List<DiagramView> diagramViewList;
    private final StateManager stateManager;
    private final Label labelProjectName;
    private final Label labelAuthorName;
    private final JTabbedPane tabbedPane;
    private static JToggleButton selectedButton;
    public PackageView(ProjectExplorer projectExplorer) {
        super(JSplitPane.VERTICAL_SPLIT);

        // Listen to ProjectExplorer
        projectExplorer.addListener(this);

        // Project Name and Project Author labels
        labelProjectName = new Label();
        labelAuthorName = new Label();
        JSplitPane labelPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, labelProjectName, labelAuthorName);
        add(labelPane);

        // TabbedPane
        tabbedPane = new JTabbedPane();
        TabbedPaneMouseAdapter tabbedPaneMouseAdapter = new TabbedPaneMouseAdapter();
        tabbedPane.addMouseListener(tabbedPaneMouseAdapter);
        tabbedPane.addMouseMotionListener(tabbedPaneMouseAdapter);
        tabbedPane.addMouseWheelListener(tabbedPaneMouseAdapter);

        // Toolbar
        JToolBar toolBar = new JToolBar(JToolBar.VERTICAL);
        toolBar.setLayout(new GridLayout(0, 1));
        addButton(toolBar, "AddInterclass", true);
        addButton(toolBar, "AddConnection", false);
        addButton(toolBar, "AddClassContent", false);
        addButton(toolBar, "Move", false);
        addButton(toolBar, "Zoom", false);
        addButton(toolBar, "RemoveElement", false);
        addButton(toolBar, "Select", false);
        addButton(toolBar, "DuplicateElement", false);

        // ZoomToFit Button
        URL ZoomToFitImageURL = getClass().getResource("/images/ZoomToFit.png");
        JButton zoomToFitButton = new JButton(new ImageIcon(Objects.requireNonNull(ZoomToFitImageURL)));
        zoomToFitButton.setToolTipText("ZoomToFit");
        zoomToFitButton.addActionListener( e -> {
            zoomToFitAction();
        });
        toolBar.add(zoomToFitButton);

        // ExportToPNG Button
        URL ExportToPNGImageURL = getClass().getResource("/images/ExportToPNG.png");
        JButton ExportToPNGButton = new JButton(new ImageIcon(Objects.requireNonNull(ExportToPNGImageURL)));
        ExportToPNGButton.setToolTipText("ExportToPNG");
        ExportToPNGButton.addActionListener( e -> {
            exportToPNGAction();
        });
        toolBar.add(ExportToPNGButton);

        // Merge TabbedPane and ToolBar
        JSplitPane drawingPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, tabbedPane, toolBar);
        drawingPane.setResizeWeight(0.95);
        add(drawingPane);

        diagramViewList = new ArrayList<>();
        stateManager = new StateManager();

        setDefaultRightPanel();
    }

    private void addButton(JToolBar toolBar, String toolText, boolean startSelected) {

        URL imageURL = getClass().getResource("/images/" + toolText + ".png");
        JToggleButton button = new JToggleButton(new ImageIcon(Objects.requireNonNull(imageURL)));
        button.setToolTipText(toolText);
        button.setFocusPainted(false); // Remove focus border

        button.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (button.isSelected()) {

                    // If another button was selected, unselect it
                    if (selectedButton != null && !button.equals(selectedButton)) {

                        // Release the previous state
                        selectedButton.setSelected(false);
                        selectedButton.setBorder(BorderFactory.createEmptyBorder());

                        // Change the state of application
                        int index = toolBar.getComponentIndex(button);
                        switch (index) {
                            case 0 -> startAddInterclassState();
                            case 1 -> startAddConnectionState();
                            case 2 -> startAddClassContentState();
                            case 3 -> startMoveState();
                            case 4 -> startZoomState();
                            case 5 -> startRemoveElementState();
                            case 6 -> startSelectElementState();
                            case 7 -> startDuplicateElementState();
                            default -> MainFrame.getInstance().getMessageGenerator().generateMessage(
                                    "Something is wrong with the names of the buttons and the states.", MessageType.ERROR);
                        }
                    }

                    // Update the reference to the selected button
                    button.setBorder(BorderFactory.createRaisedBevelBorder());
                    selectedButton = button;
                }
            }
        });

        // Set the initial selected state
        button.setBorder(BorderFactory.createEmptyBorder());
        if (startSelected) {
            button.setBorder(BorderFactory.createRaisedBevelBorder());
            selectedButton = button; // Update the reference to the selected button
        }
        toolBar.add(button);
    }


    // PERFORM ALL STATE ACTIONS
    public void mousePressed(DiagramView diagramView, Point location) {
        System.out.println("PackageView - mousePressed:");

        stateManager.getCurrentState().mousePressed(location, diagramView);
    }

    public void mouseReleased(DiagramView diagramView, Point location) {
        System.out.println("PackageView - mouseReleased:");

        stateManager.getCurrentState().mouseReleased(location, diagramView);
    }

    public void mouseDragged(DiagramView diagramView, Point startLocation, Point currentLocationOptimal, Point currentLocation) {
        System.out.println("PackageView - mouseDragged:");

        stateManager.getCurrentState().mouseDragged(startLocation, currentLocationOptimal, currentLocation, diagramView);
    }

    public void mouseWheelMoved(int wheelRotation, Point location, DiagramView diagramView) {
        System.out.println("PackageView - mouseWheelMoved:");

        stateManager.getCurrentState().mouseWheelMoved(wheelRotation, location, diagramView);
    }

    public void zoomToFitAction() {
        System.out.println("ZoomToFitAction has been performed.");

        if (tabbedPane.getTabCount() > 0) {

            // Fetch the information about DiagramView
            DiagramView diagramView = (DiagramView) tabbedPane.getComponentAt(tabbedPane.getSelectedIndex());
            Diagram diagram = diagramView.getDiagram();
            int width = diagramView.getWidth();
            int height = diagramView.getHeight();
            Point viewCentre = new Point(width / 2, height / 2);
            AffineTransform transform = diagramView.getTransform();

            // Check if current diagram has anything displayed
            if (diagram.getChildren().size() == 0) {
                System.out.println("ZoomToFit cannot be performed because there are no DiagramElements.");
                return;
            }

            // Find borders of all DiagramElements
            int minX = Integer.MAX_VALUE;
            int minY = Integer.MAX_VALUE;
            int maxX = Integer.MIN_VALUE;
            int maxY = Integer.MIN_VALUE;
            for (ClassyNode child: diagram.getChildren()) {
                if (child instanceof Interclass) {
                    Interclass interclass = (Interclass) child;
                    minX = Math.min(minX, interclass.getLocation().x);
                    minY = Math.min(minY, interclass.getLocation().y);
                    maxX = Math.max(maxX, interclass.getLocation().x + interclass.getBoxWidth());
                    maxY = Math.max(maxY, interclass.getLocation().y + interclass.getBoxHeight());
                }
            }
            Point upperLeft = new Point(minX, minY);
            Point bottomRight = new Point(maxX, maxY);

            // Center the UML Diagram
            Point centreOfMass = new Point(
                    (upperLeft.x + bottomRight.x) / 2,
                    (upperLeft.y + bottomRight.y) / 2);
            transform.transform(centreOfMass, centreOfMass);
            diagramView.move(centreOfMass, viewCentre);

            // Scale the UML Diagram so it fits the window
            transform.transform(upperLeft, upperLeft);
            transform.transform(bottomRight, bottomRight);
            double zoomFactor = Math.min(
                    (double) width / (bottomRight.x - upperLeft.x),
                    (double) height / (bottomRight.y - upperLeft.y));
            zoomFactor = Math.min(Math.max(0.0001, zoomFactor), 10000);
            System.out.println(zoomFactor);
            diagramView.zoomWithFactor(zoomFactor, viewCentre);
        }
    }

    public void exportToPNGAction() {
        System.out.println("ExportToPNGAction has been performed.");

        if (tabbedPane.getTabCount() > 0) {

            // ZoomToFit
            zoomToFitAction();

            // Create a BufferedImage to store the captured image
            JPanel panel = (JPanel) tabbedPane.getSelectedComponent();
            BufferedImage image = new BufferedImage(panel.getWidth(), panel.getHeight(), BufferedImage.TYPE_INT_ARGB);

            // Create a graphics context from the image
            Graphics2D g2d = image.createGraphics();

            // Paint the panel to the graphics context
            panel.paint(g2d);

            // Dispose the graphics context
            g2d.dispose();

            // Choose a folder using JFileChooser
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

            int result = fileChooser.showDialog(null, "Select Folder");

            if (result == JFileChooser.APPROVE_OPTION) {
                // Get the selected folder
                File selectedFolder = fileChooser.getSelectedFile();

                // Prompt the user to enter a file name
                String fileName = JOptionPane.showInputDialog("Enter file name (without extension):");

                // If the user entered a file name, save the image
                if (fileName != null && !fileName.isEmpty()) {
                    // Specify the full file path including the selected folder and file name
                    File outputFile = new File(selectedFolder, fileName + ".png");

                    try {
                        ImageIO.write(image, "png", outputFile);
                        System.out.println("Image saved to: " + outputFile.getAbsolutePath());
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                } else {
                    System.out.println("File name not provided. Image not saved.");
                }
            }
        }
    }


    // START STATES
    public void startAddInterclassState() {
        stateManager.setAddInterclassState();
    }
    public void startAddConnectionState() {
        stateManager.setAddConnectionState();
    }
    public void startAddClassContentState() {
        stateManager.setAddClassContentState();
    }
    public void startMoveState() {
        stateManager.setMoveState();
    }
    public void startZoomState() {
        stateManager.setZoomState();
    }
    public void startRemoveElementState() {
        stateManager.setRemoveElementState();
    }
    public void startSelectElementState() {
        stateManager.setSelectElementState();
    }
    public void startDuplicateElementState() {
        stateManager.setDuplicateElementState();
    }


    // RIGHT PANEL
    @Override
    public void update(Object notification) {

        // Cast notification
        Notification packageNotification = (Notification) notification;

        // PROJECT has been passed as a notification node
        if (packageNotification.getNode() instanceof Project) {
            Project aProject = (Project) packageNotification.getNode();
            if (packageNotification.getType() == NotificationType.ADD) {
                aProject.addListener(this);
                System.out.println("Added PackageView as a listener to newly created Project.");
            }
            else if (packageNotification.getType() == NotificationType.REMOVE) {
                aProject.removeListener(this);
                System.out.println("Removed PackageView as a listener from deleting Project.");
            }
            else {
                if (currentPackage != null) {
                    Project currentPackageProject = currentPackage.findParentProject();
                    if (currentPackageProject.equals(aProject)) {
                        setPackageMetadata();
                        System.out.println("Set new Package Metadata.");
                    }
                }
            }
        }

        // PACKAGE has been passed as a notification node
        if (packageNotification.getNode() instanceof Package) {
            Package aPackage = (Package) packageNotification.getNode();
            if (packageNotification.getType() == NotificationType.ADD) {
                aPackage.addListener(this);
                System.out.println("Added PackageView as a listener to newly created Package.");
            }
            else if (packageNotification.getType() == NotificationType.REMOVE) {
                aPackage.removeListener(this);
                if (aPackage.equals(currentPackage)) {
                    setCurrentPackage(null);
                }
                System.out.println("Removed PackageView as a listener from deleting Package.");
            }
            else {
                setCurrentPackage(aPackage);
                System.out.println("New Package has been set as a current displayed package.");
            }
        }

        // Diagram has been passed as a notification node
        if (packageNotification.getNode() instanceof Diagram) {
            Diagram aDiagram = (Diagram) packageNotification.getNode();
            if (packageNotification.getType() == NotificationType.ADD) {
                addDiagramView(aDiagram);
                System.out.println("Added PackageView as a listener to newly created Diagram.");
            }
            else if (packageNotification.getType() == NotificationType.REMOVE) {
                removeDiagramView(aDiagram);
                System.out.println("Removed PackageView as a listener from deleting Diagram.");
            }
        }

        updatePackageView();
    }

    public void setCurrentPackage(Package newPackage) {
        currentPackage = newPackage;
    }

    private void updatePackageView() {

        System.out.println("Number of diagramViews " + diagramViewList.size());
        if (currentPackage == null) {
            setDefaultRightPanel();
            return;
        }

        // Remember Diagram on the last selected tab
        Diagram lastSelectedDiagram = null;
        if (tabbedPane.getTabCount() > 0) {
            lastSelectedDiagram = ((DiagramView) tabbedPane.getComponentAt(tabbedPane.getSelectedIndex())).getDiagram();
            System.out.println(lastSelectedDiagram);
        }

        // Refresh
        setPackageMetadata();
        tabbedPane.removeAll();
        for (DiagramView diagramView: diagramViewList) {
            Diagram diagram = diagramView.getDiagram();
            if (currentPackage.equals(diagram.getParent())) {
                tabbedPane.addTab(diagram.getName(), diagramView);
            }
        }

        // Set selection to the last selected Diagram if it is possible
        for (int i=0; i<tabbedPane.getTabCount(); i++) {
            Diagram currentDiagram = ((DiagramView) tabbedPane.getComponentAt(i)).getDiagram();
            if (currentDiagram == lastSelectedDiagram) {
                tabbedPane.setSelectedIndex(i);
                break;
            }
        }
    }

    public DiagramView getLastSelectedTab(){
        DiagramView displayedDiagramView = null;
        if (tabbedPane.getTabCount() > 0) {
            displayedDiagramView = (DiagramView) tabbedPane.getComponentAt(tabbedPane.getSelectedIndex());
            System.out.println(displayedDiagramView);
        }

        // It returns null only when there is no open package yet
        return displayedDiagramView;
    }

    public void setPackageMetadata() {
        Project parentProject = currentPackage.findParentProject();
        labelProjectName.setText(parentProject.getName());
        labelAuthorName.setText(parentProject.getAuthor());
    }

    public void setDefaultRightPanel() {
        labelProjectName.setText("Project name");
        labelAuthorName.setText("Author name");
        tabbedPane.removeAll();
    }

    public void addDiagramView(Diagram diagram) {
        DiagramView diagramView = new DiagramView(diagram);
        diagramViewList.add(diagramView);
    }

    public void removeDiagramView(Diagram diagram) {
        for (int i = 0; i < diagramViewList.size(); i++) {
            if (diagramViewList.get(i).getDiagram().equals(diagram)) {
                diagramViewList.remove(i);
                return;
            }
        }
    }
}
