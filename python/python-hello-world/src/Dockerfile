# Python image to use.
FROM docker.io/python:3.6

# Set the working directory to /app
WORKDIR /app

# Copy the current directory contents into the container at /app
COPY . .

# Install any needed packages specified in requirements.txt
RUN pip install --trusted-host pypi.python.org -r requirements.txt

# Install ptvsd for debugging
RUN pip install ptvsd

# Run app.py when the container launches
CMD ["python", "-m", "ptvsd", "--port", "3000", "--host", "0.0.0.0", "app.py"]