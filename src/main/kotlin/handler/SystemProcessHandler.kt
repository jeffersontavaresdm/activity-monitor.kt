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

  fun getPid(pid: Int, allProcesses: String): Int? {
    val pattern = Pattern.compile("^\\w*\\s*(?<!\\S)($pid)(?!\\S).*\$")

    val result = LocalShell.filterDataProcess(allProcesses, pattern)
    return result?.toInt()
  }

  fun getPidUser(pid: Int, allProcesses: String): String {
    val pattern = Pattern.compile("^(\\S*)\\s*(?<!\\S)$pid(?!\\S).*\$")

    return LocalShell.filterDataProcess(allProcesses, pattern) ?: "unknown"
  }

  fun getPidCpu(pid: Int, allProcesses: String): Double {
    val pattern = Pattern.compile("^\\w*\\s*(?<!\\S)$pid(?!\\S)\\s*(\\d*\\S\\d*).*\$")

    val result = LocalShell.filterDataProcess(allProcesses, pattern) ?: "99.99"
    return result.toDouble()
  }

  fun getPidMem(pid: Int, allProcesses: String): Double {
    val pattern = Pattern.compile("^\\w*\\s*(?<!\\S)$pid(?!\\S)\\s*\\d*\\S\\d*\\s*(\\d*\\S\\d*).*\$")

    val result = LocalShell.filterDataProcess(allProcesses, pattern) ?: "99.99"
    return result.toDouble()
  }

  fun getPidTime(pid: Int, allProcesses: String): String {
    val pattern = Pattern.compile("^.*(?<!\\S)$pid(?!\\S).*\\d*:\\d*\\s*(\\d*:\\d*).*\$")

    return LocalShell.filterDataProcess(allProcesses, pattern) ?: "00:00"
  }

  fun getPidCmd(pid: Int, allProcesses: String): String {
    val pattern = Pattern.compile("^.*(?<!\\S)$pid(?!\\S).*\\s[A-Z].*:\\d*\\s\\s*(.*)\$")

    return LocalShell.filterDataProcess(allProcesses, pattern) ?: "unknown"
  }
}