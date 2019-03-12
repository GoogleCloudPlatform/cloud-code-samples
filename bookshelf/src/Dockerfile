FROM docker.io/python:3.6

# Install dependencies.
ADD requirements.txt /app/requirements.txt
RUN pip install -r /app/requirements.txt

# Install ptvsd for debugging
RUN pip install ptvsd

# Add application code.
ADD . /app

WORKDIR /app

# Instead of using gunicorn directly, we'll use Honcho. Honcho is a python port
# of the Foreman process manager. $PROCESSES is set in the pod manifest
# to control which processes Honcho will start.
CMD honcho start -f /app/procfile $PROCESSES