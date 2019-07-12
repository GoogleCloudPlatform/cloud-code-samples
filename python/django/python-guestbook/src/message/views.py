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
