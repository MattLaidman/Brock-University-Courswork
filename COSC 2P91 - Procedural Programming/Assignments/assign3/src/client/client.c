#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "PoolQueue.h"

/**
 * client.c is a (bad) client to demonstrate the PoolQueue library.
 * All input and output is logged into log.txt file.
 *
 * COSC 2P91 - Assignment 3
 * March 2015
 * Matt Laidman
 * 5199807, ml12ef
 */

PoolQueue *pq;
FILE *f;
char ar[4][20];

/**
 * evalChoice function executes and logs the appropriate function as selected by the
 * user.
 *
 * @param i - The choice
 */
void evalChoice (int i) {

	if (i != 0) {
		switch(i) {
		case 1:
			scanf("Enter Name: %s", ar[0]);
			fputs("Enter Name: ", f);
			fputs(ar[0], f);
			fputs("\n", f);
			PQaddSingle(&(ar[0]), pq);
			printf(":-\t\tAdded [%s]\n", ar[0]);
			fputs(":-\t\tAdded [", f);
			fputs(ar[0], f);
			fputs("]\n",f);
			break;
		case 2:
			scanf("First: %s", ar[0]);
			fputs("First: ", f);
			fputs(ar[0], f);
			fputs("\n", f);
			scanf("Second: %s", ar[1]);
			fputs("Second: ", f);
			fputs(ar[1], f);
			fputs("\n", f);
			PQaddPair(&(ar[0]), &(ar[1]), pq);
			printf(":-\t\tAdded [%s,%s]\n", ar[0], ar[1]);
			fputs(":-\t\tAdded [", f);
			fputs(ar[0], f);
			fputs(",", f);
			fputs(ar[1], f);
			fputs("]\n", f);
			break;
		case 3:
			scanf("First: %s", ar[0]);
			fputs("First: ", f);
			fputs(ar[0], f);
			fputs("\n", f);
			scanf("Second: %s", ar[1]);
			fputs("Second: ", f);
			fputs(ar[1], f);
			fputs("\n", f);
			scanf("Third: %s", ar[2]);
			fputs("Third: ", f);
			fputs(ar[2], f);
			fputs("\n", f);
			PQaddTriple(&(ar[0]), &(ar[1]), &(ar[2]), pq);
			printf(":-\t\tAdded [%s,%s,%s]\n", ar[0], ar[1], ar[2]);
			fputs(":-\t\tAdded [", f);
			fputs(ar[0], f);
			fputs(",", f);
			fputs(ar[1], f);
			fputs(",", f);
			fputs(ar[2], f);
			fputs("]\n", f);
			break;
		case 4:
			scanf("First: %s", ar[0]);
			fputs("First: ", f);
			fputs(ar[0], f);
			fputs("\n", f);
			scanf("Second: %s", ar[1]);
			fputs("Second: ", f);
			fputs(ar[1], f);
			fputs("\n", f);
			scanf("Third: %s", ar[2]);
			fputs("Third: ", f);
			fputs(ar[2], f);
			fputs("\n", f);
			scanf("Fourth: %s", ar[3]);
			fputs("Fourth: ", f);
			fputs(ar[3], f);
			fputs("\n", f);
			PQaddQuartet(&(ar[0]), &(ar[1]), &(ar[2]), &(ar[3]), pq);
			printf(":-\t\tAdded [%s,%s,%s,%s]\n", ar[0], ar[1], ar[2], ar[3]);
			fputs(":-\t\tAdded [", f);
			fputs(ar[0], f);
			fputs(",", f);
			fputs(ar[1], f);
			fputs(",", f);
			fputs(ar[2], f);
			fputs(",", f);
			fputs(ar[3], f);
			fputs("]\n", f);
			break;
		case 5:
			PQremoveSingle((void **)ar, pq);
			printf(":>\t\t[%s]\n", ar[0]);
			fputs(":>\t\t[", f);
			fputs(ar[0], f);
			fputs("]\n", f);
			break;
		case 6:
			PQremovePair((void **)ar, pq);
			printf(":>\t\t[%s,%s]\n", ar[0], ar[1]);
			fputs(":>\t\t[", f);
			fputs(ar[0], f);
			fputs(",", f);
			fputs(ar[1], f);
			fputs("]\n", f);
			break;
		case 7:
			PQremoveTriple((void **)ar, pq);
			printf(":>\t\t[%s,%s,%s]\n", ar[0], ar[1], ar[2]);
			fputs(":>\t\t[", f);
			fputs(ar[0], f);
			fputs(",", f);
			fputs(ar[1], f);
			fputs(",", f);
			fputs(ar[2], f);
			fputs("]\n", f);
			break;
		case 8:
			PQremoveQuartet((void **)ar, pq);
			printf(":>\t\t[%s,%s,%s,%s]\n", ar[0], ar[1], ar[2], ar[3]);
			fputs(":>\t\t[", f);
			fputs(ar[0], f);
			fputs(",", f);
			fputs(ar[1], f);
			fputs(",", f);
			fputs(ar[2], f);
			fputs(",", f);
			fputs(ar[3], f);
			fputs("]\n", f);
			break;
		case 9:
			printf("One:\t\t%s\n", PQhasSingle(pq)?"true":"false");
			fputs("One:\t\t", f);
			fputs(PQhasSingle(pq)?"true":"false", f);
			fputs("\n", f);
			printf("Two:\t\t%s\n", PQhasPair(pq)?"true":"false");
			fputs("Two:\t\t", f);
			fputs(PQhasPair(pq)?"true":"false", f);
			fputs("\n", f);
			printf("Three:\t\t%s\n", PQhasTriple(pq)?"true":"false");
			fputs("Three:\t\t", f);
			fputs(PQhasTriple(pq)?"true":"false", f);
			fputs("\n", f);
			printf("Four:\t\t%s\n", PQhasQuartet(pq)?"true":"false");
			fputs("Four:\t\t", f);
			fputs(PQhasQuartet(pq)?"true":"false", f);
			fputs("\n", f);
			printf("Count:\t\t%d\n", PQcount(pq));
			fputs("Count:\t\t", f);
			fprintf(f, "%d", PQcount(pq));
			fputs("\n", f);
			break;
		}
	}
}

/**
 * main function opens/creates the log file, initializes the
 * PoolQueue, and loops - calling the evalChoice function with
 * each choice - until the user quits.
 *
 * @param argc - N/A
 * @param argv - N/A
 * @return 0 on successful execution
 */
int main (int argc, char *argv[]) {

	int i = -1;
	f = fopen("log.txt", "w");
	pq = PQcreatePoolQueue();

	do {
		if (i != -1) {
			fprintf(f, "%d", i);
			fputs("\n", f);
			evalChoice(i);
		}
		printf("1. Add One\t2. Add Two\t3. Add Three\t4. Add Four\n");
		fputs("1. Add One\t2. Add Two\t3. Add Three\t4. Add Four\n", f);
		printf("5. Remove One\t6. Remove Two\t7. Remove Three\t8. Remove Four\n");
		fputs("5. Remove One\t6. Remove Two\t7. Remove Three\t8. Remove Four\n",f);
		printf("\t\t9. Test Options\t0. Quit\n");
		fputs("\t\t9. Test Options\t0. Quit\n",f);
	} while (scanf("%i", &i) != EOF && i != 0);

	return 0;
}
