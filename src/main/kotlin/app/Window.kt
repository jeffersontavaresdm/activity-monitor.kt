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
import java.awt.event.KeyEvent
import java.awt.event.KeyListener
import java.util.regex.Pattern
import javax.swing.*
import javax.swing.plaf.metal.MetalLookAndFeel
import kotlin.system.exitProcess


class Window : JFrame(), KeyListener {

  companion object {
    private val headerPanel = JPanel()
    private val centralPanel = JPanel()
  }

  init {
    title = "ACTIVITY MONITOR"
    isUndecorated = true
    getRootPane().windowDecorationStyle = JRootPane.FRAME
    defaltColor()
    createWindow(title)
    addKeyListener(this)
  }

  private fun createWindow(title: String?) {
    this.title = title
    configSize()
    this.layout = BorderLayout()
    pack()
    defaultCloseOperation = DISPOSE_ON_CLOSE
    this.isVisible = true

    headerPanel.border = BorderFactory.createEmptyBorder()
    headerPanel.layout = GridLayout()
    headerPanel.background = Color.darkGray
    val headerScrollPane = JScrollPane(headerPanel)
    this.contentPane.add(headerScrollPane, BorderLayout.PAGE_START)

    centralPanel.border = BorderFactory.createEmptyBorder()
    centralPanel.layout = GridLayout()
    centralPanel.background = Color.black
    val scrollPane = JScrollPane(centralPanel)
    scrollPane.verticalScrollBar.unitIncrement = 30
    scrollPane.horizontalScrollBar.unitIncrement = 30
    this.contentPane.add(scrollPane, BorderLayout.CENTER)
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

  override fun keyTyped(e: KeyEvent?) {}

  override fun keyReleased(e: KeyEvent?) {}

  override fun keyPressed(e: KeyEvent) {
    when (e.keyCode) {
      27 -> {
        dispose()
        exitProcess(130)
      }
      112 -> {
        MetalLookAndFeel.setCurrentTheme(ColorLayout(Color.darkGray).theme)
        UIManager.setLookAndFeel(MetalLookAndFeel())
        SwingUtilities.updateComponentTreeUI(this)
      }
      113 -> {
        MetalLookAndFeel.setCurrentTheme(ColorLayout(Color.black).theme)
        UIManager.setLookAndFeel(MetalLookAndFeel())
        SwingUtilities.updateComponentTreeUI(this)
      }
      114 -> {
        MetalLookAndFeel.setCurrentTheme(ColorLayout(Color.blue).theme)
        UIManager.setLookAndFeel(MetalLookAndFeel())
        SwingUtilities.updateComponentTreeUI(this)
      }
      115 -> {
        MetalLookAndFeel.setCurrentTheme(ColorLayout(Color.red).theme)
        UIManager.setLookAndFeel(MetalLookAndFeel())
        SwingUtilities.updateComponentTreeUI(this)
      }
      121 -> {
        val tips = """
        |Press Button to select color:
        |
        |F1: darkGray
        |F2: black
        |F3: blue
        |F4: red
        |
        |ESC: exit""".trimMargin()
        JOptionPane.showMessageDialog(this, tips)
      }
    }
  }

  private fun defaltColor() {
    val theme = ColorLayout(Color.gray).theme
    MetalLookAndFeel.setCurrentTheme(theme)
    UIManager.setLookAndFeel(MetalLookAndFeel())
    SwingUtilities.updateComponentTreeUI(this)
  }
}