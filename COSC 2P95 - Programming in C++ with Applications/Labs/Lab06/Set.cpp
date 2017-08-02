#include <sstream>
#include "Set.h"

// Integer Set Data Structure in C++
// Brock University, COSC 2P95 (Fall 2016) Lab Exercise 6
// Due: November 1, 2016
//
// Written by Matt Laidman (ml12ef, 5199807)
//
//
// Operations include:
//
// Operator     Name            Description
// ---------    -----           ------------
// A+B, A+b     Union           Set of all elements contained within A and/or B
// A^B, A^b     Intersection    Set of all elements common to both A and B
// A-B, A-b     Difference      Set of all elements contained within A, but not within B
// A[b]         Element         Test if b is a member of A
// A<=B         Subset          Test if all elements of A are included within B
// A>=B         Superset        Test if all elements of B are included within A
// A<B          Strict subset   Test if all elements of A are within B, but A!=B
// A>B          Strict superset Test if all elements of B are within A, but A!=B
// A==B         Equality        Test if both sets contain exactly the same elements
// A!=B         Inequality      Test if there exists an element within A or B not found in the other
// ~A           Complement      Set of all possible elements not found within A
// A()          Cardinality     Number of elements within A
// +A           Universe        Set of all possible elements
// -A           Empty set       Set containing no elements
// !A           Empty test      Test if set is empty
// ostream<<A   Output          Stream insertion 
// istream>>A   Input           Stream extraction
//
// Note:    Set is an implementation of the Set class in Set.h, provided with the LabExercise06 outline.
//          Set.h has not been modified.


// Default Constructor implementation
// Creates a Set with capacity 255
Set::Set() {
    Set(255);
}

// Constructor implementation
// Creates a Set with given capacity
Set::Set(short capacity) {
    this->capacity = capacity;
    for (int i = 0 ; i < capacity ; i++) elements[i] = false;
}

// Private Constructor implementation
// Creates a Set from given elements and capacity
Set::Set(const bool elements[255], int capacity) {
    for (int i = 0 ; i < capacity ; i++) this->elements[i] = elements[i];
    this->capacity = capacity;
}

// Union operator implementation
Set Set::operator+(const Set &other) {
    int ocap = other.capacity;
    int cap = capacity>ocap?capacity:ocap;
    int scap = capacity<ocap?capacity:ocap;
    Set set(elements, cap);
    for (int i = 0 ; i < scap ; i++) if (other.elements[i]) set = set+i;
    return set;
}

// Alternate Union operator implementation - "Add element"
Set Set::operator+(const int &other) {
    Set set(elements, capacity);
    set.elements[other] = true;
    return set;
}

// Difference operator implementation
Set Set::operator-(const Set &other) {
    int ocap = other.capacity;
    int cap = capacity>ocap?capacity:ocap;
    int scap = capacity<ocap?capacity:ocap;
    Set set(elements, cap);
    for (int i = 0 ; i < scap ; i++) if (other.elements[i]) set = set-i;
    return set;
}

// Alternate Difference operator implementation - "Remove element if present"
Set Set::operator-(const int &other) {
    Set set(elements, capacity);
    set.elements[other] = false;
    return set;
}

// Intersection operator implementation
Set Set::operator^(const Set &other) {
    int ocap = other.capacity;
    int cap = capacity>ocap?capacity:ocap;
    int scap = capacity<ocap?capacity:ocap;
    Set set(cap);
    for (int i = 0 ; i < scap ; i++) if (elements[i] && other.elements[i]) set = set+i;
    return set;
}

// Alternate Intersection operator implementation - "Intersection with element"
Set Set::operator^(const int &other) {
    Set set(capacity);
    set.elements[other] = true;
    return ((*this)^set);
}

// Complement operator implementation
Set Set::operator~() {
    return ((+(*this))-(*this));
}

// Set of Universe operator implementation
Set Set::operator+() {
    Set set(capacity);
    for (int i = 0 ; i < capacity ; i++) set = set+i;
    return set;
}

// Get Empty Set operator implementation
Set Set::operator-() {
    Set set(capacity);
    return set;
}

// Subset operator implementation
bool Set::operator<=(const Set &other) {
    for (int i = 0 ; i < capacity ; i++) if (elements[i] && !(other.elements[i])) return false;
    return true;
}

// Strict Subset operator implementation
bool Set::operator<(const Set &other) {
    return (((*this) <= other) && ((*this) != other));
}

// Superset operator implementation
bool Set::operator>=(const Set &other) {
    for (int i = 0 ; i < capacity ; i++) if (other.elements[i] && !(elements[i])) return false;
    return true;
}

// Strict Superset operator implementation
bool Set::operator>(const Set &other) {
    return (((*this) >= other) && ((*this) != other));
}

// Equality operator implementation
bool Set::operator==(const Set &other) {
    for (int i = 0 ; i < capacity ; i++) if (elements[i] != other.elements[i]) return false;
    return true;
}

// Inequality operator implementation
bool Set::operator!=(const Set &other) {
    for (int i = 0 ; i < capacity ; i++) if (elements[i] != other.elements[i]) return true;
    return false;
}

// Empty Set operator implementation
bool Set::operator!() {
    for (int i = 0 ; i < capacity ; i++) if (elements[i]) return false;
    return true;
}

// Cardinality operator implementation
int Set::operator()() {
    int c = 0;
    for (int i = 0 ; i < capacity ; i++) if (elements[i]) c++;
    return c;
}

// Member operator implementation - "Is element of""
bool Set::operator[](const int &pos) {
    return elements[pos];
}

// Stream Insertion operator implementation
// Prints "{x,y,...,z}"
std::ostream& operator<<(std::ostream &out, const Set &set) {
    out<<"{";
    bool flag = false;
    for (int i = 0 ; i < set.capacity ; i++) {
        if (set.elements[i]) {
            if (flag) out<<","; else flag = true;
            out<<i;
        }
    }
    out<<"}";
    return out;
}

// Stream Extraction operator implementation
// Earl's Code - Shamelessly stolen from the exercise outline
std::istream& operator>>(std::istream &in, Set &set) {
    bool arr[255];
    int cap=set.capacity;
    char open;
    in>>open;
    if (in.fail() || open!='{') {
        in.setstate(std::ios::failbit);
        return in;
    }
    for (int i=0;i<cap;i++) arr[i]=false;
    std::string buff;
    std::getline(in,buff,'}');
    std::stringstream ss(buff);
    std::string field;
    while (true) {
        std::getline(ss,field,',');
        if (ss.fail()) break;
        int el;
        std::stringstream se(field);
        se>>el;
        if (el>=0&&el<cap) arr[el]=true;
    }
    set=Set(arr,cap);
    return in;
}

// Get Cardinality of Universe operator implementation
int Set::getCapacity() {
    return capacity;
}