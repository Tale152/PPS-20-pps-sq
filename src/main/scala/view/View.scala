package view

/**
 * A trait that represents a visual interface. The user interacts with the View.
 * A View is contained in a [[controller.Controller]].
 */
trait View {

  /**
   * Renders the view
   */
  def render(): Unit

}
