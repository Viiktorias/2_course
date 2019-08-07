// Example.cpp: определяет точку входа для консольного приложения.
//
WORD w = 188;
string s = to_string(w);

#define _CRT_SECURE_NO_WARNINGS

#include <windows.h>
#include "resource.h"
#include "KList.h"
#include "Fraction.h"
#include <string>
using namespace std;

LRESULT CALLBACK WndProc(HWND, UINT, WPARAM, LPARAM);
KList<Fraction> *model = nullptr;
Controller<Fraction> *controller = nullptr;

int WINAPI WinMain(HINSTANCE hInstance, HINSTANCE hPrevInstance, LPSTR lpszCmdLine, int nCmdShow)
{
	HWND hWnd;
	MSG lpMsg;

	hWnd = CreateDialog(hInstance, MAKEINTRESOURCE(IDD_DIALOG1), NULL, (DLGPROC)WndProc);

	ShowWindow(hWnd, nCmdShow);
	UpdateWindow(hWnd);
	while (GetMessage(&lpMsg, NULL, 0, 0))
	{
		TranslateMessage(&lpMsg);
		DispatchMessage(&lpMsg);
	}
	return(lpMsg.wParam);
}

LRESULT CALLBACK WndProc(HWND hWnd, UINT messg, WPARAM wParam, LPARAM lParam)
{
	switch (messg)
	{
	case WM_INITDIALOG:
	{
		SetWindowText(GetDlgItem(hWnd, IDC_EDIT1), "1/2");
		model = new KList<Fraction>(10);
		controller = new Controller<Fraction>(model, GetDlgItem(hWnd, IDC_EDIT1), GetDlgItem(hWnd, IDC_EDIT2),
			GetDlgItem(hWnd, IDPUSH), GetDlgItem(hWnd, IDPOP), GetDlgItem(hWnd, IDCLEAR));
		break;
	}
	case WM_COMMAND:
	{
		switch (LOWORD(wParam))
		{
		case IDPUSH:
		case IDPOP:
		case IDCLEAR:
		{
			controller->translate(LOWORD(wParam));
			break;
		}
		}
		break;
	}
	case WM_DESTROY:
	{
		delete controller;
		delete model;
		PostQuitMessage(0);
		break;
	}
	default:
		return(DefWindowProc(hWnd, messg, wParam, lParam));
	}
	return 0;
}