var expect = require('chai').expect;
const chai = require('chai');
const chaiHttp = require('chai-http');

const PORT = process.env.PORT || 8080;
const HOST = 'http://localhost:'

chai.use(chaiHttp);

describe('get messages', () => {
    it('should load', (done) => {
        chai.request(HOST + PORT)
            .get('/messages')
            .end((err, res) => {
                const result = res.statusCode;
                console.log("should load123: " + res.body)
                expect(result).to.equal(200)
                done()
            });
    });
    it('should return messages', (done) => {
        chai.request(HOST + PORT)
            .get('/messages')
            .end((err, res) => {
                const result = res.body
                console.log("res: " + res.body)
                expect(result).length.greaterThan(0)
                result.forEach((message) => {
                    expect(message).include.keys('name', 'body')
                });
                done()
            });
    }) 
});

describe('post messages', () => {
    it('given empty message, should fail', (done) => {
        chai.request(HOST + PORT)
            .post('/messages').send({})
            .end((err, res) => {
                const result = res.statusCode;
                expect(result).to.equal(400);
                done();
            });
    });
    it('given valid message, should succeed', (done) => {
        chai.request(HOST + PORT)
            .post('/messages').send({name: 'test title', body: 'test body'})
            .end((err, res) => {
                const result = res.statusCode;
                expect(result).to.equal(200);
                done();
            });
    });
});

