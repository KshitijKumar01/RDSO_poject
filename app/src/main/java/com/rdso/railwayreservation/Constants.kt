package com.rdso.railwayreservation

object Constants {

    var trainObj : Train? = null

    fun getTrainList() : ArrayList<Train> {
        val trainList = ArrayList<Train>()

        val train1 = Train(
            12231,
            "LKO CDG Express",
            "Lucknow",
            "Chandigarh",
            "31/07/2022",
            "01/08/2022",
            "22:25",
            "10:05",
            "lucknow to chandigarh"
        )
        trainList.add(train1)

        val train2 = Train(
            15011,
            "LJN CDG Express",
            "Lucknow",
            "Chandigarh",
            "31/07/2022",
            "01/08/2022",
            "23:55",
            "15:05",
            "lucknow to chandigarh"
        )
        trainList.add(train2)

        val train3 = Train(
            12925,
            "Paschim Express",
            "Kota",
            "Chandigarh",
            "31/07/2022",
            "01/08/2022",
            "23:55",
            "15:05",
            "kota to chandigarh"
        )
        trainList.add(train3)

        val train4 = Train(
            12314,
            "Sealdah Rajdhani",
            "New Delhi",
            "Sealdah",
            "01/08/2022",
            "02/08/2022",
            "16:30",
            "10:10",
            "New delhi to sealdah"
        )
        trainList.add(train4)

        val train5 = Train(
            12302,
            "Hwh Rajdhani",
            "New Delhi",
            "Howrah Jn",
            "01/08/2022",
            "02/08/2022",
            "16:50",
            "09:55",
            "New delhi to howrah"
        )
        trainList.add(train5)

        val train6 = Train(
            12302,
            "Marudhar Exp",
            "Lucknow",
            "Agra",
            "10/08/2022",
            "10/08/2022",
            "00:15",
            "06:55",
            "lucknow to agra"
        )
        trainList.add(train6)

        val train7 = Train(
            12302,
            "Gomti Express",
            "Lucknow",
            "Tundla",
            "10/08/2022",
            "10/08/2022",
            "05:45",
            "11:03",
            "lucknow to tundla"
        )
        trainList.add(train7)

        val train8 = Train(
            12591,
            "Gkp Ypr Express",
            "Badhshahnagar",
            "Secunderabad Jn",
            "12/08/2022",
            "14/08/2022",
            "11:04",
            "13:40",
            "Badhshahnagar to Secunderabad Jn"
        )
        trainList.add(train8)

        val train9 = Train(
            12332,
            "Himgri Express",
            "Patna Jn",
            "Howrah Jn",
            "12/08/2022",
            "12/08/2022",
            "01:45",
            "11:30",
            "patna to howrah"
        )
        trainList.add(train9)

        val train10 = Train(
            12302,
            "Gomti Express",
            "Lucknow",
            "Tundla",
            "13/08/2022",
            "13/08/2022",
            "06:00",
            "13:03",
            "lucknow to tundla"
        )
        trainList.add(train10)

        val train11 = Train(
            12302,
            "Marudhar Exp",
            "Lucknow",
            "Agra",
            "15/08/2022",
            "15/08/2022",
            "01:45",
            "08:15",
            "lucknow to agra"
        )
        trainList.add(train11)

        val train12 = Train(
            12231,
            "LKO CDG Express",
            "Lucknow",
            "Chandigarh",
            "05/08/2022",
            "06/08/2022",
            "20:25",
            "08:05",
            "lucknow to chandigarh"
        )
        trainList.add(train12)

        return trainList
    }
}