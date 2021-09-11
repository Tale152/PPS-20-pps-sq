package controller.game.subcontroller

import controller.game.{GameMasterController, OperationType}
import model.StoryModel
import model.characters.properties.stats.StatName.StatName
import model.nodes.{ItemEvent, Pathway, StatEvent}
import view.story.StoryView
import view.util.StringFormatUtil.formatted

/**
 * The [[controller.game.subcontroller.SubController]] that contains the logic to update the
 * [[model.StoryModel]] current [[model.nodes.StoryNode]].
 */
sealed trait StoryController extends SubController {

  /**
   * Choose the pathway to update the [[model.StoryModel]] current [[model.nodes.StoryNode]].
   *
   * @param pathway the chosen pathway.
   * @throws IllegalArgumentException when selecting a pathway that does not belong to the current
   *                                  [[model.nodes.StoryNode]]
   */
  def choosePathWay(pathway: Pathway): Unit

  /**
   * Calls the [[controller.game.GameMasterController]] to grant control to the
   * [[controller.game.subcontroller.PlayerInfoController]].
   */
  def goToStatStatus(): Unit

  /**
   * Calls the [[controller.game.GameMasterController]] to grant control to the
   * [[controller.game.subcontroller.HistoryController]].
   */
  def goToHistory(): Unit

  /**
   * Calls the [[controller.game.GameMasterController]] to grant control to the
   * [[controller.game.subcontroller.ProgressSaverController]].
   */
  def goToProgressSaver(): Unit

  /**
   * Calls the [[controller.game.GameMasterController]] to grant control to the
   * [[controller.game.subcontroller.InventoryController]].
   */
  def goToInventory(): Unit
}

object StoryController {

  def apply(gameMasterController: GameMasterController, storyModel: StoryModel): StoryController =
    new StoryControllerImpl(gameMasterController, storyModel)

  class StoryControllerImpl(private val gameMasterController: GameMasterController, private val storyModel: StoryModel)
    extends StoryController {

    private val storyView: StoryView = StoryView(this)

    override def execute(): Unit = {
      processEvents()
      storyView.setNarrative(storyModel.currentStoryNode.narrative)
      storyView.setPathways(
        storyModel.currentStoryNode.pathways.filter(
          p => p.prerequisite.isEmpty || (p.prerequisite.nonEmpty && p.prerequisite.get(storyModel)))
      )
      storyView.render()
    }

    override def close(): Unit = gameMasterController.close()

    override def choosePathWay(pathway: Pathway): Unit = {
      if (!storyModel.currentStoryNode.pathways.contains(pathway)) {
        throw new IllegalArgumentException(
          "The selected Pathway does not belong to the current StoryNode: " + pathway.toString
        )
      }
      storyModel.appendToHistory(pathway.destinationNode)
      redirect()
    }

    override def goToStatStatus(): Unit = gameMasterController.executeOperation(OperationType.PlayerInfoOperation)

    override def goToHistory(): Unit = gameMasterController.executeOperation(OperationType.HistoryOperation)

    override def goToProgressSaver(): Unit = gameMasterController.executeOperation(OperationType.ProgressSaverOperation)

    override def goToInventory(): Unit = gameMasterController.executeOperation(OperationType.InventoryOperation)

    private def redirect(): Unit = if (storyModel.currentStoryNode.enemy.isEmpty) this.execute() else goToBattle()

    private def goToBattle(): Unit = gameMasterController.executeOperation(OperationType.BattleOperation)

    /**
     * Process all events, then delete them from the current [[model.nodes.StoryNode]].
     */
    private def processEvents(): Unit = {
      import view.util.StringFormatUtil.FormatElements.NewLine

      storyView.displayEvent(storyModel.currentStoryNode.events.map {
        case StatEvent(eventDescription, statModifier) =>
          storyModel.player.properties.statModifiers += statModifier
          formatted(eventDescription + NewLine +
            "Stat " + statModifier.statName + " modified " +
            "(" + getStatDifferences(statModifier.statName, statModifier.modifyStrategy) + ")"
          )
        case ItemEvent(eventDescription, item) =>
          storyModel.player.inventory = storyModel.player.inventory :+ item
          formatted(eventDescription + NewLine + "New Item found: " + item.name)
      })
      storyModel.currentStoryNode.removeAllEvents()
    }

    /**
     * @param statName The name of the stat to consider.
     * @param statModifierStrategy The strategy to apply last.
     * @return the difference between the stat value before and after the application of statModifierStrategy
     *         formatted with + or - sign.
     */
    private def getStatDifferences(statName: StatName, statModifierStrategy: Int => Int): String = {
      val originalStatValue = storyModel.player.properties.stat(statName).value
      val modifiedStatValue = storyModel.player.properties.statModifiers.filter(s => s.statName == statName)
        .foldLeft(originalStatValue)((o, m) => m.modifyStrategy(o))
      val difference = statModifierStrategy(modifiedStatValue) - modifiedStatValue
      if(difference >= 0){
        "+" + difference
      } else {
        difference.toString
      }
    }

  }

}