#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>
#include <math.h>
#include "makescene.h"

/**
 * makescene.c library takes given data and creates an image
 * of the spheres in the data given with the appropriate
 * diffuse, specular, and ambient lighting, and shadows.
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
struct Light *light;
struct Light *ambient;
int numSpheres;
int numLights;

/**
 * initializeImage function initalizes the image to the values given.
 */
void initializeImage (int w, int h, float *i) {
	width=w;
	height=h;
	image=i;
}

/**
 * getIntersect function determines if an intersect occurs with the given
 * ray and the spheres and returns the coordinates and material of the
 * closest intersection point.
 */
struct Intersection *getIntersect (struct Vector *ray, struct Sphere *sphere, struct Vector *dir) {

	struct Intersection *intersect = NULL;
	struct Vector *pos = malloc(sizeof(struct Vector));
	struct Material *mat = malloc(sizeof(struct Material));

	float t;

	float dx = dir->x;
	float dy = dir->y;
	float dz = dir->z;

	float vx = (float)ray->x;
	float vy = (float)ray->y;
	float vz = (float)ray->z;

	float cx = (float)sphere->pos->x;
	float cy = (float)sphere->pos->y;
	float cz = (float)sphere->pos->z;

	float r = (float)sphere->radius;

	float a = dx*dx + dy*dy + dz*dz;
	float b = 2*dx*(vx-cx) +  2*dy*(vy-cy) + 2*dz*(vz-cz);
	float c = cx*cx + cy*cy + cz*cz + vx*vx + vy*vy + vz*vz -
		2*(cx*vx + cy*vy + cz*vz) - r*r;

	float d = b * b - 4 * a * c;
	
	if (d >= 0) {
		intersect = malloc(sizeof(struct Intersection));
		t = ((-b) - sqrt(d))/(2);
		if (t < 0) {
			t = ((-b) + sqrt(d))/(2);
		}
		pos->x = vx + t*dx;
		pos->y = vy + t*dy;
		pos->z = vz + t*dz;
		mat->r = sphere->mat->r;
		mat->g = sphere->mat->g;
		mat->b = sphere->mat->b;
		intersect->pos = pos;
		intersect->mat = mat;
	}
	return intersect;
}

/**
 * processPixel function calculates the amount of ambient, specular,
 * and diffuse light that would be on a pixel and sets it accordingly.
 */
static void processPixel (int x, int y) {
	
	struct Intersection *intersect;
	struct Intersection *intPointer = NULL;
	struct Ray ray;
	struct Vector viewer;
	float r, g, b;
	float diffMult;
	int i = 0;
	int sphereIndex = -1;

	struct Light *diff;
	struct Light *spec;

	viewer.x = 0.0;
	viewer.y = 0.0;
	viewer.z = 1.0;

	ray.start = malloc(sizeof(struct Vector));

	ray.start->x = x;
	ray.start->y = y;
	ray.start->z = 0;

	for (i = 0 ; i < numSpheres ; i++) {
		if ((intersect = getIntersect(ray.start, &(sphere[i]), &viewer)) &&
			(intPointer == NULL || intersect->pos->z < intPointer->pos->z)) {
			intPointer = intersect;
			sphereIndex = i;
		}
	}
	if (intPointer == NULL) {
		image[y*width*3+x*3] = 0;
		image[y*width*3+x*3+1] = 0;
		image[y*width*3+x*3+2] = 0;
	} else {
		intersect = intPointer;

		diff = getDiff(intersect, sphereIndex);
		spec = getSpec(intersect, sphereIndex);

		r = ambient->r + diff->r + spec->r;
		g = ambient->g + diff->g + spec->g;
		b = ambient->b + diff->b + spec->b;

		image[y*width*3+x*3] = (r*intersect->mat->r > 1 ? 1 : r*intersect->mat->r);
		image[y*width*3+x*3+1] = (g*intersect->mat->g > 1 ? 1 : g*intersect->mat->g);
		image[y*width*3+x*3+2] = (b*intersect->mat->b > 1 ? 1 : b*intersect->mat->b);
	}
	free(ray.start);
}

/**
 * getSpec function calculates the amount of specular light that
 * is on a given intersection from the light sources.
 */
static struct Light *getSpec (struct Intersection *intersect, int sphereIndex) {
	
	struct Light *specularLight = malloc(sizeof(struct Light));
	struct Intersection *intPointer = NULL;
	struct Vector dir;
	struct Vector sNorm;
	struct Vector reflect;
	float norm;
	float r, g, b;
	float multiplier;
	float factor;
	int i, j;
	int closest;
	r = 0;
	g = 0;
	b = 0;

	specularLight->r = 0;
	specularLight->g = 0;
	specularLight->b = 0;
	if (sphere[sphereIndex].specular == 0) {
		return specularLight;
	}
	for (i = 0 ; i < numLights ; i++) {
		if (light[i].pos->z < intersect->pos->z) {
			sNorm.x = (intersect->pos->x - sphere[sphereIndex].pos->x)/sphere[sphereIndex].radius;
			sNorm.y = (intersect->pos->y - sphere[sphereIndex].pos->y)/sphere[sphereIndex].radius;
			sNorm.z = (intersect->pos->z - sphere[sphereIndex].pos->z)/sphere[sphereIndex].radius;
			dir.x = light[i].pos->x - intersect->pos->x;
			dir.y = light[i].pos->y - intersect->pos->y;
			dir.z = light[i].pos->z - intersect->pos->z;
			norm = sqrt(dir.x*dir.x + dir.y*dir.y + dir.z*dir.z);
			dir.x = dir.x/norm;
			dir.y = dir.y/norm;
			dir.z = dir.z/norm;
			for (j = 0 ; j < numSpheres ; j++) {
				if (intersect = getIntersect(light[i].pos, &sphere[j], &dir)) {
					if (intPointer == NULL) {
						intPointer = intersect;
						closest = j;
					} else if (sqrt(intersect->pos->x*intersect->pos->x + intersect->pos->y*intersect->pos->y +
						intersect->pos->z*intersect->pos->z) < sqrt(intPointer->pos->x*intPointer->pos->x +
						intPointer->pos->y*intPointer->pos->y + intPointer->pos->z*intPointer->pos->z)) {
						intPointer = intersect;
						closest = j;
					}
					if (intPointer == NULL) {
						closest = j;
					}
				}
			}
			if (sphereIndex == closest) {
				reflect.x = 2*sNorm.z*sNorm.x;
				reflect.y = 2*sNorm.z*sNorm.y;
				reflect.z = 1 + 2*sNorm.z*sNorm.z;
				multiplier = (reflect.x*dir.x + reflect.y*dir.y + reflect.z*dir.z)*
					(reflect.x*dir.x + reflect.y*dir.y + reflect.z*dir.z);
				r += (sphere[sphereIndex].specular*multiplier)*light[i].r;
				g += (sphere[sphereIndex].specular*multiplier)*light[i].g;
				b += (sphere[sphereIndex].specular*multiplier)*light[i].b;
			}
		}
	}
	specularLight->r = r;
	specularLight->g = g;
	specularLight->b = b;
	return specularLight;
}

/**
 * getDiff function calculates the amount of diffuse light that
 * is on a given intersection from the light sources.
 */
static struct Light *getDiff (struct Intersection *intersect, int sphereIndex) {

	struct Light *diffuseLight = malloc(sizeof(struct Light));
	struct Intersection *intPointer = NULL;
	struct Vector dir;
	struct Vector sNorm;
	float norm;
	float r, g, b;
	float multiplier;
	int i, j;
	int closest;
	r = 0;
	g = 0;
	b = 0;

	diffuseLight->r = 0;
	diffuseLight->g = 0;
	diffuseLight->b = 0;
	if (sphere[sphereIndex].diffuse == 0) {
		return diffuseLight;
	}

	for (i = 0 ; i < numLights ; i++) {
		if (light[i].pos->z < intersect->pos->z) {
			sNorm.x = (intersect->pos->x - sphere[sphereIndex].pos->x)/sphere[sphereIndex].radius;
			sNorm.y = (intersect->pos->y - sphere[sphereIndex].pos->y)/sphere[sphereIndex].radius;
			sNorm.z = (intersect->pos->z - sphere[sphereIndex].pos->z)/sphere[sphereIndex].radius;
			dir.x = light[i].pos->x - intersect->pos->x;
			dir.y = light[i].pos->y - intersect->pos->y;
			dir.z = light[i].pos->z - intersect->pos->z;
			norm = sqrt(dir.x*dir.x + dir.y*dir.y + dir.z*dir.z);
			dir.x = dir.x/norm;
			dir.y = dir.y/norm;
			dir.z = dir.z/norm;
			for (j = 0 ; j < numSpheres ; j++) {
				if (intersect = getIntersect(light[i].pos, &sphere[j], &dir)) {
					if (intPointer == NULL) {
						intPointer = intersect;
						closest = j;
					} else if (sqrt(intersect->pos->x*intersect->pos->x + intersect->pos->y*intersect->pos->y +
						intersect->pos->z*intersect->pos->z) < sqrt(intPointer->pos->x*intPointer->pos->x +
						intPointer->pos->y*intPointer->pos->y + intPointer->pos->z*intPointer->pos->z)) {
						intPointer = intersect;
						closest = j;
					}
				}
			}
			if (sphereIndex == closest) {
				multiplier = sNorm.x*dir.x + sNorm.y*dir.y + sNorm.z*dir.z;
				if (multiplier < 0) {
					multiplier = 0;
				}
				r += (sphere[sphereIndex].diffuse*multiplier)*light[i].r;
				g += (sphere[sphereIndex].diffuse*multiplier)*light[i].g;
				b += (sphere[sphereIndex].diffuse*multiplier)*light[i].b;
			}
		}
	}
	diffuseLight->r = r;
	diffuseLight->g = g;
	diffuseLight->b = b;
	return diffuseLight;
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
	threads->flag = 1;
}

/**
 * processImage function assigns the data array to the appropriate
 * variables and splits the image computations in threads/starts
 * each thread on the processBlinds function.
 */
void processImage (int ps, int ns, float *s, int nl, float *l) {

	pthread_t pool[ps];
	struct ThreadInfo threads[ps];
	int i;
	int notFlag;
	poolsize=ps;
	numSpheres = ns;
	numLights = nl;
	sphere =  malloc(sizeof(struct Sphere)*ns);
	light = malloc(sizeof(struct Light)*nl);
	ambient = malloc(sizeof(struct Light));

	for (i = 0 ; i < ns ; i++) {
		sphere[i].pos = malloc(sizeof(struct Vector));
		sphere[i].pos->x = s[i*9];
		sphere[i].pos->y = s[i*9+1];
		sphere[i].pos->z = s[i*9+2];
		sphere[i].radius = s[i*9+3];
		sphere[i].mat = malloc(sizeof(struct Material));
		sphere[i].mat->r = s[i*9+4];
		sphere[i].mat->g = s[i*9+5];
		sphere[i].mat->b = s[i*9+6];
		sphere[i].diffuse = s[i*9+7];
		sphere[i].specular = s[i*9+8];
	}
	for (i = 0 ; i < nl ; i++) {
		light[i].pos = malloc(sizeof(struct Vector));
		light[i].pos->x = l[i*6+3];
		light[i].pos->y = l[i*6+4];
		light[i].pos->z = l[i*6+5];
		light[i].r = l[i*6+6];
		light[i].g = l[i*6+7];
		light[i].b = l[i*6+8];
	}
	ambient->r = l[0];
	ambient->g = l[1];
	ambient->b = l[2];

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
