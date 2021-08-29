package view.util.common

import view.Frame
import view.util.scalaQuestSwingComponents.{SqSwingButton, SqSwingFlowPanel, SqSwingGridPanel}

import java.awt.event.{KeyEvent, KeyListener}


object ControlsPanel{

  /**
   * Panel containing one or more buttons with associated actions and key listeners.
   * @param controls list of pairs in wich the first argument is the key associated to the listener, the second is a
   * pair containing the string to print on the button and the action on click / key pressed
   */
  class ControlsPanel(controls: List[(String, (String, Unit => Unit))]) extends SqSwingGridPanel(0,2){
    if(controls.map(a => a._1).toSet.size != controls.size) throw new IllegalArgumentException()
    for(a <- controls){
      val btn = SqSwingButton(a._2._1, _ => a._2._2())
      this.add(btn)
      Frame.frame.addKeyListener(new KeyListener {
        override def keyTyped(e: KeyEvent): Unit =
          if (e.getKeyChar.toString.toLowerCase == a._1.toLowerCase) btn.doClick()

        override def keyPressed(e: KeyEvent): Unit = {/*does nothing*/}

        override def keyReleased(e: KeyEvent): Unit = {/*does nothing*/}
      })
    }
  }

  def apply(actions: List[(String, (String, Unit => Unit))]): ControlsPanel = new ControlsPanel(actions)
}

