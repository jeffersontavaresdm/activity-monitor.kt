package handler

import dto.SystemInfosDTO
import util.ComponentConfigs
import util.Converter
import java.awt.Color
import java.awt.Component
import java.awt.Font
import javax.swing.BorderFactory
import javax.swing.BoxLayout
import javax.swing.JLabel
import javax.swing.JPanel

class SystemInfosPanelHandler {

  fun updateSystemData(headerPanel: JPanel) {
    val systemInfo: SystemInfosDTO = getSystemInfo()
    val processesPanel = generatePanel("Process")
    val cpuPanel = generatePanel("Cpu")
    val memPanel = generatePanel("Mem")
    val swapPanel = generatePanel("Swap")

    /**
     * Process count
     */
    getProcessesPanel(systemInfo, processesPanel)

    /**
     * Cpu data
     */
    getCpuPanel(systemInfo, cpuPanel)

    /**
     * Mem data
     */
    getMemPanel(systemInfo, memPanel)

    /**
     * Swap data
     */
    getSwapPanel(systemInfo, swapPanel)

    /**
     * update frame panel header
     */
    updateHeadPanel(
      headerPanel = headerPanel,
      processesPanel = processesPanel,
      cpuPanel = cpuPanel,
      memPanel = memPanel,
      swapPanel = swapPanel
    )
  }

  private fun updateHeadPanel(
    headerPanel: JPanel,
    processesPanel: JPanel,
    cpuPanel: JPanel,
    memPanel: JPanel,
    swapPanel: JPanel,
  ) {
    headerPanel.removeAll()
    headerPanel.repaint()
    headerPanel.add(processesPanel)
    headerPanel.add(cpuPanel)
    headerPanel.add(memPanel)
    headerPanel.add(swapPanel)
    headerPanel.revalidate()
    headerPanel.updateUI()
  }

  private fun getProcessesPanel(systemData: SystemInfosDTO, processesPanel: JPanel) {
    val handler = SystemProcessHandler()
    val allPIDs: List<Int> = handler.getAllPIDs()
    val processCount = allPIDs.size.toLong()
    generateLabel(processesPanel, "tasks: $processCount")
    generateLabel(processesPanel, "running threads: " + systemData.runningThreads)
    generateLabel(processesPanel, "process cpu time: " + systemData.totalSystemTime)
  }

  private fun getCpuPanel(systemData: SystemInfosDTO, cpuPanel: JPanel) {
    generateLabel(cpuPanel, "sockets: " + systemData.sockets)
    generateLabel(cpuPanel, "cores per socket: " + systemData.availableProcessors)
    generateLabel(cpuPanel, "threads per core: " + systemData.threadsPerCore)
  }

  private fun getMemPanel(systemData: SystemInfosDTO, memPanel: JPanel) {
    generateLabel(memPanel, "free: " + Converter.converterKbyteToGigabyte(systemData.freeMem))
    generateLabel(memPanel, "used: " + Converter.converterKbyteToGigabyte(systemData.usedMem))
    generateLabel(memPanel, "total: " + Converter.converterKbyteToGigabyte(systemData.totalMem))
  }

  private fun getSwapPanel(systemData: SystemInfosDTO, swapPanel: JPanel) {
    generateLabel(swapPanel, "free: " + Converter.converterKbyteToGigabyte(systemData.freeSwap))
    generateLabel(swapPanel, "used: " + Converter.converterKbyteToGigabyte(systemData.usedSwap))
    generateLabel(swapPanel, "total: " + Converter.converterKbyteToGigabyte(systemData.totalSwap))
  }

  private fun generatePanel(title: String): JPanel {
    val panel = JPanel()
    panel.layout = BoxLayout(panel, BoxLayout.Y_AXIS)
    panel.border = BorderFactory.createEmptyBorder()
    val titleLabel = JLabel(title)
    ComponentConfigs.setFontConfigs(titleLabel)
    titleLabel.alignmentX = Component.CENTER_ALIGNMENT
    panel.add(titleLabel)
    return panel
  }

  private fun generateLabel(panel: JPanel, value: String) {
    val label = JLabel()
    setFontConfigs(label)
    label.text = value
    label.alignmentX = Component.CENTER_ALIGNMENT
    panel.add(label)
  }

  private fun setFontConfigs(label: JLabel) {
    label.font = Font("Fira Code Medium", Font.ITALIC, 13)
    label.foreground = Color.white
    label.background = Color.black
  }

  private fun getSystemInfo(): SystemInfosDTO {
    val systemInfoHandler = SystemInfosHandler()
    return SystemInfosDTO(
      runningThreads = systemInfoHandler.totalRunningThreads,
      totalSystemTime = systemInfoHandler.totalSystemTime,

      threadsPerCore = systemInfoHandler.threadsPerCore,
      availableProcessors = systemInfoHandler.availableProcessors,
      sockets = systemInfoHandler.totalSockets,

      freeMem = systemInfoHandler.freeMem,
      usedMem = systemInfoHandler.usedMem,
      totalMem = systemInfoHandler.totalMem,

      freeSwap = systemInfoHandler.freeSwap,
      usedSwap = systemInfoHandler.usedSwap,
      totalSwap = systemInfoHandler.totalSwap
    )
  }
}