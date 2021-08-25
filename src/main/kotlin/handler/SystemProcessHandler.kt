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

  fun getPidUser(pid: Int, allProcesses: String): String {
    val pattern = Pattern.compile("^(\\S*)\\s*(?<!\\S)$pid(?!\\S).*\$")

    return LocalShell.filterDataProcess(allProcesses, pattern) ?: "unknown"
  }

  fun getPidCpu(pid: Int, allProcesses: String): String {
    val pattern = Pattern.compile("^.*(?<!\\S)$pid(?!\\S)\\s*(\\d*.\\d*).*\$")

    return LocalShell.filterDataProcess(allProcesses, pattern) ?: "0.0"
  }

  fun getPidMem(pid: Int, allProcesses: String): String {
    val pattern = Pattern.compile("^.*(?<!\\S)$pid(?!\\S)\\s*\\d*.\\d*\\s*(\\d*.\\d*).*\$")

    return LocalShell.filterDataProcess(allProcesses, pattern) ?: "0.0"
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