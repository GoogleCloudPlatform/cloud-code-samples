from django.shortcuts import render
import os


def homePageView(request):
    
    return render(request, 'homepage.html', context={
        "message": "It's running!"
    })