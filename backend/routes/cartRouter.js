const express = require('express');
const db = require('../db/dbConnect');
const utils = require('../utils/utils');
const router = express.Router();

router.get('/:customer_id', async (req, res) => {
    const { customer_id } = req.params;
    const statement2 = ` select c.cart_id,c.jewellery_id,c.price,j.jewellery_name,
    c.qty,jd.jewellery_image from cart c join jewellery j join jewellery_details jd
    where c.jewellery_id=j.jewellery_id and j.jewellery_id = jd.jewellery_id and c.customer_id=?`;
    await db.query(statement2, [customer_id], (err, result) => {
        res.send(utils.createResult(err, result));
    });
}); // check for android


router.post('/add', async (req, res) => {
    const { customer_id, jewellery_id, qty, price } = req.body;
    const statement1 = `select * from cart where customer_id = ? and jewellery_id = ?`;
    const statement2 = `insert into cart(customer_id, jewellery_id, qty, price) values (?,?,?,?)`;
    db.query(statement1, [customer_id, jewellery_id], (err, result) => {
        if (result.length === 0) {
            db.query(statement2, [customer_id, jewellery_id, qty, price], (err, result2) => {
                res.send(utils.createResult(err, result2));
            });
        } else {
            const oldQty = result[0].qty;
            const statement3 = `update cart set qty=? where customer_id=? and jewellery_id=?`;
            db.query(statement3, [oldQty + qty, customer_id, jewellery_id], (err, result3) => {
                const statement5 = `select qty from cart where customer_id=? and jewellery_id=?`;
                db.query(statement5, [customer_id, jewellery_id], (err, data) => {
                    const statement4 = `update cart set price=? where customer_id=? and jewellery_id=?`;
                    db.query(statement4, [data[0].qty * price, customer_id, jewellery_id], (err, result5) => {
                        res.send(utils.createResult(err, result5));
                    });
                });
            });
        }
    });
});
router.put('/quantity/:cart_id', (request, response) => {
    const { cart_id } = request.params;
    const { qty } = request.body;
    db.query(
        `update cart set qty = ? where cart_id = ?`,
        [qty, cart_id],
        (error, result) => {
            response.send(utils.createResult(error, result));
        }
    );
});

//
router.post('/nishantaddToCartItem/:customer_id', (request, response) => {
    const { customer_id } = request.params
    const { jewellery_id, qty } = request.body
    var myprice =0;
    db.query(
        "select jewellery_price from jewellery_details where jewellery_id = ?",[jewellery_id],
        (error,j_price)=>{
           myprice = j_price[0].jewellery_price
          }
      )
  
    // make sure that the product is not already added earlier
    db.query(
      `select * from cart where customer_id = ? and jewellery_id = ?`,
      [customer_id, jewellery_id],
      (error, items) => {
        
  
        if (items.length == 0) {
          // this product does not exist in the cart
          // insert the product in the cart
          console.log("jewellery_price new product: "+myprice);
          var dbprice = qty * myprice
                  db.query(
            `insert into cart (customer_id, jewellery_id, qty, price) values (?, ?, ?, ?)`,
            [customer_id, jewellery_id, qty, dbprice],
            (error, result) => {
              response.send(utils.createResult(error, result))
            }
          )
        } else {
          // this product is already added in the cart
          // update the product quantity
          const oldQuantity = items[0].qty
          
          var tempqty = oldQuantity + qty
          var dbprice = tempqty * myprice
  
          db.query(
            `update cart set qty = ? , price = ? where customer_id = ? and jewellery_id = ?`,
            [ tempqty,dbprice, customer_id, jewellery_id],
            (error, result) => {
              response.send(utils.createResult(error, result))
            }
          )
        }
      }
    )
  }); // added by nishant


router.put('/nishantupdateQuantity/:jewellery_id', (request, response) => {
    const { jewellery_id } = request.params;
    const { quantity ,customer_id , price } = request.body;
    db.query(
        `update cart set qty = ? , price = ? where jewellery_id = ? and customer_id =?`,
        [quantity,price, jewellery_id ,customer_id],
        (error, result) => {
            response.send(utils.createResult(error, result));
        }
    );
}); // 


router.get('/carTotal/:customer_id', async (req, res) => {
     const { customer_id } = req.params;
     const statament = `select cu.customer_id, sum(ca.price) as total_price from customer cu, cart ca where cu.customer_id = ca.customer_id group by customer_id having cu.customer_id = ?;
     `;
     await db.query(statament, [customer_id], (err, result) => {
        res.send(utils.createResult(err, result));
     });
 }); //

//


// router.delete('/delete/:cart_id', (request, response) => {
//     const { cart_id } = request.params;
//     db.query(`delete from cart where cart_id = ? `, [cart_id], (error, result) => {
//         response.send(utils.createResult(error, result));
//     });
// });


router.delete('/delete/:jewellery_id', (request, response) => {
    const { jewellery_id } = request.params;
    db.query(`delete from cart where jewellery_id = ? `, [jewellery_id], (error, result) => {
        response.send(utils.createResult(error, result));
    });
});//change to delete in android also inform niraj to add customer_id in where clause

//
router.post('/deleteCartItem/:jewellery_id', (request, response) => {
    const { jewellery_id } = request.params
     const { customer_id } = request.body;
    db.query(`delete from cart where jewellery_id = ? and customer_id = ?`, [jewellery_id ,customer_id], (error, result) => {
      response.send(utils.createResult(error, result))
    })
}); // 


//

router.delete('/empty-cart/:customer_id', (req, res) => {
    const { customer_id } = req.params;
    db.query(`delete from cart where customer_id = ? `, [customer_id], (error, result) => {
        res.send(utils.createResult(error, result));
    });
}); //add to andoird


router.get('/getqty/:customer_id', (req, res) => {
    const { customer_id } = req.params;
    const statement = `select cart_id,qty from cart where customer_id = ?`;
    db.query(statement, [customer_id], (err, result) => {
        res.send(utils.createResult(err, result));
    });
});

router.get('/totalprice/:customer_id', (req, res) => {
    const { customer_id } = req.params;
    const statement = 'select sum(price) as sum from cart where customer_id=? group by customer_id;';
    db.query(statement, [customer_id], (err, result) => {
        res.send(utils.createResult(err, result));
    });
});


router.put('/increase-qty-price/:jewellery_id', async (req, res) => {
    const { jewellery_id } = req.params;
    const { qty, price } = req.body;
    const statement1 = `update cart set qty = ?,price = ? where jewellery_id = ?`;
    await db.query(statement1, [qty, price, jewellery_id], (error, result1) => {
        res.send(utils.createResult(error, result1));
    });
});
module.exports = router;
