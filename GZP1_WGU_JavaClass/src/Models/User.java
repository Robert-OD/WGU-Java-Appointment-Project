package Models;

public class User {
    private int userId;
    private String userName;
    private String password;

    public User(int userId, String userName, String password) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
    }

    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object object){
        if (object instanceof User){
            User comparedUser = (User)object;
            if (this.userId == comparedUser.getUserId()){
                if (this.userName.equals(comparedUser.getUserName())){
                    if (this.password.equals(comparedUser.getPassword())){
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
