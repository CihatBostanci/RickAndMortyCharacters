package com.example.rickandmortycharacters.home.adapter

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.rickandmortycharacters.R
import com.example.rickandmortycharacters.MainApplication
import com.example.rickandmortycharacters.manager.SharedPreferencesManager.get
import com.example.rickandmortycharacters.model.Result
import com.example.rickandmortycharacters.utils.FAVORITE_CHARACTER_ID
import com.google.android.material.card.MaterialCardView
import java.util.*


class CharacterListAdapter(
    private var dataSet: MutableList<Result>,
    private val context: Context,
    private val characterDetailListener:
    CharacterListAdapter.ViewHolder.characterDetailListener,
) : RecyclerView.Adapter<CharacterListAdapter.ViewHolder>(), Filterable {


    /**
     * Provide a reference to the type of views <that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val characterTitle: TextView
        val characterPhoto: ImageView
        val characterStatus: TextView
        val characterSpecifies: TextView
        val characterCardView: MaterialCardView
        val characterConstraintLayout: ConstraintLayout

        init {

            // Define click listener for the ViewHolder's View.
            characterTitle = view.findViewById(R.id.TWCharacterItemTitle)
            characterPhoto = view.findViewById(R.id.IWCharacter)
            characterStatus = view.findViewById(R.id.TWCharacterItemStatus)
            characterSpecifies = view.findViewById(R.id.TWCharacterItemSpecifies)
            characterCardView = view.findViewById(R.id.CVCharacterItem)
            characterConstraintLayout = view.findViewById(R.id.CLCharacterItem)

        }


        interface characterDetailListener {
            fun characterDetailListener(data: Result)
        }


    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CharacterListAdapter.ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_item_character, parent, false)

        return ViewHolder(view)
    }

    fun insertElement(element: Result) {
        this.dataSet.add(element)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onBindViewHolder(holder: CharacterListAdapter.ViewHolder, position: Int) {
        val options: RequestOptions = RequestOptions()
            .centerCrop()
            .placeholder(R.drawable.ic_baseline_account_circle_24)

        Glide.with(context).load(dataSet[position].image).apply(options)
            .into(holder.characterPhoto)
        holder.characterTitle.text = dataSet[position].name

        holder.characterStatus.text = dataSet[position].status
        holder.characterSpecifies.text = dataSet[position].species

        if(dataSet[position].id == MainApplication.sharedPreferencesManager[FAVORITE_CHARACTER_ID,0]){
                holder.characterConstraintLayout.setBackgroundColor(context.getColor(R.color.greenLight))
        }

        holder.characterCardView.setOnClickListener {
            characterDetailListener.characterDetailListener(dataSet[position])
        }

    }


    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size

    override fun getFilter(): Filter {
        return exampleFilter
    }

    private val exampleFilter: Filter = object : Filter() {
        override fun performFiltering(constraint: CharSequence): FilterResults {
            val filteredList: MutableList<Result> = mutableListOf()
            if (constraint.isEmpty()) {
                filteredList.addAll(dataSet)
            } else {
                val filterPattern =
                    constraint.toString().toLowerCase(Locale.ROOT).trim { it <= ' ' }
                for (item in dataSet) {
                    if (item.name.toLowerCase(Locale.ROOT).contains(filterPattern)) {
                        filteredList.add(item)
                    }
                }
            }
            val results = FilterResults()
            results.values = filteredList
            return results
        }

        override fun publishResults(constraint: CharSequence, results: FilterResults) {
            dataSet.clear()
            dataSet.addAll(results.values as Collection<Result>)
            notifyDataSetChanged()
        }
    }


}