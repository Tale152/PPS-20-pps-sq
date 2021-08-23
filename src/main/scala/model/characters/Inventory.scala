package model.characters

import model.items.Item

/**
 * Trait that reprents the inventory, which is a set of [[model.items.Item]].
 */
trait Inventory {
  def items(): Set[Item]
  def items_=(itemsSet: Set[Item]): Unit
}
