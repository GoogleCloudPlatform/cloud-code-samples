# Use base Python image from Docker Hub
FROM python:3.11-alpine

# Set the working directory to /app
WORKDIR /app

# copy the requirements file used for dependencies
COPY requirements.txt .

# Install any needed packages specified in requirements.txt
RUN pip install --trusted-host pypi.python.org -r requirements.txt

# Install debugpy for debugging
RUN pip install debugpy

# Copy the rest of the working directory contents into the container at /app
COPY . .

# Start the server when the container launches
CMD ["python", "manage.py", "runserver", "0.0.0.0:8080", "--noreload"]
