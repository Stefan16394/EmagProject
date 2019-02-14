package users;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Message {
	private LocalDateTime date;
	private String message;
	
	public Message(String message) {
		this.date = LocalDateTime.now();
		this.message = message;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public String getMessage() {
		return message;
	}

	@Override
	public String toString() {
		return DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(this.date ) + " - " + this.message; 	
	}
	
	
}
