struct Light {
	struct Vector *pos;
	float r, g, b;
};

struct Material {
	float r, g, b;
};
struct Intersection {
	struct Material *mat;
	struct Vector *pos;
};

struct Vector {
	float x, y, z;
};

struct Sphere {
	struct Vector *pos;
	struct Material *mat;
	int  radius;
	int diffuse;
	int specular;
};

struct Ray {
	struct Vector *start;
};

struct ThreadInfo {
	int xStart;
	int flag;
};

static void processPixel(int x, int y);
static void *processBlinds(void *td);

static struct Intersection *getIntersect (struct Vector *ray, struct Sphere *sphere, struct Vector *dir);
static struct Light *getDiff (struct Intersection *intersect, int sphereIndex);
static struct Light *getSpec (struct Intersection *intersect, int sphereIndex);

void initializeImage(int w, int h, float *i);
void processImage(int poolsize, int numSpheres, float *sphere, int numLights, float *light);