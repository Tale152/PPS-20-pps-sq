package specs

import controller.util.serialization.CustomObjectInputStream

import java.io.{ByteArrayInputStream, ByteArrayOutputStream, ObjectOutputStream}

/**
 * SerializableSpec trait used in tests of model classes to test if serialization works fine.
 * Let the test class extends this trait and use it like this:
 * "[Serialization Description]" should behave like [[specs.SerializableSpec#serializationTest(scala.Serializable)]]
 */
trait SerializableSpec { this: FlatTestSpec =>

  def serializationTest(serObject: Serializable): Unit = {
    "Serialization and Deserialization of " + serObject.toString + "(" + serObject.hashCode() + ")" should "work" in {
      val serialized = {
        val bos = new ByteArrayOutputStream()
        val out = new ObjectOutputStream(bos)
        out.writeObject(serObject)
        val result = bos.toByteArray
        out.close()
        bos.close()
        result
      }
      val bis = new ByteArrayInputStream(serialized)
      val in = new CustomObjectInputStream(bis)
      in.readObject()
      bis.close()
      in.close()
    }
  }
}
