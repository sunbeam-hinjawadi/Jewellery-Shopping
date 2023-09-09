const mysql = require('mysql');
const connection = mysql.createConnection({
    user: 'root',
    password: 'manager',
    host: 'localhost',
    port: 3306,
    database: 'project',
});
connection.connect(err => {
    if (err) throw err;
    else {
        console.log('Database Connection Established Successfully!!!');
    }
});

module.exports = connection;
