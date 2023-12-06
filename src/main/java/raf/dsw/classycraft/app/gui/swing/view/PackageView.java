package raf.dsw.classycraft.app.gui.swing.view;

import raf.dsw.classycraft.app.model.ClassyRepository.Diagram;
import raf.dsw.classycraft.app.model.ClassyRepository.Package;
import raf.dsw.classycraft.app.model.ClassyRepository.Project;
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

    public PackageView() {
        super(JSplitPane.VERTICAL_SPLIT);

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
        addButton(toolBar, "R", false);
        addButton(toolBar, "S", false);

        // Merge TabbedPane and ToolBar
        JSplitPane drawingPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, tabbedPane, toolBar);
        drawingPane.setResizeWeight(0.95);
        add(drawingPane);

        diagramViewList = new ArrayList<>();
        stateManager = new StateManager();

        setCurrentPackage(Package.getDisplayedPackage());
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
    public void startRemoveElementState() {
        stateManager.setRemoveElementState();
    }
    public void startSelectElementState() {
        stateManager.setSelectElementState();
    }


    // RIGHT PANEL
    @Override
    public void update(Object notification) {

        if (notification instanceof Diagram) {
            if (getDiagramView((Diagram) notification) != null) {
                removeDiagramView((Diagram) notification);
            } else {
                addDiagramView((Diagram) notification);
            }
        }

        // If Package is passed it means that the displayed
        // package had been changed.
        if (notification instanceof Package) {
            currentPackage.removeListener(this);
            setCurrentPackage((Package) notification);
        }

        updatePackageView();
    }

    public void setCurrentPackage(Package newPackage) {
        currentPackage = newPackage;
        currentPackage.addListener(this);
        updatePackageView();
    }

    private void updatePackageView() {

        System.out.println("Number of diagramViews " + diagramViewList.size());
        if (currentPackage == Package.getDefaultPackage()) {
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

    public DiagramView getDiagramView(Diagram diagram) {
        for (DiagramView diagramView: diagramViewList) {
            if (diagramView.getDiagram().equals(diagram)) {
                return diagramView;
            }
        }
        return null;
    }

    public void removeDiagramView(Diagram diagram) {
        for (int i = 0; i < diagramViewList.size(); i++) {
            if (diagramViewList.get(i).getDiagram().equals(diagram)) {
                diagramViewList.remove(i);
                break;
            }
        }
    }

    public List<DiagramView> getDiagramViewList() {
        return diagramViewList;
    }

    public StateManager getStateManager() {
        return stateManager;
    }

}
