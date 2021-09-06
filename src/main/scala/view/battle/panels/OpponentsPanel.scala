package view.battle.panels

import model.characters.Character

class CharacterPanel {

  class CharacterPanel(character: Character) extends SqSwingFlowPanel {
    private val fontSize = 25
    this.add(SqSwingLabel("Health: " + health._1, if (health._1 == health._2) {
      Color.GREEN
    } else {
      Color.RED
    }, fontSize, bold = true))
    this.add(SqSwingLabel("/" + health._2, Color.GREEN, fontSize, bold = true))
  }

  def apply(health: (Int, Int)): CharacterHealthPanel = new CharacterHealthPanel(health)
}
