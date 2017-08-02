#include <stdarg.h>

struct _item {
	void *item;
	struct _item *next;
};

struct _group {
	int size;
	struct _item *items;
	struct _group *next;
	int flag;
};

typedef struct _item Item;
typedef struct _group Group;

static void remG (Group *g);
static void clean (PoolQueue *q);
static Item *makeItem (void *item);
static void add (PoolQueue *queue, int count, ...);
static int has (Group *g, int c);
static int unadd (void **airlock, Group *g, int c, int i);
