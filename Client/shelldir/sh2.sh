#!/usr/bin/env bash
if test "$1"=="q";then
   if test "$2"=="d";then
       gradlew buildLib -q -Dbundle.arch=x86
   else
       gradlew buildLib -q
   fi
else
    if test "$1"=="d";then
         if test "$2"=="q";then
             gradlew buildLib -q -Dbundle.arch=x86
         else
             gradlew buildLib -Dbundle.arch=x86
         fi
    else
      gradlew buildLib
    fi
fi