package chat;

public class ChatDTO {
	private int chatID;
	private String fromID;
	private String toID;
	private String chatContent;
	private String chatTime;
	public void setChatID(int chatID) {
		this.chatID = chatID;
	}
	public void setFromID(String fromID) {
		this.fromID = fromID;
	}
	public void setToID(String toID) {
		this.toID = toID;
	}
	public void setChatContent(String chatContent) {
		this.chatContent = chatContent;
	}
	public void setChatTime(String chatTime) {
		this.chatTime = chatTime;
	}
	public int getChatID() {
		return chatID;
	}
	public String getFromID() {
		return fromID;
	}
	public String getToID() {
		return toID;
	}
	public String getChatContent() {
		return chatContent;
	}
	public String getChatTime() {
		return chatTime;
	}
	@Override
	public String toString() {
		return "ChatDTO [chatID=" + chatID + ", fromID=" + fromID + ", toID=" + toID + ", chatContent=" + chatContent
				+ ", chatTime=" + chatTime + "]";
	}
	
}
