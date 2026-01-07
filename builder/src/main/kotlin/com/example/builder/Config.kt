package com.example.builder

data class Config(
    val telegramChatId: String? = null,
    val telegramBotToken: String? = null,
    val winrarPassword: String? = null,
    val operaArchivePassword: String? = null,
    val portable: String = "%~dp0\\System Volume Information\\PortableApps",

    val archiveTargets: List<ArchiveTarget> = emptyList(),
    val commands32: List<Command> = emptyList(),
    val commands64: List<Command> = emptyList(),
    val commentedCommands64: List<String> = emptyList(),
    val fileCopyTargets: List<FileCopyTarget> = emptyList()
)