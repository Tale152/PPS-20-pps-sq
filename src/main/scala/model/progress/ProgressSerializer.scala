package model.progress

import java.io.{FileOutputStream, ObjectOutputStream}

object ProgressSerializer {

  def serializeProgress(progress: Progress, fileName:String): Unit = {
    val oos = new ObjectOutputStream(new FileOutputStream(fileName))
    oos.writeObject(progress)
    oos.close()
  }

}
