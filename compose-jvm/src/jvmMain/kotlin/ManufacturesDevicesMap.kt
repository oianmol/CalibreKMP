object ManufacturesDevicesMap {

    val manufacturersMap by lazy {
        mutableListOf<Manufacturer>().apply {
            add(Manufacturer(1, "Generic", listOf("Generic e-ink device", "Smartphone", "iPad like tablet")))
            add(
                Manufacturer(
                    2,
                    "Amazon",
                    listOf("Kindle Basic(all models)", "Kindle DX", "Fire & Fire HD", "PaperWhite", "Voyager/oasis")
                )
            )
            add(Manufacturer(3, "Android", listOf("Phone","phone with kindle reader","tablet","tab with kindle reader")))
            add(Manufacturer(4, "Apple", listOf("iphone/ipad, ipod touch")))
        }
    }

}

data class Manufacturer(val id: Int, val name: String, val devices: List<String>)