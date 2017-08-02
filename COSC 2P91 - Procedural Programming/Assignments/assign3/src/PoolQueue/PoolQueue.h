/*This file represents the interface for a Pool Queue.
 *Any function found in the implementation but not declared in this file should NOT be made available
 *to the linker!
 *
 *DO NOT MODIFY THIS FILE AT ALL
 *DO NOT EVEN ADD YOUR NAME OR STUDENT NUMBER TO IT
 *MODIFYING THIS FILE WILL RESULT IN AN INSTANT ZERO!
 */

//Since you could define your PoolQueue in any number of ways, this is just a front-end for the data structure
typedef struct {
	void *grip;
} PoolQueue;
//Note that 'grip' here is whatever you want it to be (as defined within PoolQueue.c)
//It could be a node pointer, a struct, etc.

//These add one or more items to a Pool Queue
//Items are added as void pointers, so the structure can be applied to anything
void PQaddSingle(void *item, PoolQueue *queue);
void PQaddPair(void *first, void *second, PoolQueue *queue);
void PQaddTriple(void *first, void *second, void *third, PoolQueue *queue);
void PQaddQuartet(void *first, void *second, void *third, void *fourth, PoolQueue *queue);

//If present (i.e. solveable), these load the requested number of records into a provided void pointer array
//Obviously, the client program might require a bit of coaxing, assuming the records have actual types
int PQremoveSingle(void **single, PoolQueue *queue); //Returns 0 if no single found
int PQremovePair(void **pair, PoolQueue *queue); //Returns 0 if no pair found
int PQremoveTriple(void **triple, PoolQueue *queue); //Returns 0 if no triple found
int PQremoveQuartet(void **quartet, PoolQueue *queue); //Returns 0 if no quartet found
//Technically, "not zero" is what they return when successful, but I'd suggest simply 1
//Note: if remove fails, the data structure is NOT changed! It returns a 0, and does nothing else

//These return 1 if the Pool Queue *could* return the requested amount
int PQhasSingle(PoolQueue *queue);
int PQhasPair(PoolQueue *queue);
int PQhasTriple(PoolQueue *queue);
int PQhasQuartet(PoolQueue *queue);

//Total # of items waiting (NOT # of groups!)
int PQcount(PoolQueue *queue);

//Creates a handle for the client program
//Allocates whatevever memory is necessary for an initial state
PoolQueue *PQcreatePoolQueue();

//Shorthand for clearing out a Pool Queue
//Frees both the pointer *and* its allocated contents!
void PQdisposeOfPoolQueue(PoolQueue *condemned);
//Note: Doesn't presume that the actual items added via PQaddSingle, etc. need to be freed
//i.e. if client is storing Students, this function does not free the memory for those students; only the wrappers
//Last note: To be clear, this isn't resetting the Pool Queue for use; it's deallocating even the handle itself
