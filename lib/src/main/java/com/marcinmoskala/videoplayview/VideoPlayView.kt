package com.marcinmoskala.videoplayview

import android.content.Context
import android.graphics.drawable.AnimationDrawable
import android.net.Uri
import android.util.AttributeSet
import android.util.Log
import android.view.KeyEvent
import android.view.KeyEvent.ACTION_DOWN
import android.view.KeyEvent.ACTION_UP
import android.view.MotionEvent
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.VideoView
import com.marcinmoskala.videoplayview.VideoPlayView.State.*
import java.lang.Math.*
import kotlin.properties.Delegates.observable

class VideoPlayView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0,
        defStyleRes: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    sealed class State {
        object Initial : State()
        object Loading : State()
        object Ready : State()
        object Playing : State()
        object Paused : State()
    }

    var state: State by observable(Initial as State) { _, prevState, state ->
        Log.i("VideoPlayView", "Changed state from $prevState to $state")
        loadingView.visibility = if (state is Loading) View.VISIBLE else View.GONE
        playView.visibility = if (state is Ready) View.VISIBLE else View.GONE
        imageView.visibility = if (state is Playing) View.GONE else View.VISIBLE
        when {
//            state1 is Playing && prevState is Paused -> videoView.resume()
            state is Playing -> videoView.start()
            state is Paused -> videoView.pause()
        }
    }

    var videoUrl: String? by observable(null as String?) { _, _, videoUrl ->
        require(!videoUrl.isNullOrBlank()) { "videoUrl is cannot be null or blank" }
        require(state is Initial) { "state must be Initial" }
        videoView.setVideoPath(videoUrl)
        videoView.requestFocus()
        if (state is Initial) {
            state = Loading
        }
        videoView.setOnPreparedListener {
            state = if (autoplay) Playing else Ready
        }
    }

    var looping: Boolean = false

    var autoplay: Boolean = false

    private val view by lazy { View.inflate(context, R.layout.view_video_play, null) }
    private val videoView: VideoView by view.bindView(R.id.videoView)
    val imageView: ImageView by view.bindView(R.id.imageView)
    val playView: ImageView by view.bindView(R.id.playView)
    val loadingView: ImageView by view.bindView(R.id.loadingView)

    init {
        val attrSet = context.theme.obtainStyledAttributes(attrs, R.styleable.VideoPlayView, defStyleAttr, defStyleAttr)
        try {
            looping = attrSet.getBoolean(R.styleable.VideoPlayView_loop, false)
            autoplay = attrSet.getBoolean(R.styleable.VideoPlayView_autoplay, false)
            attrSet.getDrawable(R.styleable.VideoPlayView_playButton)?.let(playView::setImageDrawable)
            attrSet.getDrawable(R.styleable.VideoPlayView_loadingButton)?.let(loadingView::setImageDrawable)
            attrSet.getText(R.styleable.VideoPlayView_videoUrl)?.toString()?.let { videoUrl = it }
        } finally {
            attrSet.recycle()
        }
        addView(view)
        videoView.touchBasedOnClick {
            when (state) {
                is Playing -> state = Paused
                is Paused -> state = Playing
            }
        }
        imageView.setOnClickListener {
            if (state is Paused || state is Ready) state = Playing
        }
        videoView.setOnCompletionListener {
            state = if (looping) Playing else Ready
        }
        playView.setOnClickListener {
            state = Playing
        }
        state = state
    }

    internal fun <T : View> View.bindView(viewId: Int) = lazy { findViewById<T>(viewId) }

    fun View.touchBasedOnClick(onClick: () -> Unit) {
        var actionDownTouch: MotionEvent? = null
        setOnTouchListener { v, e ->
            when (e.action) {
                ACTION_DOWN -> {
                    actionDownTouch = e
                }
                ACTION_UP -> {
                    actionDownTouch?.let { e2 ->
                        if (abs(e.x - e2.x) + abs(e.y - e2.y) < 30) {
                            onClick()
                        }
                    }
                }
            }
            true
        }
    }
}