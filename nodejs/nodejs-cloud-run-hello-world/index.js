const app = require('./app');
const pkg = require('./package');
const PORT = process.env.PORT || 8080;

app.listen(PORT, () => {
  console.log(
    'Hello from Cloud Run! The container started successfully and is listening for HTTP requests on $PORT'
  );
  console.log(`${pkg.name} listening on port ${PORT}`);
  console.log('Press Ctrl+C to quit.');
});
