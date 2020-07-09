package com.felixfavour.pidgipedia.view.home

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.felixfavour.pidgipedia.BottomSheetFragment
import com.felixfavour.pidgipedia.R
import com.felixfavour.pidgipedia.databinding.FragmentEventstampBinding
import com.felixfavour.pidgipedia.entity.Comment
import com.felixfavour.pidgipedia.util.Connection.SUCCESS
import com.felixfavour.pidgipedia.util.Pidgipedia
import com.felixfavour.pidgipedia.viewmodel.EventstampViewModel

/**
 * A simple [Fragment] subclass.
 */
class EventstampFragment : Fragment() {
    private lateinit var binding: FragmentEventstampBinding
    private lateinit var eventstampViewModel: EventstampViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_eventstamp, container, false)
        eventstampViewModel = ViewModelProvider(this).get(EventstampViewModel::class.java)
        // EXTRACT SAFE ARG
        val eventStampArgument = EventstampFragmentArgs.fromBundle(requireArguments()).eventstamp


        // SET LIFECYCLE OWNER
        binding.lifecycleOwner = this


        // LOAD EVENTSTAMP VALUE FROM MOST RECENT PASSED FRAGMENT ARG
        eventstampViewModel.loadEventstamp(eventStampArgument)
        eventstampViewModel.loadComments(eventStampArgument.wordId)


        // BIND XML DATA
        binding.eventstampViewModel = eventstampViewModel


        // RECYCLER VIEW
        binding.commentsList.adapter = EventstampCommentsAdapter(object: CommentClickListener {
            override fun onProfileClick(view: View, comment: Comment) {
                findNavController().navigate(
                    EventstampFragmentDirections.actionEventstampFragmentToProfileFragment2(comment.authorId, true))
            }

            @SuppressLint("SetTextI18n")
            override fun onReplyClick(view: View, comment: Comment) {
                eventstampViewModel.replyComment(comment.authorId)
            }

            override fun onDeleteClick(view: View, comment: Comment) {
                eventstampViewModel.deleteComment(comment.commentId)
            }

            override fun onEditClick(view: View, comment: Comment) {
            }
        })


        // EVENT LISTENERS
        binding.more.setOnClickListener {
            val bundle = Bundle().apply {
                putParcelable(Pidgipedia.HOME_MODAL, eventStampArgument)
            }
            BottomSheetFragment().apply {
                arguments = bundle
                show(this@EventstampFragment.parentFragmentManager, Pidgipedia.EVENTSTAMP)
            }
        }


        binding.postComment.setOnClickListener {
            if (!binding.commentInput.text.isNullOrEmpty()) {

                eventstampViewModel.uploadComment(
                    binding.commentInput.text.toString(),
                    eventStampArgument.wordId
                )
                binding.commentInput.setText("")
            }
        }

        // OBSERVE LIVE DATA CHANGES
        eventstampViewModel.status.observe(viewLifecycleOwner, Observer { status ->
            // IF status is successful, it means reply has been clicked!
            if (status == SUCCESS) {
                val author = eventstampViewModel.humanEntity.value
                binding.commentInput.setText("@${author?.username}\n")
            }
        })


        return binding.root
    }

    override fun onResume() {
        super.onResume()
        // HIDE APP LOGO
        val activity = requireActivity() as AppCompatActivity
        val appLogoContainer = activity.findViewById<ConstraintLayout>(R.id.home_toolbar_container)
        appLogoContainer.visibility = View.GONE
    }

}
