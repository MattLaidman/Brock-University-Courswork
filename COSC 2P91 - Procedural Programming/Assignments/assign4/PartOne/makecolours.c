#include <stdio.h>
#include <pthread.h>
#include "makecolours.h"

/**
 * makecolours.c library takes given data and creates a "rainbow"
 * image of a given width, height, and name.
 *
 * Written by Matt Laidman (5199807)
 * April 15, 2015
 * COSC 2P91 - Assignment 4 (Mini-Project 2)
 */

float *image;
int width;
int height;
int poolsize;

struct ThreadInfo {
	int xStart;
	int finishedFlag;
};

/**
 * initializeImage function initalizes the image to the values given.
 */
void initializeImage(int w, int h, float *i) {
	width = w;
	height = h;
	image = i;
}

/**
 * processPixel function computed to colour values of each pixel
 * passed to it.
 */
static void processPixel(int x, int y) {
	float r, g, b;
	r = x/(float)width;
	g = 0.25;
	b = y/(float)width;
	image[y*width*3+x*3] = r;
	image[y*width*3+x*3+1] = g;
	image[y*width*3+x*3+2] = b;
}

/**
 * processBlinds function computes the pixes to be computed by
 * each thread and calls the processPixel function.
 */
static void *processBlinds(void *td) {
	struct ThreadInfo *threads;
	threads = (struct ThreadInfo*)td;
	int i, j;
	for (i = 0 ; i < height ; i++)
		for (j = threads->xStart ; j < width ; j+=poolsize)
			processPixel(j, i);
	threads->finishedFlag=1;
}

/**
 * processImage function splits the image computations in threads
 * and starts each thread on the processBlinds function.
 */
void processImage(int ps) {
	pthread_t pool[ps];
	struct ThreadInfo threads[ps];
	int i;
	int notFlag;
	poolsize = ps;
	
	for (i = 0 ; i < poolsize ; i++) {
		threads[i].xStart = i;
		threads[i].finishedFlag = 0;
		pthread_create(&pool[i], NULL, processBlinds, &threads[i]);
	}
	do {
		notFlag = 0;
		for (i = 0 ; i < poolsize ; i++)
			notFlag = notFlag | !threads[i].finishedFlag;
		usleep(1000);
	} while (notFlag);
}

