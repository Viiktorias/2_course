#pragma once
#include "stdafx.h"
#include <iostream>
#include <fstream>
#include <list>
#include <iterator>
#include <string>

using namespace std;

class KList;

class Iterator;

class Visitor;

class ToStrVisitor;

class MinVisitor;

class Visitor;

class Observer;

class Viewer;

class Strategy;

class Controller;

class myInt
{
private:
	int val;
public:
	myInt(int el) { val = el; }
	myInt() { val = 0; }
	int get() { return val; }
	void set(int el) { val = el; }
	void accept(Visitor& v);
};

class KList
{
private:
	myInt *items = nullptr;
	int first;
	int size;
	int capacity;
	list<Observer*> *observers;
public:
	KList(int length);
	KList(const KList &cList);
	~KList() { delete[] items; }
	bool empty() const { return (size == 0) ? true : false; }
	int length() const { return size; }
	myInt popFront();
	myInt popBack();
	myInt& front() const { return items[first]; }
	myInt& back() const { return items[(first + size) % capacity]; }
	bool pushFront(const myInt &newElement);
	bool pushBack(const myInt &newElement);

	friend class KListIterator;
	KListIterator createIterator() const;

	void attach(Observer *o);
	void detach(Observer *o);
	void notify();

	string toString();
	int min();
};


class Iterator
{
public:
	virtual ~Iterator() {}
	virtual void first() = 0;
	virtual void next() = 0;
	virtual bool isDone() const = 0;
	virtual myInt& currentItem() const = 0;
};

class KListIterator : public Iterator
{
private:
	const KList *list;
	int index;
public:
	KListIterator(const KList* aList) { list = aList; }
	void first() override { index = 0; }
	void next() override { index++; }
	bool isDone() const override { return (index == list->size) ? true : false; }
	myInt& currentItem() const override;
};


class Visitor
{
public:
	virtual ~Visitor() {}
	virtual void visit(myInt* el) = 0;
};

class ToStrVisitor : public Visitor
{
private:
	string str;
public:
	string toString() const { return str; }
	void visit(myInt* el) override;
};

class MinVisitor : public Visitor
{
private:
	int minval;
public:
	MinVisitor() { minval = INT_MAX; }
	int min() const { return minval; }
	void visit(myInt* el) override;
};


class Observer
{
public:
	virtual ~Observer() {}
	virtual void update(KList* theChangedSubject) = 0;
};

class Viewer : public Observer
{
private:
	KList *model;
	Controller *controller;
	/*HWND inEdit;
	HWND outEdit;
	HWND popFrontButton;
	HWND popBackButton;
	HWND pushButton;*/
	void show();
public:
	Viewer(KList *m, Controller *c);
	~Viewer() { model->detach(this); }
	void update(KList* theChangedSubject) override;
	/*void enable();
	void disable();*/
};


class Strategy
{
public:
	virtual ~Strategy() {}
};

class Controller : public Strategy
{
private:
	KList *model;
	Viewer *view;
public:
	Controller(KList *m);
	~Controller() override { delete view; }
	void push_front(string el);
};