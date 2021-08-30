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

  fun getPid(process: String): Int {
    val pattern = Pattern.compile("^\\w*\\s*(?<!\\S)(\\d*)(?!\\S).*\$")

    val result = LocalShell.filterDataProcess(process, pattern)
    return result?.toInt() ?: 99999999
  }

  fun getPidUser(process: String): String {
    val pattern = Pattern.compile("^(\\S*)\\s*(?<!\\S)\\d*(?!\\S).*\$")

    return LocalShell.filterDataProcess(process, pattern) ?: "unknown"
  }

  fun getPidCpu(process: String): Double {
    val pattern = Pattern.compile("^\\w*\\s*(?<!\\S)\\d*(?!\\S)\\s*(\\d*\\.*\\d*)\\s*\\d*\\.\\d*.*\$")

    val result = LocalShell.filterDataProcess(process, pattern) ?: "99.99"
    println(result)
    return result.toDouble()
  }

  fun getPidMem(process: String): Double {
    val pattern = Pattern.compile("^\\w*\\s*(?<!\\S)\\S*(?!\\S)\\s*\\d*\\.*\\d*\\s*(\\d*\\.\\d*).*\$")

    val result = LocalShell.filterDataProcess(process, pattern) ?: "99.99"
    return result.toDouble()
  }

  fun getPidTime(process: String): String {
    val pattern = Pattern
      .compile(
        "^.*\\s*(?<!\\S)\\d*(?!\\S)\\s*\\d*\\.\\d*\\s*\\d*\\.\\d*\\s*\\d*\\s*\\d*\\s*\\S*\\s*\\S*\\s*\\d*:\\d*\\s*(\\d*:\\d*).*\$"
      )

    return LocalShell.filterDataProcess(process, pattern) ?: "00:00:00"
  }

  fun getPidCmd(process: String): String {
    val pattern = Pattern.compile("^\\w*\\s*(?<!\\S)\\d*(?!\\S).*\\s[A-Z].*:\\d*\\s\\s*(.*)\$")

    return LocalShell.filterDataProcess(process, pattern) ?: "unknown"
  }
}