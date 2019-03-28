var expect = require('chai').expect;
const chai = require('chai');
const utils = require('./utils')


describe('simple', function () {
    it('should work!', function () {
        expect(true).to.be.true;
    });
});

describe('format time', function() {
    it('given valid utc time, should return correct time ago', function () {
        const utcTime = "2019-03-27T21:46:57.000Z"
        const currTime = "2019-03-27T21:46:60.000Z" 
        const result = utils.timeAgo(utcTime, currTime)
        const expectedResult = "a few seconds ago"
        expect(result).to.equal(expectedResult)
    });


});