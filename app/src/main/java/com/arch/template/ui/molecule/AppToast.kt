package com.arch.template.ui.molecule

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import com.arch.template.R
import com.arch.template.utils.extensions.toSp

class AppToast(
    context: Context?,
    message: String = "",
    textSize: Float = R.dimen.helper_text_size.toSp().toFloat(),
    textColor: Int = Color.BLACK,
    duration: Int = LENGTH_SHORT,
    backgroundColor: Int = Color.WHITE,
    imageBackground: Int? = null
) : Toast(context) {

    private var cvToastLayout: CardView
    private var tvToastMessage: TextView
    private var ivToastImage: ImageView

    init {
        val inflater: LayoutInflater =
            context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val layout = inflater.inflate(
            R.layout.custom_toast,
            (context as Activity).findViewById(R.id.cv_toast_layout)
        )

        cvToastLayout = layout.findViewById(R.id.cv_toast_layout)
        tvToastMessage = layout.findViewById(R.id.app_toast_message)
        ivToastImage = layout.findViewById(R.id.iv_toast_image)

        cvToastLayout.radius = 16.0F
        cvToastLayout.setCardBackgroundColor(backgroundColor)

        tvToastMessage.text = message

        tvToastMessage.textSize = textSize
        tvToastMessage.setTextColor(textColor)
        imageBackground?.let { image ->
            ivToastImage.setImageResource(image)
        }
        setGravity(Gravity.BOTTOM, 0, 100)
        setDuration(duration)

        view = layout
    }

}
