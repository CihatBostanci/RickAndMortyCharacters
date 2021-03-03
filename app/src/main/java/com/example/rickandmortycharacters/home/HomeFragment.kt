package com.example.rickandmortycharacters.home

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rickandmortycharacters.databinding.FragmentHomeBinding
import com.example.rickandmortycharacters.R
import com.example.rickandmortycharacters.BaseFragment
import com.example.rickandmortycharacters.extensions.hide
import com.example.rickandmortycharacters.extensions.show
import com.example.rickandmortycharacters.home.adapter.CharacterListAdapter
import com.example.rickandmortycharacters.model.AllCharactersResponseModel
import com.example.rickandmortycharacters.model.Result
import com.example.rickandmortycharacters.utils.*
import okhttp3.ResponseBody
import org.koin.android.viewmodel.ext.android.viewModel

private const val HOME_FRAGMENT_TAG = "HOMEFRAGTAG"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : BaseFragment(), View.OnClickListener,
    CharacterListAdapter.ViewHolder.characterDetailListener{

    //Navigation Component controller
    private lateinit var navController: NavController

    //ViewModel
    private val homeViewModel by viewModel<HomeViewModel>()

    //View Binding
    // This property is only valid between onCreateView and
    // onDestroyView.
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    //To Store Local Data
    private var allCharactersResponseModel: AllCharactersResponseModel? = null
    private var mutableAllCharacterList = mutableListOf<Result>()
    private var listType = ListType.LIST
    private var pageNumber = 1
    private var endNumber = 2
    private var endItemRemember = 0
    private var selectedStatusFilter: String? = null

    //UI Components
    private var characterListAdapter: CharacterListAdapter? = null
    private var mOptionsMenu: Menu? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {}
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        setUIInit()
        initObservers()

    }


    private fun initObservers() {

        homeViewModel.getAllCharactersLiveData
            .observe(viewLifecycleOwner, _getAllCharactersObserver)
        homeViewModel.getAllCharactersWithPageLiveData
            .observe(viewLifecycleOwner, _getAllCharactersWithPageObserver)
        homeViewModel.getFilterByNameAndStatusAllCharactersWithPageLiveData
            .observe(viewLifecycleOwner, _getFilterByNameAndStatusObserver)

    }

    private fun setUIInit() {
        val user = getUserNameFromSharedPref()
        binding.TWHomeTitle.text = "Welcome $user"

        setToolBar()

        //homeViewModel.getAllCharactersWithPage(pageNumber)
        homeViewModel.getAllCharacters()

        binding.TWLoadMore.setOnClickListener(this)
        binding.TWHomeTitle.setOnClickListener(this)

        binding.CHGFilterStatus.setOnCheckedChangeListener { chipGroup, checkedId ->
            selectedStatusFilter = selectedChipTitle(checkedId)
            filterBySearchKey(null, selectedStatusFilter)
        }

    }

    private fun selectedChipTitle(chipId: Int): String? {
        return when (chipId) {
            binding.chipDead.id -> binding.chipDead.text.toString()
            binding.chipAlive.id -> binding.chipAlive.text.toString()
            binding.chipUnknown.id -> binding.chipUnknown.text.toString()
            else -> null
        }

    }

    private fun setToolBar() {
        binding.toolBar.inflateMenu(R.menu.menu_bar)
        mOptionsMenu = binding.toolBar.menu
        binding.toolBar.setOnMenuItemClickListener(object : MenuItem.OnMenuItemClickListener,
            Toolbar.OnMenuItemClickListener {
            override fun onMenuItemClick(item: MenuItem): Boolean {

                when (item.itemId) {
                    R.id.list_item -> {
                        listType = ListType.LIST
                        setRecyclerViewWithList(listType)
                    }
                    R.id.grid_item -> {
                        listType = ListType.GRID
                        setRecyclerViewWithList(listType)
                    }
                    R.id.actionSearch -> {
                        searchAction(item)
                    }
                    R.id.get_all_characters -> {
                        binding.TWLoadMore.show()
                        homeViewModel.getAllCharacters()
                    }
                }

                return true
            }
        })
        binding.IWHomeLogOut.setOnClickListener(this)
    }

    fun searchAction(item: MenuItem): Boolean {

        // Associate searchable configuration with the SearchView
        val searchManager =
            requireActivity().getSystemService(Context.SEARCH_SERVICE) as SearchManager?
        mOptionsMenu?.let { mOptionsMenuNonNull ->
            (mOptionsMenuNonNull.findItem(item.itemId)?.actionView as SearchView).setSearchableInfo(
                searchManager!!.getSearchableInfo(requireActivity().componentName)
            )
            val queryTextListener: SearchView.OnQueryTextListener =
                object : SearchView.OnQueryTextListener {
                    override fun onQueryTextChange(newText: String): Boolean {
                        val textView = requireActivity().findViewById(R.id.actionSearch) as TextView
                        textView.text = newText
                        return true
                    }

                    override fun onQueryTextSubmit(query: String): Boolean {
                        val textView = requireActivity().findViewById(R.id.actionSearch) as TextView
                        textView.text = query
                        filterBySearchKey(query, selectedStatusFilter)
                        return true
                    }
                }
            (mOptionsMenuNonNull.findItem(item.itemId)?.actionView as SearchView).setOnQueryTextListener(
                queryTextListener
            )

        }

        return true
    }

    private fun filterBySearchKey(name: String?, status: String?) {

        /*characterListAdapter?.let { characterListAdapterNonNull ->
            characterListAdapterNonNull.filter.filter(query)
        }*/
        binding.TWLoadMore.hide()
        homeViewModel.filterByNameAndStatus(name, status)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment HomeFragment.
         */

        @JvmStatic
        fun newInstance() =
            HomeFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }

    //Click Listeners
    override fun onClick(p0: View?) {
        p0?.let {
            when (it.id) {
                binding.IWHomeLogOut.id -> logOutActionFromHome()
                binding.TWLoadMore.id -> loadPageService()
                binding.TWHomeTitle.id -> {
                    binding.TWLoadMore.show()
                    homeViewModel.getAllCharacters()
                }
            }
        }
    }


    override fun characterDetailListener(data: Result) {
        val fromActionBundle =
            bundleOf(
                TRANSFER_SELECTED_CHARACTER to data,
            )
        navController.navigate(R.id.action_homeFragment_to_characterDetailFragment, fromActionBundle)
        //showToast("Character Choose to detail:"+ data.toString())
    }
    private fun loadPageService() {
        if (pageNumber < endNumber) {

            pageNumber += 1
            homeViewModel.getAllCharactersWithPage(pageNumber)
        }
    }

    private fun logOutActionFromHome() {
        super.logOutAction()
        navController.navigate(R.id.action_homeFragment_to_loginFragment)
    }

    private fun setRecyclerViewWithList(listType: ListType) {

        val layoutManager = when (listType) {
            ListType.LIST -> {
                LinearLayoutManager(requireContext())
            }
            ListType.GRID -> {
                GridLayoutManager(requireContext(), 2)
            }
        }

        binding.RVCharacterList.layoutManager = layoutManager
        binding.RVCharacterList.itemAnimator = DefaultItemAnimator()
        binding.RVCharacterList.adapter = characterListAdapter
        binding.RVCharacterList.layoutManager?.scrollToPosition(endItemRemember)
        characterListAdapter?.let {
            it.notifyDataSetChanged()
        }

    }

    //Observers
    private val _getAllCharactersObserver = Observer<Resource<ResponseBody>> {
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
                    allCharactersResponseModel = IntentUtil.gson.fromJson(
                        responseBodyNonNull.charStream(),
                        AllCharactersResponseModel::class.java
                    )
                    allCharactersResponseModel?.let { allCharactersResponseModelNonNull ->
                        endNumber = allCharactersResponseModelNonNull.info.count
                        mutableAllCharacterList = allCharactersResponseModelNonNull.results
                        characterListAdapter = CharacterListAdapter(
                            mutableAllCharacterList,
                            requireContext(),
                            this
                        )

                        setRecyclerViewWithList(listType)
                    }

                    showToast(SUCCESS_MESSAGE)
                }
            }
        }
    }
    private val _getAllCharactersWithPageObserver = Observer<Resource<ResponseBody>> {
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
                    allCharactersResponseModel = IntentUtil.gson.fromJson(
                        responseBodyNonNull.charStream(),
                        AllCharactersResponseModel::class.java
                    )
                    allCharactersResponseModel?.let { allCharactersResponseModelNonNull ->
                        endNumber = allCharactersResponseModelNonNull.info.count
                        characterListAdapter?.let { characterListAdapterNonNull ->
                            endItemRemember = characterListAdapterNonNull.itemCount
                            for (i in allCharactersResponseModelNonNull.results) {
                                characterListAdapterNonNull.insertElement(i)
                            }
                        }
                        setRecyclerViewWithList(listType)
                    }
                    showToast(SUCCESS_MESSAGE)
                }
            }
        }
    }
    private val _getFilterByNameAndStatusObserver = Observer<Resource<ResponseBody>> {
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
                    allCharactersResponseModel = IntentUtil.gson.fromJson(
                        responseBodyNonNull.charStream(),
                        AllCharactersResponseModel::class.java
                    )
                    allCharactersResponseModel?.let { allCharactersResponseModelNonNull ->
                        endNumber = allCharactersResponseModelNonNull.info.count
                        mutableAllCharacterList = allCharactersResponseModelNonNull.results
                        characterListAdapter = CharacterListAdapter(
                            mutableAllCharacterList,
                            requireContext(),this
                        )
                        setRecyclerViewWithList(listType)
                    }
                    showToast(SUCCESS_MESSAGE)
                }
            }
        }
    }




}