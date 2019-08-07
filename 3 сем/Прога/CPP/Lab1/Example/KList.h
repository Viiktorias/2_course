#include <iostream>
#include <fstream>
#include <list>
#include <iterator>

using namespace std;

template <class itype>
class Iterator;

template<class vtype>
class Visitor;

template<class otype>
class Observer;

template<class otype>
class Viewer;

template <class stype>
class Strategy;

template <class stype>
class Controller;

template <class type>
class KList
{
private:
	type *items = nullptr;
	int first;
	int size;
	int capacity;
	list<Observer<type>*> *observers;
public:
	KList(int length);
	KList(initializer_list<type> iList);
	KList(const KList<type> &cList);
	KList(KList<type> &&cList);
	KList<type>& operator = (const KList<type> &right);
	KList<type>& operator = (KList<type> &&right);
	~KList() { delete[] items; }
	KList<type> operator + (const KList<type> &right) const;
	KList<type>& operator += (const KList<type> &right);
	bool operator == (const KList &right) const;
	bool operator != (const KList &right) const;
	bool empty() const { return (size == 0) ? true : false; }
	int length() const { return size; }
	bool clear();
	bool popFront();
	bool popBack();
	bool swap(int first, int second);
	type& front() const { return items[first]; }
	type& back() const { return items[(first + size) % capacity]; }
	bool pushFront(const type &newElement);
	bool pushBack(const type &newElement);

	template <class ...Args>
	bool emplaceFront(Args&&... args);

	template<class ...Args>
	bool emplaceBack(Args&&... args);

	template <class type>
	friend istream& operator >> (istream &in, KList<type> &l);
	template <class type>
	friend ostream& operator << (ostream &out, const KList<type> &l);

	template <class itype>
	friend class KListIterator;
	KListIterator<type> createIterator() const { return KListIterator<type>(this); }

	void accept(Visitor<type>& v) { v.visitList(this); }

	void attach(Observer<type> *o) { observers->push_back(o); }
	void detach(Observer<type> *o) { observers->remove(o); }
	void notify();
};


template <class itype>
class Iterator
{
public:
	virtual ~Iterator() {}
	virtual void first() = 0;
	virtual void next() = 0;
	virtual bool isDone() const = 0;
	virtual itype currentItem() const = 0;
};

template <class itype>
class KListIterator : public Iterator<itype>
{
private:
	const KList<itype> *list;
	int index;
public:
	KListIterator(const KList<itype>* aList) { list = aList; }
	void first() override { index = 0; }
	void next() override { index++; }
	bool isDone() const override { return (index == list->size) ? true : false; }
	itype currentItem() const override;
};


template<class vtype>
class Visitor
{
public:
	virtual ~Visitor() {}
	virtual void visitList(KList<vtype>*) = 0;
};

template<class vtype>
class ToStrVisitor : public Visitor<vtype>
{
private:
	string str;
public:
	string toStr() const { return str; }
	void visitList(KList<vtype> *list) override;
};


template<class otype>
class Observer
{
public:
	virtual ~Observer() {}
	virtual void update(KList<otype>* theChangedSubject) = 0;
};

template<class otype>
class Viewer : public Observer<otype>
{
private:
	KList<otype> *model;
	Controller<otype> *controller;
	HWND inEdit;
	HWND outEdit;
	HWND pushButton;
	HWND popButton;
	HWND clearButton;
	void show();
public:
	Viewer(KList<otype> *m, Controller<otype> *c, HWND ie, HWND oe, HWND phb, HWND ppb, HWND cb);
	~Viewer() { model->detach(this); }
	void update(KList<otype>* theChangedSubject) override;
	void enable();
	void disable();
	void push();
	void pop();
	void clear();
};


template <class stype>
class Strategy
{
public:
	virtual ~Strategy() {}
};

template <class stype>
class Controller : public Strategy<stype>
{
private:
	KList<stype> *model;
	Viewer<stype> *view;
public:
	template<class ...Args>
	Controller(KList<stype> *m, Args&&... args);
	~Controller() override { delete view; }
	void pop();
	void push(string el);
	void clear();
	void translate(short param);
};

#include "KList.hpp"