@echo off
if "%1"=="q" (
   if "%2"=="d" (
       gradlew buildBundle -q -Dbundle.arch=x86
   ) else (
       gradlew buildBundle -q
   )
) else (
    if "%1"=="d" (
         if "%2"=="q" (
             gradlew buildBundle -q -Dbundle.arch=x86
         ) else (
             gradlew buildBundle -Dbundle.arch=x86
         )
    ) else (
      gradlew buildBundle
   )
)
