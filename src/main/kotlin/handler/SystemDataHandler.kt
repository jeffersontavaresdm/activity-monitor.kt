package handler

import util.LocalShell
import java.util.regex.Pattern

@Suppress("SpellCheckingInspection")
class SystemDataHandler {

  val threadsPerCore: () -> String = {
    val pattern = Pattern.compile("^.*:\\s*(.*)")
    val command = "lscpu | awk 'NR==7'"
    val result = LocalShell.getCommandResult(pattern, command)
    result ?: "unknown"
  }

  val availableProcessors: Int
    get() {
      val pattern = Pattern.compile("^.*:\\s*(.*)")
      val command = "lscpu | awk 'NR==8'"
      val result = LocalShell.getCommandResult(pattern, command)
      return result?.toInt() ?: 0
    }

  val totalRunningThreads: Int
    get() {
      val pattern = Pattern.compile("^(\\d*)$")
      val command = "ps -eo nlwp | tail -n +2 | awk '{ num_threads += $1 } END { print num_threads }'"
      val result = LocalShell.getCommandResult(pattern, command)
      return result?.toInt() ?: 0
    }

  val totalSystemTime: () -> String = {
    val pattern = Pattern.compile("^\\s*(.*)$")
    val command = "ps -o etime= -p 1"

    val result = LocalShell.getCommandResult(pattern, command)
    result ?: "00:00:00"
  }

  val totalSockets: String
    get() {
      val pattern = Pattern.compile("^.*:\\s*(.*)")
      val command = "lscpu | awk 'NR==9'"
      val result = LocalShell.getCommandResult(pattern, command)
      return result ?: "0"
    }

  val freeMem: Long
    get() {
      val pattern = Pattern.compile("^\\S*\\s*(\\d*).*")
      val command = "awk 'NR==2' /proc/meminfo"
      val result = LocalShell.getCommandResult(pattern, command)
      return result?.let { freeMem -> freeMem.toLong() + availableMem } ?: 0L
    }

  private val availableMem: Long
    get() {
      val pattern = Pattern.compile("^\\S*\\s*(\\d*).*")
      val command = "awk 'NR==3' /proc/meminfo"
      val result = LocalShell.getCommandResult(pattern, command)
      return result?.toLong() ?: 0L
    }

  val usedMem: Long
    get() = totalMem - freeMem

  val totalMem: Long
    get() {
      val pattern = Pattern.compile("^\\S*\\s*(\\d*).*")
      val command = "awk 'NR==1' /proc/meminfo"
      val result = LocalShell.getCommandResult(pattern, command)
      return result?.toLong() ?: 0L
    }

  val freeSwap: Long
    get() {
      val pattern = Pattern.compile("^\\S*\\s*(\\d*).*")
      val command = "awk 'NR==16' /proc/meminfo"
      val result = LocalShell.getCommandResult(pattern, command)
      return result?.toLong() ?: 0L
    }

  val usedSwap: Long
    get() = totalSwap - freeSwap

  val totalSwap: Long
    get() {
      val pattern = Pattern.compile("^\\S*\\s*(\\d*).*")
      val command = "awk 'NR==15' /proc/meminfo"
      val result = LocalShell.getCommandResult(pattern, command)
      return result?.toLong() ?: 0L
    }
}