package com.hoshyar.ebrahimi.hushifood

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.hoshyar.ebrahimi.hushifood.databinding.ItemFoodBinding


class FoodAdapter(private val data: ArrayList<Food> , private val foodEvents: FoodEvents) : RecyclerView.Adapter<FoodAdapter.FoodViewHolder>() {
	 
	 //ViewHolder ->
	 // here we use fucking binding
	 // 1. at first we ctraeted it in onCreateViewHolder
	 // 2. here we use in FoodViewHolder
	 inner class FoodViewHolder(val binding: ItemFoodBinding) : RecyclerView.ViewHolder( /* important -> */  binding.root) {
			@SuppressLint("SetTextI18n")
			// fill itemview views by Food ( data class )
			fun bindData(position: Int) {
				 binding.itemTxtSubject.text = data[position].txtSubject
				 binding.itemTxtCity.text = data[position].txtCity
				 binding.itemTxtPrice.text = "$" + data[position].txtPrice + " vip"
				 binding.itemTxtDistance.text = data[position].txtDistance + " miles from you"
				 binding.itemRatingMain.rating = data[position].rating
				 binding.itemTxtRating.text = "(" + data[position].numOfRating.toString() + " Ratings )"
				 Glide.with(binding.root.context).load(data[position].urlImage).transform(RoundedCorners(50)).into(binding.itemImgMain)
				 // clicked on itemView :->
				 itemView.setOnClickListener {
						foodEvents.onFoodClicked(data[adapterPosition] , adapterPosition)
				 }
				 // long clicked on itemView
				 itemView.setOnLongClickListener {
						foodEvents.onFoodLongClicked(data[adapterPosition] , adapterPosition)
						true
				 }
			}
	 }
	 
	 
	 
	 
	 // 3 Main Methods Of Adapter To Create it  ->
	 override fun onCreateViewHolder(parent: ViewGroup , viewType: Int): FoodViewHolder {
			// create binding and use it  ->
			val binding = ItemFoodBinding.inflate(LayoutInflater.from(parent.context) , parent , false)
			return FoodViewHolder(binding)
			
	 }
	 override fun onBindViewHolder(holder: FoodViewHolder , position: Int) {
			holder.bindData(position)
	 }
	 override fun getItemCount(): Int {
			return data.size
	 }
	 
	 // add food ->
	 fun addFood(newFood: Food) {
			//add food to list
			data.add(0 , newFood)
			notifyItemInserted(0)
	 }
	 
	 // update food ->
	 fun updateFood(newFood: Food , position: Int) {
			data.set(position , newFood)
			notifyItemChanged(position)
	 }
	 
	 // delete food ->
	 fun deleteFood(oldFood: Food , oldPosition: Int) {
			data.remove(oldFood)
			notifyItemRemoved(oldPosition)
	 }
	 
	 // add new List ( for searching part ) ->
	 @SuppressLint("NotifyDataSetChanged")
	 fun setData(newList: ArrayList<Food>) {
			data.clear()
			data.addAll(newList)
			notifyDataSetChanged()
	 }
	 
	 // create interFace ->
	 interface FoodEvents {
			// 1. create interface in adapter
			// 2. get an object of interface in args of Aadapter
			// 3. fill ( call ) object of your interface with your data
			// 4. implementation in MainActivity
			fun onFoodClicked(food: Food , position: Int)
			
			fun onFoodLongClicked(food: Food , pos: Int)
	 }
	 
}

