#!/bin/bash
gcc -c -fpic makecolours.c
gcc -shared -o libmakecolours.so makecolours.o
