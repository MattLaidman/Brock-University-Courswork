#include <iostream>
//Reminder: http://en.cppreference.com/w/cpp/language/operators

class Set {
private:
	bool elements[255];
	int capacity; //Yes, an unsigned char, short, or even size_t, would be better
	Set(const bool elements[255], int capacity); //Helpful for immutable types
public:
	Set();
	Set(short capacity);
	
	Set operator+(const Set &other); //Union
	Set operator+(const int &other); //Technically union; effectively 'add element'
	Set operator-(const Set &other); //Difference
	Set operator-(const int &other); //Effectively 'remove element if present'
	Set operator^(const Set &other); //Intersection
	Set operator^(const int &other); //Intersection with element
	Set operator~(); //Complement
	
	Set operator+(); //Set of universe
	Set operator-(); //Empty set (with same capacity, of course)
	
	bool operator<=(const Set &other); //Subset
	bool operator<(const Set &other); //Strict subset
	bool operator>=(const Set &other); //Superset
	bool operator>(const Set &other); //Strict superset
	bool operator==(const Set &other); //Test for set equality
	bool operator!=(const Set &other); //Test for set inequality
	bool operator!(); //Test for empty set
	
	int operator()(); //Cardinality of set. i.e. |Set|
	bool operator[](const int &pos); //Test for 'is element of'
	
	friend std::ostream& operator<<(std::ostream &out, const Set &set);
	friend std::istream& operator>>(std::istream &in, Set &set);
	
	int getCapacity(); //Cardinality of universe. i.e. |Universe| (or just 'capacity')
};
