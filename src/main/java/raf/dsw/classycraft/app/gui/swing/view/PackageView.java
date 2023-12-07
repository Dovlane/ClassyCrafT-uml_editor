package raf.dsw.classycraft.app.gui.swing.view;

import raf.dsw.classycraft.app.model.ClassyRepository.*;
import raf.dsw.classycraft.app.model.ClassyRepository.Package;
import raf.dsw.classycraft.app.model.MessageGenerator.MessageType;
import raf.dsw.classycraft.app.model.StatePattern.StateManager;
import raf.dsw.classycraft.app.model.observerPattern.IListener;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

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

        // Toolbar
        JToolBar toolBar = new JToolBar(JToolBar.VERTICAL);
        toolBar.setLayout(new GridLayout(0, 1));
        addButton(toolBar, "AI", true);
        addButton(toolBar, "AC", false);
        addButton(toolBar, "ACC", false);
        addButton(toolBar, "M", false);
        addButton(toolBar, "R", false);
        addButton(toolBar, "S", false);

        // Merge TabbedPane and ToolBar
        JSplitPane drawingPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, tabbedPane, toolBar);
        drawingPane.setResizeWeight(0.95);
        add(drawingPane);

        diagramViewList = new ArrayList<>();
        stateManager = new StateManager();

        setDefaultRightPanel();
    }

    private void addButton(JToolBar toolBar, String toolText, boolean startSelected) {
        JToggleButton button = new JToggleButton(toolText);
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
                        String buttonName = button.getText();
                        switch (buttonName) {
                            case "AI" -> startAddInterclassState();
                            case "AC" -> startAddConnectionState();
                            case "ACC" -> startAddClassContentState();
                            case "M" -> startMoveState();
                            case "R" -> startRemoveElementState();
                            case "S" -> startSelectElementState();
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
//        System.out.println("\tDiagramView pressed: " + diagramView);
//        System.out.println("\tmousePressed location: " + location);

        stateManager.getCurrentState().mousePressed(location, diagramView);
    }

    public void mouseReleased(DiagramView diagramView, Point location) {
        System.out.println("PackageView - mouseReleased:");
//        System.out.println("\tDiagramView released: " + diagramView);
//        System.out.println("\tmouseReleased location: " + location);

        stateManager.getCurrentState().mouseReleased(location, diagramView);
    }

    public void mouseDragged(DiagramView diagramView, Point startLocation, Point currentLocation) {
        System.out.println("PackageView - mouseDragged:");
//        System.out.println("\tDiagramView dragged: " + diagramView);
//        System.out.println("\tmouseDragged startLocation: " + startLocation);
//        System.out.println("\tmouseDragged currentLocation: " + currentLocation);

        stateManager.getCurrentState().mouseDragged(startLocation, currentLocation, diagramView);
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
    public void startRemoveElementState() {
        stateManager.setRemoveElementState();
    }
    public void startSelectElementState() {
        stateManager.setSelectElementState();
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
