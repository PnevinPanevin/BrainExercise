package com.sharipov.brainexercise.view.activity_test.expressions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.children
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.sharipov.brainexercise.R
import com.sharipov.brainexercise.mvp.OnBackPressedListener
import com.sharipov.brainexercise.mvp.TestView
import com.sharipov.brainexercise.presentation.ExpressionsPresenter
import com.sharipov.brainexercise.util.LockableRecyclerView
import com.sharipov.brainexercise.view.DialogManager
import kotlinx.android.synthetic.main.fragment_expressions.*
import kotlinx.android.synthetic.main.fragment_expressions.view.*
import kotlinx.android.synthetic.main.math_input.*
import kotlinx.android.synthetic.main.math_input.view.*
import kotlinx.android.synthetic.main.time_and_score.*


class ExpressionsFragment : MvpAppCompatFragment(), TestView, OnBackPressedListener {
    @InjectPresenter
    lateinit var presenter: ExpressionsPresenter

    private val dialogManager = DialogManager()

    private val buttonListener = View.OnClickListener {
        when (it.id) {
            R.id.button_0 -> onZeroPressed()
            R.id.button_1 -> onNumberPressed('1')
            R.id.button_2 -> onNumberPressed('2')
            R.id.button_3 -> onNumberPressed('3')
            R.id.button_4 -> onNumberPressed('4')
            R.id.button_5 -> onNumberPressed('5')
            R.id.button_6 -> onNumberPressed('6')
            R.id.button_7 -> onNumberPressed('7')
            R.id.button_8 -> onNumberPressed('8')
            R.id.button_9 -> onNumberPressed('9')
            R.id.button_backspace -> onBackspacePressed()
            R.id.button_return -> onReturnPressed()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(com.sharipov.brainexercise.R.layout.fragment_expressions, container, false)
            .apply {
                answerTextView.text = "0"
                gridLayout.children.forEach { it.setOnClickListener(buttonListener) }
                setupRecyclerView(recyclerView)
                dialogManager.onAttach(activity, presenter)
            }
    }

    private fun onReturnPressed() {
        presenter.checkAnswer(answerTextView.text.toString().toInt())
        answerTextView.text = "0"
    }

    private fun onNumberPressed(number: Char) = with(answerTextView) {
        text = when {
            text[0] == '0' -> "$number"
            text.length < 3 -> "$text$number"
            else -> text
        }
    }

    private fun onZeroPressed() {
        val answer = answerTextView.text
        if (answer != null && answer[0] != '0') onNumberPressed('0')
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
        layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        adapter = presenter.expressionsAdapter
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

    override fun scrollToBeginning() {
        recyclerView.scrollToPosition(0)
    }

    override fun scrollToPosition(position: Int) {
        recyclerView.smoothScrollToPosition(position)
    }

    override fun showPauseDialog(score: Int) = dialogManager.showPauseDialog(score)

    override fun showFinishDialog(score: Int) = dialogManager.showFinishDialog(score)

    override fun showLeaveDialog(score: Int) = dialogManager.showLeaveDialog(score)

    override fun onBackPressed() {
        presenter.onLeaveTest()
    }

    override fun onDetach() {
        super.onDetach()
        dialogManager.onDetach()
    }
}
