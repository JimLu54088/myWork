@echo off
setlocal enabledelayedexpansion

set "rootDir=D:\test_files\NMLroot"

rem 获取昨天的日期
for /f "tokens=1-3 delims=-" %%a in ('powershell Get-Date -Format yyyy-MM-dd') do (
    set "todayDate=%%a%%b%%c"
)
set /a "yesterdayDate=todayDate - 1"

rem 遍历文件夹
for /d %%i in ("%rootDir%\*") do (
    for %%j in (ADR3 ADT_rest APP) do (
        set "logDir=%%i\ASP_LOG\%%j"
        if exist "!logDir!\" (
            pushd "!logDir!"
            for %%f in (*.log%yesterdayDate%) do (
                rem 压缩文件
                "C:\Program Files\7-Zip\7z.exe" a -tzip "%%~nf.zip" "%%~ff"
                ren "%%~nf.zip" "%%~nxf.zip"
                rem 删除原始文件
                del "%%f"
            )
            popd
        )
    )
)

endlocal

rem 让窗口等待用户按下任意键后再关闭
pause
