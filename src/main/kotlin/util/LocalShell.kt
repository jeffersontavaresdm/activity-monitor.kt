package util

import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.util.regex.Pattern

object LocalShell {

  fun getCommandResult(pattern: Pattern, command: String): String {
    val result = executeCommand(command)
    return applyPattern(result, pattern)
  }

  fun getCommandResult(command: String): String {
    return executeCommand(command)
  }

  fun filterDataProcess(processes: String, pattern: Pattern): String? {
    val resultCommandOrNull = applyPattern(processes, pattern)
    return resultCommandOrNull.ifEmpty { null }
  }

  private fun applyPattern(allProcess: String, pattern: Pattern): String {
    val resultCommandOrNull = allProcess
      .lines()
      .filter { line -> pattern.matcher(line).matches() }
      .map { line ->
        val matcher = pattern.matcher(line)
        matcher.find()
        matcher.group(1)
      }
      .firstOrNull()
      .orEmpty()

    return resultCommandOrNull
  }

  @Throws(IOException::class)
  fun executeCommand(command: String): String {
    val commands = listOf("/bin/bash").plus("-c").plus(command)

    val processBuilder = ProcessBuilder(commands)
    val process = processBuilder.start()
    val inputStream = process.inputStream
    val inputStreamReader = InputStreamReader(inputStream)
    val bufferedReader = BufferedReader(inputStreamReader)

    var line: String?
    val builder = StringBuilder()

    while ((bufferedReader.readLine().also { readLine -> line = readLine }) != null) {
      builder.append(line).append("\n")
    }

    bufferedReader.close()

    return builder.toString()
  }
}