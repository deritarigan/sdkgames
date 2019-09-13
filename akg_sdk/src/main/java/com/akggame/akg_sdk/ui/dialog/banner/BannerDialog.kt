package com.akggame.akg_sdk.ui.dialog.banner

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.widget.ImageView
import androidx.viewpager.widget.ViewPager
import com.akggame.akg_sdk.dao.api.model.response.BannerResponse
import com.akggame.akg_sdk.presenter.InfoPresenter
import com.akggame.akg_sdk.ui.adapter.BanerAdapter
import com.akggame.akg_sdk.ui.dialog.BaseDialogFragment
import com.akggame.akg_sdk.ui.dialog.menu.InfoDialog
import com.akggame.android.sdk.R
import kotlinx.android.synthetic.main.content_dialog_banner.*
import kotlinx.android.synthetic.main.content_dialog_banner.view.*
import java.util.*

class BannerDialog() : BaseDialogFragment(),BannerIView {


    private lateinit var mView: View
    private lateinit var adapter : BanerAdapter
    private lateinit var  presenter : InfoPresenter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mView = inflater.inflate(R.layout.content_dialog_banner, container, false)
        adapter = BanerAdapter(requireActivity())
        presenter = InfoPresenter(this)

        return mView
    }

    override fun onStart() {
        super.onStart()
        presenter.onGetBanner(requireActivity())
        initView()
    }

    fun initView() {
        mView.vpBaner.adapter = adapter
        mView.dots.setupWithViewPager(mView.vpBaner)

        mView.ib_close.setOnClickListener {
           dismiss()
        }
        setAutoSlide()
    }

    fun setAutoSlide(){
        val handler = Handler()
        var currentPage = 0
        mView.vpBaner.addOnPageChangeListener(object :ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageSelected(position: Int) {

            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
              currentPage = position
            }

        })
        val update = Runnable {
            if (mView.vpBaner.getCurrentItem() == (mView.vpBaner.getAdapter()?.getCount()!!.minus(1))) {
                currentPage = 0
            }
            mView.vpBaner.setCurrentItem(currentPage++, true)
        }

        val timer  =Timer()

        timer.schedule(object : TimerTask() {

            override fun run() {
                handler.post(update)
            }
        }, 0, 4000)

    }

    override fun doOnSuccess(data: BannerResponse) {
        if (data.data!!.size != 0) {
            flDialog.visibility = View.VISIBLE
            adapter.setData(data.data as MutableList<BannerResponse.DataBean>)
        } else {
            flDialog.visibility = View.GONE
            this.dismiss()
        }
    }

}