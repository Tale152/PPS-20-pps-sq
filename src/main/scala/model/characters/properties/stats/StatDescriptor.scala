package model.characters.properties.stats

import StatName.StatName

/**
 * Trait that represent a generic StatDescriptor, where you can specify with which stat you want to work with.
 */
trait StatDescriptor extends Serializable {
  def statName(): StatName
}
