package model.items

import model.characters.Character

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

/**
 * An item that can't be used, it may be useful in the storyline.
 * @param name the item name.
 * @param description the item description.
 */
case class KeyItem(override val name: String,
                   override val description: String) extends AbstractItem(name, description) {
  override def applyEffect(character: Character): Unit = { /*does nothing*/ }

  override def postEffect(character: Character): Unit = { /*does nothing*/ }
}

/**
 * An item that can be consumed during the storyline.
 * @param name the item name.
 * @param description the item description.
 * @param consumableStrategy the actual effect of the item when it's consumed.
 */
case class ConsumableItem(override val name: String,
                          override val description: String,
                          consumableStrategy: Character => Unit) extends AbstractItem(name, description) {
  override def applyEffect(character: Character): Unit = consumableStrategy(character)

  override def postEffect(character: Character): Unit = ???
}

case class EquipItem(override val name: String,
                     override val description: String,
                    ) extends AbstractItem(name, description){

}