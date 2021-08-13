package controller.game

object OperationType {

  /**
   * Trait used by the [[controller.GameMasterController]] to specify who gains control,
   * presumably a [[controller.subcontroller.SubController]]
   */
  trait OperationType

  /**
   * Operation used by to pass control to the [[controller.subcontroller.StoryController]]
   */
  case object StoryOperation extends OperationType

}
