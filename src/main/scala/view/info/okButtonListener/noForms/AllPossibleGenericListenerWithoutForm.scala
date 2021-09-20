package view.info.okButtonListener.noForms

import controller.InfoController

/**
 * Abstract common class for buttons that does not need a form.
 * @param infoController A [[controller.InfoController]]
 * @tparam A The generic type of the items in the solutions.
 */
abstract class AllPossibleGenericListenerWithoutForm[A](val infoController: InfoController)
  extends InfoWithoutFormButtonListener(infoController) {

  /**
   * @return the solution of the prolog engine computation.
   */
  def solutions: List[List[A]]

  /**
   * @return a condition. If true, the file creation phase will be next.
   */
  override def fileSaveCondition: () => Boolean = () => solutions.nonEmpty
}
