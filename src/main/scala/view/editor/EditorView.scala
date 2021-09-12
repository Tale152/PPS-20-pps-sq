package view.editor

import controller.editor.EditorController
import controller.util.Resources.ResourceName
import view.AbstractView
import view.editor.forms.DeletePathway.showDeletePathwayForm
import view.editor.forms.DeleteStoryNode.showDeleteStoryNodeForm
import view.editor.forms.EditPathway.showEditPathwayForm
import view.editor.forms.EditStoryNode.showEditStoryNodeForm
import view.editor.forms.NewPathway.showNewPathwayForm
import view.editor.forms.NewStoryNode.showNewStoryNodeForm
import view.editor.forms.AddStatModifier.showAddStatModifierForm
import view.util.SqFileChooser
import view.util.common.{ControlsPanel, Scrollable, VerticalButtons}
import view.util.scalaQuestSwingComponents.SqSwingButton

import java.awt.BorderLayout

trait EditorView extends AbstractView

object EditorView {

  private class EditorViewSwing(private val editorController: EditorController) extends EditorView {

    this.setLayout(new BorderLayout())

    override def populateView(): Unit = {

      this.add(ControlsPanel(List(
        ("n", ("[N] Nodes narrative", _ => editorController.switchNodesNarrativeVisibility())),
        ("p", ("[P] Pathways description", _ => editorController.switchPathwaysDescriptionVisibility()))
      )), BorderLayout.NORTH)

      this.add(Scrollable(VerticalButtons(List(
        SqSwingButton("Add new story node", _  => showNewStoryNodeForm(editorController)),
        SqSwingButton("Edit existing story node", _ => showEditStoryNodeForm(editorController)),
        SqSwingButton("Delete existing story node", _=> showDeleteStoryNodeForm(editorController)),
        SqSwingButton("Add new pathway", _ => showNewPathwayForm(editorController)),
        SqSwingButton("Edit existing pathway", _ => showEditPathwayForm(editorController)),
        SqSwingButton("Delete existing pathway", _ => showDeletePathwayForm(editorController)),
        SqSwingButton("Add stat modifier", _ => showAddStatModifierForm(editorController))
      ))), BorderLayout.CENTER)

      this.add(ControlsPanel(List(
        ("q", ("[Q] Quit", _ => editorController.close())),
        ("s", ("[S] Save", _ => SqFileChooser.showFileSave(
          "Save story",
          editorController.save,
          "story." + ResourceName.FileExtensions.StoryFileExtension,
          this
        )))
      )), BorderLayout.SOUTH)
    }

  }

  def apply(editorController: EditorController): EditorView = new EditorViewSwing(editorController)
}
