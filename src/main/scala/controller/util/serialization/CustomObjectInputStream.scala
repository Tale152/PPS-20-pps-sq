package controller.util.serialization

import java.io.{InputStream, ObjectInputStream}

/**
 * Class used to resolve a JVM bug that could not resolve a class in some rare cases.
 * Useful for Deserialization.
 * @param inputStream the inputStream
 */
class CustomObjectInputStream(inputStream: InputStream)
  extends ObjectInputStream(inputStream) {
  override def resolveClass(desc: java.io.ObjectStreamClass): Class[_] = {
    try {
      Class.forName(desc.getName, false, getClass.getClassLoader)
    }
    catch {
      case _: ClassNotFoundException => super.resolveClass(desc)
    }
  }
}