object ManufacturesDevicesMap {

    val manufacturersMap by lazy {
        mutableListOf<Manufacturer>().apply {
            add(Manufacturer("Generic", listOf("Generic e-ink device", "Smartphone", "iPad like tablet")))
            add(Manufacturer("Generic", listOf("Generic e-ink device", "Smartphone", "iPad like tablet")))
            add(Manufacturer("Generic", listOf("Generic e-ink device", "Smartphone", "iPad like tablet")))
            add(Manufacturer("Generic", listOf("Generic e-ink device", "Smartphone", "iPad like tablet")))
            add(Manufacturer("Generic", listOf("Generic e-ink device", "Smartphone", "iPad like tablet")))
            add(Manufacturer("Generic", listOf("Generic e-ink device", "Smartphone", "iPad like tablet")))
            add(Manufacturer("Generic", listOf("Generic e-ink device", "Smartphone", "iPad like tablet")))
            add(Manufacturer("Generic", listOf("Generic e-ink device", "Smartphone", "iPad like tablet")))
            add(Manufacturer("Generic", listOf("Generic e-ink device", "Smartphone", "iPad like tablet")))
            add(Manufacturer("Generic", listOf("Generic e-ink device", "Smartphone", "iPad like tablet")))
            add(Manufacturer("Generic", listOf("Generic e-ink device", "Smartphone", "iPad like tablet")))
        }
    }

}

data class Manufacturer(val name: String, val devices: List<String>)