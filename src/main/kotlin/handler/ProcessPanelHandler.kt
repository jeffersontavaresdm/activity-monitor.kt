@file:Suppress("SpellCheckingInspection")

package handler

import dto.ProcessDTO
import util.ComponentConfigs
import util.LocalShell
import java.awt.Color
import java.awt.Component
import java.awt.Dimension
import java.awt.Font
import java.util.function.Consumer
import javax.swing.*

class ProcessPanelHandler {

  fun updateProcesses(processPane: JPanel) {
    val processes: List<ProcessDTO> = getProcesses()
    val processCount = processes.size

    val pidPanel = generatePanel("PID")
    val userPanel = generatePanel("USER")
    val cpuPanel = generatePanel("CPU")
    val memPanel = generatePanel("MEM")
    val timePanel = generatePanel("TIME")
    val cmdPanel = generatePanel("CMD")

    processes.forEach(Consumer { process ->
      generateLabel(pidPanel, process.pid.toString(), processCount)
      generateLabel(userPanel, process.user, processCount)
      generateLabel(cpuPanel, process.cpu.toString(), processCount)
      generateLabel(memPanel, process.mem.toString(), processCount)
      generateLabel(timePanel, process.time.toString(), processCount)
      generateLabel(cmdPanel, process.command, processCount)
    })

    updateProcessesPanel(
      processPane = processPane,
      pidPanel = pidPanel,
      userPanel = userPanel,
      cpuPanel = cpuPanel,
      memPanel = memPanel,
      timePanel = timePanel,
      cmdPanel = cmdPanel
    )
  }

  private fun updateProcessesPanel(
    processPane: JPanel,
    pidPanel: JPanel,
    userPanel: JPanel,
    cpuPanel: JPanel,
    memPanel: JPanel,
    timePanel: JPanel,
    cmdPanel: JPanel,
  ) {
    processPane.removeAll()
    processPane.repaint()
    processPane.add(pidPanel)
    processPane.add(userPanel)
    processPane.add(cpuPanel)
    processPane.add(memPanel)
    processPane.add(timePanel)
    processPane.add(cmdPanel)
    processPane.revalidate()
    processPane.updateUI()
  }

  private fun generatePanel(title: String): JPanel {
    val panel = JPanel()
    panel.layout = BoxLayout(panel, BoxLayout.Y_AXIS)
    panel.border = BorderFactory.createLineBorder(Color.lightGray)
    val titleLabel = JLabel(title)
    ComponentConfigs.setFontConfigs(titleLabel)
    titleLabel.alignmentX = Component.CENTER_ALIGNMENT
    panel.add(titleLabel)
    val separator = JSeparator(SwingConstants.HORIZONTAL)
    separator.foreground = Color.white
    panel.add(separator)
    return panel
  }

  private fun generateLabel(panel: JPanel, value: String, processCount: Int) {
    val label = JLabel()
    label.font = Font("Fira Code Medium", Font.PLAIN, 14)
    label.foreground = Color.white
    label.background = Color.black
    label.text = value
    label.alignmentX = Component.CENTER_ALIGNMENT
    panel.preferredSize = Dimension(Int.MIN_VALUE, (processCount + 1) * label.minimumSize.height + 5)
    panel.add(label)
  }

  private fun getProcesses(): List<ProcessDTO> {
    val systemProcessHandler = SystemProcessHandler()
    val pids: List<Int> = systemProcessHandler.getAllPIDs()
    val allProcess = LocalShell.executeCommand("ps aux")

    val start = System.currentTimeMillis()

    val processList: MutableList<ProcessDTO> = mutableListOf()

    pids.parallelStream()
      .forEach { _pid ->
        val pid = systemProcessHandler.getPid(_pid, allProcess)
        if (pid != null) {
          processList.add(
            ProcessDTO(
              pid = _pid,
              user = systemProcessHandler.getPidUser(pid, allProcess),
              cpu = systemProcessHandler.getPidCpu(pid, allProcess),
              mem = systemProcessHandler.getPidMem(pid, allProcess),
              time = systemProcessHandler.getPidTime(pid, allProcess),
              command = systemProcessHandler.getPidCmd(_pid, allProcess),
            )
          )
        }
      }

    println("\ngetProcesses took ${System.currentTimeMillis() - start}ms")

    return processList
      .sortedBy(ProcessDTO::mem)
      .reversed()
  }
}