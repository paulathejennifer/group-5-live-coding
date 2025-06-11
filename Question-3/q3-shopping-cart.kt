data class CartItem(val id: Int, val name: String, val price: Double, var quantity: Int)

var cart = mutableListOf<CartItem>()

fun addItem(id: Int, name: String, price: Double, quantity: Int = 1) {
    val item = CartItem(id, name, price, quantity)
    cart.add(item)
}

fun removeItem(id: Int) {
    cart = cart.filter { it.id != id }.toMutableList()
}

fun updateItemQuantity(id: Int, quantity: Int) {
    val item = cart.find { it.id == id }
    if (item != null) {
        if (quantity <= 0) {
            removeItem(id)
        } else {
            item.quantity = quantity
        }
    }
}

fun calculateTotal(): Double {
    return cart.sumOf { it.price * it.quantity }
}

fun main() {
    addItem(1, "Laptop", 1000.0, 1)
    addItem(2, "Phone", 500.0, 2)
    updateItemQuantity(3, 5)
    removeItem(1)
    println("Total Price: ${calculateTotal()}")
    println("Cart Items: $cart")
}
