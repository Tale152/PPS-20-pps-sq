package controller.game

object OperationType {

  /**
   * Trait used by the [[controller.GameMasterController]] to specify who gains control,
   * presumably a [[controller.subcontroller.SubController]]
   */
  trait OperationType

  /**
   * Operation used to pass control to the [[controller.game.subcontroller.StoryController]]
   */
  case object StoryOperation extends OperationType

  /**
   * Operation used to pass control to the [[controller.game.subcontroller.StatStatusController]]
   */
  case object StatStatusOperation extends OperationType

}
