#include "stdafx.h"
#include <iostream>
#include <fstream>
#include <list>
#include <iterator>
#include <string>
#include <assert.h>
#include "KList.h"

using namespace std;

void myInt::accept(Visitor & v)
{
	v.visit(this);
}

KList::KList(int length)
{
	first = 0;
	size = 0;
	capacity = length;
	items = new myInt[capacity];
	observers = new list<Observer*>;
}

KList::KList(const KList &cList)
{
	first = 0;
	size = cList.size;
	capacity = size;
	items = new myInt[size];
	observers = new list<Observer*>;
	for (int i = 0; i < size; i++)
		items[i] = cList.items[(cList.first + i) % size];
}

myInt KList::popFront()
{
	assert(size != 0);
	first = (first + 1) % capacity;
	size--;
	notify();
	int i = (first - 1) % capacity;
	if (i == -1)
		i += capacity;
	return items[i];
}

myInt KList::popBack()
{
	assert(size != 0);
	size--;
	notify();
	return items[(first + size + 1) % capacity];
}

bool KList::pushFront(const myInt & newElement)
{
	if (size < capacity)
	{
		first = (first - 1) % capacity;
		if (first == -1)
			first += capacity;
		size++;
		items[first] = newElement;
	}
	else
	{
		myInt *temp = new myInt[size + 1];
		for (int i = 1; i < size; i++)
			temp[i + 1] = items[(first + i) % capacity];
		capacity++;
		delete[] items;
		items = temp;
		first = 0;
		items[0] = newElement;
		size++;
	}
	notify();
	return false;
}

bool KList::pushBack(const myInt & newElement)
{
	if (size < capacity)
	{
		items[(first + size) % capacity] = newElement;
		size++;
	}
	else
	{
		myInt *temp = new myInt[size + 1];
		for (int i = 0; i < size; i++)
			temp[i] = items[(first + i) % capacity];
		capacity++;
		delete[] items;
		items = temp;
		first = 0;
		items[size] = newElement;
		size++;
	}
	notify();
	return false;
}

KListIterator KList::createIterator() const
{
	return KListIterator(this);
}


void KList::attach(Observer * o)
{
	observers->push_back(o);
}

void KList::detach(Observer * o)
{
	observers->remove(o);
}

void KList::notify()
{
	if (!observers->empty())
	{
		list<Observer*> ::iterator i;
		for (i = observers->begin(); i != observers->end(); advance(i, 1))
			(*i)->update(this);
	}
}


myInt & KListIterator::currentItem() const
{
	if (index < list->size)
	{
		return list->items[(list->first + index) % list->capacity];
	}
	else return list->items[(list->first + list->size) % list->capacity];
}

string KList::toString()
{
	ToStrVisitor strVisitor;
	KListIterator iterator(this);
	for (iterator.first(); !iterator.isDone(); iterator.next())
	{
		iterator.currentItem().accept(strVisitor);
	}
	return strVisitor.toString();
}

void ToStrVisitor::visit(myInt* el)
{
	str += to_string(el->get()) + " ";
}

int KList::min()
{
	MinVisitor minVisitor;
	KListIterator iterator(this);
	for (iterator.first(); !iterator.isDone(); iterator.next())
	{
		iterator.currentItem().accept(minVisitor);
	}
	return minVisitor.min();
}

void MinVisitor::visit(myInt* el)
{
	if (minval > el->get())
		minval = el->get();
}


Viewer::Viewer(KList *m, Controller *c)
{
	model = m;
	model->attach(this);
	controller = c;
}

void Viewer::update(KList* theChangedSubject)
{
	if (theChangedSubject == model)
		show();
}

void Viewer::show()
{
	system("cls");
	cout << "Структура: ";
	cout << model->toString() << endl;
	cout << "Минимум: ";
	cout << +model->min() << endl;
}

//void Viewer::enable()
//{
//	EnableWindow(popFrontButton, true);
//	EnableWindow(popBackButton, true);
//}
//
//void Viewer::disable()
//{
//	EnableWindow(popFrontButton, false);
//	EnableWindow(popBackButton, false);
//}

Controller::Controller(KList* m)
{
	model = m;
	view = new Viewer(m, this);
}

void Controller::push_front(string el)
{
	/*if (model->empty())
		view->enable();*/
	model->pushFront(myInt(stoi(el)));
}