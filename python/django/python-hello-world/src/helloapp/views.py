from django.shortcuts import render
import os
import requests


def homePageView(request):
    
    return render(request, 'homepage.html', context={
        "message": "It's running!"
    })