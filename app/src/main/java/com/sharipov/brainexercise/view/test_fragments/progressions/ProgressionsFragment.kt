package com.sharipov.brainexercise.view.test_fragments.progressions


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
import com.sharipov.brainexercise.model.repository.ProgressionsRepository
import com.sharipov.brainexercise.mvp.TestView
import com.sharipov.brainexercise.presentation.BasePresenter
import com.sharipov.brainexercise.util.LockableRecyclerView
import com.sharipov.brainexercise.util.MaterialNumberpad
import com.sharipov.brainexercise.view.DialogManager
import com.sharipov.brainexercise.view.test_details.TestDetailsFragment
import kotlinx.android.synthetic.main.fragment_progressions.*
import kotlinx.android.synthetic.main.fragment_progressions.view.*

class ProgressionsFragment : MvpAppCompatFragment(), TestView {
    @InjectPresenter
    lateinit var presenter: BasePresenter

    private val dialogManager: DialogManager = DialogManager()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_progressions, container, false)
            .apply {
                answerTextView.text = "0"
                numPad.setOnNumpadClickListener {
                    when(it){
                        MaterialNumberpad.BUTTON_ACTION_DONE -> onReturnPressed()
                        MaterialNumberpad.BUTTON_ACTION_DELETE -> onBackspacePressed()
                        MaterialNumberpad.BUTTON_0 -> onZeroPressed()
                        else -> onNumberPressed(it)
                    }
                }
                setupRecyclerView(recyclerView)
                dialogManager.onAttach(activity, presenter)
                presenter.testName = ResultInteractor.PROGRESSIONS
                hintTextView.text = arguments?.getString(TestDetailsFragment.HINT)
            }
    }

    private fun onReturnPressed() {
        presenter.checkAnswer(answerTextView.text.toString())
        answerTextView.text = "0"
    }

    private fun onNumberPressed(number: Int) = with(answerTextView) {
        text = when {
            text[0] == '0' -> "$number"
            text.length < 3 -> "$text$number"
            else -> text
        }
    }

    private fun onZeroPressed() {
        val answer = answerTextView.text
        if (answer != null && answer[0] != '0') onNumberPressed(0)
    }

    private fun onBackspacePressed() {
        val answer = answerTextView.text
        if (answer.length == 1) answerTextView.text = "0"
        else answerTextView.text = answer.substring(0, answer.length - 1)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.onPrepareTest()
    }

    private fun setupRecyclerView(recyclerView: LockableRecyclerView) = with(recyclerView) {
        val progressionsAdapter = ProgressionsAdapter()
            .apply { progressions = ProgressionsRepository.getProgressions() }
        presenter.testAdapter = progressionsAdapter
        adapter = progressionsAdapter
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

    override fun showFinishDialog(result: TestResult) = dialogManager.showFinishDialog(result)

    override fun showLeaveDialog(score: Int) = dialogManager.showLeaveDialog(score)

    override fun onBackPressed() = presenter.onLeaveTest()

    override fun onPause() {
        super.onPause()
        presenter.onFragmentPause()
    }

    override fun onResume() {
        super.onResume()
        presenter.onFragmentResume()
    }

    override fun onDetach() {
        super.onDetach()
        dialogManager.onDetach()
    }
}
