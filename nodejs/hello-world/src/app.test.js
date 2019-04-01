var expect = require('chai').expect;
const chai = require('chai');
const chaiHttp = require('chai-http');

chai.use(chaiHttp);

describe('simple', function () {
    it('should work!', function () {
        expect(true).to.be.true;
    });
});

describe('hello world', function () {
    it('should load', function (done) {
        const PORT = process.env.PORT || 8080;
        chai.request('http://localhost:' + PORT)
            .get('/')
            .end(function (err, res) {
                const result = res.statusCode;
                expect(result).to.equal(200)
                done()
            });
    });
});
