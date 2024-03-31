package ar.edu.unsam.phm.magicnightsback.dto

import ar.edu.unsam.phm.magicnightsback.domain.ShowDate
import ar.edu.unsam.phm.magicnightsback.domain.User

class ShowDatesDTO(
    val date: String,
    val friendImgs: List<String>,
    val totalFriends: Int,
    val rating: Double,
    val totalCommets: Int
)

//fun ShowDate.toShowDateDTO(user: User) = ShowDatesDTO(
//    date = this.date.toString(),
//    friendImgs = this.friendsAttending(user),
//    totalFriends = this.show.friends.size,
//    rating = this.totalRating(),
//    totalCommets = this.show.comments.size
//)