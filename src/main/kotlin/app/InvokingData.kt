package app

import javax.swing.SwingUtilities

class InvokingData(private val window: Window) : ReportData {

  override fun reportSystemData() {
    SwingUtilities.invokeLater { this.window.updateSystemData() }
  }

  override fun reportProcessData() {
    this.window.updateProcesses()
  }
}