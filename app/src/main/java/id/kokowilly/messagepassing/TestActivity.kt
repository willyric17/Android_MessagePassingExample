package id.kokowilly.messagepassing

import android.app.Activity
import android.os.Bundle
import android.widget.Toast
import id.kokowilly.shoppingcart.ShoppingCartProtocol
import kotlinx.android.synthetic.main.activity_test.*

class TestActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_test)

        submit.setOnClickListener {
            val dataText = data.text.toString()

            ShoppingCartProtocol.request(dataText)
                .then {
                    runOnUiThread {
                        Toast.makeText(baseContext, it, Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }
}