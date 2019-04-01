const expect = require('chai').expect;
const utils = require('./utils')

describe('format time', () => {
    it('given valid utc time, should return correct time ago', () => {
        const utcTime = "2019-03-27T21:46:57.000Z"
        const currTime = "2019-03-27T21:46:59.000Z" 
        const result = utils.timeAgo(utcTime, currTime)
        const expectedResult = "a few seconds ago"
        expect(result).to.equal(expectedResult)
    });
});

describe('format messages', () => {
    it('given valid messages, should return valid updated messages', () => {
        const testMessages = [
            {
                "name": "testname",
                "body": "test message body",
                "timestamp": "2019-03-27T21:46:57.000Z" 
            }
        ]
        const result = utils.formatMessages(testMessages)
        console.log(result)
        expect(result.length).equal(1)
        expect(result[0]).include.keys('name', 'body', 'timestamp', 'timeAgo')
    });
});