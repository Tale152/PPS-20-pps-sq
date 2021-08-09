package controller

object OperationType {

  /**
   * Trait used by the [[controller.MasterController]] to specify who gains control,
   * presumably a [[controller.subcontroller.SubController]]
   */
  trait OperationType

  /**
   *  Operation used by to pass control to the [[controller.subcontroller.StoryController]]
   */
  case object StoryOperation extends OperationType

}
