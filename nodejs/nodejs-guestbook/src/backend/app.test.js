var expect = require('chai').expect;
const chai = require('chai');
const chaiHttp = require('chai-http');
const app = require('./app.js')
const Message = require('./routes/messages')

const PORT = process.env.PORT || 8080;
const HOST = 'http://localhost:'

chai.use(chaiHttp);

describe('get messages', function () {
    it('should load', function(done) {
        chai.request(HOST + PORT)
            .get('/messages')
            .end(function(err, res){
                const result = res.statusCode;
                console.log("should load123: " + res.body)
                expect(result).to.equal(200)
                done()
            });
    });
    it('should return messages', function(done) {
        chai.request(HOST + PORT)
            .get('/messages')
            .end(function(err, res){
                const result = res.body
                console.log("res: " + res.body)
                expect(result).length.greaterThan(0)
                result.forEach(function(message) {
                    expect(message).include.keys('name', 'body')
                });
                done()
            });
    }) 
});

describe('post messages', function () {
    it('given empty message, should fail', function(done) {
        chai.request(HOST + PORT)
            .post('/messages').send({})
            .end(function(err, res){
                const result = res.statusCode;
                expect(result).to.equal(400);
                done();
            });
    });
    it('given valid message, should succeed', function(done) {
        chai.request(HOST + PORT)
            .post('/messages').send({name: 'test title', body: 'test body'})
            .end(function(err, res){
                const result = res.statusCode;
                expect(result).to.equal(200);
                done();
            });
    });
});

