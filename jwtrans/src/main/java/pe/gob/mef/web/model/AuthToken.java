package pe.gob.mef.web.model;

public class AuthToken {

    private String token;
    private Object user;

    public AuthToken(){

    }

    public AuthToken(String token, Object user){
        this.token = token;
        this.user = user;
    }

    public AuthToken(String token){
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

	public Object getUser() {
		return user;
	}

	public void setUser(Object user) {
		this.user = user;
	}

  

}
