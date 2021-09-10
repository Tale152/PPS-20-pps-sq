package view.mainMenu.buttonListeners

import controller.ApplicationController
import controller.util.serialization.StoryNodeSerializer.deserializeStory
import model.nodes.StoryNode
import view.mainMenu.MainMenu
import view.mainMenu.buttonListeners.MainMenuButtonListeners.MainMenuChooserButtonListener
import view.util.scalaQuestSwingComponents.SqSwingButton
import view.util.scalaQuestSwingComponents.dialog.SqSwingDialog

import java.awt.event.ActionEvent
import javax.swing.JFileChooser

case class EditorButtonListener(override val applicationController: ApplicationController,
                                mainMenu: MainMenu)
  extends MainMenuChooserButtonListener(applicationController) {

  override def actionPerformed(e: ActionEvent): Unit = {
    SqSwingDialog(
      "ScalaQuest",
      "Do you want to create a new story or load an existing one?",
      List(
        SqSwingButton("New story", _ => ApplicationController.goToEditor(
          StoryNode(0, "start", None, Set(), List()))
        ),
        SqSwingButton("Load story", _ =>
          if (fileChooser.showOpenDialog(mainMenu) == JFileChooser.APPROVE_OPTION) {
            ApplicationController.goToEditor(deserializeStory(fileChooser.getSelectedFile.getPath))
          })
      ))
  }

}
