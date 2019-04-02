const winston = require('winston')
const expressWinston = require('express-winston')
const consoleTransport = new winston.transports.Console({
    colorize: true
})
const winstonOptions = {
    transports: [consoleTransport],
    expressFormat: true,
    meta: false,
    format: winston.format.combine(winston.format.timestamp(), winston.format.prettyPrint())
}

// Logger to capture all requests and outputs them to the console
const requestLogger = expressWinston.logger(winstonOptions)
// Logger to capture any top-level errors and outputs them to the console
const errorLogger = expressWinston.errorLogger(winstonOptions)
winston.add(consoleTransport)

module.exports = {
    requestLogger: requestLogger,
    errorLogger: errorLogger

}
