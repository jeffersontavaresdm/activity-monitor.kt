@file:Suppress("SpellCheckingInspection")

package app

import handler.ProcessPanelHandler
import handler.SystemDataPanelHandler
import util.ColorLayout
import util.LocalShell
import java.awt.BorderLayout
import java.awt.Color
import java.awt.Dimension
import java.awt.GridLayout
import java.util.regex.Pattern
import javax.swing.*
import javax.swing.plaf.metal.MetalLookAndFeel


class Window : JFrame() {

  companion object {
    private val headerPanel = JPanel()
    private val centralPanel = JPanel()
  }

  init {
    createWindow()
    addKeyListener(WindowKeyListener(this))
  }

  private fun createWindow() {
    title = "ACTIVITY MONITOR"
    configSize()
    layout = BorderLayout()
    isUndecorated = true
    getRootPane().windowDecorationStyle = JRootPane.FRAME
    defaltColor()
    pack()
    defaultCloseOperation = DISPOSE_ON_CLOSE
    isVisible = true

    headerPanel.border = BorderFactory.createEmptyBorder()
    headerPanel.layout = GridLayout()
    headerPanel.background = Color.darkGray
    val headerScrollPane = JScrollPane(headerPanel)
    contentPane.add(headerScrollPane, BorderLayout.PAGE_START)

    centralPanel.border = BorderFactory.createEmptyBorder()
    centralPanel.layout = GridLayout()
    centralPanel.background = Color.black
    val scrollPane = JScrollPane(centralPanel)
    scrollPane.verticalScrollBar.unitIncrement = 30
    scrollPane.horizontalScrollBar.unitIncrement = 30
    contentPane.add(scrollPane, BorderLayout.CENTER)
  }

  private fun configSize() {
    val command = "xdpyinfo | awk '/dimensions/{print $2}'"
    val widthPattern = Pattern.compile("^(\\d*)\\D\\d*$")
    val heightPattern = Pattern.compile("^\\d*\\D(\\d*)$")
    val width = command.getDimension(widthPattern)
    val height = command.getDimension(heightPattern)
    this.preferredSize = Dimension(width, height)
  }

  private fun String.getDimension(pattern: Pattern): Int {
    var command = this
    command = LocalShell.executeCommand(command)

    val value: String = command
      .lines()
      .filter { line: String -> pattern.matcher(line).matches() }
      .map { line: String ->
        val matcher = pattern.matcher(line)
        matcher.find()
        matcher.group(1)
      }.first() ?: "480"
    return value.toInt()
  }

  fun updateSystemData() {
    SystemDataPanelHandler().updateSystemData(headerPanel)
  }

  fun updateProcesses() {
    ProcessPanelHandler().updateProcesses(centralPanel)
  }

  private fun defaltColor() {
    val theme = ColorLayout(Color.darkGray).theme
    MetalLookAndFeel.setCurrentTheme(theme)
    UIManager.setLookAndFeel(MetalLookAndFeel())
    SwingUtilities.updateComponentTreeUI(this)
  }
}