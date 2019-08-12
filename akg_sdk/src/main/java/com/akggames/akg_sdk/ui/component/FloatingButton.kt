package com.akggames.akg_sdk.ui.component

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Point
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.view.View
import android.view.ViewTreeObserver
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.akggames.akg_sdk.*
import com.akggames.akg_sdk.animateFade
import com.akggames.akg_sdk.animateScale
import com.akggames.akg_sdk.dao.api.model.FloatingItem
import com.akggames.akg_sdk.ui.adapter.FloatingAdapter
import com.akggames.akg_sdk.ui.adapter.FloatingAdapterListener
import com.akggames.akg_sdk.updateLayoutParams
import com.akggames.akg_sdk.visible
import com.akggames.android.R
import de.hdodenhof.circleimageview.CircleImageView

class FloatingButton : FrameLayout {

    enum class ORIENTATION {
        HORIZONTAL,
        VERTICAL
    }

    enum class CircleAnchor {
        LEFT,
        RIGHT,
        TOP,
        BOTTOM
    }

    enum class Animation {
        NONE,
        FADE,
        SCALE,
    }

    var isFloating = false
        private set
    var isNavigating = false
        private set

    private lateinit var drawable: GradientDrawable
    private var orientation = ORIENTATION.HORIZONTAL
    private var circleAnchor = CircleAnchor.LEFT
    val circleIcon = CircleImageView(context)
    val recyclerView = RecyclerView(context)
    var autoNavigate = true
    var autoDip = true
    var duration = 350L

    var circleSize = 24f
        set(value) {
            field = value
            updateSubmarine()
        }
    var circleImage: Drawable? = null
        set(value) {
            field = value
            updateSubmarine()
        }
    var circlePadding = 4f
        set(value) {
            field = value
            updateSubmarine()
        }
    var circleBorderSize = 0f
        set(value) {
            field = value
            updateSubmarine()
        }
    var circleBorderColor = ContextCompat.getColor(context, R.color.red)
        set(value) {
            field = value
            updateSubmarine()
        }
    var radius = dp2Px(48).toFloat()
        set(value) {
            field = value
            updateSubmarine()
        }
    var color = ContextCompat.getColor(context, R.color.red)
        set(value) {
            field = value
            updateSubmarine()
        }
    var borderSize = 0f
        set(value) {
            field = value
            updateSubmarine()
        }
    var borderColor = ContextCompat.getColor(context, R.color.red)
        set(value) {
            field = value
            updateSubmarine()
        }
    var expandSize = context.displaySize().x - dp2Px(30)
        set(value) {
            field = value
            updateSubmarine()
        }
    var floatingButtonAnimation = Animation.NONE
        set(value) {
            field = value
            updateSubmarine()
        }

    private var adapter = FloatingAdapter()
    var floatingCircleClickListener: FloatingItemClickListener? = null
        set(value) {
            field = value
            circleIcon.setOnClickListener { value?.onCircleClick() }
        }
    var floatingAdapterListener: FloatingAdapterListener? = null
        set(value) {
            field = value
            adapter = FloatingAdapter(value)
            recyclerView.adapter = adapter
        }


    private fun getAttrs(attributeSet: AttributeSet) {
        val typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.FloatingButton)
        try {
            setTypeArray(typedArray)
        } finally {
            typedArray.recycle()
        }
    }

    private fun getAttrs(attributeSet: AttributeSet, defStyleAttr: Int) {
        val typedArray = context.obtainStyledAttributes(
            attributeSet,
            R.styleable.FloatingButton,
            defStyleAttr,
            0
        )
        try {
            setTypeArray(typedArray)
        } finally {
            typedArray.recycle()
        }
    }

    constructor(context: Context) : super(context)

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
        getAttrs(attributeSet)
    }

    constructor(context: Context, attributeSet: AttributeSet, defStyle: Int) : super(context, attributeSet, defStyle) {
        getAttrs(attributeSet, defStyle)
    }

    private fun setTypeArray(a: TypedArray) {
        when (a.getInt(R.styleable.FloatingButton_floating_button_orientation, 0)) {
            0 -> this.orientation = ORIENTATION.HORIZONTAL
            1 -> this.orientation = ORIENTATION.VERTICAL
        }
        when (a.getInt(R.styleable.FloatingButton_floating_button_circleAnchor, 0)) {
            0 -> this.circleAnchor = CircleAnchor.LEFT
            1 -> this.circleAnchor = CircleAnchor.RIGHT
            2 -> this.circleAnchor = CircleAnchor.TOP
            3 -> this.circleAnchor = CircleAnchor.BOTTOM
        }
        when (a.getInt(R.styleable.FloatingButton_floating_button_animation, 0)) {
            0 -> this.floatingButtonAnimation = Animation.NONE
            1 -> this.floatingButtonAnimation = Animation.FADE
            2 -> this.floatingButtonAnimation = Animation.SCALE
        }
        this.autoNavigate = a.getBoolean(R.styleable.FloatingButton_floating_button_autoNavigate, this.autoNavigate)
        this.autoDip = a.getBoolean(R.styleable.FloatingButton_floating_button_autoDip, this.autoDip)
        this.duration = a.getInt(R.styleable.FloatingButton_floating_button_duration, this.duration.toInt()).toLong()
        this.circleSize = a.getDimension(R.styleable.FloatingButton_floating_button_circleSize, this.circleSize)
        this.circleImage = a.getDrawable(R.styleable.FloatingButton_floating_button_circleDrawable)
        this.circlePadding =
            a.getDimension(R.styleable.FloatingButton_floating_button_circlePadding, this.circlePadding)
        this.circleBorderSize =
            a.getDimension(R.styleable.FloatingButton_floating_button_circleBorderSize, this.circleBorderSize)
        this.circleBorderColor =
            a.getColor(R.styleable.FloatingButton_floating_button_circleBorderColor, this.circleBorderColor)
        this.radius = a.getDimension(R.styleable.FloatingButton_floating_button_radius, this.radius)
        this.color = a.getColor(R.styleable.FloatingButton_floating_button_color, this.color)
        this.borderSize = a.getDimension(R.styleable.FloatingButton_floating_button_borderSize, this.borderSize)
        this.borderColor = a.getColor(R.styleable.FloatingButton_floating_button_borderColor, this.borderColor)
        this.expandSize =
            a.getDimension(R.styleable.FloatingButton_floating_button_expandSize, this.expandSize.toFloat()).toInt()
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        updateSubmarine()
    }

    /** updates submarine attributes. */
    private fun updateSubmarine() {
        updateSize()
        updateBackground()
        updateCircleIcon()
        updateChildViews()
        updateAnimation()
    }

    /** updates the submarine view's param sizes. */
    private fun updateSize() {
        this.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                viewTreeObserver.removeOnGlobalLayoutListener(this)
                updateLayoutParams {
                    width = dp2Px(circleSize)
                    height = dp2Px(circleSize)
                }
            }
        })
    }

    /** updates background and bordering. */
    private fun updateBackground() {
        drawable = ContextCompat.getDrawable(context, R.drawable.rectangle_layout) as GradientDrawable
        drawable.cornerRadius = radius
        drawable.setColor(color)
        if (borderSize != 0f) {
            drawable.setStroke(borderSize.toInt(), borderColor)
        }
        background = drawable
    }

    /** updates the circle icon. */
    private fun updateCircleIcon() {
        if (circleImage != null) {
            circleIcon.scaleType = ImageView.ScaleType.CENTER_CROP
            circleIcon.setImageDrawable(circleImage)
            circleIcon.setPadding(
                dp2Px(circlePadding),
                dp2Px(circlePadding),
                dp2Px(circlePadding),
                dp2Px(circlePadding)
            )
            circleIcon.borderWidth = circleBorderSize.toInt()
            circleIcon.borderColor = circleBorderColor
        }
    }

    /** updates the recyclerView and circle icon. */
    private fun updateChildViews() {
        removeAllViews()
        if (orientation == ORIENTATION.HORIZONTAL) {
            if (circleAnchor == CircleAnchor.LEFT) {
                addCircleImageView()
                addHorizontalRecyclerView()
                recyclerView.x = getCircleViewAllocation().toFloat()
            } else {
                addHorizontalRecyclerView()
                addCircleImageView()
            }
        } else {
            if (circleAnchor == CircleAnchor.TOP) {
                addCircleImageView()
                addVerticalRecyclerView()
                recyclerView.y = getCircleViewAllocation().toFloat()
            } else {
                addVerticalRecyclerView()
                addCircleImageView()
            }
        }
        invalidate()
    }

    /** updates the view attributes following animation. */
    private fun updateAnimation() {
        when (floatingButtonAnimation) {
            Animation.SCALE -> {
                scaleX = 0f
                scaleY = 0f
                visible(false)
            }
            Animation.FADE -> {
                alpha = 0f
                visible(false)
            }
            Animation.NONE -> {
                scaleX = 1f
                scaleY = 1f
                alpha = 1f
                visible(true)
            }
        }
    }

    /** draws circle icon view. */
    private fun addCircleImageView() {
        addView(circleIcon, dp2Px(circleSize), dp2Px(circleSize))
    }

    /** draws recyclerView horizontally. */
    private fun addHorizontalRecyclerView() {
        recyclerView.visible(false)
        recyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        recyclerView.adapter = adapter
        addView(recyclerView, getRecyclerViewSize(), dp2Px(circleSize))
    }

    /** draws recyclerView vertically. */
    private fun addVerticalRecyclerView() {
        recyclerView.visible(false)
        recyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        recyclerView.adapter = adapter
        addView(recyclerView, dp2Px(circleSize), getRecyclerViewSize())
    }

    /** floats the circle icon on the layout. */
    fun float() {
        if (!isFloating) {
            isFloating = true
            visible(true)

            when (floatingButtonAnimation) {
                Animation.SCALE -> {
                    animateScale(1.0f, 1.0f, duration / 2)
                        .doAfterAnimate { autoNavigate() }
                }
                Animation.FADE -> {
                    animateFade(1.0f, duration)
                        .doAfterAnimate { autoNavigate() }
                }
                Animation.NONE -> autoNavigate()
            }
        }
    }

    /** spreads the navigation views and listing items. */
    fun navigate() {
        if (!isNavigating) {
            isNavigating = true
            recyclerView.visible(true)
            beginDelayedTransition(duration)
            if (orientation == ORIENTATION.HORIZONTAL) {
                updateWidthParams(expandSize, radius)
                if (circleAnchor == CircleAnchor.RIGHT) {
                    circleIcon.translateX(duration, getRecyclerViewSize().toFloat())
                }
            } else if (orientation == ORIENTATION.VERTICAL) {
                updateHeightParams(expandSize, radius)
                if (circleAnchor == CircleAnchor.BOTTOM) {
                    circleIcon.translateY(duration, getRecyclerViewSize().toFloat())
                }
            }
        }
    }

    /** folds the navigation views and un-lists items. */
    fun retreat() {
        if (isNavigating) {
            isNavigating = false
            beginDelayedTransition(duration)

            if (orientation == ORIENTATION.HORIZONTAL) {
                updateWidthParams(dp2Px(circleSize), 1000f)

                circleIcon.translateX(duration, 0f)
                    .doAfterAnimate {
                        if (!isNavigating) {
                            recyclerView.visible(false)
                            autoDip()
                            circleIcon.visibility= View.VISIBLE
                        }
                    }
            } else if (orientation == ORIENTATION.VERTICAL) {
                updateHeightParams(dp2Px(circleSize), 1000f)
                circleIcon.translateY(duration, 0f)
                    .doAfterAnimate {
                        if (!isNavigating) {
                            recyclerView.visible(false)
                            autoDip()
                        }
                    }
            }
        }
    }

    /** dips the circle icon on the layer. */
    fun dip() {
        if (isFloating) {
            if (isNavigating && autoDip) {
                retreat()
            } else if (!isNavigating) {
                isFloating = false

                when (floatingButtonAnimation) {
                    Animation.SCALE -> {
                        animateScale(0f, 0f, duration / 2)
                            .doAfterAnimate {
                                scaleX = 0f
                                scaleY = 0f
                                visible(false)
                            }
                    }
                    Animation.FADE -> {
                        animateFade(.0f, duration)
                            .doAfterAnimate {
                                alpha = 0f
                                visible(false)
                            }
                    }
                    Animation.NONE -> visible(false)
                }
            }
        }
    }

    /** navigates if the [autoNavigate] attribute is ture. */
    private fun autoNavigate() {
        if (autoNavigate) {
            navigate()
        }
    }

    /** dips if the [autoDip] attribute is true. */
    private fun autoDip() {
        if (autoDip) {
            dip()
        }
    }

    /** adds a [SubmarineItem] to the navigation adapter. */
    fun addItem(floatingItem: FloatingItem) {
        adapter.addItem(getWrapperItem(floatingItem))
    }

    /** adds a [SubmarineItem] list to the navigation adapter. */
    fun addItems(floatingItems: List<FloatingItem>) {
        for (floattingItem in floatingItems) {
            adapter.addItem(getWrapperItem(floattingItem))
        }
    }

    /** removes a [SubmarineItem] to the navigation adapter. */
    fun removeItems(floatingItem: FloatingItem) {
        adapter.removeItem(getWrapperItem(floatingItem))
    }

    /** clears all of the [SubmarineItem] on the navigation adapter. */
    fun clearAllItems() {
        adapter.clearAllItems()
    }

    /** updates layout width params. */
    private fun updateWidthParams(value: Int, radius: Float) {
        updateLayoutParams {
            width = value
            drawable.cornerRadius = radius
        }
    }

    /** updates layout height params. */
    private fun updateHeightParams(value: Int, radius: Float) {
        updateLayoutParams {
            height = value
            drawable.cornerRadius = radius
        }
    }

    /** gets wrapped item [SubmarineItemWrapper]. */
    private fun getWrapperItem(floatingItem: FloatingItem): ItemWrapper {
        return ItemWrapper(floatingItem, 0, getRecyclerViewSize(), ORIENTATION.HORIZONTAL)
    }

    /** gets circle icon's size. */
    private fun getCircleViewAllocation(): Int {
        return dp2Px(circleSize)
    }

    /** gets recyclerView's size. */
    private fun getRecyclerViewSize(): Int {
        val params = layoutParams
        var width = expandSize - getCircleViewAllocation()
        if (params is MarginLayoutParams) {
            val margins = (params.leftMargin + params.rightMargin)
            val space = context.displaySize().x - expandSize - margins
            if (space < 0) width += space
        }
        return width
    }

    /** gets px size from the dp size. */
    private fun dp2Px(size: Int): Int {
        return context.dp2Px(size)
    }

    /** gets px size from the dp size. */
    private fun dp2Px(size: Float): Int {
        return context.dp2Px(size)
    }
}