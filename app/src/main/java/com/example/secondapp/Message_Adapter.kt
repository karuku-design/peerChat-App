package com.example.secondapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth

class Message_Adapter(val context: Context, val messageList:ArrayList<Message>):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

        val ITEM_RECEIVE=1
        val ITEM_SENT=2

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if(viewType==1){
            val view:View= LayoutInflater.from(context).inflate(R.layout.receive,parent,false)
            return (userAdapter.userViewHolder(view))
        }else {
            val view:View= LayoutInflater.from(context).inflate(R.layout.sent,parent,false)
            return (userAdapter.userViewHolder(view))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentMessage=messageList[position]

        if (holder.javaClass == SentViewHolder::class.java){
            //do stuff for sent view holder
            val viewHolder=holder as SentViewHolder
            holder.sentMessage.text=currentMessage.message

        }else if(holder.javaClass == ReceiveViewHolder::class.java){
            //do stuff for receive
            val viewHolder=holder as ReceiveViewHolder
            holder.receiveMessage.text=currentMessage.message
        }
    }

    override fun getItemViewType(position: Int): Int {
        var currentMessage=messageList[position]
        if (FirebaseAuth.getInstance().currentUser?.uid.equals(currentMessage.senderId)){
            return ITEM_SENT
        }else{
            return ITEM_RECEIVE
        }
    }

    override fun getItemCount(): Int {
        return messageList.size
    }

    class SentViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val sentMessage=itemView.findViewById<TextView>(R.id.txt_sent_message)
    }
    class ReceiveViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val receiveMessage=itemView.findViewById<TextView>(R.id.txt_receive_message)
    }
}