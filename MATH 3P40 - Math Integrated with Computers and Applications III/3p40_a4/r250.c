/* r250.c      the r250 uniform random number algorithm



               Kirkpatrick, S., and E. Stoll, 1981; "A Very Fast

               Shift-Register Sequence Random Number Generator",

               Journal of Computational Physics, V.40



               also:



               see W.L. Maier, DDJ May 1991







*/



/*static char rcsid[] = "@(#)r250.c      1.2 15:50:31 11/21/94   EFC";*/





#include <limits.h>

#include <math.h>



/* defines to allow for 16 or 32 bit integers */

#define BITS 31





#if WORD_BIT == 32

#ifndef BITS

#define BITS   32

#endif

#else

#ifndef BITS

#define BITS    16

#endif

#endif



#if BITS == 31

#define MSB          0x40000000L

#define ALL_BITS     0x7fffffffL

#define HALF_RANGE   0x20000000L

#define STEP         7

#endif



#if BITS == 32

#define MSB          0x80000000L

#define ALL_BITS     0xffffffffL

#define HALF_RANGE   0x40000000L

#define STEP         7

#endif



#if BITS == 16

#define MSB         0x8000

#define ALL_BITS    0xffff

#define HALF_RANGE  0x4000

#define STEP        11

#endif



static unsigned int r250_buffer[ 250 ];

static int r250_index;



/* here comes (inserted by me) */



/* rndlcg            Linear Congruential Method, the "minimal standard generat

or"

                     Park & Miller, 1988, Comm of the ACM, 31(10), pp. 1192-12

01



*/



static long int quotient  = LONG_MAX / 16807L;

static long int remainder1 = LONG_MAX % 16807L;



static long int seed_val = 1L;



long set_seed(long int sd)

{

        return seed_val = sd;

}



long get_seed()

{

        return seed_val;

}





unsigned long int randlcg()       /* returns a random unsigned integer */

{

        if ( seed_val <= quotient )

                seed_val = (seed_val * 16807L) % LONG_MAX;

        else

        {

                long int high_part = seed_val / quotient;

                long int low_part  = seed_val % quotient;



                long int test = 16807L * low_part - remainder1 * high_part;



                if ( test > 0 )

                        seed_val = test;

                else

                        seed_val = test + LONG_MAX;



        }



        return seed_val;

}

/* end insertion */



#ifdef NO_PROTO

void r250_init(sd)

int seed;

#else

void r250_init(int sd)

#endif

{

       int j, k;

       unsigned int mask, msb;



#ifdef TRUST_RAND        



#if BITS == 32 || BITS == 31       

       srand48( sd );

#else

       srand( sd );

#endif 





#else

       set_seed( sd );

#endif

       

       r250_index = 0;

       for (j = 0; j < 250; j++)      /* fill r250 buffer with BITS-1 bit valu

es */

#ifdef TRUST_RAND

#if BITS == 32 || BITS == 31

               r250_buffer[j] = (unsigned int)lrand48();

#else

               r250_buffer[j] = rand();

#endif

#else

               r250_buffer[j] = randlcg();

#endif





       for (j = 0; j < 250; j++)       /* set some MSBs to 1 */

#ifdef TRUST_RAND

               if ( rand() > HALF_RANGE )

                       r250_buffer[j] |= MSB;

#else

               if ( randlcg() > HALF_RANGE )

                       r250_buffer[j] |= MSB;

#endif





       msb = MSB;              /* turn on diagonal bit */

       mask = ALL_BITS;        /* turn off the leftmost bits */



       for (j = 0; j < BITS; j++)

       {

               k = STEP * j + 3;       /* select a word to operate on */

               r250_buffer[k] &= mask; /* turn off bits left of the diagonal */

               r250_buffer[k] |= msb;  /* turn on the diagonal bit */

               mask >>= 1;

               msb  >>= 1;

       }



}



unsigned int r250()            /* returns a random unsigned integer */

{

       register int    j;

       register unsigned int new_rand;



       if ( r250_index >= 147 )

               j = r250_index - 147;   /* wrap pointer around */

       else

               j = r250_index + 103;



       new_rand = r250_buffer[ r250_index ] ^ r250_buffer[ j ];

       r250_buffer[ r250_index ] = new_rand;



       if ( r250_index >= 249 )        /* increment pointer for next time */

               r250_index = 0;

       else

               r250_index++;



       return new_rand;



}





double dr250()         /* returns a random double in range 0..1 */

{

       register int    j;

       register unsigned int new_rand;



       if ( r250_index >= 147 )

               j = r250_index - 147;   /* wrap pointer around */

       else

               j = r250_index + 103;



       new_rand = r250_buffer[ r250_index ] ^ r250_buffer[ j ];

       r250_buffer[ r250_index ] = new_rand;



       if ( r250_index >= 249 )        /* increment pointer for next time */

               r250_index = 0;

       else

               r250_index++;



       return (double)new_rand / ALL_BITS;



}



#ifdef MAIN



/* test driver prints out either NMR_RAND values or a histogram        */



#include <stdio.h>



#define NMR_RAND       5000

#define MAX_BINS       500



#ifdef NO_PROTO

void main(argc, argv)

int argc;

char **argv;

#else

void main(int argc, char **argv)

#endif

{

       int j,k,nmr_bins,seed;

       int bins[MAX_BINS];

       double randm, bin_inc;

       double bin_limit[MAX_BINS];



       if ( argc != 3 )

       {

               printf("Usage -- %s nmr_bins seed\n", argv[0]);

               exit(1);

       }



       nmr_bins = atoi( argv[1] );

       if ( nmr_bins > MAX_BINS )

       {

               printf("ERROR -- maximum number of bins is %d\n", MAX_BINS);

               exit(1);

       }



       seed = atoi( argv[2] );



       r250_init( seed );



       if ( nmr_bins < 1 )     /* just print out the numbers */

       {

               for (j = 0; j < NMR_RAND; j++)

                       printf("%f\n", dr250() );

               exit(0);

       }

       

       bin_inc = 1.0 / nmr_bins;

       for (j = 0; j < nmr_bins; j++)  /* initialize bins to zero */

       {

               bins[j] = 0;

               bin_limit[j] = (j + 1) * bin_inc;

       }



       bin_limit[nmr_bins-1] = 1.0e7;  /* make sure all others are in last bin

 */



       for (j = 0; j < NMR_RAND; j++)

       {

               randm = r250() / (double)ALL_BITS;

               for (k = 0; k < nmr_bins; k++)

                       if ( randm < bin_limit[k] )

                       {

                               bins[k]++;

                               break;

                       }

       }





       for (j = 0; j < nmr_bins; j++)

               printf("%d\n", bins[j]);

       

}



#endif



