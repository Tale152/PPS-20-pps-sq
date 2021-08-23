package model.items

/**
 * Trait that represents an Item.
 */
trait Item {
  val name: String
  val description: String
  def use(character: Character): Unit
}
