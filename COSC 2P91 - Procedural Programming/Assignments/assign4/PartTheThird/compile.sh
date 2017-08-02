#!/bin/bash
gcc -c -fpic makescene.c
gcc -shared -o libmakescene.so makescene.o
