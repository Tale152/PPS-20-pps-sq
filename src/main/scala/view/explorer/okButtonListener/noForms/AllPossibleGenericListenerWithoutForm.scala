package view.explorer.okButtonListener.noForms

import controller.ExplorerController

/**
 * Abstract common class for buttons that does not need a form.
 *
 * @param explorerController A [[controller.ExplorerController]]
 * @tparam A The generic type of the items in the solutions.
 */
abstract class AllPossibleGenericListenerWithoutForm[A](val explorerController: ExplorerController)
  extends ExplorerWithoutFormButtonListener(explorerController) {

  /**
   * @return the solution of the prolog engine computation.
   */
  def solutions: List[List[A]]

  /**
   * @return a condition. If true, the file creation phase will be next.
   */
  override def fileSaveCondition: () => Boolean = () => solutions.nonEmpty
}
