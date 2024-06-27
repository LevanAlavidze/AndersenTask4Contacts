package com.example.andersentask4contacts

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class ContactAdapter(
    private var contactsList: List<Contact>,
    private val context: Context
) : RecyclerView.Adapter<ContactAdapter.ContactViewHolder>() {

    inner class ContactViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val contactName: TextView = itemView.findViewById(R.id.contactName)
        val contactSurname: TextView = itemView.findViewById(R.id.contactSurname)
        val contactImage: ImageView = itemView.findViewById(R.id.contactImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_contact, parent, false)
        return ContactViewHolder(view)
    }



    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val contact = contactsList[position]
        holder.contactName.text = contact.name
        holder.contactSurname.text = contact.surname
        Picasso.get().load(contact.imageUrl).into(holder.contactImage)

        holder.itemView.setOnLongClickListener {
            AlertDialog.Builder(context)
                .setTitle("Delete Contact")
                .setMessage("Are you sure you want to delete this contact?")
                .setPositiveButton("Yes") { dialog, which ->
                    contactsList = contactsList.toMutableList().apply { removeAt(position) }
                    notifyItemRemoved(position)
                    notifyItemRangeChanged(position, contactsList.size)
                }
                .setNegativeButton("No", null)
                .show()
            true
        }
    }


    override fun getItemCount() = contactsList.size

    fun updateList(newList: List<Contact>) {
        val diffResult = DiffUtil.calculateDiff(ContactDiffCallback(contactsList, newList))
        contactsList = newList
        diffResult.dispatchUpdatesTo(this)
    }


}

class ContactDiffCallback(
    private val oldList: List<Contact>,
    private val newList: List<Contact>
                        ): DiffUtil.Callback(){
    override fun getOldListSize() = oldList.size
    override fun getNewListSize() = newList.size
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldList[oldItemPosition].imageUrl == newList[newItemPosition].imageUrl
    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldList[oldItemPosition] == newList[newItemPosition]
}