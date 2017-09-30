@echo off
call batdir/bat3.bat
if "%1"=="q" (
   if "%2"=="d" (
       call batdir/bat4.bat q d
   ) else (
       call batdir/bat4.bat q
   )
) else (
    if "%1"=="d" (
         if "%2"=="q" (
             call batdir/bat4.bat q d
         ) else (
             call batdir/bat4.bat d
         )
    ) else (
      call batdir/bat4.bat
   )
)

