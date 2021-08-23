package model.items

/**
 * Trait that represents an Item.
 */
trait Item {
  val name: String
  val description: String
  def use(character: Character): Unit
}

/**
 * The abstract class that describe an item.
 * @param name the item name.
 * @param description the item description.
 */
abstract class AbstractItem(override val name: String,
                            override val description: String) extends Item {

  /**
   * Template method that use [[model.items.AbstractItem#applyEffect(java.lang.Character)]]
   * and [[model.items.AbstractItem#postEffect(java.lang.Character)]].
   * @param character, the target of the item effect.
   */
  override def use(character: Character): Unit = {
    applyEffect(character)
    postEffect(character)
  }

  abstract def applyEffect(character: Character): Unit
  abstract def postEffect(character: Character): Unit
}
