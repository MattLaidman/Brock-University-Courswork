#ifndef PCLIMB_H
#define PCLIMB_H


// COSC 2P95 - Programming in C++ with Applications
// Brock University (FALL 2016)
// Instructor: Earl Foxwell

// Lab Exercise 9 - Section 01

// Matt Laidman
// ml12ef, 5199807

// Parallel Hill-Climbing!


#include <iostream>
#include <cmath>
#include <signal.h>
#include <unistd.h>
#include <pthread.h>
#include <float.h>

using namespace std;

volatile float bestX, bestY, bestZ;
volatile int occupied;
volatile bool continuing;

pthread_mutex_t ioLock, bestLock;

float getRandStart();
float getRandMod();
float eggHolder(float, float);
void* climb(void*);

// signal handlers
void interrupted(int);
void printMin(int);
void printMin();

#endif