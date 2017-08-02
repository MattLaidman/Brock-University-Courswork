#pragma once
struct node {
	int digit;
	struct node *next;
};

struct node *add (struct node *head, int i);
struct node *unadd (struct node *head);