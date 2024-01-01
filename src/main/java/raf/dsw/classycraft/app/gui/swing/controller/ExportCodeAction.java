package raf.dsw.classycraft.app.gui.swing.controller;

import com.fasterxml.jackson.annotation.JsonIgnore;
import raf.dsw.classycraft.app.gui.swing.tree.model.ClassyTreeItem;
import raf.dsw.classycraft.app.gui.swing.view.MainFrame;
import raf.dsw.classycraft.app.model.ClassyRepository.Diagram;
import raf.dsw.classycraft.app.model.ClassyRepository.Package;
import raf.dsw.classycraft.app.model.ClassyRepository.Project;
import raf.dsw.classycraft.app.model.MessageGenerator.MessageType;
import raf.dsw.classycraft.app.model.compositePattern.ClassyNode;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

import org.javatuples.Pair;

public class ExportCodeAction extends AbstractClassyAction{

    public ExportCodeAction() {
        putValue(SMALL_ICON, loadIcon("/images/exportToCode.png"));
        putValue(NAME, "Export code action");
        putValue(SHORT_DESCRIPTION, "Export code of entire project");
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        // Check if any classy tree item is selected

        ClassyTreeItem selectedTreeItem = MainFrame.getInstance().getClassyTree().getSelectedNode();
        if (selectedTreeItem == null || !(selectedTreeItem.getClassyNode() instanceof Project)) {
            MainFrame.getInstance().getMessageGenerator().generateMessage("ClassyTreeItem must be selected, and it must be Project.", MessageType.ERROR);
            return;
        }

        Project chosenProject = (Project) selectedTreeItem.getClassyNode();

        // Open JFileChooser
        JFileChooser jfc = new JFileChooser();
        jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        if (jfc.showOpenDialog(MainFrame.getInstance()) == JFileChooser.APPROVE_OPTION) {
            try {
                File file = jfc.getSelectedFile();
                Path pathForChosenProject = Paths.get(file.getPath(), selectedTreeItem.toString());

                // here program deletes old generated code, and generates a new one
                if (Files.exists(pathForChosenProject)) {
                    try (Stream<Path> pathStream = Files.walk(pathForChosenProject)) {
                        pathStream.sorted(Comparator.reverseOrder())
                                .map(Path::toFile)
                                .forEach(File::delete);
                    }
                }
                File projectFolder = new File(file, selectedTreeItem.toString());
                projectFolder.mkdir();
                createFolderStructure(projectFolder, chosenProject);

            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }

        System.out.println("ExportCodeAction has been performed");
    }

    private void createFolderStructure(File rootFile, Project project) throws IOException {
        List<Pair<File, ClassyNode>> fileClassyNodeList = new ArrayList<>();

        for (ClassyNode child : project.getChildren()) {
            fileClassyNodeList.add(new Pair<>(rootFile, child));
        }

        while (fileClassyNodeList.size() > 0) {
            Pair<File, ClassyNode> pairFileClassyNode = fileClassyNodeList.get(0);
            fileClassyNodeList.remove(0);
            File parentFile = pairFileClassyNode.getValue0();
            ClassyNode node = pairFileClassyNode.getValue1();

            if (node instanceof Package) {
                File file = new File(parentFile, getTreeName(node));
                file.mkdir();
                for (ClassyNode childNode : ((Package) node).getChildren()) {
                    fileClassyNodeList.add(new Pair<>(file, childNode));
                }
            }

            if (node instanceof Diagram) {
                String fileName = getTreeName(node) + ".java";
                File file = new File(parentFile + File.separator +  fileName);
                file.getParentFile().mkdirs();
                file.createNewFile();

                FileWriter fileWriter = null;
                PrintWriter printWriter = null;
                try{
                    fileWriter = new FileWriter(file);
                    printWriter = new PrintWriter(fileWriter);
                    printWriter.write(node.toString());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } finally {
                    if (printWriter != null)
                        printWriter.close();
                    if (fileWriter != null)
                    {
                        try{
                            fileWriter.close();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
        }
    }

    @JsonIgnore
    private String getTreeName(ClassyNode node) {
        return MainFrame.getInstance().getClassyTree().getRoot().getTreeItemFromClassyNode(node).toString();
    }
}
