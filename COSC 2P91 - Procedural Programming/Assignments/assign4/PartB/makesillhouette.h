struct Vector {
	float x,y,z;
};

struct Sphere {
	struct Vector *pos;
	int  radius;
};

struct Ray {
	struct Vector *start;
} ;

struct ThreadInfo {
	int xStart;
	int flag;
};

static void processPixel(int x, int y);
static void *processBlinds(void *td);

void initializeImage(int w, int h, float *i);
void processImage(int poolsize, int numSpheres, float *sphere);

int hasIntersect (struct Ray *ray);
