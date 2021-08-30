package controller.game

private[controller] object OperationType {

  /**
   * Trait used by the [[controller.game.GameMasterController]] to specify who gains control,
   * presumably a [[controller.game.subcontroller.SubController]]
   */
  sealed trait OperationType

  /**
   * Operation used to pass control to the [[controller.game.subcontroller.StoryController]]
   */
  case object StoryOperation extends OperationType

  /**
   * Operation used to pass control to the [[controller.game.subcontroller.PlayerInfoController]]
   */
  case object PlayerInfoOperation extends OperationType

  /**
   * Operation used to pass control to the [[controller.game.subcontroller.HistoryController]]
   */
  case object HistoryOperation extends OperationType

  /**
   * Operation used to pass control to the [[controller.game.subcontroller.ProgressSaverController]]
   */
  case object ProgressSaverOperation extends OperationType
}
