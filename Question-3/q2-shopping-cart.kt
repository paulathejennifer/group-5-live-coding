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
