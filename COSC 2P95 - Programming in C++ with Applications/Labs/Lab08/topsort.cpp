#include <iostream>
#include <stdexcept>
#include <queue>

#include "graph.h"

using namespace std;

void printGraph(Graph);
queue<string> topSort(Graph);

// COSC 2P95 (FALL 2016)
// Brock University

// Instructor: Earl Foxwell

// Lab Exercise 8

// Matt Laidman
// ml12ef, 5199807
 
// topsort.cpp

// Expects a file as the only command line argument.
// File is assumed to agree with spec Lab Exercise outline.

// Creates and performs a topological sort on the graph in the given file.

int main(int argc, char* argv[]) {
    if (argc != 2) {
        cout<<"Expected file.\n";
        return 0;
    }

    // try to create graph
    cout<<"Using: "<<argv[1]<<"\n";
    try {
        Graph graph(argv[1]);
        cout<<"Loaded graph.\n\n";
        printGraph(graph); // print out vertices and edges
        queue<string> q = topSort(graph); // get topsort
        if (q.empty()) { // if no top sort
            cout<<"Cyclic dependencies; no topological sort possible.\n";
        } else { // print out the topsort
            cout<<"Topological sort found!\n";
            while(!q.empty()) {
                cout<<q.front();
                q.pop();
                if (!q.empty()) {
                    cout<<" ";
                } else {
                    cout<<"\n";
                }
            }

        }
    } catch(runtime_error e) {
        cout<<e.what(); // file not found
    }

    return 0;
}

// topSort function
// perform the topological sorting algorithm on the graph
// returns a string queue of the valid sort
queue<string> topSort(Graph graph) {
    queue<string> q;
    int numVertices = graph.getNumVertices();
    int numVisited = 0;

    bool cycle = false;
    bool visited[numVertices];
    for (int i = 0 ; i < numVertices ; i++) {
        visited[i] = false;
    }

    cout<<"\n";
    while (!cycle && numVisited < numVertices) {
        for (int i = 0 ; i < numVertices ; i++) {
            if (!visited[i] && graph.getIndegree(i) == 0) { // 0 indegree?
                q.push(graph.getLabel(i)); // put vertex in queue
                visited[i] = true;
                numVisited++;
                for (int j = 0 ; j < numVertices ; j++) { // remove all outgoing edges
                    if (graph.hasEdge(i, j)) {
                        graph.removeEdge(i, j);
                    }
                }
            }
        }
        // Check if cycle exists
        for (int i = 0 ; i < numVertices ; i++) {
            if (!visited[i] && graph.getIndegree(i) == 0) {
                cycle = false;
                break;
            }
            if (numVisited != numVertices) {
                cycle = true;
            }
        }
    }
    if (cycle) { // if cycle, emoty the queue
        while(!q.empty()) {
            q.pop();
        }
    }
    return q;
}

// printGraph function
// Prints out the vertices and the edges in the graph.
void printGraph(Graph graph) {
    int numVertices = graph.getNumVertices();
    
    cout<<"Vertices:\n";
    for (int i = 0 ; i < numVertices ; i++) {
        cout<<"["<<i<<":"<<graph.getLabel(i)<<"]";
        if (i != numVertices - 1) {
            cout<<", ";
        }
        if ((i+1)%6 == 0) {
            cout<<"\n";
        }
    }
    
    bool hasEdges;
    cout<<"\nEdges:\n";
    for (int i = 0 ; i < numVertices ; i++) {
        hasEdges = false;
        for (int j = 0 ; j < numVertices ; j++) {
            if (graph.hasEdge(i, j)) {
                hasEdges = true;
                break;
            }
        }
        if (hasEdges) {
            cout<<graph.getLabel(i)<<" -> ";
            for (int j = 0 ; j < numVertices ; j++) {
                if (graph.hasEdge(i, j)) {
                    cout<<graph.getLabel(j)<<" ";
                }
            }
            cout<<"\n";
        }
    }

    return;
}