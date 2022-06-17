package handler

import util.LocalShell

@Suppress("SpellCheckingInspection")
class SystemInfosHandler {

  val threadsPerCore: Int
    get() {
      val command = "lscpu | awk 'NR==11' | sed 's/ //g' | cut -d: -f2"
      return LocalShell
        .getCommandResult(command)
        .replace("\n", "")
        .toInt()
    }

  val availableProcessors: Int
    get() {
      val command = "lscpu | awk 'NR==12' | cut -d: -f2 | sed 's/ //g'"
      return LocalShell
        .getCommandResult(command)
        .replace("\n", "")
        .toInt()
    }

  val totalRunningThreads: Int
    get() {
      val command = "ps -eo nlwp | tail -n +2 | awk '{ num_threads += $1 } END { print num_threads }' | sed 's/ //g'"
      return LocalShell
        .getCommandResult(command)
        .replace("\n", "")
        .toInt()
    }

  val totalSystemTime: String
    get() {
      val command = "ps -o etime= 1 | sed 's/ //g'"
      return LocalShell.getCommandResult(command)
    }

  val totalSockets: String
    get() {
      val command = "lscpu | awk 'NR==13' | sed 's/ //g' | cut -d: -f 2"
      return LocalShell.getCommandResult(command)
    }

  val freeMem: Long
    get() {
      val command = "awk 'NR==2' /proc/meminfo | sed 's/kB/ /g' | cut -d: -f2 | sed 's/ //g'"
      return LocalShell
        .getCommandResult(command)
        .replace("\n", "")
        .toLong()
        .plus(availableMem)
    }

  private val availableMem: Long
    get() {
      val command = "awk 'NR==3' /proc/meminfo | sed 's/kB/ /g' | cut -d: -f2 | sed 's/ //g'"
      return LocalShell
        .getCommandResult(command)
        .replace("\n", "")
        .toLong()
    }

  val usedMem: Long
    get() = totalMem - freeMem

  val totalMem: Long
    get() {
      val command = "awk 'NR==1' /proc/meminfo | sed 's/kB/ /g' | cut -d: -f2 | sed 's/ //g'"
      return LocalShell
        .getCommandResult(command)
        .replace("\n", "")
        .toLong()
    }

  val freeSwap: Long
    get() {
      val command = "awk 'NR==16' /proc/meminfo | sed 's/kB/ /g' | cut -d: -f2 | sed 's/ //g'"
      return LocalShell
        .getCommandResult(command)
        .replace("\n", "")
        .toLong()
    }

  val usedSwap: Long
    get() = totalSwap - freeSwap

  val totalSwap: Long
    get() {
      val command = "awk 'NR==15' /proc/meminfo | sed 's/kB/ /g' | cut -d: -f2 | sed 's/ //g'"
      return LocalShell
        .getCommandResult(command)
        .replace("\n", "")
        .toLong()
    }
}