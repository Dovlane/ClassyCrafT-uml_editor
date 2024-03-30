# ClassyCrafT - uml_editor

ClassyCrafT is a desktop application designed to assist students and programmers in creating and editing class diagrams. It provides tools for visualizing the structure of projects, allowing users to seamlessly add and rearrange classes within their diagrams.

## Interface Overview

Upon opening ClassyCrafT, you'll encounter the following interface:

<img width="800" alt="Classy Craft" src=https://github.com/Dovlane/ClassyCrafT-uml_editor/assets/57462728/d80c5231-eab3-46c6-bedd-057bd9f424c3>


The top ribbon contains tools for creating and editing project structure. From left to right, these tools include:
- Add new project 
- Add new package 
- Add new diagram
- Remove item
- Rename item
- Add author
- Save project/package/diagram
- Save current diagram to templates
- Generate Java code for the project

With these tools you can create and organize your diagrams in a way that mimics your future project structure. In a single project you can have multiple packages, they can be nested, and each can contain multiple diagrams.

The middle section of the window is dedicated to displaying and editing diagrams, and on the left side is a tree view of currently opened projects. 
Users can add and remove diagram elements using the toolbar on the right side, which includes options such as:
- Add class/enum/interface
- Add a relationship
- View and edit contents of element
- Move whole scene or individual element
- Zoom in/out
- Remove element
- Selection tool


Below is an example of how one could use ClassCrafT to model an UML class diagram for a small sample project. Through that example we will showcase some available features and explains their usage.

## Example project
### Creating a project structure
Let's start by creating your first project. Click on the 'Add new project' button located in the top left corner. A new project will appear in a tree view. Select the project by clicking on it, then proceed to create a package and a diagram in the same fashion using the adjacent buttons. 
You should end up with a project structure looking like this:

<img width="520" alt="project structure" src="https://github.com/Dovlane/ClassyCrafT-uml_editor/assets/57462728/1e99b3ce-70ae-4455-87c1-a3cf44bc0259">


Open a diagram by double clicking on a package. You should see a tab appear in the main window, one for each diagram created. 
To rename project, package or diagram you can use 'Rename item' button, a pop-up will appear to input a new name. Alternativly you can just triple click the item and type in a name. Similarly you can add a name of a project's author.


<img width="520" alt="rename and add author" src="https://github.com/Dovlane/ClassyCrafT-uml_editor/assets/57462728/bfa79e62-b164-4de2-85ce-4ebb2f222e94">

### Adding classes to diagram
With all preparations complete, we can now move on to creating the diagram itself. Click on the 'Add class' button, first one in the left side toolbar. 
A window will pop up where you can provide information about class being created. Specify classes's name and modifiers here, then click 'OK' to confirm your choice. Blue rectangle representing your newly created class will appear on a diagram view and tree will be refreshed accordingly. You can rename or remove diagram items from tree in same way as previously shown. 

<p float="left">
  <img height="300" alt="add class" src="https://github.com/Dovlane/ClassyCrafT-uml_editor/assets/57462728/f0278e6f-1a78-4148-a646-8dfaec95aaa1">
  <img height="300" alt="class" src="https://github.com/Dovlane/ClassyCrafT-uml_editor/assets/57462728/2ba9a0fc-c185-4452-9c5f-8c20dcd39d7b">
</p>

To add methods and attributes select 'Add content button' represented by a pencil icon in the toolbar and select desired class. Provide name and a data type in appropriate fields and choose modifiers from drop-down menus. Use radio buttons to indicate whether it's an attribute or a method and click on the 'ADD' button to add it. It will be listed in the table at the bottom of a pop-up. You can select items in the tables and remove them using 'DELETE' button. Click 'OK' button to save the changes. 

<img height="400" alt="add content window" src="https://github.com/Dovlane/ClassyCrafT-uml_editor/assets/57462728/42e33d3e-f5f7-4bd5-89aa-1767c04630d4">

### Adding relationships

Select the chain link icon to add relationships between objects. Click and drag the connection from one object to another. You will be prompted to choose type of a relationship. After clicking 'OK' relationship will be added.

<p float="left">
  <img height="270" alt="add relationship" src="https://github.com/Dovlane/ClassyCrafT-uml_editor/assets/57462728/dca0fc38-2a59-4c0f-9e0d-14a93c633471">
  <img height="270" alt="relationship added" src="https://github.com/Dovlane/ClassyCrafT-uml_editor/assets/57462728/09c560a0-f657-4b3a-96bc-4fe8d4f5b7df">
</p>

'Edit item' button can be used on the relationships as well. Here you can change cardinality accessibility and name of an attribute corresponding to a composition or am aggregation relationship. 
  
  <img height="200" alt="relationship content" src="https://github.com/Dovlane/ClassyCrafT-uml_editor/assets/57462728/4d2dfa1a-5ca8-4d92-858a-f4a184334f27">

By repeting previous steps for all the other items finished diagram could look something like this:

<img width="850" alt="final diagram" src="https://github.com/Dovlane/ClassyCrafT-uml_editor/assets/57462728/68fe5d02-ef3c-4932-92af-02fb3a02f2c2">

### Saving and opening

In order not to loose your work when closing application you can save a whole project or a part of it by selecting item you want to save and clicking on the 'Save' button in the top ribbon. Save dialog will open for you to choose a destination. 
 
 <img height="280" alt="relationship content" src="https://github.com/Dovlane/ClassyCrafT-uml_editor/assets/57462728/2d0359d1-49f2-492e-83b3-41f5b1d16c92">

Now your project i safely stored. To open it again use a 'Open' menu item and choose a file to be opened.

<p float="left">
  <img height="270" alt="add relationship" src="https://github.com/Dovlane/ClassyCrafT-uml_editor/assets/57462728/892d25e1-6498-4bc9-8b5b-e709422d2e36">
  <img height="270" alt="relationship added" src="https://github.com/Dovlane/ClassyCrafT-uml_editor/assets/57462728/308efdab-f607-4166-bc62-e980a193d039">
</p>

### Exporting

ClassyCrafT help you with writing code for your application by automaticaly generating shell of a code containing classes with all attributes using a diagram you made as a template. To do this click on the java logo icon in the top ribbon and choose location for a .java file to be stored. This is what code generated for this diagram looks like: 

<img width="635" alt="67" src="https://github.com/Dovlane/ClassyCrafT-uml_editor/assets/57462728/0885755e-1e74-46af-8c16-1e6ae17a28ac">

