@file:Suppress("SpellCheckingInspection")

package handler

import util.LocalShell
import java.io.File
import java.nio.file.Paths
import java.time.LocalTime
import java.util.*
import java.util.regex.Pattern

class SystemProcessHandler {

  val allPIDs: List<Int>
    get() {
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

  fun getPidUser(pid: Int): String {
    val pattern = Pattern.compile("(^.*)$")
    val command = "ps -o uname= -p $pid"
    return LocalShell.getCommandResult(pattern, command) ?: "unknown"
  }

  fun getPidCpu(pid: Int): Double {
    val pattern = Pattern.compile("^(\\d*.\\d*)\$|\\n")
    val command = "ps -e -o pid,%cpu | grep $pid | awk '{print $2}'"
    val result = LocalShell.getCommandResult(pattern, command)
    return result?.toDouble() ?: 0.0
  }

  fun getPidMem(pid: Int): Double {
    val pattern = Pattern.compile("^(\\d*.\\d*)$")
    val command = "ps -e -o pid,%mem | grep $pid | awk '{print $2}'"
    val result = LocalShell.getCommandResult(pattern, command)
    return result?.toDouble() ?: 0.0
  }

  fun getPidTime(pid: Int): LocalTime {
    val pattern = Pattern.compile("^\\s*(.*)$")
    val command = "ps -o etimes= -p $pid"
    val result = LocalShell.getCommandResult(pattern, command)
    val secs = result?.toInt() ?: 0
    val timeFormatted = String.format("%02d:%02d:%02d", secs / 3600, secs % 3600 / 60, secs % 60)
    return LocalTime.parse(timeFormatted)
  }

  fun getPidCmd(pid: Int): String {
    val pattern = Pattern.compile("^.*/(.*\\w)")
    val command = "ps -e -o pid,cmd | grep $pid | awk '{print $2}'"
    return LocalShell.getCommandResult(pattern, command) ?: "unknown"
  }
}