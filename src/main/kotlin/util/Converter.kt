@file:Suppress("SpellCheckingInspection", "NAME_SHADOWING")

package util

import java.text.CharacterIterator
import java.text.StringCharacterIterator
import java.util.concurrent.TimeUnit

object Converter {

  fun converterKbyteToGigabyte(kbytes: Long): String {
    var kbytes = kbytes
    return if (-1000 < kbytes && kbytes < 1000) {
      "$kbytes B"
    } else {
      val characterIterator: CharacterIterator = StringCharacterIterator("MG")
      while (kbytes <= -999950 || kbytes >= 999950) {
        kbytes /= 1000
        characterIterator.next()
      }
      String.format("%.1f %cB", kbytes / 1000.0, characterIterator.current())
    }
  }

  fun converterBytes(bytes: Long): String {
    var bytes = bytes
    return if (-1000 < bytes && bytes < 1000) {
      "$bytes B"
    } else {
      val characterIterator: CharacterIterator = StringCharacterIterator("kMGTPE")
      while (bytes <= -999950 || bytes >= 999950) {
        bytes /= 1000
        characterIterator.next()
      }
      String.format("%.1f %cB", bytes / 1000.0, characterIterator.current())
    }
  }

  fun convertNanoSecToMilliSec(nanoSec: Long?): Long {
    return TimeUnit.MILLISECONDS.convert(nanoSec!!, TimeUnit.NANOSECONDS)
  }
}