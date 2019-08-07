// KR.cpp: определяет точку входа для консольного приложения.
//

#include "stdafx.h"
#include "KList.h"
#include <string>
using namespace std;


int main()
{
	setlocale(LC_ALL, "Russian");
	KList *model = new KList(10);
	Controller *controller = new Controller(model);
	controller->push_front("123");
	controller->push_front("4344");
	controller->push_front("4");
	system("pause");
	controller->push_front("1");
	controller->push_front("43");
	system("pause");
    return 0;
}

