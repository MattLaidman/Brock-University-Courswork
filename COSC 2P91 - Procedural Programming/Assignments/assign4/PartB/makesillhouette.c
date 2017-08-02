#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>
#include "makesillhouette.h"

/**
 * makesillhouette.c library takes given data and creates a sillhouette
 * image of the spheres in the data given.
 *
 * Written by Matt Laidman (5199807)
 * April 15, 2015
 * COSC 2P91 - Assignment 4 (Mini-Project 2)
 */


float *image;
int width;
int height;
int poolsize;
struct Sphere *sphere;
int numSpheres;

/**
 * initializeImage function initalizes the image to the values given.
 */
void initializeImage (int w, int h, float *i) {
	width=w;
	height=h;
	image=i;
}

/**
 * hasIntersect function determines if an intersect occurs with the given
 * ray and the spheres.
 */
int hasIntersect (struct Ray *ray) {

	int i;

	float dx = 0.0;
	float dy = 0.0;
	float dz = 1.0;

	float vx = (float)ray->start->x;
	float vy = (float)ray->start->y;
	float vz = (float)ray->start->z;

	for (i = 0 ; i < numSpheres ; i++) {
		float cx = (float)sphere[i].pos->x;
		float cy = (float)sphere[i].pos->y;
		float cz = (float)sphere[i].pos->z;

		float r = (float)sphere[i].radius;

		float a = dx*dx + dy*dy + dz*dz;
		float b = 2*dx*(vx-cx) +  2*dy*(vy-cy) +  2*dz*(vz-cz);
		float c = cx*cx + cy*cy + cz*cz + vx*vx + vy*vy + vz*vz -
			2*(cx*vx + cy*vy + cz*vz) - r*r;

		float d = b * b - 4 * a * c;
		
		if(d > 0)
			return 1;
	}
	return 0;
}

/**
 * processPixel function sets the pixel to white or black depending on
 * if and intersection occurs at that location or not.
 */
static void processPixel (int x, int y) {
	
	struct Ray ray;
	ray.start = malloc(sizeof(struct Vector));

	ray.start->x = x;
	ray.start->y = y;
	ray.start->z = 0;

	if (hasIntersect(&ray)) {
		image[y*width*3+x*3] = 1;
		image[y*width*3+x*3+1] = 1;
		image[y*width*3+x*3+2] = 1;
	} else {
		image[y*width*3+x*3] = 0;
		image[y*width*3+x*3+1] = 0;
		image[y*width*3+x*3+2] = 0;
	}
}

/**
 * processBlinds function computes the pixes to be computed by
 * each thread and calls the processPixel function.
 */
static void *processBlinds (void *td) {

	struct ThreadInfo *threads;
	threads = (struct ThreadInfo*)td;
	int i, j;
	for (i = 0 ; i < height ; i++)
		for (j = threads->xStart ; j < width ; j+=poolsize)
			processPixel(j,i);
	threads->flag=1;
}

/**
 * processImage function assigns the data array to the appropriate
 * variables and splits the image computations in threads/starts
 * each thread on the processBlinds function.
 */
void processImage (int ps, int n, float *s) {

	pthread_t pool[ps];
	struct ThreadInfo threads[ps];
	int i;
	int notFlag;
	poolsize = ps;
	numSpheres = n;
	sphere =  malloc(sizeof(struct Sphere)*numSpheres);

	for (i = 0 ; i < n ; i++) {
		sphere[i].pos = malloc(sizeof(struct Vector));
		sphere[i].pos->x = s[i*4];
		sphere[i].pos->y = s[i*4+1];
		sphere[i].pos->z = s[i*4+2];
		sphere[i].radius = s[i*4+3];
	}


	for (i = 0 ; i < poolsize ; i++) {
		threads[i].xStart =i ;
		threads[i].flag = 0;
		pthread_create(&pool[i], NULL, processBlinds, &threads[i]);
	}
	do {
		notFlag = 0;
		for (i = 0 ; i < poolsize ; i++)
			notFlag = notFlag | !threads[i].flag;
		usleep(1000);
	} while (notFlag);
}
