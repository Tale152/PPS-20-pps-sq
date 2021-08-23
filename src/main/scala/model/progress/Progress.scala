package model.progress

trait Progress extends Serializable {
  def serializableHistory: SerializableHistory
}

object Progress {
  private class ProgressImpl(override val serializableHistory: SerializableHistory) extends Progress

  def apply(serializableHistory: SerializableHistory): Progress = new ProgressImpl(serializableHistory)
}