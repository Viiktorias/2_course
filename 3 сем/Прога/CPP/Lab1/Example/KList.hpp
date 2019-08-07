template<class type>
KList<type>::KList(int length)
{
	first = 0;
	size = 0;
	capacity = length;
	items = new type[capacity];
	observers = new list<Observer<type>*>;
}

template<class type>
KList<type>::KList(initializer_list<type> iList)
{
	first = 0;
	size = iList.size();
	capacity = size;
	items = new type[size];
	observers = new list<Observer<type>*>;
	int i = 0;
	for (auto &item : iList)
	{
		items[i] = item;
		i++;
	}
}

template<class type>
KList<type>::KList(const KList<type> &cList)
{
	first = 0;
	size = cList.size;
	capacity = size;
	items = new type[size];
	observers = new list<Observer<type>*>;
	for (int i = 0; i < size; i++)
		items[i] = cList.items[(cList.first + i) % size];
}

template<class type>
KList<type>::KList(KList<type>&& cList)
{
	first = cList.first;
	size = cList.size;
	capacity = cList.capacity;
	items = cList.items;
	observers = new list<Observer<type>*>;
	cList.items = nullptr;
	cList.observers = nullptr;
	cList.capacity = 0;
	cList.size = 0;
	cList.first = 0;
}

template<class type>
istream & operator >> (istream & in, KList<type>& l)
{
	first = 0;
	size = capacity;
	for (int i = 0; i < capacity; i++)
		in >> items[i];
	notify();
	return in;
}

template<class type>
ostream & operator<<(ostream & out, const KList<type>& l)
{
	for (int i = first; i < size; i++)
	{
		out << items[(first + i) % capacity] << " ";
	}
	return out;
}

template<class type>
KList<type> KList<type>::operator+(const KList<type>& right) const
{
	KList<type> *temp = new KList<type>(size + right.size);
	int i = 0;
	for (int j = first; j < size; i++; j++;)
		temp->items[i] = items[j % capacity];
	for (int j = right.first; j < right.size; i++; j++;)
		temp.items[i] = right.items[j % right.capacity];
	temp->size = i;
	return *temp;
}

template<class type>
KList<type>& KList<type>::operator+=(const KList<type>& right)
{
	if (size + right.size > capacity)
	{
		type *temp = new type[size + right.size];
		for (int i = 0; i < size; i++)
			temp[i] = items[(first + i) % capacity];
		for (int i = 0; i < right.size; i++)
			temp[size + i] = right.items[(right.first + i) % right.capacity];
		delete[] items;
		items = temp;
		capacity = size + right.size;
		first = 0;
	}
	else
	{
		for (int i = 0; i < right.size; i++)
			items[(first + size + i) % capacity] = right.items[(right.first + i) % right.capacity];
	}
	size += right.size;
	notify();
	return *this;
}

template<class type>
KList<type>& KList<type>::operator=(const KList<type>& right)
{
	if (&right != this)
	{
		size = right.size;
		first = 0;
		if (right.size > capacity)
		{
			capacity = right.size;
			delete[] items;
			items = new type[capacity];
			for (int i = 0; i < size; i++)
				items[i % capacity] = right.items[(right.first + i) % right.capacity];

		}
		else
		{
			for (int i = 0; i < right.size; i++)
				items[i % capacity] = right.items[(right.first + i) % right.capacity];
			size = right.size;
		}
	}
	notify();
	return *this;
}

template<class type>
KList<type>& KList<type>::operator=(KList<type>&& right)
{
	if (&right != this)
	{
		first = cList.first;
		size = cList.size;
		capacity = cList.capacity;
		items = cList.items;
		cList.items = nullptr;
		cList.observers = nullptr;
		cList.capacity = 0;
		cList.size = 0;
		cList.first = 0;
		notify();
	}
	return *this;
}

template<class type>
bool KList<type>::operator==(const KList & right) const
{
	if (size != right.size)
		return false;
	else
	{
		for (int i = 0; i < size; i++)
			if (items[(first + i) % capacity] != right.items[(right.first + i) % right.capacity])
				return false;
	}
	return true;
}

template<class type>
bool KList<type>::operator!=(const KList & right) const
{
	return (&this == right) ? false : true;
}

template<class type>
bool KList<type>::clear()
{
	size = 0;
	notify();
	return false;
}

template<class type>
bool KList<type>::popFront()
{
	if (size != 0)
	{
		first = (first + 1) % capacity;
		size--;
		notify();
		return false;
	}
	else return true;
}

template<class type>
bool KList<type>::popBack()
{
	if (size != 0)
	{
		size--;
		notify();
		return false;
	}
	else return true;
}

template<class type>
bool KList<type>::swap(int first, int second)
{
	if ((first < size) && (second < size))
	{
		swap(items[(first + first) % capacity], items[(first + second) % capacity]);
		notify();
		return false;
	}
	else return true;
}

template<class type>
bool KList<type>::pushFront(const type & newElement)
{
	if (size < capacity)
	{
		first = (first - 1) % capacity;
		size++;
		items[first] = newElement;
	}
	else
	{
		type *temp = new type[size + 1];
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

template<class type>
bool KList<type>::pushBack(const type & newElement)
{
	if (size < capacity)
	{
		items[(first + size) % capacity] = newElement;
		size++;
	}
	else
	{
		type *temp = new type[size + 1];
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

template<class type>
template<class ...Args>
bool KList<type>::emplaceFront(Args && ... args)
{
	if (size < capacity)
	{
		first = (first - 1) % capacity;
		new(items + first) type(args ...);
		size++;
	}
	else
	{
		type *temp = new type[size + 1];
		for (int i = 1; i < size; i++)
			temp[i + 1] = items[(first + i) % capacity];
		capacity++;
		delete[] items;
		items = temp;
		first = 0;
		new(items) type(args ...);
		size++;
	}
	notify();
	return false;
}

template<class type>
template<class ...Args>
bool KList<type>::emplaceBack(Args && ... args)
{
	if (size < capacity)
	{
		new(items + (first + size) % capacity) type(args ...);
		size++;
	}
	else
	{
		type *temp = new type[size + 1];
		for (int i = 0; i < size; i++)
			temp[i] = items[(first + i) % capacity];
		capacity++;
		delete[] items;
		items = temp;
		first = 0;
		new(items + size) type(args ...);
		size++;
	}
	notify();
	return false;
}

template<class type>
void KList<type>::notify()
{
	if (!observers->empty())
	{
		list<Observer<type>*> ::iterator i;
		for (i = observers->begin(); i != observers->end(); advance(i, 1))
			(*i)->update(this);
	}
}


template<class itype>
itype KListIterator<itype>::currentItem() const
{
	if (index < list->size)
	{
		return list->items[(list->first + index) % list->capacity];
	}
	else return itype();
}


template<class vtype>
void ToStrVisitor<vtype>::visitList(KList<vtype> *list)
{
	str = "";
	KListIterator<vtype> iterator(list);
	for (iterator.first(); !iterator.isDone(); iterator.next())
		str += to_string(iterator.currentItem()) + " ";
}


template<class otype>
Viewer<otype>::Viewer(KList<otype> *m, Controller<otype> *c, HWND ie, HWND oe, HWND phb, HWND ppb, HWND cb)
{
	model = m;
	model->attach(this);
	controller = c;
	inEdit = ie;
	outEdit = oe;
	pushButton = phb;
	popButton = ppb;
	clearButton = cb;
}

template<class otype>
void Viewer<otype>::update(KList<otype>* theChangedSubject)
{
	if (theChangedSubject == model)
		show();
}

template<class otype>
void Viewer<otype>::show()
{
	ToStrVisitor<otype> visitor;
	model->accept(visitor);
	SetWindowText(outEdit, visitor.toStr().c_str());
}

template<class otype>
void Viewer<otype>::enable()
{
	EnableWindow(popButton, true);
	EnableWindow(clearButton, true);
}

template<class otype>
void Viewer<otype>::disable()
{
	EnableWindow(popButton, false);
	EnableWindow(clearButton, false);
}

template<class otype>
void Viewer<otype>::push()
{
	char buf[21];
	GetWindowText(inEdit, buf, 21);
	controller->push(buf);
}

template<class otype>
inline void Viewer<otype>::pop()
{
	controller->pop();
}

template<class otype>
inline void Viewer<otype>::clear()
{
	controller->clear();
}


template<class stype>
template<class ...Args>
Controller<stype>::Controller(KList<stype>* m, Args&&... args)
{
	model = m;
	view = new Viewer<stype>(m, this, args ...);
}

template<class stype>
void Controller<stype>::pop()
{
	model->popBack();
	if (model->empty())
		view->disable();
}

template<class stype>
void Controller<stype>::push(string el)
{
	if (model->empty())
		view->enable();
	model->emplaceBack(el);
}

template<class stype>
void Controller<stype>::clear()
{
	model->clear();
	view->disable();
}

template<class stype>
void Controller<stype>::translate(short param)
{
	switch (param)
	{
	case IDPUSH:
	{
		view->push();
		break;
	}
	case IDPOP:
	{
		view->pop();
		break;
	}
	case IDCLEAR:
	{
		view->clear();
		break;
	}
	}
}