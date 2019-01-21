package sanaebadi.info.customview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.DrawableRes
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.ContextCompat
import de.hdodenhof.circleimageview.CircleImageView

class CustomIndicator : CoordinatorLayout {

    private lateinit var parent: CoordinatorLayout
    private lateinit var image: CircleImageView
    private lateinit var enableView: View

    constructor(context: Context) : super(context) {
        initViews()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initViews()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initViews()
    }


    private fun initViews() {
        val view: View = LayoutInflater.from(context).inflate(
            R.layout.indicator_view_layout, this
            , true
        )

        parent = view.findViewById(R.id.parent)
        image = view.findViewById(R.id.image)
        enableView = view.findViewById(R.id.enableView)
    }

    fun setImage(@DrawableRes resId: Int) {
        image.setImageResource(resId)
    }

    fun setOnClickedListener(onClickListener: OnClickListener) {
        parent.setOnClickListener(onClickListener)
    }

    fun isEnableView(enable: Boolean) {
        if (enable) {
            enableView.background = ContextCompat.getDrawable(context, R.drawable.circle_view)
        } else {
            enableView.background = ContextCompat.getDrawable(context, R.drawable.circle_view_disable)
        }
    }
}