package view.editor

import controller.editor.EditorController
import controller.util.ResourceNames.FileExtensions
import view.AbstractView
import view.editor.forms.enemies.{DeleteEnemy, NewEnemy}
import view.editor.forms.events.{DeleteEvent, NewEvent}
import view.editor.forms.pathways.{DeletePathway, DetailsPathway, EditPathway, NewPathway}
import view.editor.forms.prerequisites.{DeletePathwayPrerequisite, NewPathwayPrerequisite}
import view.editor.forms.storyNodes.{DeleteStoryNode, DetailsStoryNode, EditStoryNode, NewStoryNode}
import view.util.common.StandardKeyListener.quitKeyListener
import view.util.common.{ControlsPanel, Scrollable, VerticalButtons}
import view.util.scalaQuestSwingComponents.SqSwingButton
import view.util.scalaQuestSwingComponents.dialog.SqSwingDialog
import view.util.scalaQuestSwingComponents.fileChooser.SqSwingStoryFileChooser
import java.awt.BorderLayout
import java.awt.event.ActionEvent
import java.io.File
import javax.swing.{JComponent, JFileChooser}

trait EditorView extends AbstractView

object EditorView {

  def showForbiddenActionDialog(message: String): SqSwingDialog = SqSwingDialog(
    "Forbidden action",
    message,
    List(SqSwingButton("ok", _ => {})),
    closable = false
  )

  private class EditorViewSwing(private val editorController: EditorController) extends EditorView {

    this.setLayout(new BorderLayout())

    def showFileSave(title: String, onSave: String => Unit, selectedFileName: String, parent: JComponent): Unit = {
      val chooser = SqSwingStoryFileChooser(title)
      chooser.setSelectedFile(new File(selectedFileName))
      if (chooser.showSaveDialog(parent) == JFileChooser.APPROVE_OPTION) {
        onSave(chooser.getSelectedFile.getPath)
      }
    }

    override def populateView(): Unit = {

      this.add(ControlsPanel(List(
        ("n", ("[N] Nodes narrative", _ => editorController.switchNodesNarrativeVisibility())),
        ("p", ("[P] Pathways description", _ => editorController.switchPathwaysDescriptionVisibility())),
        ("i", ("[I] Info", _ => editorController.goToExplorer()))
      )), BorderLayout.NORTH)

      this.add(Scrollable(VerticalButtons(List(
        SqSwingButton("Show story node details", _ => DetailsStoryNode().show(editorController)),
        SqSwingButton("Add new story node", _ => NewStoryNode().show(editorController)),
        SqSwingButton("Edit existing story node", _ => EditStoryNode().show(editorController)),
        SqSwingButton("Delete existing story node", _ => DeleteStoryNode().show(editorController)),
        SqSwingButton("Show pathway details", _ => DetailsPathway().show(editorController)),
        SqSwingButton("Add new pathway", _ => NewPathway().show(editorController)),
        SqSwingButton("Edit existing pathway", _ => EditPathway().show(editorController)),
        SqSwingButton("Delete existing pathway", _ => DeletePathway().show(editorController)),
        SqSwingButton("Add new event", _ => NewEvent().show(editorController)),
        SqSwingButton("Delete existing event", _ => DeleteEvent().show(editorController)),
        SqSwingButton("Add new enemy", _ => NewEnemy().show(editorController)),
        SqSwingButton("Delete existing enemy", _ => DeleteEnemy().show(editorController)),
        SqSwingButton("Add new pathway prerequisite", _ => NewPathwayPrerequisite().show(editorController)),
        SqSwingButton("Delete existing pathway prerequisite", _ => DeletePathwayPrerequisite().show(editorController))
      ))), BorderLayout.CENTER)

      this.add(ControlsPanel(List(
        quitKeyListener("Do you really want to exit from editor?",
          (_: ActionEvent) => editorController.close()),
        ("s", ("[S] Save", _ => showFileSave("Save story", editorController.save,
          "story." + FileExtensions.StoryFileExtension, this))))),
        BorderLayout.SOUTH)
    }

  }

  def apply(editorController: EditorController): EditorView = new EditorViewSwing(editorController)
}
