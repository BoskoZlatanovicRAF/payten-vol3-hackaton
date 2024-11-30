package com.example.payten_windowsxp_userapp.Users.user.QR

import android.graphics.Bitmap
import android.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.payten_windowsxp_userapp.Users.user.QR.GenerateQRContract.GenerateQRState
import com.example.payten_windowsxp_userapp.auth.AuthStore
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.qrcode.QRCodeWriter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GenerateQRViewModel @Inject constructor(
    private val authStore: AuthStore,
): ViewModel(){
    private val _state = MutableStateFlow(GenerateQRState())
    val state = _state.asStateFlow()

    private fun setState(reducer: GenerateQRState.() -> GenerateQRState) = _state.update(reducer)

    init {
        loadFromDataStore();
    }

    private fun loadFromDataStore() {
        viewModelScope.launch {
            setState { copy(loading = true) }
            val authData = authStore.authData.value
            if(authData.firstname.isNotEmpty()){
                val qrCode = generateQrCode(
                    userId = authData.id,
                    membership = "Gold", // ili dobijeno iz podataka
                    discount = 0.1 // ili dobijeno iz podataka
                )
                setState {
                    copy(loading = false,
                        userId = authData.id,
                        qrBitmap = qrCode
                    )
                }
            } else {
                setState { copy(loading = false) }
            }
        }
    }

    private fun generateQrCode(userId: Long, membership: String, discount: Double): Bitmap? {
        return try {
            val qrContent = "USER_ID:$userId;MEMBERSHIP:$membership;DISCOUNT:$discount;"
            getQrCodeBitmap(qrContent)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun getQrCodeBitmap(qrContent: String): Bitmap {
        val size = 512 // pixels
        val hints = hashMapOf<EncodeHintType, Int>().also { it[EncodeHintType.MARGIN] = 1 } // QR margine
        val bits = QRCodeWriter().encode(qrContent, BarcodeFormat.QR_CODE, size, size, hints)
        return Bitmap.createBitmap(size, size, Bitmap.Config.RGB_565).also {
            for (x in 0 until size) {
                for (y in 0 until size) {
                    it.setPixel(x, y, if (bits[x, y]) Color.BLACK else Color.WHITE)
                }
            }
        }
    }


}