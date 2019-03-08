package com.sharipov.brainexercise.view.activity_test.comparisons


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.sharipov.brainexercise.R
import com.sharipov.brainexercise.model.ComparisonAnswer
import com.sharipov.brainexercise.model.ComparisonsRepository
import com.sharipov.brainexercise.mvp.OnBackPressedListener
import com.sharipov.brainexercise.mvp.TestView
import com.sharipov.brainexercise.presentation.ComparisonsPresenter
import com.sharipov.brainexercise.view.DialogManager
import kotlinx.android.synthetic.main.count_down_text_view.*
import kotlinx.android.synthetic.main.fragment_comparisons.*
import kotlinx.android.synthetic.main.fragment_comparisons.view.*
import kotlinx.android.synthetic.main.time_and_score.*

class ComparisonsFragment : MvpAppCompatFragment(),
    TestView,
    OnBackPressedListener,
    ComparisonsAdapter.OnComparisionAnswerListener {

    @InjectPresenter
    lateinit var presenter: ComparisonsPresenter

    private val dialogManager: DialogManager = DialogManager()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_comparisons, container, false)
            .apply {
                val comparisonsAdapter = ComparisonsAdapter(fragmentManager)
                comparisonsAdapter.comparisons = ComparisonsRepository.getCardList()
                comparisonsAdapter.onAnswerListener = this@ComparisonsFragment

                viewPager.isLocked = true
                viewPager.adapter = comparisonsAdapter
                presenter.comparisonsAdapter = comparisonsAdapter

                dialogManager.onAttach(activity, presenter)
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.onPrepareTest()
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

    override fun scrollToBeginning() = viewPager.setCurrentItem(0, false)

    override fun scrollToPosition(position: Int) {
        viewPager.currentItem = position
    }

    override fun showPauseDialog(score: Int) = dialogManager.showPauseDialog(score)

    override fun showFinishDialog(score: Int) = dialogManager.showFinishDialog(score)

    override fun showLeaveDialog(score: Int) = dialogManager.showLeaveDialog(score)

    override fun onBackPressed() = presenter.onLeaveTest()

    override fun onAnswer(answer: ComparisonAnswer) = presenter.checkAnswer(answer)

    override fun onDestroy() {
        super.onDestroy()
        dialogManager.onDetach()
    }
}
