#include <windows.h>
#include "resource.h"
#include <iostream>
#include <fstream>
#include <list>
#include <iterator>
#include <string>
#include "KList.h"

using namespace std;



KList::KList(int length)
{
	first = 0;
	size = 0;
	capacity = length;
	items = new string[capacity];
	observers = new list<Observer*>;
}

KList::KList(const KList &cList)
{
	first = 0;
	size = cList.size;
	capacity = size;
	items = new string[size];
	observers = new list<Observer*>;
	for (int i = 0; i < size; i++)
		items[i] = cList.items[(cList.first + i) % size];
}

string KList::popFront()
{
	if (size != 0)
	{
		first = (first + 1) % capacity;
		size--;
		notify();
		int i = (first - 1) % capacity;
		if (i == -1)
			i += capacity;
		return items[i];
	}
	else return "";
}

string KList::popBack()
{
	if (size != 0)
	{
		size--;
		notify();
		return items[(first + size + 1) % capacity];
	}
	else return "";
}

bool KList::pushFront(const string & newElement)
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
		string *temp = new string[size + 1];
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

bool KList::pushBack(const string & newElement)
{
	if (size < capacity)
	{
		items[(first + size) % capacity] = newElement;
		size++;
	}
	else
	{
		string *temp = new string[size + 1];
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

void KList::accept(Visitor & v)
{
	v.visitList(this);
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


string & KListIterator::currentItem() const
{
	if (index < list->size)
	{
		return list->items[(list->first + index) % list->capacity];
	}
	else return list->items[(list->first + list->size) % list->capacity];
}


void ToStrVisitor::visitList(KList *list)
{
	str = "";
	KListIterator iterator(list);
	for (iterator.first(); !iterator.isDone(); iterator.next())
		str += iterator.currentItem() + "\r\n";
}


void MinVisitor::visitList(KList *list)
{
	str = "";
	unsigned int minLen = INT_MAX;
	KListIterator iterator(list);
	for (iterator.first(); !iterator.isDone(); iterator.next())
		if (iterator.currentItem().length() < minLen)
		{
			minLen = iterator.currentItem().length();
			str = iterator.currentItem();
		}
}

void MaxVisitor::visitList(KList *list)
{
	str = "";
	unsigned int maxLen = 0;
	KListIterator iterator(list);
	for (iterator.first(); !iterator.isDone(); iterator.next())
		if (iterator.currentItem().length() >= maxLen)
		{
			maxLen = iterator.currentItem().length();
			str = iterator.currentItem();
		}
}

Viewer::Viewer(KList *m, Controller *c, HWND ie, HWND oe, HWND pfb, HWND pbb, HWND pb)
{
	model = m;
	model->attach(this);
	controller = c;
	inEdit = ie;
	outEdit = oe;
	popFrontButton = pfb;
	popBackButton = pbb;
	pushButton = pb;
}

void Viewer::update(KList* theChangedSubject)
{
	if (theChangedSubject == model)
		show();
}

void Viewer::show()
{
	MinVisitor minVisitor;
	model->accept(minVisitor);
	MaxVisitor maxVisitor;
	model->accept(maxVisitor);
	ToStrVisitor toStrVisitor;
	model->accept(toStrVisitor);
	SetWindowText(outEdit, (minVisitor.minEl() + "\t" + maxVisitor.maxEl() + "\r\n" + toStrVisitor.toStr()).c_str());
}

void Viewer::enable()
{
	EnableWindow(popFrontButton, true);
	EnableWindow(popBackButton, true);
}

void Viewer::disable()
{
	EnableWindow(popFrontButton, false);
	EnableWindow(popBackButton, false);
}

void Viewer::push()
{
	char buf[21];
	GetWindowText(inEdit, buf, 21);
	controller->push(buf);
}

inline void Viewer::popFront()
{
	controller->popFront();
}

inline void Viewer::popBack()
{
	controller->popBack();
}


Controller::Controller(KList* m, HWND ie, HWND oe, HWND pfb, HWND pbb, HWND pb)
{
	model = m;
	view = new Viewer(m, this, ie, oe, pfb, pbb, pb);
}

void Controller::popFront()
{
	model->popFront();
	if (model->empty())
		view->disable();
}

void Controller::popBack()
{
	model->popBack();
	if (model->empty())
		view->disable();
}

void Controller::push(string el)
{
	if (model->empty())
		view->enable();
	model->pushBack(el);
}

void Controller::translate(short param)
{
	switch (param)
	{
	case IDPUSH:
	{
		view->push();
		break;
	}
	case IDPOPFRONT:
	{
		view->popFront();
		break;
	}
	case IDPOPBACK:
	{
		view->popBack();
		break;
	}
	}
}