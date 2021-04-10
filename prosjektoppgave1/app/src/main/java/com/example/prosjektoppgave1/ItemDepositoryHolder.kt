package com.example.prosjektoppgave1

class ItemDepositoryHolder {

    private lateinit var itemCollection: MutableList<Todo>


    var onItem: ((List<Todo>) -> Unit)? = null
    var onItemUpdate: ((todo: Todo) -> Unit)? = null


    fun loadList(){
        itemCollection = mutableListOf(

        )

        onItem?.invoke(itemCollection)
    }

    fun updateCollection(list:Todo){
        itemCollection.add(list)
        onItem?.invoke(itemCollection)
    }

    fun delete(i:Todo){
        itemCollection.remove(i)
        onItem?.invoke(itemCollection)
    }
    fun size(){
        itemCollection.size
    }

    companion object{
        val instance = ItemDepositoryHolder()
    }

}