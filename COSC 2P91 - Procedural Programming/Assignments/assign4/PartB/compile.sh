#!/bin/bash
gcc -c -fpic makesillhouette.c
gcc -shared -o libmakesillhouette.so makesillhouette.o
