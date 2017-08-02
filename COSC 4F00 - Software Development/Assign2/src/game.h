#ifndef GAME_H
#define GAME_H

#include <iostream>
#include <pthread.h>

#include "board.h"
#include "player.h"

// COSC 4F00 - Software Development (2016)
// Instructor: Vlad Wojcik
// Brock University
//
// Assignment #: 2
// Due: November 18, 2016
//
// Matt Laidman
// ml12ef, 5199807
//
//
// Three Dimensional Tic-Tac-Toe
// An Exercise in AI and Concurrency in C++

// game.h

// Global Variables

Board* board;
Player* p1;
Player* p2;

// Function Prototypes

void playGame();

#endif