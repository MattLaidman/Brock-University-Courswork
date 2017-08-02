#include "ring.h"
#include <iostream>

struct Person {
    int age, height;
    Person(int age, int height) : age(age), height(height) {};
};

int main(int argc, char *argv[]) {

    Ring<char> cr;
    Ring<Person> pr;
    char a = 'a', b = 'b', c = 'c';
    Person pa(25, 180), pb(30, 185), pc(45, 170);    
    

    std::cout<<"Iterate through Ring of primitive types (char):"<<std::endl;
    

    cr.addBefore(a);
    cr.addAfter(b);
    cr.addBefore(c);
    std::cout<<cr.getCurrent()<<" ";
    cr.next();
    std::cout<<cr.getCurrent()<<" ";
    cr.next();
    std::cout<<cr.getCurrent()<<" ";
    cr.next();
    std::cout<<cr.getCurrent()<<" ";
    cr.next();
    std::cout<<cr.getCurrent()<<" ";
    cr.next();
    std::cout<<cr.getCurrent()<<" "<<std::endl<<std::endl;

    std::cout<<"Ring size: "<<cr.getSize()<<std::endl;
    std::cout<<"Removing \"current\"!"<<std::endl;
    cr.removeCurrent();
    std::cout<<"Ring size: "<<cr.getSize()<<std::endl;
    std::cout<<"Iterate backwards through same Ring:"<<std::endl;

    std::cout<<cr.getCurrent()<<" ";
    cr.previous();
    std::cout<<cr.getCurrent()<<" ";
    cr.previous();
    std::cout<<cr.getCurrent()<<" ";
    cr.previous();
    std::cout<<cr.getCurrent()<<" ";
    cr.previous();
    std::cout<<cr.getCurrent()<<" ";
    cr.previous();
    std::cout<<cr.getCurrent()<<" "<<std::endl<<std::endl;

    std::cout<<"Iterate through Ring of non-primitive types [\"Person (age, height)\" struct]:"<<std::endl;

    pr.addBefore(pa);
    pr.addAfter(pb);
    pr.addBefore(pc);
    std::cout<<pr.getCurrent().age<<" "<<pr.getCurrent().height<<std::endl;
    pr.next();
    std::cout<<pr.getCurrent().age<<" "<<pr.getCurrent().height<<std::endl;
    pr.next();
    std::cout<<pr.getCurrent().age<<" "<<pr.getCurrent().height<<std::endl;
    pr.next();
    std::cout<<pr.getCurrent().age<<" "<<pr.getCurrent().height<<std::endl;
    pr.next();
    std::cout<<pr.getCurrent().age<<" "<<pr.getCurrent().height<<std::endl;
    pr.next();
    std::cout<<pr.getCurrent().age<<" "<<pr.getCurrent().height<<std::endl<<std::endl;

    std::cout<<"Ring size: "<<pr.getSize()<<std::endl;
    std::cout<<"Removing \"current\"!"<<std::endl;
    pr.removeCurrent();
    std::cout<<"Ring size: "<<pr.getSize()<<std::endl;
    std::cout<<"Iterate backwards through same Ring:"<<std::endl;
    
    std::cout<<pr.getCurrent().age<<" "<<pr.getCurrent().height<<std::endl;
    pr.previous();
    std::cout<<pr.getCurrent().age<<" "<<pr.getCurrent().height<<std::endl;
    pr.previous();
    std::cout<<pr.getCurrent().age<<" "<<pr.getCurrent().height<<std::endl;
    pr.previous();
    std::cout<<pr.getCurrent().age<<" "<<pr.getCurrent().height<<std::endl;
    pr.previous();
    std::cout<<pr.getCurrent().age<<" "<<pr.getCurrent().height<<std::endl;
    pr.previous();
    std::cout<<pr.getCurrent().age<<" "<<pr.getCurrent().height<<std::endl<<std::endl;


    std::cout<<"Calling remove current 3 times on same Ring (std::runtime_error will be thrown!):"<<std::endl;

    std::cout<<"Ring size: "<<pr.getSize()<<std::endl;
    std::cout<<"Removing \"current\"!"<<std::endl;
    pr.removeCurrent();
    std::cout<<"Ring size: "<<pr.getSize()<<std::endl;
    std::cout<<"Removing \"current\"!"<<std::endl;
    pr.removeCurrent();
    std::cout<<"Ring size: "<<pr.getSize()<<std::endl;
    std::cout<<"Removing \"current\"!"<<std::endl;
    pr.removeCurrent();


    return 0;
}
