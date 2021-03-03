package com.example.rickandmortycharacters.characterdetail

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.rickandmortycharacters.R
import com.example.rickandmortycharacters.BaseFragment
import com.example.rickandmortycharacters.databinding.FragmentCharacterDetailBinding
import com.example.rickandmortycharacters.model.EpisodeResponseModel
import com.example.rickandmortycharacters.model.Result
import com.example.rickandmortycharacters.model.UserModel
import com.example.rickandmortycharacters.utils.*
import okhttp3.ResponseBody
import org.koin.android.viewmodel.ext.android.viewModel

private const val CHARACTER_DETAIL_FRAGMENT_TAG = "CharDetailFraTag"

/**
 * A simple [Fragment] subclass.
 * Use the [CharacterDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@RequiresApi(Build.VERSION_CODES.M)
class CharacterDetailFragment : BaseFragment(), View.OnClickListener {

    //Navigation Component controller
    private lateinit var navController: NavController

    //ViewModel
    private val characterDetailViewModel by viewModel<CharacterDetailViewModel>()

    private var characterDetailsResponseModelLiveData =
        MutableLiveData<Result?>()

    private var episodeResponseModel = EpisodeResponseModel()

    private var selectedFavoriteFlag = false

    //View Binding
    // This property is only valid between onCreateView and
    // onDestroyView.
    private var _binding: FragmentCharacterDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            if (it.getSerializable(TRANSFER_SELECTED_CHARACTER) as Result? != null) {
                val characterDetailsResponseModel: Result =
                    it.getSerializable(TRANSFER_SELECTED_CHARACTER) as Result
                characterDetailsResponseModelLiveData.value = characterDetailsResponseModel
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCharacterDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUIInit()
        initObservers()
    }


    private fun initObservers() {
        characterDetailViewModel.updateFavoriteCharacterLiveData
            .observe(viewLifecycleOwner, _updateFavoriteCharacterObserver)
        characterDetailViewModel.getUserLiveData
            .observe(viewLifecycleOwner, _getUserModelObserver)
        characterDetailViewModel.getSingleEpisodeLiveData
            .observe(viewLifecycleOwner, _getSingleEpisodeObserver)
    }

    private fun setUIInit() {
        binding.IWFavoriteCharacterDetailItem.setOnClickListener(this)
        characterDetailsResponseModelLiveData.observe(viewLifecycleOwner, Observer { it ->
            if (it != null) {
                if(it.id == getFavoriteCharacterIdFromSharedPref()){
                    selectedFavoriteFlag = true
                    favoriteCharacter()
                }
                val options: RequestOptions = RequestOptions()
                    .centerCrop()
                    .placeholder(R.drawable.ic_baseline_account_circle_24)
                Glide.with(requireContext()).load(it.image).apply(options)
                    .into(binding.IWCharacterDetail)
                binding.TWCharacterItemDetailTitle.text = it.name
                binding.TWCharacterItemDetailStatus.text = it.status
                binding.TWCharacterItemDetailSpecifies.text = it.species
                binding.TWCharacterItemDetailNumberOfEpisodes.text = it.episode.size.toString()
                binding.TWCharacterItemDetailGender.text = it.gender
                binding.TWCharacterItemDetailOriginLocationName.text = it.origin.name
                binding.TWCharacterItemDetailLastKnownLocationName.text = it.location.name

                val lastEpisode = it.episode.last()
                val lastEpisodeNumber = lastEpisode.filter { lastEpisodeDigit-> lastEpisodeDigit.isDigit() }
                characterDetailViewModel.getSingleEpisode(lastEpisodeNumber.toInt())
            }
        })
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         * @return A new instance of fragment CharacterDetailFragment.
         */
        @JvmStatic
        fun newInstance() =
            CharacterDetailFragment().apply {
                arguments = Bundle().apply {}
            }
    }

    //Click Listeners
    override fun onClick(p0: View?) {
        p0?.let {
            when (it.id) {
                binding.IWFavoriteCharacterDetailItem.id -> actionRegisterFavoriteCharacter()
            }
        }
    }

    private fun actionRegisterFavoriteCharacter() {
        characterDetailsResponseModelLiveData.observe(
            viewLifecycleOwner,
            Observer { characterDetailsResponseModelLiveData ->
                characterDetailsResponseModelLiveData?.let { characterDetailsResponseModelLiveDataNonNull ->
                    getUserIdFromSharedPref()?.let { userIdNonNull ->
                        if(selectedFavoriteFlag){
                            selectedFavoriteFlag = false
                            characterDetailViewModel.updateFavoriteCharacter(
                                userIdNonNull,
                               0
                            )

                        } else {
                            characterDetailViewModel.updateFavoriteCharacter(
                                userIdNonNull,
                                characterDetailsResponseModelLiveDataNonNull.id
                            )
                            selectedFavoriteFlag = true
                        }
                    }
                }
            })

    }
    private fun favoriteCharacter() {
        if(selectedFavoriteFlag){
            binding.CLCharacterDetailItem.setBackgroundColor(
                requireContext().getColor(
                    R.color.greenLight
                )
            )
            binding.IWFavoriteCharacterDetailItem.setImageDrawable(
                requireActivity().getDrawable(
                    R.drawable.ic_baseline_grade_24
                )
            )
        } else {
            binding.CLCharacterDetailItem.setBackgroundColor(
                requireContext().getColor(
                    R.color.stroke
                )
            )
            binding.IWFavoriteCharacterDetailItem.setImageDrawable(
                requireActivity().getDrawable(
                    R.drawable.ic_outline_grade_24
                )
            )
        }

    }
    //Init Observers
    private val _updateFavoriteCharacterObserver = Observer<Resource<Boolean>> {
        when (it.status) {
            Status.ERROR -> {
                hideLoading()
                showToast(it.message)
            }
            Status.LOADING -> {
                showLoading()
            }
            Status.SUCCESS -> {
                hideLoading()
                it.data?.let { responseBodyNonNull ->
                    if (responseBodyNonNull) {
                        getUserIdFromSharedPref()?.let {
                            favoriteCharacter()
                            characterDetailViewModel.getUserById(it)
                        }

                    }
                    showToast(SUCCESS_MESSAGE)
                }
            }
        }
    }




    private val _getUserModelObserver = Observer<Resource<UserModel>> {
        when (it.status) {
            Status.LOADING -> showLoading()
            Status.ERROR -> {
                hideLoading()
                showToast(it.message)
            }
            Status.SUCCESS -> {
                it.data?.let { user ->
                    hideLoading()
                    Log.d(CHARACTER_DETAIL_FRAGMENT_TAG, it.data.toString())
                    //showToast(SUCCESS_MESSAGE)
                    setUserOnSharedPref(user)
                }
            }
        }
    }

    private val _getSingleEpisodeObserver = Observer<Resource< ResponseBody >> {
        when (it.status) {
            Status.LOADING -> showLoading()
            Status.ERROR -> {
                hideLoading()
                showToast(it.message)
            }
            Status.SUCCESS -> {
                it.data?.let { responseBodNonNull ->
                    hideLoading()
                    Log.d(CHARACTER_DETAIL_FRAGMENT_TAG, it.data.toString())

                    episodeResponseModel = IntentUtil.gson.fromJson(
                        responseBodNonNull.charStream(),
                        EpisodeResponseModel::class.java
                    )
                    binding.TWCharacterItemDetailLastSeen.text = episodeResponseModel.results[0].name
                    binding.TWCharacterItemDetailAirDate.text  = episodeResponseModel.results[0].airDate
                }
            }
        }
    }

}