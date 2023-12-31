const jwt = require('jsonwebtoken');
const config = require('../config');
const verifyToken = (req, res, next) => {
    const authHeader = req.headers.token;
    if (authHeader) {
        jwt.verify(token, config.secret, (err, user) => {
            if (err) res.status(403).json("Token is not valid");
            req.user = user;
            next();
        });
    } else {
        return res.status(401).json("You are not authenticated!!");
    }
};
const verifyTokenAndAuthorization = (req, res, next) => {
    verifyToken(req, res, () => {
        if (req.user.id === req.params.id) {
            next();
        } else {
            res.status(403).json("You are not alowed to do that!");
        }
    });
};

const verifyTokenAndAdmin = (req, res, next) => {
    verifyToken(req, res, () => {
        if (req.user.isAdmin) {
            next();
        } else {
            res.status(403).json("You are not alowed to do that!");
        }
    });
};

module.exports = {
    verifyToken,
    verifyTokenAndAuthorization,
    verifyTokenAndAdmin,
};
