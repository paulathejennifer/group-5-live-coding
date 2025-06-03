let cart = [];

function addItem(id, name, price, quantity = 1) {
    const item = { id, name, price, quantity };
    cart.push(item);
}

function removeItem(id) {
    cart = cart.filter(item => item.id !== id);
}

function updateItemQuantity(id, quantity) {
    const item = cart.find(item => item.id === id);
    if (item) {
        if (quantity <= 0) {
            removeItem(id);
        } else {
            item.quantity = quantity;
        }
    }
}

function calculateTotal() {
    return cart.reduce((total, item) => total + item.price * item.quantity, 0);
}
addItem(1, 'Laptop', 1000, 1);
addItem(2, 'Phone', 500, 2);
updateItemQuantity(2, 3); 
removeItem(1);
console.log('Total Price:', calculateTotal()); 
console.log('Cart Items:', cart); 
 