package com.hoshyar.ebrahimi.hushifood

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hoshyar.ebrahimi.hushifood.databinding.ActivityMainBinding
import com.hoshyar.ebrahimi.hushifood.databinding.DialogAddNewItemBinding
import com.hoshyar.ebrahimi.hushifood.databinding.DialogDeleteItemBinding
import com.hoshyar.ebrahimi.hushifood.databinding.DialogUpdateItemBinding
import kotlin.math.log

// implement FoodAdapter.FoodEvents (InterFace) here ->
class MainActivity : AppCompatActivity() , FoodAdapter.FoodEvents {
	 lateinit var binding: ActivityMainBinding
	 lateinit var myAdapter: FoodAdapter
	 override fun onCreate(savedInstanceState: Bundle?) {
			super.onCreate(savedInstanceState)
			binding = ActivityMainBinding.inflate(layoutInflater)
			setContentView(binding.root)
			
			Log.v("activityMainBinding","oncreate called ..")
			
			//set Adapter
			val foodList = arrayListOf(
				 Food(
						"Hamburger" ,
						"15" ,
						"3" ,
						"Isfahan, Iran" ,
						"https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food1.jpg" ,
						20 ,
						4.5f
				 ) ,
				 Food(
						"Grilled fish" ,
						"20" ,
						"2.1" ,
						"Tehran, Iran" ,
						"https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food2.jpg" ,
						10 ,
						4f
				 ) ,
				 Food(
						"Lasania" ,
						"40" ,
						"1.4" ,
						"Isfahan, Iran" ,
						"https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food3.jpg" ,
						30 ,
						2f
				 ) ,
				 Food(
						"pizza" ,
						"10" ,
						"2.5" ,
						"Zahedan, Iran" ,
						"https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food4.jpg" ,
						80 ,
						1.5f
				 ) ,
				 Food(
						"Sushi" ,
						"20" ,
						"3.2" ,
						"Mashhad, Iran" ,
						"https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food5.jpg" ,
						200 ,
						3f
				 ) ,
				 Food(
						"Roasted Fish" ,
						"40" ,
						"3.7" ,
						"Jolfa, Iran" ,
						"https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food6.jpg" ,
						50 ,
						3.5f
				 ) ,
				 Food(
						"Fried chicken" ,
						"70" ,
						"3.5" ,
						"NewYork, USA" ,
						"https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food7.jpg" ,
						70 ,
						2.5f
				 ) ,
				 Food(
						"Vegetable salad" ,
						"12" ,
						"3.6" ,
						"Berlin, Germany" ,
						"https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food8.jpg" ,
						40 ,
						4.5f
				 ) ,
				 Food(
						"Grilled chicken" ,
						"10" ,
						"3.7" ,
						"Beijing, China" ,
						"https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food9.jpg" ,
						15 ,
						5f
				 ) ,
				 Food(
						"Baryooni" ,
						"16" ,
						"10" ,
						"Ilam, Iran" ,
						"https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food10.jpg" ,
						28 ,
						4.5f
				 ) ,
				 Food(
						"Ghorme Sabzi" ,
						"11.5" ,
						"7.5" ,
						"Karaj, Iran" ,
						"https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food11.jpg" ,
						27 ,
						5f
				 ) ,
				 Food(
						"Rice" ,
						"12.5" ,
						"2.4" ,
						"Shiraz, Iran" ,
						"https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food12.jpg" ,
						35 ,
						2.5f
				 ) ,
			)
			myAdapter = FoodAdapter(foodList.clone() as ArrayList<Food> , this)
			binding.recyclerMain.adapter = myAdapter
			binding.recyclerMain.layoutManager = LinearLayoutManager(this , RecyclerView.VERTICAL , false)
		
			//add new item
			binding.btnAddNewFood.setOnClickListener {
				 val dialog = AlertDialog.Builder(this).create()
				 val dialogBinding = DialogAddNewItemBinding.inflate(layoutInflater)
				 dialog.setCancelable(true)
				 dialog.setView(dialogBinding.root)
				 dialog.show()
				
				 // btn done clicked ( added new item ) ->
				 dialogBinding.dialogBtnDone.setOnClickListener {
						// here we check to sure all values are full ...
						if (
							 dialogBinding.dialogEdtFoodCity.length() > 0 &&
							 dialogBinding.dialogEdtNameFood.length() > 0 &&
							 dialogBinding.dialogEdtFoodPrice.length() > 0 &&
							 dialogBinding.dialogEdtFoodDistance.length() > 0
						) {
							 // these values are my dialogAddNewFood values ->
							 val txtName = dialogBinding.dialogEdtNameFood.text.toString()
							 val txtCity = dialogBinding.dialogEdtFoodCity.text.toString()
							 val txtPrice = dialogBinding.dialogEdtFoodPrice.text.toString()
							 val txtDistance = dialogBinding.dialogEdtFoodDistance.text.toString()
							 val txtRatingNumber: Int = (1 until 150).random()
							 val ratingBarStar: Float = (1..5).random().toFloat()
							 val randomForUrlImage = (0 until 12).random()
							 val urlPic = foodList[randomForUrlImage].urlImage
							
							 // here we create a Food with above values ->
							 val newFood = Food(
									txtName ,
									txtDistance ,
									txtPrice ,
									txtCity ,
									urlPic ,
									txtRatingNumber ,
									ratingBarStar
							 )
							 // we call addFood() and fill with the newFood that we created it in above
							 myAdapter.addFood(newFood)
							 dialog.dismiss()
							 binding.recyclerMain.scrollToPosition(0)
							
						} else {
							 // if all values wasnt full it shows a toast ->
							 Toast.makeText(this , "fill all values" , Toast.LENGTH_SHORT).show()
						}
				 }
			}
			
			
			// search part ->
			binding.edtSearch.addTextChangedListener { editTextInput ->
				 if (editTextInput!!.isNotEmpty()) {
						// filter data ...
						val cloneList = foodList.clone() as ArrayList<Food>
						val filteredList = cloneList.filter { itemFood ->
							 (itemFood.txtSubject.contains(editTextInput))
						}
						myAdapter.setData(filteredList as ArrayList<Food>)
				 } else {
						// show all data
						myAdapter.setData(foodList.clone() as ArrayList<Food>)
				 }
			}
	 }
	 // Methods of FoodEvents InterFace that we have to implement   :->
	 // 1. onFoodClick -> { update an item }
	 override fun onFoodClicked(food: Food , position: Int) {
			
			val dialog = AlertDialog.Builder(this).create()
			val dialogUpdateItemBinding = DialogUpdateItemBinding.inflate(layoutInflater)
			dialog.setView(dialogUpdateItemBinding.root)
			dialog.show()
			// fill updateDialog values with past datas ->
			dialogUpdateItemBinding.dialogEdtNameFood.setText(food.txtSubject)
			dialogUpdateItemBinding.dialogEdtFoodCity.setText(food.txtCity)
			dialogUpdateItemBinding.dialogEdtFoodPrice.setText(food.txtPrice)
			dialogUpdateItemBinding.dialogEdtFoodDistance.setText(food.txtDistance)
			
			
			// btn update click
			dialogUpdateItemBinding.dialogUpdateBtnDone.setOnClickListener {
				 if (
						dialogUpdateItemBinding.dialogEdtFoodCity.length() > 0 &&
						dialogUpdateItemBinding.dialogEdtNameFood.length() > 0 &&
						dialogUpdateItemBinding.dialogEdtFoodPrice.length() > 0 &&
						dialogUpdateItemBinding.dialogEdtFoodDistance.length() > 0
				 ) {
				 	 // here we get dialogUpdateItemBinding values in some val to create newFood and set it in same position ->
						val txtName = dialogUpdateItemBinding.dialogEdtNameFood.text.toString()
						val txtCity = dialogUpdateItemBinding.dialogEdtFoodCity.text.toString()
						val txtPrice = dialogUpdateItemBinding.dialogEdtFoodPrice.text.toString()
						val txtDistance = dialogUpdateItemBinding.dialogEdtFoodDistance.text.toString()
						
						//create new food to add to recyclerView in same position (for update ) with above values ->
						val newFood: Food = Food(txtName , txtDistance , txtPrice , txtCity , food.urlImage , food.numOfRating , food.rating)
						
						// update item :
						myAdapter.updateFood(newFood , position)
						dialog.dismiss()
				 } else {
						Toast.makeText(this , "enetr correct value" , Toast.LENGTH_SHORT).show()
				 }
				 
			}
			
			
			// btn cancle clicked ->
			dialogUpdateItemBinding.dialogUpdateBtnCancle.setOnClickListener {
				 dialog.dismiss()
			}
			
	 }
	 // 2. onFoodLongClick -> { delete an item }
	 override fun onFoodLongClicked(food: Food , pos: Int) {
			// show dialog ->
			val dialog = AlertDialog.Builder(this).create()
			val dialogDeleteItemBinding = DialogDeleteItemBinding.inflate(layoutInflater)
			dialog.setView(dialogDeleteItemBinding.root)
			dialog.show()
			
			// click surebtn to remove and call deleteFood() ->
			dialogDeleteItemBinding.dialogBtnDeleteSure.setOnClickListener {
				 dialog.dismiss()
				 
				 // deleteFood() called ->
				 myAdapter.deleteFood( food , pos)
				 binding.recyclerMain.scrollToPosition(0)
				 
			}
			
			
			//  click canclebtn and dialog dismiss()
			dialogDeleteItemBinding.dialogBtnDeleteCancle.setOnClickListener {
				 dialog.dismiss()
			}
	 }
	 
	 
}

