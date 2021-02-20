package pe.gob.mef.security.model;

public class LoginUser {

    private String username;
    private String password;
    private int areCArea;
    
    

    public int getAreCArea() {
		return areCArea;
	}

	public void setAreCArea(int areCArea) {
		this.areCArea = areCArea;
	}

	public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
