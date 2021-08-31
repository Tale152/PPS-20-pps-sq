package controller.util.serialization

import java.io.{FileInputStream, FileOutputStream, ObjectOutputStream}

/**
 * Utility Object that contains methods to serialize and deserialize objects in files.
 */
private[serialization] object FileSerializer {

  /**
   * Serialize the object in a file.
   *
   * @param serializable the object to serialize.
   * @param fileName     the file path that will contain the serialized object.
   */
  def serializeObject(serializable: Serializable, fileName: String): Unit = {
    val objectOutputStream = new ObjectOutputStream(new FileOutputStream(fileName))
    objectOutputStream.writeObject(serializable)
    objectOutputStream.close()
  }

  /**
   * Deserialize the object from a file.
   *
   * @param fileUri the file path that contains the serialized object.
   * @return the deserialized object.
   */
  def deserializeObject(fileUri: String): AnyRef = {
    val objectInputStream = new CustomObjectInputStream(new FileInputStream(fileUri))
    val serializable = objectInputStream.readObject
    objectInputStream.close()
    serializable
  }

}
