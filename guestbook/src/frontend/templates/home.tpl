<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Guestbook</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">

</head>
<body>
    <div class="container">
        <h1>
            <a href="/">
                My Guestbook
            </a>
        </h1>

        {% if error %}
        <div class="alert alert-danger" role="alert">
            <strong>ERROR:</strong> {{ error }}
        </div>
        {% endif %}

        <form class="form-inline" method="POST" action="/post">
            <label class="sr-only" for="name">Name</label>
            <div class="input-group mb-2 mr-sm-2">
                <div class="input-group-prepend">
                <div class="input-group-text">Your Name</div>
                </div>
                <input type="text" class="form-control" id="name" name="name" placeholder="">
            </div>
            <label class="sr-only" for="message">Message</label>
            <div class="input-group mb-2 mr-sm-2">
                <div class="input-group-prepend">
                <div class="input-group-text">Message</div>
                </div>
                <input type="text" class="form-control" id="message" name="message">
            </div>
            <button type="submit" class="btn btn-primary mb-2">Post to Guestbook</button>
        </form>

        {% for m in messages %}
        <div class="card my-3">
            <div class="card-body">
                <h5 class="card-title">{{m.Author}}</h5>
                <h6 class="card-subtitle mb-2 text-muted">{{m.Date}}</h6>
                <p class="card-text">
                    {{m.Message}}
                </p>
            </div>
        </div>
        {% endfor %}
    </div>
</body>
</html>
