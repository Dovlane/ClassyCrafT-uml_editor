package raf.dsw.classycraft.app.gui.swing.view;

import raf.dsw.classycraft.app.model.ClassyRepository.Project;

import javax.swing.*;
import java.awt.*;

public class ProjectView extends JSplitPane{
    private PackageView packageView;
    private Label labelProjectName;
    private Label labelAuthorName;
    private Project selectedProject = null;

    public ProjectView(PackageView packageView, Label labelProjectName, Label labelAuthorName) {
        super(JSplitPane.VERTICAL_SPLIT);
        JSplitPane labelPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, labelProjectName, labelAuthorName);
        super.add(labelPane);
        super.add(packageView);
        this.packageView = packageView;
        this.labelProjectName = labelProjectName;
        this.labelAuthorName = labelAuthorName;
    }

    public PackageView getPackageView() {
        return packageView;
    }

    public Label getLabelProjectName() {
        return labelProjectName;
    }

    public Label getLabelAuthorName() {
        return labelAuthorName;
    }

    public Project getSelectedProject() {
        return selectedProject;
    }
}
