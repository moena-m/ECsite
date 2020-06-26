//画面からJavaプログラムに送るデータを管理するクラス
//HTMLからPOSTメソッドで送信された値(=formタグの中身)。
package jp.co.internous.ecsite.model.form;

import java.io.Serializable;

//Serializable　インターフェイスを実装するLoginFormの作成
public class LoginForm implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String userName;
	private String password;
	
	public String getUserName() {
		return userName;
	}
	
	public void setUserName(String userName) {
		this.userName =userName;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
}
