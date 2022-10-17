package com.example.win6

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.win6.model.Response

class MainAdapter(val list: ArrayList<Response>) : RecyclerView.Adapter<MainAdapter.MainHolder>() {

    inner class MainHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val firstTeamImageView = itemView.findViewById<ImageView>(R.id.first_team_img)
        val secondTeamImageView = itemView.findViewById<ImageView>(R.id.second_team_img)
        val firstTeamName = itemView.findViewById<TextView>(R.id.first_team_name)
        val secondTeamName = itemView.findViewById<TextView>(R.id.second_team_name)
        val leagueNameTv = itemView.findViewById<TextView>(R.id.textView_league)
        val statusTv = itemView.findViewById<TextView>(R.id.textView_status)
        val value_quarter1_first = itemView.findViewById<TextView>(R.id.value_quarter1_first)
        val value_quarter2_first = itemView.findViewById<TextView>(R.id.value_quarter2_first)
        val value_quarter3_first = itemView.findViewById<TextView>(R.id.value_quarter3_first)
        val value_quarter4_first = itemView.findViewById<TextView>(R.id.value_quarter4_first)
        val value_quarter1_second = itemView.findViewById<TextView>(R.id.value_quarter1_second)
        val value_quarter2_second = itemView.findViewById<TextView>(R.id.value_quarter2_second)
        val value_quarter3_second = itemView.findViewById<TextView>(R.id.value_quarter3_second)
        val value_quarter4_second = itemView.findViewById<TextView>(R.id.value_quarter4_second)
        val valuet_total_first = itemView.findViewById<TextView>(R.id.total_first)
        val valuet_total_second = itemView.findViewById<TextView>(R.id.total_second)
        val tvTime = itemView.findViewById<TextView>(R.id.tv_time)
        fun bind(
            firstTeamImg: String,
            secondTeamImg: String,
            nameFirst: String,
            nameSecond: String,
            league: String,
            status: String,
            quarter1_first: String,
            quarter2_first: String,
            quarter3_first: String,
            quarter4_first: String,
            quarter1_second: String,
            quarter2_second: String,
            quarter3_second: String,
            quarter4_second: String,
            totalFirst: String,
            totalSecond: String,
            time: String
        ) {
            Glide.with(itemView.context)
                .load(firstTeamImg)
                .override(100, 100)
                .into(firstTeamImageView)
            Glide.with(itemView.context)
                .load(secondTeamImg)
                .override(100, 100)
                .into(secondTeamImageView)
            firstTeamName.text = nameFirst
            secondTeamName.text = nameSecond
            leagueNameTv.text = league
            statusTv.text = status
            value_quarter1_first.text = quarter1_first
            value_quarter2_first.text = quarter2_first
            value_quarter3_first.text = quarter3_first
            value_quarter4_first.text = quarter4_first
            value_quarter1_second.text = quarter1_second
            value_quarter2_second.text = quarter2_second
            value_quarter3_second.text = quarter3_second
            value_quarter4_second.text = quarter4_second
            valuet_total_first.text = totalFirst
            valuet_total_second.text = totalSecond
            tvTime.text = time
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.recycler_item, parent, false)
        return MainHolder(view)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        holder.bind(
            list[position].teams.home.logo.toString(),
            list[position].teams.away.logo.toString(),
            list[position].teams.home.name,
            list[position].teams.away.name,
            list[position].league.name,
            list[position].status.long,
            list[position].scores.home.quarter1.toString(),
            list[position].scores.home.quarter2.toString(),
            list[position].scores.home.quarter3.toString(),
            list[position].scores.home.quarter4.toString(),
            list[position].scores.away.quarter1.toString(),
            list[position].scores.away.quarter2.toString(),
            list[position].scores.away.quarter3.toString(),
            list[position].scores.away.quarter4.toString(),
            list[position].scores.home.total.toString(),
            list[position].scores.away.total.toString(),
            list[position].time
        )


    }

    override fun getItemCount(): Int {
        return list.size
    }
}