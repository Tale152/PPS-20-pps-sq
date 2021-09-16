package view.editor

import controller.editor.EditorController
import controller.util.Resources.ResourceName
import view.AbstractView
import view.editor.forms.conditions.DeletePathwayPrerequisite.showDeletePathwayPrerequisiteForm
import view.editor.forms.conditions.NewPathwayPrerequisite.showNewPathwayPrerequisiteForm
import view.editor.forms.enemies.DeleteEnemy.showDeleteEnemyForm
import view.editor.forms.pathways.DeletePathway.showDeletePathwayForm
import view.editor.forms.storyNodes.DeleteStoryNode.showDeleteStoryNodeForm
import view.editor.forms.pathways.EditPathway.showEditPathwayForm
import view.editor.forms.storyNodes.EditStoryNode.showEditStoryNodeForm
import view.editor.forms.pathways.NewPathway.showNewPathwayForm
import view.editor.forms.storyNodes.NewStoryNode.showNewStoryNodeForm
import view.editor.forms.events.NewEvent.showNewEventForm
import view.editor.forms.events.DeleteEvent.showDeleteEventForm
import view.editor.forms.enemies.NewEnemy.showNewEnemyForm
import view.util.SqFileChooser
import view.util.common.{ControlsPanel, Scrollable, VerticalButtons}
import view.util.scalaQuestSwingComponents.SqSwingButton
import view.util.scalaQuestSwingComponents.dialog.SqSwingDialog

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
        SqSwingButton("Delete existing story node", _=> {
          if(editorController.getNodesIds(n => editorController.isStoryNodeDeletable(n.id)).nonEmpty){
            showDeleteStoryNodeForm(editorController)
          } else {
            showForbiddenActionDialog("There aren't deletable nodes")
          }
        }),
        SqSwingButton("Add new pathway", _ => {
          if(editorController.getValidNodesForPathwayOrigin().nonEmpty){
            showNewPathwayForm(editorController)
          } else {
            showForbiddenActionDialog("There aren't new possible pathways")
          }
        }),
        SqSwingButton("Edit existing pathway", _ => showEditPathwayForm(editorController)),
        SqSwingButton("Delete existing pathway", _ => showDeletePathwayForm(editorController)),
        SqSwingButton("Add new event", _ => showNewEventForm(editorController)),
        SqSwingButton("Delete existing event", _ => showDeleteEventForm(editorController)),
        SqSwingButton("Add new enemy", _ => showNewEnemyForm(editorController)),
        SqSwingButton("Delete existing enemy", _ => showDeleteEnemyForm(editorController)),
        SqSwingButton("Add new pathway condition", _ => showNewPathwayPrerequisiteForm(editorController)),
        SqSwingButton("Delete existing pathway condition", _ => showDeletePathwayPrerequisiteForm(editorController))
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

    private def showForbiddenActionDialog(message: String) = SqSwingDialog(
      "Forbidden action",
      message,
      List(SqSwingButton("ok", _ => {})),
      closable = false
    )

  }

  def apply(editorController: EditorController): EditorView = new EditorViewSwing(editorController)
}
