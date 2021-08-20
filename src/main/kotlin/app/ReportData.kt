package app

import javax.swing.SwingUtilities

class ReportData(private val window: Window) {

  fun reportSystemData() {
    SwingUtilities.invokeLater { this.window.updateSystemData() }
  }

  fun reportProcesses() {
    this.window.updateProcesses()
  }
}