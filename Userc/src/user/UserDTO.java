package user;

public class UserDTO {
	String userID;
	String userPassword;
	String userName;
	int userAge;
	String userGender;
	String userEmail;
	String userProfile;
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public void setUserAge(int userAge) {
		this.userAge = userAge;
	}
	public void setUserGender(String userGander) {
		this.userGender = userGander;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public void setUserProfile(String userProfile) {
		this.userProfile = userProfile;
	}
	public String getUserID() {
		return userID;
	}
	public String getUserPassword() {
		return userPassword;
	}
	public String getUserName() {
		return userName;
	}
	public int getUserAge() {
		return userAge;
	}
	public String getUserGender() {
		return userGender;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public String getUserProfile() {
		return userProfile;
	}
	@Override
	public String toString() {
		return "UserDTO [userID=" + userID + ", userPassword=" + userPassword + ", userName=" + userName + ", userAge="
				+ userAge + ", userGander=" + userGender + ", userEmail=" + userEmail + ", userProfile=" + userProfile
				+ "]";
	}

}
