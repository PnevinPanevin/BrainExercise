package com.sharipov.brainexercise.view.test_fragments.comparisons


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
import com.sharipov.brainexercise.model.repository.ComparisonAnswer
import com.sharipov.brainexercise.mvp.TestView
import com.sharipov.brainexercise.presentation.BasePresenter
import com.sharipov.brainexercise.util.LockableRecyclerView
import com.sharipov.brainexercise.view.DialogManager
import kotlinx.android.synthetic.main.count_down_text_view.*
import kotlinx.android.synthetic.main.fragment_comparisons.*
import kotlinx.android.synthetic.main.fragment_comparisons.view.*
import kotlinx.android.synthetic.main.time_and_score.*

class ComparisonsFragment : MvpAppCompatFragment(),
    TestView,
    ComparisonsAdapter.OnComparisionAnswerListener {

    @InjectPresenter
    lateinit var presenter: BasePresenter

    private val dialogManager: DialogManager = DialogManager()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_comparisons, container, false)
            .apply {
                setupRecyclerView(recyclerView)
                dialogManager.onAttach(activity, presenter)
                presenter.testName = ResultInteractor.COMPARISONS
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.onPrepareTest()
    }

    private fun setupRecyclerView(recyclerView: LockableRecyclerView) = with(recyclerView) {
        val comparisonsAdapter = ComparisonsAdapter()
        comparisonsAdapter.onAnswerListener = this@ComparisonsFragment
        presenter.testAdapter = comparisonsAdapter
        adapter = comparisonsAdapter
        layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        isLocked = true
        hasFixedSize()
    }

    override fun updateTime(timeLeft: String) {
        timeLeftTextView.text = timeLeft
    }

    override fun updateScore(score: Int) {
        scoreTextView.text = score.toString()
    }

    override fun updateCountDown(countsLeft: String) {
        countDownTextView.text = countsLeft
    }

    override fun setCountDownVisibility(visibility: Int) {
        countDownTextView.visibility = visibility
    }

    override fun scrollToBeginning() = recyclerView.scrollToPosition(0)

    override fun scrollToPosition(position: Int) = recyclerView.smoothScrollToPosition(position)

    override fun showPauseDialog(score: Int) = dialogManager.showPauseDialog(score)

    override fun showFinishDialog(score: Int) = dialogManager.showFinishDialog(score)

    override fun showLeaveDialog(score: Int) = dialogManager.showLeaveDialog(score)

    override fun onBackPressed() = presenter.onLeaveTest()

    override fun onAnswer(answer: ComparisonAnswer) = presenter.checkAnswer(answer)

    override fun onDetach() {
        super.onDetach()
        dialogManager.onDetach()
    }

    override fun onPause() {
        super.onPause()
        presenter.onFragmentPause()
    }

    override fun onResume() {
        super.onResume()
        presenter.onFragmentResume()
    }
}
