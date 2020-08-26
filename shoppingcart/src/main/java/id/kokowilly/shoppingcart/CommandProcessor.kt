package id.kokowilly.shoppingcart

import com.google.gson.Gson
import id.kokowilly.shoppingcart.entity.ItemEntity
import id.kokowilly.shoppingcart.entity.ShoppingCartEntity

class CommandProcessor {
    private inline val formatError
        get() =
            IllegalArgumentException("command must be provided, with format `action:{\"format\":\"json\"}`")
    private val success = """{"success":1}"""
    private val gson = Gson()

    /**
     * @param command message from UI with format `action:{"something":"value", "withformat":"json"}`
     * @return return message in json format, or empty if error happened
     * @throws Exception
     */
    @Throws(Exception::class)
    fun compile(command: String): String {
        val (action, data) = extract(command)

        return when (action) {
            "addItem" -> addItem(data)
            "getShoppingCart" -> getShoppingCart()
            "reset" -> reset()
            else -> throw NoSuchMethodException("Action not found")
        }
    }

    private fun extract(command: String): Pair<String, String> {
        if (command.isEmpty()) throw formatError

        val separator = command.indexOf(':')
        if (separator <= 0) throw formatError

        val action = command.substring(0..separator -1)
        val data = command.substring(separator + 1)

        if (!data.startsWith('{') || !data.endsWith('}')) throw formatError

        return action to data
    }

    private fun getShoppingCart(): String {
        return gson.toJson(ShoppingCartProtocol.shoppingCart)
    }

    private fun reset(): String {
        ShoppingCartProtocol.shoppingCart = ShoppingCartEntity()

        return success
    }

    private fun addItem(jsonData: String): String {
        val item = gson.fromJson<ItemEntity>(jsonData)

        ShoppingCartProtocol.shoppingCart.items.add(item)

        return success
    }
}

inline fun <reified T> Gson.fromJson(json: String): T {
    return fromJson<T>(json, T::class.java)
}