class ShoppingCart {
    private val cart: MutableMap<String, Item> = mutableMapOf()
    data class Item(var quantity: Int, var price: Double)
    fun addItem(itemName: String, quantity: Int, price: Double) {
        if (itemName in cart) {
            cart[itemName]!!.quantity += quantity
        } else {
            cart[itemName] = Item(quantity, price)
        }
    }
    fun removeItem(itemName: String, quantity: Int) {
        if (itemName in cart) {
            if (quantity >= cart[itemName]!!.quantity) {
                cart.remove(itemName)
            } else {
                cart[itemName]!!.quantity -= quantity
            }
        }
    }
    fun updateItem(itemName: String, quantity: Int, price: Double) {
        if (itemName in cart) {
            cart[itemName]!!.quantity = quantity
            cart[itemName]!!.price = price
        } else {
            println("Item '$itemName' not found in cart.")
        }
    }
    fun viewCart() {
        if (cart.isEmpty()) {
            println("Your shopping cart is empty.")
            return
        }
        println("Shopping Cart:")
        cart.forEach { (itemName, item) ->
            println("$itemName: Quantity: ${item.quantity}, Price: $${"%.2f".format(item.price)}")
        }
    }
}
fun main() {
    val cart = ShoppingCart()
    cart.addItem("Laptop", 1, 1200.0)
    cart.addItem("Mouse", 2, 25.0)
    cart.addItem("Laptop", 1, 1200.0)
    cart.viewCart()
    cart.removeItem("Mouse", 1)
    cart.updateItem("Laptop", 2, 1300.0)
    cart.viewCart()
    cart.removeItem("Laptop", 3)
    cart.viewCart()
}
//
//Initialize the Cart
//
//Create an empty map to store items. The key is the item name (String), and the value is an Item object (with quantity and price).
//Add Item (addItem)
//
//If the item already exists in the cart:
//Increase the item's quantity by the given amount.
//Else:
//Add the item to the cart with the given quantity and price.
//Remove Item (removeItem)
//
//If the item exists in the cart:
//If the given quantity is greater than or equal to the current quantity:
//Remove the item from the cart.
//Else:
//Decrease the item's quantity by the given amount.
//Update Item (updateItem)
//
//If the item exists in the cart:
//Set the item's quantity and price to the new given values.
//Else:
//Print an error message saying the item is not found.
//View Cart (viewCart)
//
//If the cart is empty:
//Print that the cart is empty.
//Else:
//For each item in the cart, print its name, quantity, and price.
//Calculate and print the total number of items and the total price.

class Shopping{
    private val crt= MutableMap<String,Item>=mutableMapOf()
    data class Item(var quantity: Int,var price: Double)
    fun add(itemName: String,quantity: Int,price: Double){
     if(itemName in crt){
         crt[itemName]!!.quantity +=quantity
     }
        else{
            crt[itemName]!!.quantity=Item(quantity,price)
     }
    }
    fun remove(itemName: String,quantity: Int){
        if(itemName in crt){
            if(quantity>=crt[itemName]!!.quantity){
                crt.remove(itemName)
            }
            else{
                crt[itemName]!!.quantity -=quantity
            }
        }
    }

    fun update(itemName: String,quantity: Int,price: Double){
        if(itemName in Cart){
            cart[itemName]!!.quantity=quantity
        }
    }
}
