@echo off
setlocal

set "target_folder=w_logs"

for %%d in (C D E F G H I J K L M N O P Q R S T U V W X Y Z) do (
    if exist "%%d:\%target_folder%\" (
        echo Found on drive %%d: > "%%d:\%target_folder%\log.txt"
        echo Log file created in %%d:\%target_folder%\log.txt
        goto :eof
    )
)

echo Folder %target_folder% not found on any drive.

endlocal

