package controller.util

object Checker {

  /**
   * Implicit class that adds a method to check for Exception and perform another action if so.
   * @param action The action the caller is trying to perform.
   */
  implicit class ActionChecker(action: () => Unit) {
    /**
     * Checks if an action causes a [[scala.Exception]], launch failAction in that case.
     *
     * @param failAction The action to perform if the deserialization fails.
     */
    def ifFails(failAction: () => Unit): Unit = {
      try {
        action()
      } catch {
        case _: Exception => failAction()
      }
    }
  }

}
