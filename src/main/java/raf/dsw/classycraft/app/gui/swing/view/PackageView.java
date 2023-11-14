package raf.dsw.classycraft.app.gui.swing.view;

import raf.dsw.classycraft.app.model.ClassyRepository.Diagram;
import raf.dsw.classycraft.app.model.ClassyRepository.Package;
import raf.dsw.classycraft.app.model.ClassyRepository.Project;
import raf.dsw.classycraft.app.model.compositePattern.ClassyNode;
import raf.dsw.classycraft.app.model.observerPattern.IListener;

import javax.swing.*;
import java.awt.*;

public class PackageView extends JSplitPane implements IListener {

    private Package currentPackage;
    private Label labelProjectName;
    private Label labelAuthorName;
    private JTabbedPane tabbedPane;

    public PackageView(Package newPackage) {
        super(JSplitPane.VERTICAL_SPLIT);

        labelProjectName = new Label();
        labelAuthorName = new Label();
        tabbedPane = new JTabbedPane();

        JSplitPane labelPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, labelProjectName, labelAuthorName);
        add(labelPane);
        add(tabbedPane);

        setCurrentPackage(newPackage);
    }

    @Override
    public void update(Object notification) {
        if (notification instanceof Package) {
            setCurrentPackage((Package) notification);
        }
    }

    public void setCurrentPackage(Package newPackage) {
        currentPackage = newPackage;
        updatePackageView();
    }

    public void updatePackageView() {

        if ((currentPackage == null) || (currentPackage.getParent() == null)) {
            setDefaultRightPanel();
            currentPackage = null;
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
        for (ClassyNode child: currentPackage.getChildren()) {
            if (child instanceof Diagram) {
                DiagramView diagramView = new DiagramView((Diagram) child);
                tabbedPane.addTab(child.getName(), diagramView);
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

    public void setPackageMetadata() {

        ClassyNode tmp = currentPackage;
        while ((tmp != null) && !(tmp instanceof Project)) {
            tmp = tmp.getParent();
        }

        if (tmp == null) {
            throw new IllegalArgumentException("Package is not part of any Project.");
        }

        labelProjectName.setText(tmp.getName());
        labelAuthorName.setText(((Project) tmp).getAuthor());
    }

    public void setDefaultRightPanel() {
        labelProjectName.setText("Project name");
        labelAuthorName.setText("Author name");
        tabbedPane.removeAll();
    }

}
