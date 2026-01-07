package com.example.builder

data class ArchiveTarget(
    val archiveName: String,
    val sourcePath: String,
    val password: String? = null,// "q123abc!!!"
    val hideErrors: Boolean = true
)