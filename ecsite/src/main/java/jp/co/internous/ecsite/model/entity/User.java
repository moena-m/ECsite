package jp.co.internous.ecsite.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


//@Entityアノテーションを付与すると、Springの機能により 当該クラスはEntityとして振る舞う
@Entity
//@Tableアノテーションは、DBにある「どのテーブルの実体 なのか」を指定
@Table(name="user")
public class User {

	@Id //　←@Id  プライマリキーであることを指定
	@Column(name="id") //　←@Column  テーブルのどのカラムとマッピングするかを指定
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	//　↑　@GeneratedValue  IDフィールドの振る舞い方を指定
	//今回はAuto_incrementとして振る舞う
	private long id;
	
	@Column(name="user_name")
	private String userName;
	
	@Column(name="password")
	private String password;
	
	@Column(name="full_name")
	private String fullName;
	
	@Column(name="is_admin")
	private int isAdmin;
	
//各フィールドのGetter/Setter 作成
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id =id;
	}
	
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
		
	public String getFullName() {
		return fullName;
	}
		
	public void setFullName(String fullName) {
		this.fullName =fullName;
	}
	
	public int getIsAdmin() {
		return isAdmin;
	}
	
	public void setIsAdmin(int isAdmin) {
		this.isAdmin = isAdmin;
	}
	
}
