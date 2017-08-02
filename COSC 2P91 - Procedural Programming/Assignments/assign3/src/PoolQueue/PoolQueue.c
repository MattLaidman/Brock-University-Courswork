#include <stdio.h>
#include <stdlib.h>
#include "PoolQueue.h"
#include "PQDefs.h"

/**
 * PoolQueue.c is an implementation of the PoolQueue as defined by
 * the PoolQueue.h header file.
 *
 * COSC 2P91 - Assignment 3 (Mini-Project 1)
 * Matt Laidman
 * 5199807, ml12ef
 * March 2015
 */

/**
 * PQaddSingle function adds a single item to a group and thus to
 * the queue via the internal add function.
 *
 * @param item - The item being added
 * @param queue - The PoolQueue
 */
void PQaddSingle (void *item, PoolQueue *queue) {

	add(queue, 1, item);
}

/**
 * PQaddPair function adds a pair to a group and thus to
 * the queue via the internal add function.
 *
 * @param first - The first items
 * @param second - The second item
 * @param queue - The PoolQueue
 */
void PQaddPair (void *first, void *second, PoolQueue *queue) {

	add(queue, 2, first, second);
}

/**
 * PQaddTriple function adds a triplet to a group and thus to
 * the queue via the internal add function.
 *
 * @param first - The first item
 * @param second - The second item
 * @param third - The third item
 * @param queue - The PoolQueue
 */
void PQaddTriple (void *first, void *second, void *third, PoolQueue *queue) {

	add(queue, 3, first, second, third);
}

/**
 * PQaddQuartet function adds a quartet to a group and thus to
 * the queue via the internal add function.
 *
 * @param first - The first item
 * @param second - The second item
 * @param third - The third item
 * @param fourth - The fourth item
 * @param queue - The PoolQueue
 */
void PQaddQuartet (void *first, void *second, void *third, void *fourth, PoolQueue *queue) {

	add(queue, 4, first, second, third, fourth);
}

/**
 * PQremoveSingle removes the single item from the queue with the highest priority by
 * calling the internal unadd function.
 *
 * @param single - The array to return the item
 * @param queue - The PoolQueue
 * @return 0 if no single is found, otherwise 1
 */
int PQremoveSingle(void **single, PoolQueue *queue) {

	int r;

	r = unadd(single, queue->grip, 1, 0);
	if (r) {
		clean(queue);
	}
	return r;
}

/**
 * PQremovePair removes the pair from the queue with the highest priority by
 * calling the internal unadd function.
 *
 * @param pair - The array to return the items
 * @param queue - The PoolQueue
 * @return 0 if no pair is found, otherwise 1
 */
int PQremovePair(void **pair, PoolQueue *queue) {

	int r;

	r = unadd(pair, queue->grip, 2, 0);
	if (r) {
		clean(queue);
	}
	return r;
}

/**
 * PQremoveTriple removes the triplet from the queue with the highest priority by
 * calling the internal unadd function.
 *
 * @param triple - The array to return the items
 * @param queue - The PoolQueue
 * @return 0 if no triplet is found, otherwise 1
 */
int PQremoveTriple(void **triple, PoolQueue *queue) {

	int r;

	r = unadd(triple, queue->grip, 3, 0);
	if (r) {
		clean(queue);
	}
	return r;
}

/**
 * PQremoveQuartet removes the quartet from the queue with the highest priority by
 * calling the internal unadd function.
 *
 * @param quartet - The array to return the items
 * @param queue - The PoolQueue
 * @return 0 if no quartet is found, otherwise 1
 */
int PQremoveQuartet(void **quartet, PoolQueue *queue) {

	int r;

	r = unadd(quartet, queue->grip, 4, 0);
	if (r) {
		clean(queue);
	}
	return r;
}

/**
 * PQhasSingle function returns 0 if the PoolQueue can not make a group
 * of 1
 *
 * @param queue - The PoolQueue
 * @return 0 of PoolQueue can not make group
 */
int PQhasSingle(PoolQueue *queue) {

	return has(queue->grip, 1);
}

/**
 * PQhasPair function returns 0 if the PoolQueue can not make a group
 * of 2
 *
 * @param queue - The PoolQueue
 * @return 0 of PoolQueue can not make group
 */
int PQhasPair(PoolQueue *queue) {

	return has(queue->grip, 2);
}

/**
 * PQhasTriple function returns 0 if the PoolQueue can not make a group
 * of 3
 *
 * @param queue - The PoolQueue
 * @return 0 of PoolQueue can not make group
 */
int PQhasTriple(PoolQueue *queue) {

	return has(queue->grip, 3);
}

/**
 * PQhasQuartet function returns 0 if the PoolQueue can not make a group
 * of 4
 *
 * @param queue - The PoolQueue
 * @return 0 of PoolQueue can not make group
 */
int PQhasQuartet(PoolQueue *queue) {

	return has(queue->grip, 4);
}

/**
 * PQCount function returns the total number of items waiting in the
 * PoolQueue.
 *
 * @param queue - The PoolQueue
 * @return The number of items in the PoolQueue
 */
int PQcount(PoolQueue *queue) {

	Group *gptr  = queue->grip;
	int sum = 0;

	while (gptr != NULL) {
		sum += gptr->size;
		gptr = gptr->next;
	}
	return sum;
}

/**
 * PQcreatePoolQueue function creates a handle for the client program
 * utilizing the PoolQueue.
 *
 * @return The PoolQueue
 */
PoolQueue *PQcreatePoolQueue ( ) {

	PoolQueue *pq = (PoolQueue *)malloc(sizeof(PoolQueue));
	pq->grip = NULL;

	return pq;
}


/**
 * PQdisposeOfPoolQueue function removes all internal handles for the PoolQueue
 * and free up all memory associated with it.
 *
 * This will not free the memory of the actual items that were in the queue.
 *
 * @param condemned - The PoolQueue
 */
void PQdisposeOfPoolQueue(PoolQueue *condemned) {

	Group *gptr = condemned->grip;
	while (gptr != NULL) {
		gptr->flag = 1;
	}
	clean(condemned);
	free((void *)condemned);
}

/**
 * Static remG function will free the memory of a passed Group struct.
 *
 * @param g - The group to free
 */
static void remG (Group *g) {

	Item *i = g->items;
	Item *ti;

	while (i != NULL) {
		ti = i;
		i = i->next;
		free((void *)ti);
	}
	free((void *)g);
}

/**
 * Static clean function chacks the flags on the Groups and calls the static
 * remG function to remove them.
 *
 * @param q - The PoolQueue
 */
static void clean (PoolQueue *q) {

	//System.gc().... Oh wait...

	Group *pg = NULL;
	Group *g = q->grip;
	Group *tg;

	while (g != NULL) {
		if (g->flag) {
			tg = g;
			g = g->next;
			if (tg->items == NULL) {
				printf("Whyyyyyy\n");
			}
			remG(tg);
			if (pg == NULL) {
				q->grip = g;
			} else {
				pg->next = g;
			}
		} else {
			pg = g;
			g = g->next;
		}
	}
}

/**
 * static makeItem function creates an item struct with the passed item.
 *
 * @param  item - the item to wrap
 * @return the wrapped item
 */
static Item *makeItem (void *item) {

	printf("make0");
	Item *i = (Item *)malloc(sizeof(Item));

	printf("make1");
	i->item = item;
	printf("make2");
	i->next = NULL;

	return i;
}

/**
 * Static variadic add function takes any number of arguments, adds them into a group
 * and then pushes the group to the PoolQueue.
 *
 * @param queue - The PoolQueue
 * @param count - The number of items being added to the group
 */
static void add (PoolQueue *queue, int count, ...) {

	printf("add0");
	va_list items;
	int j;
	printf("add1");
	Group *qptr = queue->grip;
	printf("add2");
	Group *gptr = (Group *)malloc(sizeof(Group));
	Item *iptr;
	Item *piptr;

	gptr->items = NULL;
	gptr->next = NULL;
	gptr->size = 0;
	gptr->flag = count;

	printf("add3");
	va_start(items, count);
	for (j = 0 ; j < count ; j++) {
		printf("here0");
		iptr = makeItem(va_arg(items, void *));
		printf("here1");
		piptr->next = iptr;
		printf("here2");
		if (gptr->items == NULL) {
			gptr->items = iptr;
		}
		printf("here3");
		piptr = iptr;
		printf("here4");
		iptr = iptr->next;
	}
	va_end(items);

	if (qptr == NULL) {
		qptr = gptr;
	} else if (qptr->next == NULL) {
		qptr->next = gptr;
	} else {
		while (qptr->next != NULL) {
			qptr = qptr->next;
		}
		qptr->next = gptr;
	}
}

/**
 * static has function recursively checks the queue to see if the given number
 * could be returned by the queue.
 *
 * @param g - the group to check
 * @param c - the number to remove
 * @return 1 if queue can return given amount, 0 if not
 */
static int has (Group *g, int c) {

	if (c == 0) {
		return 1;
	}
	if (g == NULL) {
		return 0;
	}
	if (g->size == c) {
		return 1;
	}
	if (g->size < c) {
		return has(g->next, (c - g->size));
	} else {
		return has (g->next, c);
	}
}

/**
 * Static getItems function copies the contents of the passed group to the airlock.
 *
 * @param airlock - PoolQueue -> airlock -> Space!
 * @param g - The group
 * @param i - The index
 */
static void getItems (void **airlock, Group *g, int *i) {

	int j;
	Item *iptr = g->items;

	for (j = 0 ; j < g->size ; j++) {
		airlock[(*i)] = iptr->item;
		iptr = iptr->next;
		(*i)++;
	}
	g->flag = 1;
}

/**
 * Static unadd function removes the given count from the queue, choosing the groups that
 * meet the remove count requirement that have the highest priority.
 *
 * @param airlock - PoolQueue -> airlock -> Space!
 * @param queue - The PoolQueue
 * @param count - The number of items to remove
 * @return 0 if no group matching count requirement is found
 */
static int unadd (void **airlock, Group *g, int c, int i) {

	if (c == 0) {
		return 1;
	}
	if (!has(g, c)) {
		return 0;
	}
	if (g->size == c) {
		getItems(airlock, g, &i);
		return 1;
	}
	if (g->size < c) {
		c = (c - g->size);
		getItems(airlock, g, &i);
		return unadd(airlock, g->next, (c - g->size), i);
	} else {
		return unadd(airlock, g->next, c, i);
	}
}
