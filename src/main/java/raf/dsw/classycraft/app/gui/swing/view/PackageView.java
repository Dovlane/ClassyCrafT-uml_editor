package raf.dsw.classycraft.app.gui.swing.view;

import raf.dsw.classycraft.app.core.ApplicationFramework;
import raf.dsw.classycraft.app.model.ClassyRepository.Diagram;
import raf.dsw.classycraft.app.model.ClassyRepository.Package;
import raf.dsw.classycraft.app.model.ClassyRepository.Project;
import raf.dsw.classycraft.app.model.observerPattern.IListener;

import javax.swing.*;
import java.awt.*;

public class PackageView extends JSplitPane implements IListener {

    private Package currentPackage;
    private Label labelProjectName;
    private Label labelAuthorName;
    private JTabbedPane tabbedPane;

    public PackageView() {
        super(JSplitPane.VERTICAL_SPLIT);

        labelProjectName = new Label();
        labelAuthorName = new Label();
        tabbedPane = new JTabbedPane();
        TabbedPaneMouseAdapter tabbedPaneMouseAdapter = new TabbedPaneMouseAdapter();
        tabbedPane.addMouseListener(tabbedPaneMouseAdapter);
        tabbedPane.addMouseMotionListener(tabbedPaneMouseAdapter);

        JSplitPane labelPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, labelProjectName, labelAuthorName);
        add(labelPane);
        add(tabbedPane);
        setCurrentPackage(Package.getDisplayedPackage());
    }

    @Override
    public void update(Object notification) {

        // If null is passed it means that only the update
        // on the existing package should happen
        if (notification == null) {
            updatePackageView();
            return;
        }

        // If Package is passed it means that the displayed
        // package had been changed.
        if (notification instanceof Package) {
            currentPackage.removeListener(this);
            setCurrentPackage((Package) notification);
        }
    }

    public void setCurrentPackage(Package newPackage) {
        currentPackage = newPackage;
        currentPackage.addListener(this);
        updatePackageView();
    }

    private void updatePackageView() {

        if (currentPackage == Package.getDefaultPackage()) {
            setDefaultRightPanel();
            return;
        }

        // Remember last selected tab
        Diagram lastSelectedDiagram = null;
        if (tabbedPane.getTabCount() > 0) {
            lastSelectedDiagram = ((DiagramView) tabbedPane.getComponentAt(tabbedPane.getSelectedIndex())).getDiagram();
            System.out.println(lastSelectedDiagram);
        }

        // Refresh
        setPackageMetadata();
        tabbedPane.removeAll();
        for (DiagramView diagramView: ApplicationFramework.getInstance().getClassyRepository().getDiagramViewList()) {
            Diagram diagram = diagramView.getDiagram();
            if (currentPackage == diagram.getParent()) {
                tabbedPane.addTab(diagram.getName(), diagramView);
            }
        }

        // Set selection to the last selected tab if it is possible
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

}
