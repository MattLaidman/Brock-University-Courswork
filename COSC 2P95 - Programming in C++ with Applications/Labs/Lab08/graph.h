#ifndef GRAPH_H
#define GRAPH_H

#include <string>
#include <cstring>
#include <cstdlib>
#include <stdexcept>
#include <fstream>

using namespace std;

// COSC 2P95 (FALL 2016)
// Brock University

// Instructor: Earl Foxwell

// Lab Exercise 8

// Matt Laidman
// ml12ef, 5199807


// Graph class

// Creates a graph data structure from a given file.
// File is expected to agree with the spec in the lab exercise outline.

class Graph {
    private:
        int numVertices;
        int numEdges;
        string* labels;
        int* indegrees;
        bool** adjVertices;
    public:
        Graph(char*);
        Graph(const Graph&);
        Graph& operator= (const Graph&);
        int getNumVertices();
        int getNumEdges();
        int getIndegree(int);
        string getLabel(int);
        void removeEdge(int, int);
        bool hasEdge(int, int);
        ~Graph();
};


// Graph constructor
Graph::Graph(char* graph) {
    fstream in(graph, fstream::in);
    if (!in.is_open()) throw runtime_error("Unable to open file.");

    char temp [256];
    
    // get numVertices
    in.getline(temp, 256, '\n');
    numVertices = atoi(temp);

    // initialize private arrays
    labels = new string[numVertices];
    indegrees = new int[numVertices];
    adjVertices = new bool*[numVertices];
    for (int i = 0 ; i < numVertices ; i++) {
        adjVertices[i] = new bool[numVertices];
        labels[i] = "";
        indegrees[i] = 0;
        for (int j = 0 ; j < numVertices ; j++) {
            adjVertices[i][j] = false;
        }
    }

    for (int i = 0 ; i < numVertices ; i++) { // get labels
        if (i == numVertices-1) {
            in.getline(temp, 256, '\n');
        } else {
            in.getline(temp, 256, '\t');
        }
        labels[i] = temp;
    }

    // get numEdges
    in.getline(temp, 256, '\n');
    numEdges = atoi(temp);

    int a = 0, b = 0;

    // Fill adjacency matrix
    for (int i = 0 ; i < numEdges ; i++) {
        in.getline(temp, 256, '\t');
        a = atoi(temp);
        in.getline(temp, 256, '\n');
        b = atoi(temp);
        indegrees[b]++;
        adjVertices[a][b] = true;
    }
}

// Graph copy constructor
Graph::Graph(const Graph& graph) {
    numVertices = graph.numVertices;
    numEdges = graph.numEdges;

    labels = new string[numVertices];
    memcpy(labels, graph.labels, numVertices*sizeof(string));

    indegrees = new int[numVertices];
    memcpy(indegrees, graph.indegrees, numVertices*sizeof(int));

    adjVertices = new bool*[numVertices];
    for (int i = 0 ; i < numVertices ; i++) {
        adjVertices[i] = new bool[numVertices];
        memcpy(adjVertices[i], graph.adjVertices[i], numVertices*sizeof(bool));
    }
}

// Graph assignment operator
Graph& Graph::operator= (const Graph& graph) {
    if (this != &graph) {
        numVertices = graph.numVertices;
        numEdges = graph.numEdges;
        memcpy(labels, graph.labels, numVertices*sizeof(string));
        memcpy(indegrees, graph.indegrees, numVertices*sizeof(int));
        for (int i = 0 ; i < numVertices ; i++) {
            memcpy(adjVertices[i], graph.adjVertices[i], numVertices*sizeof(bool));
        }
    }
    return *this;
}

// getNumVertices function
int Graph::getNumVertices() {
    return numVertices;
}

// getNumEdges function
int Graph::getNumEdges() {
    return numEdges;
}

// getIndegree function
int Graph::getIndegree(int vertex) {
    if (vertex < numVertices) {
        return indegrees[vertex];
    }
    return -1;
}

// getLabel function
string Graph::getLabel(int vertex) {
    if (vertex < numVertices) {
        return labels[vertex];
    }
    return "";
}

// removeEdge funcrtion
void Graph::removeEdge(int from, int to) {
    if (from < numVertices && to < numVertices && adjVertices[from][to]) {
        indegrees[to]--;
        adjVertices[from][to] = false;
    }
    return;
}

// hasEdge function
bool Graph::hasEdge(int from, int to) {
    if (from < numVertices && to < numVertices) {
        return adjVertices[from][to];
    }
    return false;
}

// Graph destructor
Graph::~Graph() {
    for (int i = 0 ; i < numVertices ; i++) {
        delete[] adjVertices[i];
    }
    delete[] adjVertices;
    delete[] indegrees;
}

#endif