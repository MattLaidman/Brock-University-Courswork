#ifndef RING_H
#define RING_H

#include <stdexcept>

// Doubly-Linked Ring Data Structure in C++
// Brock University, COSC 4F00 (2016) Assignment 1
// Due October 17, 2016
//
// Written by Matt Laidman (ml12ef, 5199807)
//
// Operations include:
//
//      1)      Constructor     -   Creation of an empty Ring
//      2)      getSize         -   Get number of items in Ring
//      3)      addBefore       -   Add item before the 'current' pointer
//      4)      addAfter        -   Add item after the 'current' pointer
//      5)      getCurrent      -   Get 'current' item
//      6)      removeCurrent   -   Remove 'current' item
//      7)      next            -   Move 'current' pointer to the next item
//      8)      previous        -   Move 'current' pointer to the previous item
//      9)      isEmpty         -   Check if the Ring is empty
//      10)     makeEmpty       -   Remove all items from the Ring
//
// Note: std::runtime_error will be thrown if getCurrent() or
//       removeCurrent() is called on a Ring of size 0. 


// Ring Definition:

template <typename T> class Ring {
    struct RingNode;
    RingNode *current; // the current item
    int size; // the number of items in Ring
public:
    Ring(); // create empty Ring
    int getSize(); // get size of Ring
    void addBefore(T); // add item before current
    void addAfter(T); // add item after current
    T getCurrent(); // get current item
    void removeCurrent(); // remove  current item
    void next(); // move current to next item
    void previous(); // move current to previous item
    bool isEmpty(); // check if Ring is empty
    void makeEmpty(); // remove all items from Ring
    ~Ring(); // destructor
};


// Implementation of RingNode struct

template <typename T> struct Ring<T>::RingNode { // RingNode object
    T item; // the item
    RingNode* next; // the next RingNode in the Ring
    RingNode* previous; // the previous RingNode in the Ring
    RingNode(T item) : item(item) {} // RingNode Constructor
};


// Implementation of Ring Functions

// Constructor
// Create empty Ring
template <typename T> Ring<T>::Ring() {
    size = 0;
}

// Get size of Ring
template <typename T> int Ring<T>::getSize() {
    return size;
}

// Add item before current
template <typename T> void Ring<T>::addBefore(T i) {
    RingNode* t = new RingNode(i); // create a new RingNode
    if (size == 0) { // if first item
        current = t;
        current->next = current;
        current->previous = current;
    } else { // else not first item
        current->previous->next = t; // insert between current and previous
        t->previous = current->previous;
        t->next = current;
        current->previous = t;
    }
    size++;
    return;
}

// Add item after current
template <typename T> void Ring<T>::addAfter(T i) {
    RingNode* t = new RingNode(i); // create new RingNode
    if (size == 0) { // if first item
        current = t;
        current->next = current;
        current->previous = current;
    } else { // else not first item
        current->next->previous = t; // insert between current and next
        t->next = current->next;
        t->previous = current;
        current->next = t;
    }
    size++;
    return;
}

// Get current item
template <typename T> T Ring<T>::getCurrent() {
    if (size == 0) throw std::runtime_error("Cannot call function getCurrent() from Ring of size 0"); // if size 0 no current
    return current->item; // otherwise return the item
}

// Remove current item
template <typename T> void Ring<T>::removeCurrent() {
    if (size == 0) throw std::runtime_error("Cannot call function removeCurrent() from Ring of size 0"); // if size 0 no current
    if (size == 1) { // if size 1
        delete current;
    } else { // else more than 1 in Ring
        RingNode* t = current; // temp to keep track of current
        current->next->previous = current->previous; // point 'around' current
        current->previous->next = current->next;
        current = current->next;
        delete t;
    }
    size--;
    return;
}

// Move current to next item
template <typename T> void Ring<T>::next() {
    if (size < 2) return; // if size 0 or 1 no next
    current = current->next;
    return;
}

// Move current to previous item
template <typename T> void Ring<T>::previous() {
    if (size < 2) return; // if size 0 or 1 no next
    current = current->previous;
    return;
}

// Check if Ring is empty
template <typename T> bool Ring<T>::isEmpty() {
    return size == 0;
}

// Remove all items from the Ring
template <typename T> void Ring<T>::makeEmpty() {
    if (size == 0) return;
    for (int i = 0 ; i < size ; i++) {
        this->removeCurrent();
    }
    return;
}

// Destructor
// Destroys the ring
template <typename T> Ring<T>::~Ring() {
    makeEmpty();
}

#endif