package products;

public enum ProductCategories{
	PHONES_TABLETS_LAPTOPS,
	COMPUTERS,
	TV_AUDIO_FOTO,
	HEALTH_BEAUTY;
  
	enum PhonesTabletsLaptops{
		PHONES,
		TABLETS,
		LAPTOPS
	}
	
	enum Computers{
		DESKTOP_COMPUTERS_MONITORS,
		PC_COMPONENTS,
		SOFTUER
	}
	
	enum TVAudioFoto{
		TVS,
		HOME_CINEMA_AUDIO,
		ELECTRONICS,
		FOTO_VIDEO
	}

	enum HealthBeauty{
		PARFUMES,
		MAKEUP_MANICURE,
		COSMETICS,
		ACCESSORIES
	}
}


