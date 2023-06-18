package com.timmitof.cityreport.core.utils

enum class Importance(val value: Int?) {
    Like(1),
    Dislike(2)
}

enum class StatusComplaince(val value: Int) {
    Reported(1),
    Reviewed(2),
    Performed(3),
    Finished(4)
}