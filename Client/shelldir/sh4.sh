#!/usr/bin/env bash
if test "$1"=="q";then
   if test "$2"=="d";then
       gradlew buildBundle -q -Dbundle.arch=x86
   else
       gradlew buildBundle -q
   fi
else
    if test "$1"=="d";then
         if test "$2"=="q";then
             gradlew buildBundle -q -Dbundle.arch=x86
         else
             gradlew buildBundle -Dbundle.arch=x86
         fi
    else
      gradlew buildBundle
    fi
fi
