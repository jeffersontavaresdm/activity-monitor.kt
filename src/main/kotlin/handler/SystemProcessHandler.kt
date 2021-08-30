@file:Suppress("SpellCheckingInspection")

package handler

import util.LocalShell
import java.io.File
import java.nio.file.Paths
import java.util.*
import java.util.regex.Pattern


class SystemProcessHandler {

  fun getAllPIDs(): List<Int> {
    val path = Paths.get("/proc")
    val procDirectory = File(path.toUri())
    return if (procDirectory.isDirectory) {
      val procFiles = procDirectory.listFiles() ?: throw RuntimeException("Invalid Proc folder!")
      Arrays.stream(procFiles)
        .filter { file: File ->
          file.isDirectory && file.name.matches(Regex("^\\d*$"))
        }
        .map { file: File -> Integer.valueOf(file.name) }
        .toList()
    } else {
      throw RuntimeException("Invalid Proc Folder!")
    }
  }

  fun getPid(process: String): Int? {
    val pattern = Pattern.compile("^\\S*\\s*(?<!\\S)(\\d*)(?!\\S).*\$")

    val result = LocalShell.filterDataProcess(process, pattern)
    return result?.toInt()
  }

  fun getUser(process: String): String {
    val pattern = Pattern.compile("^(\\S*)\\s*(?<!\\S)\\d*(?!\\S).*\$")

    return LocalShell.filterDataProcess(process, pattern) ?: "unknown"
  }

  fun getCpu(process: String): Double {
    val pattern = Pattern.compile("^\\S*\\s*(?<!\\S)\\d*(?!\\S)\\s*(\\d*.\\d)\\s*\\d*\\.\\d*\\s*\\d*\\s*.*\$")

    val result = LocalShell.filterDataProcess(process, pattern) ?: "0.0"
    return result.toDouble()
  }

  fun getMem(process: String): Double {
    val pattern = Pattern.compile("^\\S*\\s*(?<!\\S)\\d*(?!\\S)\\s*\\d*.\\d\\s*(\\d*.\\d)\\s*\\d*\\s*\\d*\\s*.*\$")

    val result = LocalShell.filterDataProcess(process, pattern) ?: "0.0"
    return result.toDouble()
  }

  fun getTime(process: String): String {
    val pattern = Pattern
      .compile(
        "^\\S*\\s*(?<!\\S)\\d*(?!\\S)\\s*\\d*.\\d\\s*\\d*\\.\\d*\\s*\\d*\\s*\\d*\\s*\\S*\\s*\\S*\\s*\\d*:\\d*\\s*(\\d*:\\d*)\\s*.*\$"
      )

    return LocalShell.filterDataProcess(process, pattern) ?: return "00:00"
  }

  fun getCmd(process: String): String {
    val pattern = Pattern
      .compile(
        "^\\S*\\s*(?<!\\S)\\d*(?!\\S)\\s*\\d*.\\d\\s*\\d*\\.\\d*\\s*\\d*\\s*\\d*\\s*\\S*\\s*\\S*\\s*\\d*:\\d*\\s*\\d*:\\d*\\s*(.*)\$"
      )

    return LocalShell.filterDataProcess(process, pattern) ?: "unknown"
  }
}