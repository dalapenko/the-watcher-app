package tech.dalapenko.thewatcher.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import tech.dalapenko.thewatcher.databinding.FragmentCardBinding

class CardFragment : Fragment() {

    private val args: CardFragmentArgs by navArgs()

    private var _binding: FragmentCardBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCardBinding.inflate(inflater, container, false)
        binding.args = args

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}