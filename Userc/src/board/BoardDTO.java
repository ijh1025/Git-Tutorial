package board;

public class BoardDTO {
	private  String userID;
	private  int boardID;
	private  String boardTitle;
	private  String boardContent;
	private  String boardDate;
	private  int boardHit;
	private  String boardFile;
	private  String boardRealFile;
	private  int boardGroup;
	private  int boardSequence;
	private  int boardLevel;
	private  int boardAvailable;
	
	public int getBoardAvailable() {
		return boardAvailable;
	}
	public void setBoardAvailable(int boardAvailable) {
		this.boardAvailable = boardAvailable;
	}
	public String getBoardRealFile() {
		return boardRealFile;
	}
	public String getUserID() {
		return userID;
	}
	public int getBoardID() {
		return boardID;
	}
	public String getBoardTitle() {
		return boardTitle;
	}
	public String getBoardContent() {
		return boardContent;
	}
	public String getBoardDate() {
		return boardDate;
	}
	public int getBoardHit() {
		return boardHit;
	}
	public String getBoardFile() {
		return boardFile;
	}
	public String getBoarlRealFile() {
		return boardRealFile;
	}
	public int getBoardGroup() {
		return boardGroup;
	}
	public int getBoardSequence() {
		return boardSequence;
	}
	public int getBoardLevel() {
		return boardLevel;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public void setBoardID(int boardID) {
		this.boardID = boardID;
	}
	public void setBoardTitle(String boardTitle) {
		this.boardTitle = boardTitle;
	}
	public void setBoardContent(String boardContent) {
		this.boardContent = boardContent;
	}
	public void setBoardDate(String boardDate) {
		this.boardDate = boardDate;
	}
	public void setBoardHit(int boardHit) {
		this.boardHit = boardHit;
	}
	public void setBoardFile(String boardFile) {
		this.boardFile = boardFile;
	}
	public void setBoardRealFile(String boardRealFile) {
		this.boardRealFile = boardRealFile;
	}
	public void setBoardGroup(int boardGroup) {
		this.boardGroup = boardGroup;
	}
	public void setBoardSequence(int boardSequence) {
		this.boardSequence = boardSequence;
	}
	public void setBoardLevel(int boardLevel) {
		this.boardLevel = boardLevel;
	}
	@Override
	public String toString() {
		return "BoardDTO [userID=" + userID + ", boardID=" + boardID + ", boardTitle=" + boardTitle + ", boardContent="
				+ boardContent + ", boardDate=" + boardDate + ", boardHit=" + boardHit + ", boardFile=" + boardFile
				+ ", boardRealFile=" + boardRealFile + ", boardGroup=" + boardGroup + ", boardSequence=" + boardSequence
				+ ", boardLevel=" + boardLevel + ", boardAvailable=" + boardAvailable + "]";
	}
	
	
	
}
