var expect = require('chai').expect;
const chai = require('chai');
const chaiHttp = require('chai-http');
const app = require('./app')
chai.use(chaiHttp);

describe('hello world', function () {
    it('should load', function (done) {
        chai.request(app)
            .get('/')
            .end(function (err, res) {
                const result = res.statusCode;
                expect(result).to.equal(200)
                done()
            });
    });
});
