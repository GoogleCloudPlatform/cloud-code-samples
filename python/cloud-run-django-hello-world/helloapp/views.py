from django.shortcuts import render
import os
import requests


def get_metadata(item_name):
    metadata_url = 'http://metadata.google.internal/computeMetadata/v1/'
    headers = {'Metadata-Flavor': 'Google'}

    try:
        r = requests.get(metadata_url + item_name, headers=headers)
        return r.text
    except:
        return 'Unavailable'


def homepage(request):
    project = get_metadata('project/project-id')
    service = os.environ.get('K_SERVICE', 'Unknown service')
    revision = os.environ.get('K_REVISION', 'Unknown revision')
    
    return render(request, 'homepage.html', context={
        "message": "It's running!",
        "Project": project,
        "Service": service,
        "Revision": revision,
    })

def aboutpage(request):
    return render(request, 'aboutpage.html', context={})