var expect = require('chai').expect;
const chai = require('chai');
const chaiHttp = require('chai-http');
const app = require('./app.js')
const Message = require('./routes/messages')

const PORT = process.env.PORT || 8080;
const HOST = 'http://localhost:'

chai.use(chaiHttp);

describe('simple', function () {
    it('should work!', function () {
        expect(true).to.be.true;
    });
});
describe('root', function () {
    it('should load', function (done) {
        chai.request(HOST + PORT)
            .get('/')
            .end(function (err, res) {
                const result = res.statusCode;
                expect(result).to.equal(200)
                done()
            });
    });
});

describe('get messages', function () {
    it('should load', function(done) {
        chai.request(HOST + PORT)
            .get('/messages')
            .end(function(err, res){
                const result = res.statusCode;
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
})

// describe('create', function () {
//     it('given invalid params, throws exception', function(done) {
//         // const invalidMsg = {"a": "b"}
//         // expect(Message.create.bind(Message, {})).to.throw()
//         // Message.create({})
//         // console.log("result: " + result)
//         done()
//     });
//     it('given valid params, successfully creates', function(done) {
//         const result = Message.create({name: "test name", "body": "testing things"})
//         console.log("result: " + result)
//         done()
//     });
// });

// describe('getAll method', function() {
//     it('should return messages', function(done) {
//         const result = Message.getAll()
//         console.log("result : " + result)
//         // expect(result).length.above(0)
//         done()
//     });
// });
