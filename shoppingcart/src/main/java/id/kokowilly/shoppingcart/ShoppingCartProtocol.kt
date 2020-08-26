package id.kokowilly.shoppingcart

import id.kokowilly.shoppingcart.entity.ShoppingCartEntity
import java.util.*
import java.util.concurrent.Executor
import java.util.concurrent.Executors

object ShoppingCartProtocol {
    private val executor = Executors.newSingleThreadExecutor()
    private val processor = CommandProcessor()

    internal var shoppingCart = ShoppingCartEntity()

    @Synchronized
    fun request(requestJson: String): ResponseChain {
        val chain = ResponseChain()

        executor.execute {
            val result = try {
                processor.compile(requestJson)
            } catch (e: Exception) {
                //log if necessary
                ""
            }
            chain.assignValue(result)
        }

        return chain
    }

}

class ResponseChain {
    lateinit var callback: (String) -> Unit
    lateinit var value: String

    // will be called in original thread
    fun then(callback: (String) -> Unit) {
        if (::value.isInitialized) {
            callback(value)
        } else {
            this.callback = callback
        }
    }

    internal fun assignValue(value: String) {
        if (::callback.isInitialized) {
            callback(value)
        } else {
            this.value = value
        }
    }
}
