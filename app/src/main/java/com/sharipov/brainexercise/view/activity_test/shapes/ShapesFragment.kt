package com.sharipov.brainexercise.view.activity_test.shapes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.sharipov.brainexercise.R
import com.sharipov.brainexercise.model.Answer
import com.sharipov.brainexercise.mvp.OnBackPressedListener
import com.sharipov.brainexercise.mvp.TestView
import com.sharipov.brainexercise.presentation.ShapesPresenter
import com.sharipov.brainexercise.view.DialogManager
import kotlinx.android.synthetic.main.fragment_shapes.*
import kotlinx.android.synthetic.main.fragment_shapes.view.*
import kotlinx.android.synthetic.main.time_and_score.*

class ShapesFragment : MvpAppCompatFragment(), TestView, OnBackPressedListener {

    @InjectPresenter
    lateinit var presenter: ShapesPresenter

    private val dialogManager = DialogManager()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_shapes, container, false)
            .apply {
                setupRecyclerView(recyclerView)
                buttonNo.setOnClickListener { presenter.checkAnswer(Answer.NO) }
                buttonYes.setOnClickListener { presenter.checkAnswer(Answer.YES) }
                dialogManager.onAttach(activity, presenter)
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.onPrepareTest()
    }

    private fun setupRecyclerView(recyclerView: RecyclerView) = with(recyclerView) {
        layoutManager = androidx.recyclerview.widget.LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        adapter = presenter.shapesAdapter
        hasFixedSize()
        setOnTouchListener { v, e -> true }
    }

    override fun updateCountDown(countsLeft: String) {
        countDownTextView.text = countsLeft
    }

    override fun setCountDownVisibility(visibility: Int) {
        countDownTextView.visibility = visibility
    }

    override fun updateTime(timeLeft: String) {
        timeLeftTextView.text = timeLeft
    }

    override fun updateScore(score: Int) {
        scoreTextView.text = score.toString()
    }

    override fun scrollToBeginning() {
        recyclerView.scrollToPosition(0)
    }

    override fun scrollToPosition(position: Int) {
        recyclerView.smoothScrollToPosition(position)
    }

    override fun showPauseDialog(score: Int) = dialogManager.showPauseDialog(score)

    override fun showFinishDialog(score: Int) = dialogManager.showFinishDialog(score)

    override fun showLeaveDialog(score: Int) = dialogManager.showLeaveDialog(score)

    override fun onPause() {
        super.onPause()
        presenter.onFragmentPause()
    }

    override fun onResume() {
        super.onResume()
        presenter.onFragmentResume()
    }

    override fun onBackPressed() {
        presenter.onLeaveTest()
    }

    override fun onDetach() {
        super.onDetach()
        dialogManager.onDetach()
    }
}
