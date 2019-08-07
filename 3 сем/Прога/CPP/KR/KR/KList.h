#include <windows.h>
#include "resource.h"
#include <iostream>
#include <fstream>
#include <list>
#include <iterator>
#include <string>

using namespace std;

class Iterator;

class Visitor;

class ToStrVisitor;

class MinVisitor;

class Visitor;

class Observer;

class Viewer;

class Strategy;

class Controller;

class KList
{
private:
	string *items = nullptr;
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
	string popFront();
	string popBack();
	string& front() const { return items[first]; }
	string& back() const { return items[(first + size) % capacity]; }
	bool pushFront(const string &newElement);
	bool pushBack(const string &newElement);

	friend class KListIterator;
	KListIterator createIterator() const;

	void accept(Visitor& v);

	void attach(Observer *o);
	void detach(Observer *o);
	void notify();
};


class Iterator
{
public:
	virtual ~Iterator() {}
	virtual void first() = 0;
	virtual void next() = 0;
	virtual bool isDone() const = 0;
	virtual string& currentItem() const = 0;
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
	string& currentItem() const override;
};


class Visitor
{
public:
	virtual ~Visitor() {}
	virtual void visitList(KList*) = 0;
};

class ToStrVisitor : public Visitor
{
private:
	string str;
public:
	string toStr() const { return str; }
	void visitList(KList *list) override;
};

class MinVisitor : public Visitor
{
private:
	string str;
public:
	string minEl() const { return str; }
	void visitList(KList *list) override;
};

class MaxVisitor : public Visitor
{
private:
	string str;
public:
	string maxEl() const { return str; }
	void visitList(KList *list) override;
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
	HWND inEdit;
	HWND outEdit;
	HWND popFrontButton;
	HWND popBackButton;
	HWND pushButton;
	void show();
public:
	Viewer(KList *m, Controller *c, HWND ie, HWND oe, HWND pfb, HWND pbb, HWND pb);
	~Viewer() { model->detach(this); }
	void update(KList* theChangedSubject) override;
	void enable();
	void disable();
	void push();
	void popFront();
	void popBack();
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
	Controller(KList *m, HWND ie, HWND oe, HWND pfb, HWND pbb, HWND pb);
	~Controller() override { delete view; }
	void popFront();
	void popBack();
	void push(string el);
	void translate(short param);
};