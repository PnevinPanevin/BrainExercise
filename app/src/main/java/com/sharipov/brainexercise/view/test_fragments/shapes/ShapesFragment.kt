package com.sharipov.brainexercise.view.test_fragments.shapes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.sharipov.brainexercise.R
import com.sharipov.brainexercise.interactor.ResultInteractor
import com.sharipov.brainexercise.model.firebase.TestResult
import com.sharipov.brainexercise.model.repository.Answer
import com.sharipov.brainexercise.mvp.TestView
import com.sharipov.brainexercise.presentation.NBackPresenter
import com.sharipov.brainexercise.util.LockableRecyclerView
import com.sharipov.brainexercise.view.DialogManager
import com.sharipov.brainexercise.view.test_details.TestDetailsFragment.Companion.HINT
import kotlinx.android.synthetic.main.fragment_shapes.*
import kotlinx.android.synthetic.main.fragment_shapes.view.*

class ShapesFragment : MvpAppCompatFragment(), TestView {
    @InjectPresenter
    lateinit var presenter: NBackPresenter

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

                presenter.testName = ResultInteractor.SHAPES
                hintTextView.text = arguments?.getString(HINT)
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.onPrepareTest()
    }

    private fun setupRecyclerView(recyclerView: LockableRecyclerView) = with(recyclerView) {
        val shapesAdapter = ShapesAdapter()
        presenter.testAdapter = shapesAdapter
        adapter = shapesAdapter
        layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        isLocked = true
        hasFixedSize()
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

    override fun scrollToBeginning() = recyclerView.scrollToPosition(0)

    override fun scrollToPosition(position: Int) = recyclerView.smoothScrollToPosition(position)

    override fun showPauseDialog(score: Int) = dialogManager.showPauseDialog(score)

    override fun showFinishDialog(result: TestResult) = dialogManager.showFinishDialog(result)

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
