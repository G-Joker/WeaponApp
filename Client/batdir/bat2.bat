@echo off
if "%1"=="q" (
   if "%2"=="d" (
       gradlew buildLib -q -Dbundle.arch=x86
   ) else (
       gradlew buildLib -q
   )
) else (
    if "%1"=="d" (
         if "%2"=="q" (
             gradlew buildLib -q -Dbundle.arch=x86
         ) else (
             gradlew buildLib -Dbundle.arch=x86
         )
    ) else (
      gradlew buildLib
   )
)
