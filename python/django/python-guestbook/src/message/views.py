# Copyright 2023 Google LLC
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.

import dateutil.relativedelta

from django.shortcuts import render, redirect
from django.utils import timezone
from .models import Message

def _format_date(date_obj):
    """Format the datetime object in a human readable way."""
    now = timezone.now()
    rd = dateutil.relativedelta.relativedelta(now, date_obj)

    for n, unit in [(rd.years, 'year'), (rd.days, 'day'), (rd.hours, 'hour'),
                    (rd.minutes, 'minute')]:
        if n == 1:
            return '{} {} ago'.format(n, unit)
        elif n > 1:
            return '{} {}s ago'.format(n, unit)
    return 'just now'

def main(request):
    all_msgs = Message.objects.order_by('-id')
    msg_list = [
        {'author': msg.author, 'message': msg.content,
         'date': _format_date(msg.date)}
        for msg in all_msgs
    ]
    return render(request, 'home.html', {'messages': msg_list})

def post(request):
    message = Message.objects.create(
        author=request.POST['name'], content=request.POST['message'])
    message.save()
    return redirect('main')
