package view

import view.util.scalaQuestSwingComponents.SqSwingPanel

/**
 * A trait that represents a visual interface. The user interacts with the View.
 * A View is contained in a [[controller.Controller]].
 */
sealed trait View {

  /**
   * Renders the view.
   */
  def render(): Unit

}

abstract class AbstractView extends SqSwingPanel with View {

  /**
   * Template method that renders the view.
   */
  override def render(): Unit = {
    this.updateUI()
    this.removeAll()
    Frame.frame.getKeyListeners.foreach(k => Frame.frame.removeKeyListener(k))
    populateView()
    Frame.setPanel(this)
    Frame.setVisible(true)
  }

  /**
   * Sub-portion of render() where graphical elements are added.
   */
  def populateView(): Unit

}
